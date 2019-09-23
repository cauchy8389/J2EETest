package testspringcloud.esclient.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import testspringcloud.esclient.entity.EsPage;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * @Author: zhy
 * @Description: 搜索引擎师范Util类
 * @Date 2019-7-11
 */
@Component
@ConditionalOnBean(name="restHighLevelClient")
public class ElasticsearchUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtil.class);

    //过度用，未来也许没有type，目前要求一个index一个type，所以不需要设定，默认为es_type
    private static String es_type = "es_type";

//    private TransportClient transportClient;

    @Autowired
    @Qualifier(value="restHighLevelClient")
    private RestHighLevelClient restClient;

    private static RestHighLevelClient client;

    /**
     * @PostContruct是spring框架的注解 spring容器初始化的时候执行该方法
     */
    @PostConstruct
    public void init() {
        client = this.restClient;
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public static boolean createIndex(String index) {
        if (!isIndexExist(index)) {
            LOGGER.info("Index is not exits!");
        }

        CreateIndexRequest cir = new CreateIndexRequest(index);
        //CreateIndexRequestBuilder cirb = new CreateIndexRequestBuilder();
        CreateIndexResponse indexresponse = null;
        try {
            indexresponse = client.indices().create(cir, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // prepareCreate(index).execute().actionGet();
        LOGGER.info("执行建立成功？" + indexresponse.isAcknowledged());
        return indexresponse.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index) {
        if (!isIndexExist(index)) {
            LOGGER.info("Index is not exits!");
        }
        DeleteIndexRequest dir = new DeleteIndexRequest(index);
        DeleteIndexResponse dResponse = null;
        try {
            dResponse = client.indices().delete(dir,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //client.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()) {
            LOGGER.info("delete index " + index + "  successfully!");
        } else {
            LOGGER.info("Fail to delete index " + index);
        }
        return dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index) {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        boolean inExistsResponse = false;
        try {
            inExistsResponse = client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            //return inExistsResponse;
        }

        return inExistsResponse;
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param id         数据ID
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String id) {
        IndexRequest request = new IndexRequest(index);
        request.id(id).type(es_type).source(jsonObject, XContentType.JSON);

        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return "-1";
        }
        LOGGER.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 数据添加
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @return
     */
    public static String addData(JSONObject jsonObject, String index) {
        return addData(jsonObject, index, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    public static <T> List<String> indexList(Map<String,T> map,String index){
        BulkRequest bulkRequest = new BulkRequest();
        for(Map.Entry<String,T> entry:map.entrySet()){
            IndexRequest request = new IndexRequest(index);
            request.id(entry.getKey()).type(es_type).source(JSON.toJSONString(entry.getValue()),XContentType.JSON);
            bulkRequest.add(request);
        }
        return bulkExecute(bulkRequest,index);
    }

    public static List<String> bulkExecute(BulkRequest bulkRequest, String index)  {
        BulkResponse bulkResponse ;
        List<String> failIdList = new ArrayList<>();
        try {
            bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            for(BulkItemResponse itemResponse:bulkResponse.getItems()){
                if(itemResponse.isFailed()){
                    failIdList.add(itemResponse.getId());
                    LOGGER.error("bulk fail,id-{},index-{},failMessge-{}",itemResponse.getId(),itemResponse.getIndex(),itemResponse.getFailureMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.error("bulk fail all"+index,e);
            return null;
        }
        return failIdList;
    }

    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param id    数据ID
     */
    public static void deleteDataById(String index, String id) {
        DeleteRequest request = new DeleteRequest(index);
        request.id(id).type(es_type);
        try {
            DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);

            LOGGER.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param id         数据ID
     * @return
     */
    public static void updateDataById(JSONObject jsonObject, String index, String id) {

        UpdateRequest updateRequest = new UpdateRequest();

        updateRequest.index(index).id(id).type(es_type).doc(jsonObject);

        try {
            client.update(updateRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public static Map<String, Object> searchDataById(String index, String id, String fields) {

        GetRequest getRequest = new GetRequest(index).id(id).type(es_type);

        if(StringUtils.isNotEmpty(fields)) {
            getRequest.fetchSourceContext(new FetchSourceContext(true, fields.split(","),null));

            //storedFields(fields.split(","));
        }

        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();

            return new HashMap<String, Object>();
        }

        return getResponse.getSource();
    }


    /**
     * 使用分词查询,并分页
     *
     * @param index          索引名称
     * @param startPage      当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public static EsPage searchDataPage(String index, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            searchSourceBuilder.fetchSource(fields.split(","), null);
        }

//排序字段
        if (StringUtils.isNotEmpty(sortField)) {
            searchSourceBuilder.sort(sortField, SortOrder.DESC);
        }

// 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            //highlightBuilder.preTags("<span style='color:red' >");//设置前缀
            //highlightBuilder.postTags("</span>");//设置后缀

            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchSourceBuilder.highlighter(highlightBuilder);
        }

//searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(query);

        // 分页应用
        searchSourceBuilder.from(startPage).size(pageSize);

        // 设置是否按查询匹配度排序
        searchSourceBuilder.explain(true);

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        LOGGER.info("\n{}", searchSourceBuilder);

        // 执行搜索,返回搜索响应信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        searchRequest.searchType(SearchType.QUERY_THEN_FETCH);
        searchRequest.indices(index);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //= searchSourceBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        LOGGER.debug("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
// 解析对象
            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);

            return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
        }

        return null;

    }


    /**
     * 使用分词查询
     *
     * @param index          索引名称
     * @param query          查询条件
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public static List<Map<String, Object>> searchListData(
            String index, QueryBuilder query, Integer size,
            String fields, String sortField, String highlightField) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
//        if (StringUtils.isNotEmpty(type)) {
//            searchSourceBuilder.setTypes(type.split(","));
//        }

        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        searchSourceBuilder.query(query);

        if (StringUtils.isNotEmpty(fields)) {
            searchSourceBuilder.fetchSource(fields.split(","), null);
        }
        searchSourceBuilder.fetchSource(true);

        if (StringUtils.isNotEmpty(sortField)) {
            searchSourceBuilder.sort(sortField, SortOrder.DESC);
        }

        if (size != null && size > 0) {
            searchSourceBuilder.size(size);
        }

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        LOGGER.info("\n{}", searchSourceBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        searchRequest.searchType(SearchType.QUERY_THEN_FETCH);
        searchRequest.indices(index);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            return setSearchResponse(searchResponse, highlightField);
        }
        return null;

    }


    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
        StringBuffer stringBuffer = new StringBuffer();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());

            if (StringUtils.isNotEmpty(highlightField)) {

                System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
//遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }

        return sourceList;
    }

}
package common.sensitivewordsfilter.algorithm.dfa;

import common.sensitivewordsfilter.algorithm.AbstractFilterExecutor;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * DFA 脱敏算法实现支持类
 * 
 * @author zhy
 */
public final class DfaFilterExecutor extends AbstractFilterExecutor<Map<Character,String>> {

	private static class SingleFactory {
		private static final DfaFilterExecutor INSTANCE = new DfaFilterExecutor();
	}

	public static final DfaFilterExecutor getInstance() {
		return SingleFactory.INSTANCE;
	}
	
	public DfaFilterExecutor() {
		super("DFA 脱敏算法实现支持类");
	}
	
	@Override
	protected void put(Set<String> sensitiveWordSet) {

		//初始化敏感词容器，减少扩容操作
		//sensitiveWordMap = new HashMap(sensitiveWordSet.size());
		String key;
		Map nowMap;
		Map<String, String> newWorMap;
		//迭代sensitiveWordSet
		Iterator<String> iterator = sensitiveWordSet.iterator();
		while (iterator.hasNext()) {
			//关键字
			key = iterator.next();
			nowMap = cacheNodes;
			for (int i = 0; i < key.length(); i++) {
				//转换成char型
				char keyChar = key.charAt(i);
				//库中获取关键字
				Object wordMap = nowMap.get(keyChar);
				//如果存在该key，直接赋值，用于下一个循环获取
				if (wordMap != null) {
					nowMap = (Map) wordMap;
				} else {
					//不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<>();
					//不是最后一个
					newWorMap.put("isEnd", "0");
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}

				if (i == key.length() - 1) {
					//最后一个
					nowMap.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 *
	 * @param content
	 * @param beginIndex
	 * @param partMatch
	 * @return 如果存在，则返回敏感词字符的长度，不存在返回0
	 */
	private int checkSensitiveWord(String content, int beginIndex, boolean partMatch) {
		//敏感词结束标识位：用于敏感词只有1位的情况
		boolean flag = false;
		//匹配标识数默认为0
		int matchFlag = 0;
		char word;
		Map nowMap = cacheNodes;
		for (int i = beginIndex; i < content.length(); i++) {
			word = content.charAt(i);
			//获取指定key
			nowMap = (Map) nowMap.get(word);
			if (nowMap != null) {//存在，则判断是否为最后一个
				//找到相应key，匹配标识+1
				matchFlag++;
				//如果为最后一个匹配规则,结束循环，返回匹配标识数
				if ("1".equals(nowMap.get("isEnd"))) {
					//结束标志位为true
					flag = true;
					//最小规则，直接返回,最大规则还需继续查找
					if (partMatch) {
						break;
					}
				}
			} else {//不存在，直接返回
				break;
			}
		}
		if (matchFlag < 2 || !flag) {//长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
	}

	/**
	 * 判断文字是否包含敏感字符
	 *
	 * @param txt       文字
	 * @param partMatch 匹配规则 true：最小匹配规则，false：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 */
	public boolean checkExistence(String txt, boolean partMatch) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {
			int matchFlag = checkSensitiveWord(txt, i, partMatch); //判断是否包含敏感字符
			if (matchFlag > 0) {    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 判断文字是否包含敏感字符
	 *
	 * @param txt 文字
	 * @return 若包含返回true，否则返回false
	 */
	public boolean checkExistence(String txt) {
		return checkExistence(txt, false);
	}

	/**
	 * 获取文字中的敏感词
	 *
	 * @param txt       文字
	 * @param partMatch 匹配规则 true：最小匹配规则，false：最大匹配规则
	 * @return
	 */
	public Set<String> getSensitiveWords(String txt, boolean partMatch) {
		Set<String> sensitiveWordList = new HashSet<>();

		for (int i = 0; i < txt.length(); i++) {
			//判断是否包含敏感字符
			int length = checkSensitiveWord(txt, i, partMatch);
			if (length > 0) {//存在,加入list中
				sensitiveWordList.add(txt.substring(i, i + length));
				i = i + length - 1;//减1的原因，是因为for会自增
			}
		}

		return sensitiveWordList;
	}
	
	@Override
	protected Map<Character,String> getCacheNodes() {
		return Maps.newHashMap();
	}
	
	public static void main(String[] args) {
		DfaFilterExecutor.getInstance().init();
		Set<String> set = new HashSet<String>();
		set.add("中国人");
		set.add("中国男人");
		set.add("中国人民");
		set.add("人民");
		set.add("中间");
		set.add("女人");

		set.add("一举");
		set.add("一举成名");
		set.add("一举成名走四方");
		set.add("成名");
		set.add("走四方");
		DfaFilterExecutor.getInstance().put(set);
		
		String content = "我们中国人都是好人，在他们中间有男人和女人。中国男人很惨，中国人民长期被压迫。";
		System.out.println(DfaFilterExecutor.getInstance().checkExistence(content,true));
		System.out.println(DfaFilterExecutor.getInstance().checkExistence(content));
		System.out.println(DfaFilterExecutor.getInstance().getSensitiveWords(content,true));
		System.out.println(DfaFilterExecutor.getInstance().getSensitiveWords(content,true));
		System.out.println(DfaFilterExecutor.getInstance().filter(content, '*',false));
		System.out.println(DfaFilterExecutor.getInstance().highlight(content,false));
		System.out.println(DfaFilterExecutor.getInstance().highlight(content,false));
		
		content = "一举成名走四方的是什么";
		System.out.println(DfaFilterExecutor.getInstance().getSensitiveWords(content,false));
		System.out.println(DfaFilterExecutor.getInstance().getSensitiveWords(content,false));
		System.out.println(DfaFilterExecutor.getInstance().filter(content, '*',false));
		System.out.println(DfaFilterExecutor.getInstance().highlight(content,false));
		System.out.println(DfaFilterExecutor.getInstance().highlight(content,false));
		
	}
}

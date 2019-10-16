package common.sensitivewordsfilter.algorithm;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import common.sensitivewordsfilter.AbstractSensitiveWordsFilter;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 抽象敏感词过滤执行器，提供过滤的缓存初始化、刷新、销毁基础封装
 * 
 * @author zhy
 */
@Log4j2
public abstract class AbstractFilterExecutor<T> extends AbstractSensitiveWordsFilter {
	private static final String HTML_HIGHLIGHT = "<font color='red'>%s</font>";

	protected T cacheNodes;
	@Getter
	private String listenerName;

	public AbstractFilterExecutor(String listenerName) {
		super();
		this.setExecutor(this);
		this.initAbstractSensitiveWordsFilter(this);
		this.listenerName = listenerName;
	}
	
	/**
	 * 分词数据对象模型
	 * @author zhy
	 */
	protected abstract T getCacheNodes();
	
	@Override
	public void init() throws RuntimeException {
		try {
			if (cacheNodes == null) {
				log.debug("{}: 初始化数据", getListenerName());
				refresh();
			} else {
				log.debug("{}: 已初始化数据，无需重复执行", getListenerName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}

	@Override
	public void refresh() throws RuntimeException {
		log.debug("{}: 刷新数据", getListenerName());
		
		try {
			cacheNodes = this.getCacheNodes();
			/*
			List<SensitiveWords> list = JvmWordsCache.getInstance().get();
			for (SensitiveWords word : list) {
				put(word);
			}
			*/
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void destroy() throws RuntimeException {
		log.debug("{}: 销毁数据", getListenerName());
		
		try {
			cacheNodes = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//	@Override
//	public abstract boolean checkExistence(String content);
//
//	@Override
//	public abstract boolean checkExistence(String content, boolean partMatch);
//
//	@Override
//	public abstract Set<String> getSensitiveWords(String content,boolean partMatch);

	@Override
	public String highlight(String content,boolean partMatch) {
		Set<String> words = this.getSensitiveWords(content,partMatch);

		Iterator<String> iter = words.iterator();
		while (iter.hasNext()) {
			String word = iter.next();
			content = content.replaceAll(word, String.format(HTML_HIGHLIGHT, word));
		}

		return content;
	}

	@Override
	public String filter(String content, char replaceChar, boolean partMatch) {
		Set<String> words = this.getSensitiveWords(content, partMatch);

		Iterator<String> iter = words.iterator();
		while (iter.hasNext()) {
			String word = iter.next();
			content = content.replaceAll(word, Strings.repeat(String.valueOf(replaceChar), word.length()));
		}

		return content;
	}
}

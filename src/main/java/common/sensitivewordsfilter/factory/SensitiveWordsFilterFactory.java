package common.sensitivewordsfilter.factory;

import common.sensitivewordsfilter.AbstractSensitiveWordsFilter;

/**
 * 敏感词库过滤实现工厂
 * @author zhy
 */
public final class SensitiveWordsFilterFactory {

	public static AbstractSensitiveWordsFilter create(FilterType filterType) throws Exception {
		
		return (AbstractSensitiveWordsFilter) Class.forName(filterType.getClazz().getName()).newInstance();
	}
}

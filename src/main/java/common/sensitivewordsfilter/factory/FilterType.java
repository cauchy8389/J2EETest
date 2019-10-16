package common.sensitivewordsfilter.factory;

import common.sensitivewordsfilter.AbstractSensitiveWordsFilter;
import common.sensitivewordsfilter.algorithm.dfa.DfaFilterExecutor;


/**
 * 敏感词算法实现类型
 * @author zhy
 */
public enum FilterType {

	DFA("dfa算法", DfaFilterExecutor.class);
	
	private String desc;
	private Class<? extends AbstractSensitiveWordsFilter> clazz;
	
	FilterType(String desc, Class<? extends AbstractSensitiveWordsFilter> clazz) {
		this.desc = desc;
		this.clazz = clazz;
	}
	
	public String getDesc() {
		return desc;
	}

	public Class<? extends AbstractSensitiveWordsFilter> getClazz() {
		return clazz;
	}
}

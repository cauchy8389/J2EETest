package common.sensitivewordsfilter.algorithm.dfa2;

import cn.hutool.core.collection.CollectionUtil;
import common.sensitivewordsfilter.algorithm.AbstractFilterExecutor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DFA 脱敏算法实现支持类
 * 
 * @author zhy
 * @Description 明显速度不行
 */
public final class Dfa2FilterExecutor extends AbstractFilterExecutor<WordTree> {

	private static class SingleFactory {
		private static final Dfa2FilterExecutor INSTANCE = new Dfa2FilterExecutor();
	}

	public static final Dfa2FilterExecutor getInstance() {
		return SingleFactory.INSTANCE;
	}
	
	public Dfa2FilterExecutor() {
		super("DFA 脱敏算法实现支持类");
	}
	
	@Override
	protected void put(Set<String> sensitiveWordSet) {

		cacheNodes.addWords(sensitiveWordSet);
	}

	/**
	 * 判断文字是否包含敏感字符
	 *
	 * @param txt       文字
	 * @param partMatch 匹配规则 true：最小匹配规则，false：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 */
	public boolean checkExistence(String txt, boolean partMatch) {
		List<String> matchAll = cacheNodes.matchAll(txt, 1, false, partMatch); //判断是否包含敏感字符
		return CollectionUtil.isNotEmpty(matchAll);
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
		List<String> matchAll = cacheNodes.matchAll(txt, -1, false, partMatch); //判断是否包含敏感字符
		Set<String> sensitiveWordList = new HashSet<>(matchAll);

		return sensitiveWordList;
	}
	
	@Override
	protected WordTree getCacheNodes() {
		return new WordTree();
	}
	
	public static void main(String[] args) {
		Dfa2FilterExecutor.getInstance().init();
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
		Dfa2FilterExecutor.getInstance().put(set);
		
		String content = "我们中国人都是好人，在他们中间有男人和女人。中国男人很惨，中国人民长期被压迫。";
		System.out.println(Dfa2FilterExecutor.getInstance().checkExistence(content,true));
		System.out.println(Dfa2FilterExecutor.getInstance().checkExistence(content));
		System.out.println(Dfa2FilterExecutor.getInstance().getSensitiveWords(content,true));
		System.out.println(Dfa2FilterExecutor.getInstance().getSensitiveWords(content,true));
		System.out.println(Dfa2FilterExecutor.getInstance().filter(content, '*',false));
		System.out.println(Dfa2FilterExecutor.getInstance().highlight(content,false));
		System.out.println(Dfa2FilterExecutor.getInstance().highlight(content,false));
		
		content = "一举成名走四方的是什么";
		System.out.println(Dfa2FilterExecutor.getInstance().getSensitiveWords(content,false));
		System.out.println(Dfa2FilterExecutor.getInstance().getSensitiveWords(content,false));
		System.out.println(Dfa2FilterExecutor.getInstance().filter(content, '*',false));
		System.out.println(Dfa2FilterExecutor.getInstance().highlight(content,false));
		System.out.println(Dfa2FilterExecutor.getInstance().highlight(content,false));
		
	}
}

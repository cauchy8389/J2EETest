package common.sensitivewordsfilter.cache;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * Jvm 敏感词缓存
 * @author zhy
 */
@Log4j2
public final class JvmWordsCache extends WordsCache {

	private WordsCache wordsCache;
	public static List<String> cache = null;

	private static class SingleFactory {
		
		private static final JvmWordsCache INSTANCE = new JvmWordsCache();
	}

	public static final JvmWordsCache getInstance() {
		
		return SingleFactory.INSTANCE;
	}
	
	private JvmWordsCache() {
		super("JVM 脱敏词库缓存");
	}

	@Override
	public void setDataSource(Object dataSource) {
		super.setDataSource(dataSource);

		if (dataSource instanceof WordsCache) {
			this.wordsCache = (WordsCache) dataSource;
		} else {
			throw new IllegalArgumentException("未知数据源类型" + getListenerName());
		}
	}

	@Override
	public boolean init() throws RuntimeException {
		super.init();

		if (cache == null || cache.isEmpty()) {
			log.debug("{}: jvm cache 首次初始化", getListenerName());
			cache = Lists.newArrayList();

			return refresh();
		} else {
			log.debug("{}: jvm cache 已被初始化，无需重复执行", getListenerName());
		}

		return true;
	}

	public boolean put(String words) throws Exception {
		super.put(words);

		cache.add(words);
		return true;
	}

	public boolean put(Collection<String> words) throws Exception {
		super.put(words);

		cache.addAll(words);
		return true;
	}

	public List<String> get() {
		return cache;
	}

	public boolean update(String word) throws Exception {
		super.update(word);

		if (remove(word)) {
			return put(word);
		}

		return false;
	}

	public boolean remove(final String word) throws Exception {
		super.remove(word);

		if (word == null) {
			return false;
		}

		return Iterators.removeIf(cache.iterator(), new Predicate<String>() {
			@Override
			public boolean apply(String item) {
				if (StringUtils.equals(word, item)) {
					return true;
				}
				return false;
			}
		});
	}

	public boolean refresh() throws RuntimeException {
		super.refresh();

		log.debug("{}: 从新刷新初始化JVM缓存", getListenerName());
		try {
			cache.clear();
			if(wordsCache != null) {
				List<String> words = wordsCache.get();
				if (!CollectionUtils.isEmpty(words)) {
					cache.addAll(words);
				}
			}
			log.debug("{}: JVM缓存敏感词数量：{}", getListenerName(), cache.size());
		} catch (Exception e) {
			throw e;
		}

		return true;
	}
}
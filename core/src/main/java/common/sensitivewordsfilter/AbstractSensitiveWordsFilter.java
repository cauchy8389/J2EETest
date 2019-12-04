package common.sensitivewordsfilter;

import com.google.common.collect.Sets;
import com.google.common.io.Files;
import common.sensitivewordsfilter.cache.JvmWordsCache;
import common.sensitivewordsfilter.cache.WordsCache;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 敏感词库抽象接口实现
 * 
 * @author zhy
 */
@Log4j2
public abstract class AbstractSensitiveWordsFilter{

	private volatile static boolean HAS_INIT_WORDS_CACHE = false;
	
	public void initAll() throws RuntimeException {
		
		if (!HAS_INIT_WORDS_CACHE) {
			log.debug("初始化所有缓存");
			this.initCache();
			this.init();
		} else {
			log.debug("缓存已被初始化，无需重复执行！");
		}
	}
	
	public void refreshAll() throws Exception {

		log.debug("刷新所有缓存");
		JvmWordsCache.getInstance().refresh();
		this.refresh();
	}

	public abstract boolean checkExistence(String content, boolean partMatch);

	public abstract boolean checkExistence(String content);

	public abstract Set<String> getSensitiveWords(String content,boolean partMatch);

	public abstract String highlight(String content,boolean partMatch);

	public abstract String filter(String content, char replaceChar,boolean partMatch);


	public void init() throws RuntimeException {
	}

	public void initCache() throws RuntimeException {
		WordsCache jvmWordsCache = JvmWordsCache.getInstance();
		jvmWordsCache.init();
		for (SensitiveWordsType type : SensitiveWordsType.values()) {

			try {
				int i = 0;
				Set<String> words = Sets.newHashSet();

				//File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "sensitivewords" + File.separator + type.getName() + "词库.txt");
				ClassPathResource classPathResource =
						new ClassPathResource("sensitivewords" + File.separator + type.getName() + "词库.txt");
				if(!classPathResource.exists())
					continue;

				File file = classPathResource.getFile();

				List<String> wordlists = Files.readLines(file, StandardCharsets.UTF_8);

				for (String word : wordlists) {
					if(StringUtils.isBlank(word))
						continue;

					words.add(word);
					i++;
				}

				jvmWordsCache.put(words);

				System.out.println("循环单词: " + i + ", 插入数据：" + words.size());
				Thread.sleep(15);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	/**
	 * 添加分词
	 * @author zhy
	 * @throws RuntimeException
	 */
	protected abstract void put(Set<String> sensitiveWordSet);

	public void putAllCacheWords() {
		WordsCache jvmWordsCache = JvmWordsCache.getInstance();
		Set<String> set = new HashSet<>(jvmWordsCache.get());
		this.put(set);
	}

	public abstract void refresh();

	public abstract void destroy();
}

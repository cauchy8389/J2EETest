package common.sensitivewordsfilter.cache;

import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.List;

/**
 * abstract word cache interface method
 * @author zhy
 */
@Log4j2
public abstract class WordsCache{

    private String listenerName;

    public WordsCache(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getListenerName() {
        return listenerName;
    }


    public void setDataSource(Object dataSource) {
        log.debug("{}: bindDataSource: {}", listenerName, dataSource);
    }


    public boolean init() throws RuntimeException {
        log.debug("{}: init word cache", listenerName);

        return true;
    }


    public boolean put(String words) throws Exception {
        log.debug("{}: put word: {}", listenerName, words);

        return true;
    }


    public boolean put(Collection<String> words) throws Exception {
        log.debug("{}: put word list: {}", listenerName, words);

        return true;
    }


    public List<String> get() {
        log.debug("{}: get word list", listenerName);

        return null;
    }


    public boolean update(String word) throws Exception {
        log.debug("{}: update word: {}", listenerName, word);

        return true;
    }


    public boolean remove(String words) throws Exception {
        log.debug("{}: remove word: {}", listenerName, words);

        return false;
    }


    public boolean refresh() throws RuntimeException {
        log.debug("{}: refresh word cache", listenerName);

        return false;
    }
}

package sessionstore.keyvaluestore;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import play.Play;
import sessionstore.NotDefiniedCacheException;

import java.util.HashMap;
import java.util.Map;

public class EhCacheStore implements KeyValueStore {

	private final CacheManager cacheManager;

	public EhCacheStore() {
		cacheManager = CacheManager.create(Play.application().configuration().getString("sessionstore.ehcache.cachemanager.configurationurl"));
	}

	@Override
	public void set(String cacheName, String key, String value) throws Exception {

		Cache cache = cacheManager.getCache(cacheName);
		if(cache == null) {
			throw new NotDefiniedCacheException();
		}
		try {
			cache.put(new Element(key, value));
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public String get(String cacheName, String key) throws Exception {

		Cache cache = cacheManager.getCache(cacheName);
		if(cache == null) {
			throw new NotDefiniedCacheException();
		}
		try {
			Element element = cache.get(key);
			if(element != null) {
				return (String) element.getObjectValue();
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public void delete(String cacheName, String key) throws Exception {

		Cache cache = cacheManager.getCache(cacheName);
		if(cache == null) {
			throw new NotDefiniedCacheException();
		}
		try {
			cache.remove(key);
		} catch(Exception e) {
		}

	}

	public Map<Object, Object> listAllKeysAndValue(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		Map<Object, Object> map = new HashMap<Object, Object>();
		for(Object key : cache.getKeys()) {
			Element element = cache.get(key);
			map.put(element.getObjectKey(), element.getObjectValue());
		}
		return map;
	}

	// isConnected is not necessary but helps to keep the logic behind the RIAK version
	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void close() {
		if(cacheManager != null) {
			cacheManager.shutdown();
		}
	}
}

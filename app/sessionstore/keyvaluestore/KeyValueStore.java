package sessionstore.keyvaluestore;

import java.util.Map;

public interface KeyValueStore {

	public void set(String cache, String key, String value) throws Exception;

	public String get(String cache, String key) throws Exception;

	public void delete(String cache, String key) throws Exception;

	public Map<Object, Object> listAllKeysAndValue(String cache) throws Exception;

	public boolean isConnected();

	public void close();
}

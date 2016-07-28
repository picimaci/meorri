package sessionstore;

import models.enums.SessionName;
import play.Logger;
import sessionstore.keyvaluestore.EhCacheStore;
import sessionstore.keyvaluestore.KeyValueStore;
import sessionstore.utils.Constants;
import sessionstore.utils.Utils;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.util.Map;

public class SessionStore implements Closeable {
	private final Logger.ALogger logger = Logger.of(Constants.LOGGER_SESSIONSTORE);
	private static final String LOG_TAG = "<<sessionstore.SessionStore>>";

	private static SessionStore instance = null;

	private final KeyValueStore store;

	private SessionStore() {
		store = new EhCacheStore();
	}

	public static SessionStore getInstance() {
		if(!Utils.isSessionStoreEnabled()) {
			return null;
		}
		if(instance == null) {
			instance = new SessionStore();
		}
		return instance;
	};

	public boolean isConnected() {
		if(store == null) {
			return false;
		}
		return store.isConnected();
	}

	@Override
	public void close() {
		if(store != null) {
			store.close();
		}
	}

	public boolean set(String aggr, String key, String value) throws Exception {
		try {
			store.set(aggr, key, value);
			return true;
		} catch(Exception e) {
			logger.error(LOG_TAG + " - Failed to store key(" + key, value + ")", e);
			throw e;
		}

	}

	public String get(String aggr, String key) throws Exception {
		try {
			return store.get(aggr, key);
		} catch(Exception e) {
			logger.error(LOG_TAG + " - Error getting key(" + key + ")", e);
			throw e;
		}
	}

	public boolean delete(String aggr, String key) throws Exception {
		try {
			store.delete(aggr, key);
			return true;
		} catch(Exception e) {
			logger.error(LOG_TAG + " - Error deleting key(" + key + ")", e);
			throw e;
		}
	}

	@Nullable
	public String getSessionId(String loginName) throws Exception {
		try {
			String sessionId = store.get(SessionName.USERS.toString(), loginName);
			return sessionId;
		} catch(Exception e) {
			logger.error(LOG_TAG + " - Error getting sessionId for login name " + loginName + ".", e);
			throw e;
		}
	}

	public void setSessionId(String loginName, String sessionId) throws Exception {
		try {
			store.set(SessionName.USERS.toString(), loginName, sessionId);
		} catch(Exception e) {
			logger.error(LOG_TAG + " - Error setting sessionId for login name " + loginName + ".", e);
			throw e;
		}
	}

	public void deleteSessionIdForUser(String loginName) throws Exception {
		try {
			store.delete(SessionName.USERS.toString(), loginName);
		} catch(Exception e) {
			logger.error(LOG_TAG + " - Error setting sessionId for login name " + loginName + ".", e);
			throw e;
		}
	}

	public Map<Object, Object> listAllKeysAndValue(String aggr) throws Exception {
		try {
			return store.listAllKeysAndValue(aggr);
		} catch(Exception e) {

			throw e;
		}
	}
}

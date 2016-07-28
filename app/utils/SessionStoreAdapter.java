package utils;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.DTOs.core.SysUserDTO;
import controllers.sysuser.service.SysUserListSessionDTO;
import models.enums.SessionName;
import play.Logger;
import play.libs.Json;
import sessionstore.SessionStore;

import java.util.Map;

public class SessionStoreAdapter {

	private static final String LOG_TAG = "<<Applocation.SessionStore>>";

	private static SessionStoreAdapter instance;
	private final SessionStore sessionStore;

	private SessionStoreAdapter() {
		sessionStore = SessionStore.getInstance();
	}

	public static SessionStoreAdapter getInstance() {
		if(instance == null) {
			instance = new SessionStoreAdapter();
		}
		return instance;
	}

	public boolean isConnected() {
		if(sessionStore == null) {
			return false;
		}
		return sessionStore.isConnected();
	}

	public String getSessionDataAsString(String aggr, String key) {
		try {
			return sessionStore.get(aggr, key);
		} catch(Exception e) {
			Logger.error(LOG_TAG + "Error getting cachedata (" + aggr + "," + key + ") as String", e);
			return null;
		}
	}

	public boolean setSysUserListSession(String key, SysUserListSessionDTO sysUserListSessionDTO) {
		try {
			sessionStore.set(SessionName.SYSUSERS.toString(), key, Json.toJson(sysUserListSessionDTO).toString());
			return true;
		} catch(Exception e) {
			Logger.error(LOG_TAG + "Error setting sysusercache (" + key + ")", e);
			return false;
		}
	}

	public SysUserListSessionDTO getSysUserListDTO(String key) {
		try {
			String sysUserListDTOString = sessionStore.get(SessionName.SYSUSERS.toString(), key);
			if(sysUserListDTOString != null) {
				JsonNode userJson = Json.parse(sysUserListDTOString);
				SysUserListSessionDTO sysUserListSessionDTO = Json.fromJson(userJson, SysUserListSessionDTO.class);
				return sysUserListSessionDTO;
			}
		} catch(Exception e) {
			Logger.error(LOG_TAG + "Error getting sysusercache (" + key + ") as Json", e);
		}
		return new SysUserListSessionDTO();
	}

	public SysUserDTO getUserSession(String sessionId) throws Exception {
		String sysUserDTOString = sessionStore.get(SessionName.USERS.toString(), sessionId);
		if(sysUserDTOString == null) {
			return null;
		}
		JsonNode sysUserDTOJson = Json.parse(sysUserDTOString);
		SysUserDTO sysUserDTO = Json.fromJson(sysUserDTOJson, SysUserDTO.class);
		return sysUserDTO;
	}

	public void setUserSession(String sessionId, SysUserDTO sysUserDTO) throws Exception {
		sessionStore.set(SessionName.USERS.toString(), sessionId, Json.toJson(sysUserDTO).toString());
	}

	public void deleteUserSession(String sessionId) throws Exception {
		sessionStore.delete(SessionName.USERS.toString(), sessionId);
	}

	public String getSessionId(String loginName) throws Exception {
		return sessionStore.getSessionId(loginName);
	}

	public void setSessionId(String loginName, String sessionId) throws Exception {
		sessionStore.setSessionId(loginName, sessionId);
	}

	public void deleteSessionIdForUser(String loginName) throws Exception {
		sessionStore.deleteSessionIdForUser(loginName);
	}

	// just for debugging
	public Map<Object, Object> listAllKeysAndValue(String cacheName) throws Exception {
		return sessionStore.listAllKeysAndValue(cacheName);
	}

}

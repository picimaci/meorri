package utils;

import controllers.DTOs.core.SysUserDTO;
import daos.SysUserDAO;
import models.SysUser;
import play.Logger;
import play.mvc.Http;

import java.util.Optional;
import java.util.UUID;

public class UserSession {
    public static String getCookieSessionId(Http.Session session) {
        return session.get("sessionId");
    }

    /**
     * @deprecated Calling this causes a DB request. Use getCurrentUserDTO() method instead.
     */
    @Deprecated
    public static SysUser getCurrentUser(Http.Session session) {
        String sessionId = getCookieSessionId(session);
        SysUser sysUser;
        try {
            SysUserDTO sysUserDTO = getCurrentUserDTO(sessionId);
            Optional<SysUser> model = SysUserDAO.findById(sysUserDTO.id);
            if(!model.isPresent()) {
                Logger.error("No sysUser for this id: " + sysUserDTO.id);
                return null;
            }
            sysUser = model.get();
        } catch(Exception e) {
            Optional<SysUser> model = SysUserDAO.findBySessionId(sessionId);
            if(!model.isPresent()) {
                Logger.error("No sysUserDTO for this sessionId: " + sessionId, e);
                return null;
            }
            sysUser = model.get();
        }
        return sysUser;
    }

    public static SysUserDTO getCurrentUserDTO(Http.Session session) {
        return getCurrentUserDTO(getCookieSessionId(session));
    }

    public static SysUserDTO getCurrentUserDTO(String sessionId) {
        try {
            return SessionStoreAdapter.getInstance().getUserSession(sessionId);
        } catch(Exception e) {
            Logger.error("No sysUserDTO for this sessionId: " + sessionId + ". Getting user from DB and generating bean.", e);
            Optional<SysUser> model = SysUserDAO.findBySessionId(sessionId);
            if(!model.isPresent()) {
                Logger.error("No sysUserDTO for this sessionId: " + sessionId, e);
                return null;
            }
            SysUser sysUser = model.get();
            return SysUserDTO.createFromModel(sysUser);
        }
    }

    public static String obtainSessionId(SysUser user) {
        String sessionId;
        try {
            sessionId = SessionStoreAdapter.getInstance().getSessionId(user.email);
            if(sessionId == null) {
                sessionId = UUID.randomUUID().toString();
            }
        } catch(Exception e) {
            sessionId = UUID.randomUUID().toString();
        }
        try {
            SessionStoreAdapter.getInstance().setSessionId(user.email, sessionId);
        } catch(Exception e) {
            Logger.error("Cannot store sessionId for loginName", e);
        }
        return sessionId;
    }
}

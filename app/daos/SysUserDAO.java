package daos;

import controllers.DTOs.core.AbstractListFilterDTO;
import models.SysUser;
import play.Logger;
import play.db.jpa.JPA;
import utils.queryexecutor.QueryExecutor;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SysUserDAO extends BaseDAO{

    public static List<SysUser> findSysUsers(Map<String, Object> filters, AbstractListFilterDTO filterBean) {
        String selectHeader = "SELECT su FROM picimaci_sysuser su";
        return QueryExecutor.getResultList(selectHeader, filters, filterBean, SysUser.class);
    }

    public static Optional<SysUser> findById(long id) {
        String select = "SELECT su FROM picimaci_sysuser su"
                + " WHERE su.id = :id";
        TypedQuery<SysUser> query = JPA.em().createQuery(select, SysUser.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch(NoResultException e) {
            Logger.warn("Couldn't find sysuser with id: " + id, e);
            return Optional.empty();
        }
    }

    public static Optional<SysUser> deleteById(Long id) {
        return SysUserDAO.findById(id).map(sysuser -> {
            sysuser.deleted = true;
            sysuser.save();
            return Optional.of(sysuser);
        }).orElse(Optional.empty());
    }

    public static Optional<SysUser> activateById(Long id) {
        return SysUserDAO.findById(id).map(sysuser -> {
            sysuser.isActive = true;
            sysuser.save();
            return Optional.of(sysuser);
        }).orElse(Optional.empty());
    }

    public static Optional<SysUser> deactivateById(Long id) {
        return SysUserDAO.findById(id).map(sysuser -> {
            sysuser.isActive = false;
            sysuser.save();
            return Optional.of(sysuser);
        }).orElse(Optional.empty());
    }

    public static Long countSysUsers(Map<String, Object> filters, AbstractListFilterDTO filterBean) {
        String selectHeader = "SELECT count(su) FROM picimaci_sysuser su";
        return QueryExecutor.getSingleResult(selectHeader, filters, SysUser.class, Long.class);
    }

    public static Optional<SysUser> findByEmail(String email) {
        String select = "SELECT su FROM picimaci_sysuser su"
                + " WHERE su.email = :email";
        TypedQuery<SysUser> query = JPA.em().createQuery(select, SysUser.class);
        query.setParameter("email", email);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch(NoResultException e) {
            Logger.warn("Couldn't find sysuser with e-mail: " + email, e);
            return Optional.empty();
        }
    }

    public static Optional<SysUser> findByResetPasswordToken(String token) {
        String select = "SELECT su FROM picimaci_sysuser su"
                + " WHERE su.resetPasswordToken = :token";
        TypedQuery<SysUser> query = JPA.em().createQuery(select, SysUser.class);
        query.setParameter("token", token);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch(NoResultException e) {
            Logger.warn("Couldn't find sysuser with reset password token: " + token, e);
            return Optional.empty();
        }
    }

    public static Optional<SysUser> findBySessionId(String sessionId) {
        String select = "SELECT su FROM picimaci_sysuser su"
                + " WHERE su.sessionId = :sessionId";
        TypedQuery<SysUser> query = JPA.em().createQuery(select, SysUser.class);
        query.setParameter("sessionId", sessionId);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch(NoResultException e) {
            Logger.warn("Couldn't find sysuser with session id: " + sessionId, e);
            return Optional.empty();
        }
    }

    public static Optional<SysUser> findByUserCredentials(String email, String password) {
        String select = "SELECT su FROM picimaci_sysuser su"
                + " WHERE su.email = :email"
                + " AND su.password = :password";
        TypedQuery<SysUser> query = JPA.em().createQuery(select, SysUser.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch(NoResultException e) {
            Logger.warn("Couldn't find sysuser with user credentials: " + email + ", " + password, e);
            return Optional.empty();
        }
    }
}
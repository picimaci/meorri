package controllers.sysuser.service;

import controllers.core.ServiceError;
import daos.SysUserDAO;
import models.SysUser;
import play.libs.F;
import utils.AppConf;
import utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static controllers.core.ServiceError.SYSUSER_NOT_FOUND;

public class SysUserServiceCS {

    public static F.Either<ServiceError, SysUser> findByEmail(String email) {
        Optional<SysUser> model = SysUserDAO.findByEmail(email);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        SysUser sysUser = model.get();

        return F.Either.Right(sysUser);
    }

    public static F.Either<ServiceError, SysUser> findByResetPasswordToken(String token) {
        Optional<SysUser> model = SysUserDAO.findByResetPasswordToken(token);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        SysUser sysUser = model.get();

        return F.Either.Right(sysUser);
    }

    public static F.Either<ServiceError, SysUser> findBySessionId(String sessionId) {
        Optional<SysUser> model = SysUserDAO.findBySessionId(sessionId);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        SysUser sysUser = model.get();

        return F.Either.Right(sysUser);
    }

    public static F.Either<ServiceError, SysUser> findByUserCredentials(String email, String password) {
        Optional<SysUser> model = SysUserDAO.findByUserCredentials(email, StringUtils.getMD5hash(password));
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        SysUser sysUser = model.get();

        return F.Either.Right(sysUser);
    }

    public static List<String> getLanguages() {
        return Arrays.asList(AppConf.getConfigStringArray("play.i18n.langs"));
    }
}

package controllers.login;

import controllers.core.ServiceError;
import daos.SysUserDAO;
import exceptions.SendEmailException;
import models.SysUser;
import play.Logger;
import play.libs.F;
import utils.EmailUtils;
import utils.StringUtils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static controllers.core.ServiceError.EMAIL_NOT_SENT;
import static controllers.core.ServiceError.LOGIN_NO_USER_FOUND;

public class LoginCS {
    public static F.Either<ServiceError, SysUser> authenticate(LoginDTO loginDTO) {
        Optional<SysUser> model = SysUserDAO.findByUserCredentials(loginDTO.email, StringUtils.getMD5hash(loginDTO.password));

        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(LOGIN_NO_USER_FOUND));
        }
        SysUser sysUser = model.get();

        if (sysUser == null || !sysUser.webUser) {
            return F.Either.Left(new ServiceError(LOGIN_NO_USER_FOUND));
        }

        sysUser.lastLoginDate = new Date();
        sysUser.city = loginDTO.city;
        sysUser.country = loginDTO.country;
        sysUser.region = loginDTO.region;
        sysUser.save();

        return F.Either.Right(sysUser);
    }

    public static F.Either<ServiceError, SysUser> forgotPassword(LoginDTO loginDTO) {
        Optional<SysUser> model = SysUserDAO.findByEmail(loginDTO.email);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(LOGIN_NO_USER_FOUND));
        }

        SysUser sysUser = model.get();

        String token = LoginCS.generateResetPasswordToken();
        sysUser.resetPasswordToken = token;
        sysUser.save();

        try {
            EmailUtils.sendResetPasswordEmail(sysUser.email, token);
        } catch (SendEmailException e) {
            Logger.warn("Counldn't send e-mail to: " + sysUser.email, e);
            return F.Either.Left(new ServiceError(EMAIL_NOT_SENT));
        }

        return F.Either.Right(sysUser);
    }

    public static String generateResetPasswordToken() {
        String resetPasswordToken = UUID.randomUUID().toString();
        return resetPasswordToken;
    }
}

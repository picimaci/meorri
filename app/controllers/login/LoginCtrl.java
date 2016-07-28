package controllers.login;

import controllers.BaseCtrl;
import controllers.DTOs.core.SysUserDTO;
import controllers.core.ServiceError;
import controllers.routes;
import controllers.sysuser.service.SysUserServiceCtrl;
import daos.SysUserDAO;
import models.SysUser;
import play.Logger;
import play.api.routing.JavaScriptReverseRoute;
import play.db.jpa.Transactional;
import play.filters.csrf.RequireCSRFCheck;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;
import utils.SessionStoreAdapter;
import utils.StringUtils;
import utils.UserSession;
import views.html.resetpassword;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginCtrl extends BaseCtrl {

    public static List<JavaScriptReverseRoute> getRoutes() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();
        routeList.add(controllers.login.routes.javascript.LoginCtrl.authenticate());
        routeList.add(controllers.login.routes.javascript.LoginCtrl.forgotPassword());
        routeList.add(controllers.login.routes.javascript.LoginCtrl.resetPassword());
        routeList.add(controllers.login.routes.javascript.LoginCtrl.saveNewPassword());
        return routeList;
    }

    @RequireCSRFCheck
    @Transactional
    public Result authenticate() {
        if (!request().getHeader("Content-Type").contains("application/json")) {
            return badRequest();
        }

        LoginDTO loginDTO = Json.fromJson(request().body().asJson(), LoginDTO.class);
        String path = loginDTO.path;
        F.Either<ServiceError, SysUser> e = LoginCS.authenticate(loginDTO);

        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            flash("error", Messages.get(errorMessage));
            return ok(routes.Application.login(path).url());
        }

        SysUser user = e.right.get();
        storeUserToSession(user);

        return ok(path);
    }

    @Transactional
    public Result forgotPassword() {
        LoginDTO loginDTO = Json.fromJson(request().body().asJson(), LoginDTO.class);

        F.Either<ServiceError, SysUser> e = LoginCS.forgotPassword(loginDTO);
        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            flash("error", Messages.get(errorMessage));
            return ok();
        }

        return ok();
    }

    @Transactional
    public Result resetPassword(String token) {
        Optional<SysUser> model = SysUserDAO.findByResetPasswordToken(token);
        if(!model.isPresent()) {
            Logger.warn("No sysUserFound with token " + token);
            return badRequest();
        }
        return ok(resetpassword.render(token));
    }


    @Transactional
    public Result saveNewPassword() {
        String token = request().body().asJson().get("token").asText();
        String password = request().body().asJson().get("password").asText();

        Optional<SysUser> model = SysUserDAO.findByResetPasswordToken(token);
        if(!model.isPresent()) {
            Logger.warn("No sysUserFound with token " + token);
            return badRequest();
        }
        SysUser sysUser = model.get();
        sysUser.resetPasswordToken = null;
        sysUser.password = StringUtils.getMD5hash(password);
        sysUser.save();
        storeUserToSession(sysUser);
        return ok();
    }


    private static void setLanguageForUser(SysUser user) {
        if (user != null) {
            if (user.languageCode == null) {
                Logger.info("User(" + user.id + ")'s languageCode is not set. Using browser's default.");
                changeLang(Lang.defaultLang().code());
            } else {
                changeLang(user.languageCode);
            }
        }
    }

    public static void storeUserToSession(SysUser user) {
        setLanguageForUser(user);
        session("id", Long.toString(user.id));

        String newSessionId = UserSession.obtainSessionId(user);

        user.sessionId = newSessionId;
        user.save();

        session("sessionId", newSessionId);

        try {
            SessionStoreAdapter.getInstance().setUserSession(newSessionId, SysUserDTO.createFromModel(user));
        } catch (Exception e) {
            Logger.error("Failed to store User(" + user.id + ") in sessionStore", e);
        }
    }
}

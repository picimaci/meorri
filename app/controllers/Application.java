package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import controllers.DTOs.core.SysUserDTO;
import controllers.login.LoginCtrl;
import controllers.sysuser.edit.SysUserEditCtrl;
import controllers.sysuser.importsysuser.SysUserImportCtrl;
import controllers.sysuser.list.SysUserListCtrl;
import controllers.sysuser.profile.SysUserProfileEditCtrl;
import controllers.sysuser.service.SysUserServiceCtrl;
import daos.SysUserDAO;
import jsmessages.JsMessages;
import jsmessages.JsMessagesFactory;
import jsmessages.japi.Helper;
import models.SysUser;
import play.Logger;
import play.Routes;
import play.api.routing.JavaScriptReverseRoute;
import play.db.jpa.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.libs.Scala;
import play.mvc.Controller;
import play.mvc.Result;
import utils.SessionStoreAdapter;
import utils.UserSession;
import views.html.gallery.gallery;
import views.html.index;
import views.html.login;
import views.html.news;
import views.html.timeline;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

public class Application extends Controller {

    private final JsMessages jsMessages;

    @Inject
    public Application(JsMessagesFactory jsMessagesFactory) {
        jsMessages = jsMessagesFactory.all();
    }


    @AddCSRFToken
    @SubjectPresent
    public Result index() {
        return ok(index.render());
    }

    @SubjectPresent
    public Result gallery() {
        return ok(gallery.render());
    }

    @SubjectPresent
    public Result news() {
        return ok(news.render());
    }

    @SubjectPresent
    public Result timeline() {
        return ok(timeline.render());
    }

    public Result dummy(String x) {
        return ok();
    }


    public Result jsMessages() {
        return ok(jsMessages.apply(Scala.Option("window.Messages"), Helper.messagesFromCurrentHttpContext()));
    }

    public Result jsRoutes() {
        response().setContentType("message/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", generateJavascriptRouting()));
    }

    @AddCSRFToken
    public Result login(String path) {
        return ok(login.render(path));
    }

    @Transactional
    public Result logout() {
        SysUserDTO currentUser = UserSession.getCurrentUserDTO(session());
        if (currentUser != null) {
            String loginName = currentUser.email;
            String sessionId = currentUser.sessionId;
            Optional<SysUser> model = SysUserDAO.findBySessionId(sessionId);
            if(!model.isPresent()) {
                return badRequest();
            }

            SysUser user = model.get();
            user.sessionId = null;
            user.save();

            try {
                SessionStoreAdapter.getInstance().deleteSessionIdForUser(loginName);
                SessionStoreAdapter.getInstance().deleteUserSession(sessionId);
            } catch (Exception e) {
                Logger.error("Cannot find sessionId by loginName: " + loginName, e);
            }
        }
        response().discardCookie("csrfToken");
        session().clear();

        return redirect(routes.Application.login("/"));
    }

    private JavaScriptReverseRoute[] generateJavascriptRouting() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();

        routeList.addAll(SysUserListCtrl.getRoutes());
        routeList.addAll(SysUserEditCtrl.getRoutes());
        routeList.addAll(SysUserImportCtrl.getRoutes());
        routeList.addAll(SysUserProfileEditCtrl.getRoutes());
        routeList.addAll(SysUserServiceCtrl.getRoutes());
        routeList.addAll(LoginCtrl.getRoutes());

        routeList.add(routes.javascript.Application.index());
        routeList.add(routes.javascript.Assets.versioned());

        routeList.add(routes.javascript.Application.gallery());
        routeList.add(routes.javascript.Application.news());
        routeList.add(routes.javascript.Application.timeline());

        return routeList.toArray(new JavaScriptReverseRoute[routeList.size()]);
    }
}

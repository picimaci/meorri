package controllers.sysuser.service;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import controllers.BaseCtrl;
import controllers.core.ServiceError;
import controllers.interceptors.XHRAction;
import models.SysUser;
import play.api.routing.JavaScriptReverseRoute;
import play.db.jpa.Transactional;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.With;
import utils.AppConf;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SysUserServiceCtrl extends BaseCtrl {
    public static List<JavaScriptReverseRoute> getRoutes() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();
        routeList.add(controllers.sysuser.service.routes.javascript.SysUserServiceCtrl.getLanguages());
        return routeList;
    }

    @SubjectPresent
    @Transactional
    @With(XHRAction.class)
    public Result getLanguages() {
        return ok(Json.toJson(SysUserServiceCS.getLanguages()));
    }
}

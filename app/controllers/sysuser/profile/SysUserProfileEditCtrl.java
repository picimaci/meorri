package controllers.sysuser.profile;

import be.objectify.deadbolt.java.actions.Pattern;
import controllers.BaseCtrl;
import controllers.core.ServiceError;
import models.SysUser;
import models.constants.Privilege;
import play.api.routing.JavaScriptReverseRoute;
import play.db.jpa.Transactional;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;
import utils.UserSession;
import views.html.sysuser.sysuserprofileedit;

import java.util.ArrayList;
import java.util.List;

public class SysUserProfileEditCtrl extends BaseCtrl{
    public static List<JavaScriptReverseRoute> getRoutes() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();
        routeList.add(controllers.sysuser.profile.routes.javascript.SysUserProfileEditCtrl.index());
        routeList.add(controllers.sysuser.profile.routes.javascript.SysUserProfileEditCtrl.get());
        routeList.add(controllers.sysuser.profile.routes.javascript.SysUserProfileEditCtrl.save());
        return routeList;
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT_PROFILE)
    @Transactional
    public Result index() {
        return ok(sysuserprofileedit.render());
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT_PROFILE)
    @Transactional
    public Result get() {
        String sessionId = UserSession.getCookieSessionId(session());
        F.Either<ServiceError, SysUserProfileEditDTO> e = SysUserProfileEditCS.findBySessionId(sessionId);
        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            return badRequest(errorMessage);
        }
        return ok(Json.toJson(e.right.get()));
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT_PROFILE)
    @Transactional
    public Result save() {

        System.out.println("REquEST");
        System.out.println(request().getHeader("Content-Type"));

        if (!request().getHeader("Content-Type").contains("application/json")) {
            return badRequest();
        }

        SysUserProfileEditDTO editDTO = Json.fromJson(request().body().asJson(), SysUserProfileEditDTO.class);

        F.Either<ServiceError, SysUser> e = SysUserProfileEditCS.saveProfile(editDTO);

        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            return badRequest(errorMessage);
        }

        return ok();
    }
}

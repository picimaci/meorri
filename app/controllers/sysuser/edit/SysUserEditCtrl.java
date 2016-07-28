package controllers.sysuser.edit;

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
import play.mvc.With;
import views.html.sysuser.sysuseredit;
import controllers.interceptors.XHRAction;

import java.util.ArrayList;
import java.util.List;

public class SysUserEditCtrl extends BaseCtrl{

    public static List<JavaScriptReverseRoute> getRoutes() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();
        routeList.add(controllers.sysuser.edit.routes.javascript.SysUserEditCtrl.index());
        routeList.add(controllers.sysuser.edit.routes.javascript.SysUserEditCtrl.get());
        routeList.add(controllers.sysuser.edit.routes.javascript.SysUserEditCtrl.save());
        return routeList;
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT)
    @Transactional
    public Result index(long id) {
        if (SysUserEditCS.exists(id)) {
            return ok(sysuseredit.render(id));
        } else {
            return badRequest();
        }
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT)
    @Transactional
    @With(XHRAction.class)
    public Result get(long id) {
        F.Either<ServiceError, SysUserEditDTO> e = SysUserEditCS.findById(id);
        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            return badRequest(errorMessage);
        }
        return ok(Json.toJson(e.right.get()));
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT)
    @Transactional
    @With(XHRAction.class)
    public Result save() {
        if (!request().getHeader("Content-Type").contains("application/json")) {
            return badRequest();
        }

        SysUserEditDTO editDTO = Json.fromJson(request().body().asJson(), SysUserEditDTO.class);

        F.Either<ServiceError, SysUser> e = SysUserEditCS.saveSysUser(editDTO);

        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            return badRequest(errorMessage);
        }

        return ok();
    }
}

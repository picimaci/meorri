package controllers.sysuser.list;

import be.objectify.deadbolt.java.actions.Pattern;
import controllers.BaseCtrl;
import controllers.core.ServiceError;
import controllers.interceptors.Pagination;
import controllers.interceptors.PaginationInterceptor;
import controllers.interceptors.XHRAction;
import models.SysUser;
import models.constants.Privilege;
import play.api.routing.JavaScriptReverseRoute;
import play.db.jpa.Transactional;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.With;
import views.html.sysuser.sysuserlist;

import java.util.ArrayList;
import java.util.List;

public class SysUserListCtrl extends BaseCtrl{
    public static List<JavaScriptReverseRoute> getRoutes() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();
        routeList.add(controllers.sysuser.list.routes.javascript.SysUserListCtrl.index());
        routeList.add(controllers.sysuser.list.routes.javascript.SysUserListCtrl.listAjax());
        routeList.add(controllers.sysuser.list.routes.javascript.SysUserListCtrl.activate());
        routeList.add(controllers.sysuser.list.routes.javascript.SysUserListCtrl.deactivate());
        routeList.add(controllers.sysuser.list.routes.javascript.SysUserListCtrl.delete());
        return routeList;
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_VIEW_LIST)
    @Transactional
    public Result index() {
        return ok(sysuserlist.render());
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_VIEW_LIST)
    @Transactional
    @With({XHRAction.class, PaginationInterceptor.class})
    public Result listAjax() {
        Pagination pagination = (Pagination) ctx().args.get(PaginationInterceptor.PAGINATION_CONTEXT);
        SysUserListFilterDTO filter = SysUserListFilterDTO.fromRequestParams(request().queryString(), pagination);

        F.Either<ServiceError, List<SysUserListDTO>> listEither = SysUserListCS.findSysUsers(filter);
        if (listEither.left.isDefined()) {
            String errorMessage = listEither.left.get().getMessage();
            return badRequest(errorMessage);
        }
        List<SysUserListDTO> sysUsers = listEither.right.get();

        F.Either<ServiceError, Long> countEither = SysUserListCS.countSysUsers(filter);
        if (countEither.left.isDefined()) {
            String errorMessage = countEither.left.get().getMessage();
            return badRequest(errorMessage);
        }
        long rowCount = countEither.right.get();

        response().setHeader("Content-Range", "" + pagination.range + "/" + rowCount);
        return ok(Json.toJson(sysUsers));
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT)
    @Transactional
    public Result activate(long id) {

        F.Either<ServiceError, SysUser> e = SysUserListCS.activate(id);
        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            return badRequest(errorMessage);
        }
        return ok();
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT)
    @Transactional
    public Result deactivate(long id) {
        F.Either<ServiceError, SysUser> e = SysUserListCS.deactivate(id);
        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            return badRequest(errorMessage);
        }
        return ok();
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_EDIT)
    @Transactional
    @With(XHRAction.class)
    public Result delete(long id) {
        F.Either<ServiceError, SysUser> e = SysUserListCS.deleteById(id);
        if (e.left.isDefined()) {
            String errorMessage = e.left.get().getMessage();
            return badRequest(errorMessage);
        }
        return ok();
    }
}

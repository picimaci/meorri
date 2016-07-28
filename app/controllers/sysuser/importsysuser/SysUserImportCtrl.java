package controllers.sysuser.importsysuser;

import be.objectify.deadbolt.java.actions.Pattern;
import controllers.BaseCtrl;
import controllers.core.ServiceError;
import models.SysUser;
import models.constants.Privilege;
import play.api.routing.JavaScriptReverseRoute;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import utils.csvparser.sysuser.SysUserCSV;
import utils.csvparser.sysuser.SysUserCSVParser;
import views.html.sysuser.sysuserimport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SysUserImportCtrl extends BaseCtrl{
    public static List<JavaScriptReverseRoute> getRoutes() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();
        routeList.add(controllers.sysuser.importsysuser.routes.javascript.SysUserImportCtrl.index());
        routeList.add(controllers.sysuser.importsysuser.routes.javascript.SysUserImportCtrl.importSysUsersFromCSV());
        return routeList;
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_IMPORT)
    @Transactional
    public Result index() {
        return ok(sysuserimport.render());
    }

    @Pattern(Privilege.SYSUSER_PRIVILEGE_IMPORT)
    @Transactional
    public Result importSysUsersFromCSV() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        try {
            List<SysUserCSV> productCSVList = SysUserCSVParser.parser.read(body.getFile("file").getFile());
            F.Either<ServiceError, List<SysUser>> listEither = SysUserImportCS.importSysUsers(productCSVList);

            if (listEither.left.isDefined()) {
                String errorMessage = listEither.left.get().getMessage();
                return badRequest(errorMessage);
            }

            return ok();

        } catch (IOException e) {
            return badRequest(Messages.get("product.import.ioexception"));
        } catch (IllegalArgumentException e) {
            return badRequest(Messages.get("sysuserimport.illegalargumentexception"));
        }
    }
}

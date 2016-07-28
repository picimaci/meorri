package controllers;

import exceptions.NotImplementedGetRouteMethod;
import play.api.routing.JavaScriptReverseRoute;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class BaseCtrl extends Controller {

    public Result notAuthorized() {
        flash("error", Messages.get("global.error.noauthorization"));
        return redirect(routes.Application.index());
    }

    public static List<JavaScriptReverseRoute> getRoutes() {
        throw new NotImplementedGetRouteMethod();
    }
}

package controllers;

import com.google.common.collect.ImmutableMap;
import jsmessages.JsMessages;
import jsmessages.JsMessagesFactory;
import jsmessages.japi.Helper;
import models.Gallery;
import play.*;
import play.api.routing.JavaScriptReverseRoute;
import play.db.Database;
import play.db.Databases;
import play.db.jpa.Transactional;
import play.libs.Scala;
import play.mvc.*;

import views.html.*;
import views.html.gallery.gallery;

import javax.inject.Inject;
import java.util.ArrayList;

public class Application extends Controller {
    private static JsMessages jsMessages;

    @Inject
    public Application(JsMessagesFactory jsMessagesFactory) {
        jsMessages = jsMessagesFactory.all();
    }

    public Result index() {
        return ok(news.render());
    }

    @Transactional
    public Result gallery() {

//        Gallery g = new Gallery();
////        g.id = 2;
//        g.name = "Almafa";
//        g.key = "blubla";
//
//        g.persist();

        return ok(timeline.render());
    }

    public Result jsMessages() {
        return ok(jsMessages.apply(Scala.Option("window.Messages"), Helper.messagesFromCurrentHttpContext()));
    }

    public Result jsRoutes() {
        response().setContentType("message/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", generateJavascriptRouting()));
    }

    private JavaScriptReverseRoute[] generateJavascriptRouting() {
        ArrayList<JavaScriptReverseRoute> routeList = new ArrayList<>();

        routeList.add(routes.javascript.Application.index());
        routeList.add(routes.javascript.Application.gallery());
        routeList.add(routes.javascript.Assets.versioned());

        return routeList.toArray(new JavaScriptReverseRoute[routeList.size()]);
    }
}

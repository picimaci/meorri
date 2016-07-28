package controllers.interceptors;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class XHRAction extends Action.Simple {
    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        if (ctx._requestHeader().headers().get("X-Requested-With").nonEmpty()) {
            return delegate.call(ctx);
        } else {
            return F.Promise.pure((Result) unauthorized("unauthorized"));
        }
    }
}
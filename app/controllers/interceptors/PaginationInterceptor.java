package controllers.interceptors;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class PaginationInterceptor extends Action.Simple {

    private static final String RANGE_HEADER = "range";
    public static final String PAGINATION_CONTEXT = "pagination_context";

    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String range = ctx.request().getHeader(RANGE_HEADER);
        Pagination pagination = new Pagination();
        try {
            String[] rangeArray = range.split("-");
            pagination.range = range;

            int from = Integer.parseInt(rangeArray[0]);
            int to = Integer.parseInt(rangeArray[1]);
            pagination.from = from;
            pagination.to = to;
            ctx.args.put(PAGINATION_CONTEXT, pagination);

        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            return F.Promise.pure((Result) badRequest("Range header is absent or has wrong format"));
        }
        return delegate.call(ctx);
    }
}
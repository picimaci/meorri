package exceptions;

public class GetSingleResultError extends RuntimeException {
    public GetSingleResultError(Throwable throwable) {
        super(throwable);
    }
}
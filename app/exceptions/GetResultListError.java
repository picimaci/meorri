package exceptions;

public class GetResultListError extends RuntimeException {
    public GetResultListError(Throwable throwable) {
        super(throwable);
    }
}
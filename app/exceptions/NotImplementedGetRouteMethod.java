package exceptions;

public class NotImplementedGetRouteMethod extends RuntimeException {

    public NotImplementedGetRouteMethod() {
        super("One of the controllers not implement the getRoutes method!");
    }

    public NotImplementedGetRouteMethod(String message) {
        super(message);
    }
}
package controllers.core;

import play.i18n.Messages;

public class ServiceError {

    public final static String GLOBAL_UNKNOWN_ERROR = "global.error.unknown";

    public final static String SYSUSER_NOT_FOUND = "sysuser.error.notfound";
    public final static String SYSUSER_IMPORT_NO_SYSUSER = "sysuser.error.nosysuser";
    public final static String SYSUSER_DUPLICATE = "sysuser.error.duplicate";
    public final static String SYSUSER_LANGUAGE = "sysuser.error.language";

    public final static String LOGIN_NO_USER_FOUND = "login.nouserfound";

    public final static String EMAIL_NOT_SENT = "email.notsent";

    private String errorKey;

    public ServiceError() {
        this(GLOBAL_UNKNOWN_ERROR);
    }

    public ServiceError(String errorKey) {
        this.errorKey = errorKey;
    }

    public String getMessage() {
        return Messages.get(errorKey);
    }
}
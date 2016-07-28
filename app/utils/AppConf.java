package utils;

import play.Play;

import java.util.List;

public class AppConf {
    public static final String APPLICATION_VERSION = "application.version";
    public static final String CONFIG_DEADBOLT_ENABLED = "deadbolt.enabled";
    public static final String SMTP_HOST = "smtp.host";
    public static final String SMTP_USERNAME = "smtp.user";
    public static final String SMTP_PASSWORD = "smtp.password";
    public static final String SMTP_TLS = "smtp.tls";
    public static final String EMAIL_SENDER = "email.sender";
    public static final String BASE_URL = "baseurl";

    public static String getConfigString(String key) {
        return Play.application().configuration().getString(key);
    }

    public static Boolean getConfigBoolean(String key) {
        return Play.application().configuration().getBoolean(key);
    }

    public static Integer getConfigInt(String key) {
        return Play.application().configuration().getInt(key);
    }

    public static List<String> getConfigStringList(String key) {
        return Play.application().configuration().getStringList(key);
    }

    public static String[] getConfigStringArray(String key) {
        List<String> l = Play.application().configuration().getStringList(key);
        return l.toArray(new String[l.size()]);
    }
}
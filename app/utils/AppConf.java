package utils;

/**
 * Created by PICIMACI on 2016.07.17..
 */

import play.Play;

/**
 * Created by PICIMACI on 2016.07.06..
 */
public class AppConf {
    public static final String APPLICATION_VERSION = "application.version";

    public static String getConfigString(String key) {
        return Play.application().configuration().getString(key);
    }
}


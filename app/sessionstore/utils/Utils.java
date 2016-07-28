package sessionstore.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Utils {

	private static Config config = ConfigFactory.load();

	public static boolean isSessionStoreEnabled() {
		return config.getBoolean(Constants.CONFIG_RIAK_ENABLED);
	}

	public static String selectedDB() {
		return config.getString(Constants.CONFIG_SELECTED_DB);
	}

	public static String getRiakURL1() {
		return config.getString(Constants.CONFIG_RIAK_URL_1);
	}

	public static String getRiakURL2() {
		return config.getString(Constants.CONFIG_RIAK_URL_2);
	}

	public static int getRiakPort() {
		return config.getInt(Constants.CONFIG_RIAK_PORT);
	}

	public static int getRiakTimeout() {
		return config.getInt(Constants.CONFIG_RIAK_TIMEOUT);
	}

	public static String getBucketName() {
		return config.getString(Constants.CONFIG_BUCKET_SESSION);
	}

}

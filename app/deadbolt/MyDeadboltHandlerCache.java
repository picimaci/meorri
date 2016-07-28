package deadbolt;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.cache.HandlerCache;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MyDeadboltHandlerCache implements HandlerCache {
	private final DeadboltHandler defaultHandler = new MyDeadboltHandler();
	private final Map<String, DeadboltHandler> handlers = new HashMap<>();

	public MyDeadboltHandlerCache() {
		handlers.put(MyDeadboltHandlerKeys.DEFAULT.key, defaultHandler);
	}

	@Override
	public DeadboltHandler apply(final String key) {
		return handlers.get(key);
	}

	@Override
	public DeadboltHandler get() {
		return defaultHandler;
	}
}

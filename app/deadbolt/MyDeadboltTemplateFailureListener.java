package deadbolt;

import be.objectify.deadbolt.java.TemplateFailureListener;
import play.Logger;

import javax.inject.Singleton;

@Singleton
public class MyDeadboltTemplateFailureListener implements TemplateFailureListener {
	private static Logger.ALogger LOGGER = Logger.of(MyDeadboltTemplateFailureListener.class);

	@Override
	public void failure(final String message, final long timeout) {
		LOGGER.error("Template constraint failure: message [{}]  timeout [{}]ms", message, timeout);
	}
}

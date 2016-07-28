package deadbolt;

import be.objectify.deadbolt.java.ConfigKeys;

public enum MyDeadboltHandlerKeys {
	DEFAULT(ConfigKeys.DEFAULT_HANDLER_KEY),
	ALT("altHandler");

	public final String key;

	MyDeadboltHandlerKeys(final String key)
	{
		this.key = key;
	}
}

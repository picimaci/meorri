package deadbolt;

import be.objectify.deadbolt.java.TemplateFailureListener;
import be.objectify.deadbolt.java.cache.HandlerCache;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

import javax.inject.Singleton;

public class DeadboltHook extends Module {
	@Override
	public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
		return seq(bind(TemplateFailureListener.class).to(MyDeadboltTemplateFailureListener.class).in(Singleton.class),
				bind(HandlerCache.class).to(MyDeadboltHandlerCache.class).in(Singleton.class));
	}
}

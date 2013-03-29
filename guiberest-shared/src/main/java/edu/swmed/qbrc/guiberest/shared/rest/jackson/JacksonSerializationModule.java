package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.deserializers.UserExpressionDeserializer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.deserializers.TableJSONContainerDeserializer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers.UserExpressionSerializer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers.TableJSONContainerSerializer;

public class JacksonSerializationModule extends SimpleModule {
	public JacksonSerializationModule() {
		super("JacksonSerializationModule", new Version(1, 0, 0, null));
		addSerializer(User.class, new UserExpressionSerializer());
		addSerializer(TableJSONContainer.class, new TableJSONContainerSerializer());
		addDeserializer(User.class, new UserExpressionDeserializer());
		addDeserializer(TableJSONContainer.class, new TableJSONContainerDeserializer());
	}
}
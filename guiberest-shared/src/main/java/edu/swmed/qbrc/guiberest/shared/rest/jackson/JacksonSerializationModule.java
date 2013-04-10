package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.util.HashMap;
import java.util.List;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.type.TypeReference;
import org.reflections.Reflections;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.datapackage.DataPackage;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.deserializers.TableJSONContainerDeserializer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.deserializers.TableRowDeserializer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers.DataPackageSerializer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers.TableJSONContainerSerializer;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.serializers.TableRowSerializer;

public class JacksonSerializationModule extends SimpleModule {
	
	@SuppressWarnings("rawtypes")
	public JacksonSerializationModule(final ReflectionFactory reflectionFactory, final Reflections reflections) {
		
		super("JacksonSerializationModule", new Version(1, 0, 0, null));

		// Serialization for container (table schema, etc.)
		addSerializer(TableJSONContainer.class, new TableJSONContainerSerializer(reflectionFactory));
		
		addSerializer(DataPackage.class, new DataPackageSerializer(reflectionFactory, reflections));
		
		// Serialization for data types (rows)
		addSerializer(User.class, new TableRowSerializer<User>(reflectionFactory));
		addSerializer(Role.class, new TableRowSerializer<Role>(reflectionFactory));
		
		/* We need a HashMap of Object classes, each matched with a TypeReference to a list of
		 * that respective type.  The TableJSONContainerDeserializer matches the object class
		 * ("java_type" field) from the JSON data to the appropriate type reference in the HashMap
		 * to determine how to deserialize the array of rows.
		 */
		HashMap<Class<?>, TypeReference> typeRefs = new HashMap<Class<?>, TypeReference>();
		typeRefs.put(User.class, new TypeReference<List<User>>(){});
		typeRefs.put(Role.class, new TypeReference<List<Role>>(){});

		// Deserialization for containers (table schema, etc.)
		addDeserializer(TableJSONContainer.class, new TableJSONContainerDeserializer(typeRefs));

		// Deserialization for data types (rows)
		addDeserializer(User.class, new TableRowDeserializer<User>(User.class, reflectionFactory));
		addDeserializer(Role.class, new TableRowDeserializer<Role>(Role.class, reflectionFactory));
		
	}
}
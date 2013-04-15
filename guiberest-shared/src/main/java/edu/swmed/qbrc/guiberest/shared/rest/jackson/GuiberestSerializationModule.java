package edu.swmed.qbrc.guiberest.shared.rest.jackson;

import java.util.HashMap;
import java.util.List;
import org.codehaus.jackson.type.TypeReference;
import org.reflections.Reflections;

import com.google.inject.Inject;

import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.jacksonate.rest.jackson.RestBaseUrl;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.jackson.deserializers.TableJSONContainerDeserializer;
import edu.swmed.qbrc.jacksonate.rest.jackson.deserializers.TableRowDeserializer;
import edu.swmed.qbrc.jacksonate.rest.jackson.serializers.DataPackageSerializer;
import edu.swmed.qbrc.jacksonate.rest.jackson.serializers.TableJSONContainerSerializer;
import edu.swmed.qbrc.jacksonate.rest.jackson.serializers.TableRowSerializer;

public class GuiberestSerializationModule extends JacksonSerializationModule {
	
	@SuppressWarnings("rawtypes")
	@Inject
	public GuiberestSerializationModule(final ReflectionFactory reflectionFactory, final Reflections reflections, final RestBaseUrl restBaseUrl) {
		
		super(reflectionFactory, reflections, restBaseUrl);

		// Serialization for container (table schema, etc.)
		addSerializer(TableJSONContainer.class, new TableJSONContainerSerializer(reflectionFactory, restBaseUrl));
		
		addSerializer(DataPackage.class, new DataPackageSerializer(reflectionFactory, reflections, restBaseUrl));
		
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
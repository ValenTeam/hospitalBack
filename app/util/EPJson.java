package util;
/**
 * Created by felipeplazas on 2/10/17.
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import play.libs.Json;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class EPJson {
    /**
     * Equivalent to {@link #object(Object...) object(...).toString()}
     *
     * @param params Key, values in k1, v1, ...., kn, vn form
     * @return Json as String
     */
    @Nonnull
    public static String string(Object... params) {
        return object(params).toString();
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public static Map<String, Object> map(Object... params) {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(object(params), Map.class);
        return (Map<String, Object>) map;
    }

    /**
     * Creates a Json object with the parameters. Id keys are transformed to mongo format (_id)
     *
     * @param params Key, values in k1, v1, ...., kn, vn form
     * @return Json object
     */
    @Nonnull
    public static ObjectNode object(Object... params) {
        return transform(true, params);
    }

    /**
     * Creates a Json object with the parameters. If a key with name id is found, it is not changed to
     * Mongo format (_id)
     *
     * @param params Key, values in k1, v1, ...., kn, vn form
     * @return Json object
     */
    @Nonnull
    public static ObjectNode objectPreserveIdParam(Object... params) {
        return transform(false, params);
    }

    private static ObjectNode transform(boolean transformId, Object[] params) {
        ObjectNode obj = Json.newObject();

        Map<String, ObjectNode> nodes = new HashMap<>();
        if (params.length % 2 != 0)
            throw new IllegalArgumentException("Params provided are not correct. Keys and values do not match: " + Arrays.asList(params));
        for (int i = 0; i < params.length - 1; i += 2) {

            Object p = params[i + 1];
            String fieldName = params[i].toString();
            if (transformId && (fieldName.equals("id") || fieldName.endsWith(".id")))
                fieldName = fieldName.replace("id", "_id");
            if (p == null)
                obj.putNull(fieldName);
            else if (p instanceof String) {
                String val = (String) p;
                if (transformId && fieldName.endsWith("_id"))
                    setObjectId(obj, fieldName, new ObjectId(val));
                else
                    obj.put(fieldName, val);
            } else if (p instanceof Boolean)
                obj.put(fieldName, (Boolean) p);
            else if (p instanceof Integer)
                obj.put(fieldName, (Integer) p);
            else if (p instanceof Long)
                obj.put(fieldName, (Long) p);
            else if (p instanceof Double)
                obj.put(fieldName, (double) p);
            else if (p instanceof ArrayNode) {
                ArrayNode arr = (ArrayNode) p;
                obj.putArray(fieldName).addAll(arr);
            }else if (p instanceof ObjectNode) {
                ObjectNode node = (ObjectNode) p;
                if (nodes.containsKey(fieldName)) {
                    ObjectNode existing = (ObjectNode) obj.get(fieldName);
                    node.fields().forEachRemaining(entry -> existing.set(entry.getKey(), entry.getValue()));
                } else {
                    obj.set(fieldName, node);
                    nodes.put(fieldName, node);
                }

            } else if (p instanceof Iterable) {
                ArrayNode arr = obj.putArray(fieldName);
                Iterable list = (Iterable) p;
                for (Object o : list) {
                    add(arr, o);
                }
            } else if (p.getClass().isArray()) {
                ArrayNode arr = obj.putArray(fieldName);
                Object[] array = (Object[]) p;
                for (Object o : array) {
                    add(arr, o);
                }
            } else if (p instanceof ObjectId) {
                ObjectId val = (ObjectId) p;
                setObjectId(obj, fieldName, val);
            } else if (p.getClass().isEnum())
                obj.put(fieldName, p.toString());
            else
                obj.set(fieldName, Json.toJson(p));//Just transform to json
//                throw new RuntimeException("EPJson: Case not implemented for " + p.getClass());
        }
        return obj;
    }

    private static void setObjectId(ObjectNode obj, String fieldName, ObjectId val) {
        obj.set(fieldName, EPJson.object("$oid", val.toString()));
    }

    private static void add(ArrayNode arr, Object p) {
        if (p == null)
            arr.addNull();
        else if (p instanceof String)
            arr.add((String) p);
        else if (p instanceof Boolean)
            arr.add((Boolean) p);
        else if (p instanceof Integer)
            arr.add((Integer) p);
        else if (p instanceof Long)
            arr.add((Long) p);
        else if (p instanceof Double)
            arr.add((Double) p);
        else if (p instanceof JsonNode)
            arr.add((JsonNode) p);
        else if (p instanceof ObjectId)
            arr.add(EPJson.object("$oid", p.toString()));
        else if (p.getClass().isEnum())
            arr.add(p.toString());
        else
            throw new RuntimeException("No array add implemented for " + p);
    }

    public static ArrayNode array(Object... params) {
        ArrayNode arr = Json.newArray();
        for (Object o : params)
            add(arr, o);

        return arr;
    }
}

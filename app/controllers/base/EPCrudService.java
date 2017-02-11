package controllers.base;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import models.base.IdObject;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;
import util.EPJson;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by felipeplazas on 2/10/17.
 */
public class EPCrudService<T extends IdObject> {

    private Class<T> clazz;
    private String collectionName;
    private MongoCollection collection;

    public EPCrudService(String collectionName, Class<T> clazz) {
        this.collectionName = collectionName;
        this.clazz = clazz;
    }

    /**
     * This method allows to get a reference to the encapsulated Jongo collection. You can use it
     * to get such a reference and make use of all the Mongo querying capabilities.
     * @return {@link MongoCollection Refrence} to encapsulated Jongo collection
     */
    public MongoCollection collection() {
        if (collection == null)
            collection = PlayJongo.getCollection(collectionName);

        return collection;
    }

    /**
     * Saves an object to DB as a new document.
     *
     * @param object Object to save
     * @return The object
     */
    @Nonnull
    public T create(T object) {
        object.setId(null);
        WriteResult res = collection().save(object);
        if (res.getUpsertedId() != null) {
            object.setId(res.getUpsertedId().toString());
            return object;
        }
        throw new RuntimeException("Entity " + clazz.getSimpleName() + " cannot be created :(");
    }

    /**
     * Retrieves an object with DB by its id. Method is guaranteed to return
     * an object or fail if it cannot be found
     *
     * @param id Id to look for
     * @return The object.
     * @throws if object cannot be found
     */
    @Nonnull
    public T getById(String id) throws Exception {
        T res = findById(id);
        if (res == null)
            throw new Exception("Entity " + clazz.getSimpleName() + " can't be found by id: " + id);
        return res;
    }

    /**
     * Tries to retrieve an object with DB by its Id
     *
     * @param id Id to look for
     * @return Object found or null
     */
    @Nullable
    public T findById(String id) throws Exception{
        return collection().find(EPJson.object("id",id).toString()).as(clazz).next();
    }

    /**
     * Saves the object to te DB. This operation performs an upsert depending on whether the object
     * has an id or not. If the creation/update fails, an exception is thrown.
     *
     * @param object Object to be saved
     * @return The same object received as param. The id might have been updated if the operation resulted
     * in a creation.
     * @throws Exception If the operation fails
     */
    @Nonnull
    public T save(T object) {
        if (object.getId() != null)
            return update(object.getId(), object);
        return create(object);
    }

    public T update(String id, T object) {
        object.setId(id);

        WriteResult res = collection().update(new ObjectId(id)).with(object);
        if (res.getN() > 0)
            return object;
        throw new RuntimeException("Entity " + clazz.getSimpleName() + " cannot be updated :(");
    }

    /**
     * Sets the object defined by id as deleted
     *
     * @param id Id to look for
     */
    public void delete(String id) throws Exception{
        T t = findById(id);
        t.setDeleted(true);
        save(t);
    }

    public int hardDelete(Object... query) {
        String deleteQuery = EPJson.string(query);
        System.out.println("deleteQuery = " + deleteQuery);
        WriteResult res = collection().remove(deleteQuery);
        return res.getN();
    }
}
package models.base;

/**
 * Created by felipeplazas on 2/10/17.
 */
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;


@JsonInclude(JsonInclude.Include.ALWAYS)
public class IdObject {

    @MongoId
    @MongoObjectId
    protected String id;

    private Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted == null ? false : deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IdObject))
            return false;
        IdObject idObj = (IdObject) obj;
        if (id == null || idObj.id == null)
            return false;
        return id.equals(idObj.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

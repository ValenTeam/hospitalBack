package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.base.IdObject;
//import models.base.IdObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by felipeplazas on 2/9/17.
 */

public class Hospital extends IdObject {

    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

package controllers;

/**
 * Created by felipeplazas on 2/9/17.
 */

import controllers.base.EPController;
import models.Hospital;
import play.mvc.Result;
import play.mvc.With;
import util.EPJson;
import util.TokenAuth;

public class HospitalController extends EPController {

    /**
     * Creates a hospital, we assume the request is done properly (only we can create hospitals)
     * @return created hospital object
     */
    @With(TokenAuth.class)
    public Result create() {
        Hospital hospital = bodyAs(Hospital.class);
        hospitalesCrud.save(hospital);
        return ok(hospital);
    }

    /**
     * Finds all the (first 100) hospitals
     * @return OK 200 with a list that may be empty if there are no hospitals.
     */
    public Result listAll() {
        Iterable<Hospital> hospitals = hospitalesCrud.collection().find().limit(100).as(Hospital.class);
        //Return a 200 response with all the hospitals serialized.
        return ok(hospitals);
    }

    /**
     * Find a hospital with the given id
     * @param id
     * @return OK 200 if Hospital exists, 400 ERROR if it doesn't
     */
    public Result findById(String id) {
        Hospital hospital = null;
        hospital = hospitalesCrud.findById(id);
        if (hospital == null)
            return error("Object does not exist", 400);
        return ok(hospital);
    }

    /**
     * Finds all the (first 100) hospitals that match the given name.
     * @param name
     * @return 200 OK response with a list that may be empty if there are no matches.
     */
    public Result findByName(String name) {
        String query = EPJson.object("name", name).toString();
        Iterable<Hospital> hospitals = hospitalesCrud.collection().find(query).limit(100).as(Hospital.class);
        return ok(hospitals);
    }

}

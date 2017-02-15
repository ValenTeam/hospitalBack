package controllers;

import controllers.base.EPController;
import models.*;
import play.mvc.Result;

import java.util.ArrayList;

/**
 * kjhfkjhfkhkhfdkhkdh
 */
public class MarcapasosController extends EPController {

    /**
     * Creates a marcapasos, we assume the request is done properly
     * @return created marcapasos object
     */
    public Result create() {
        Marcapasos marcapasos = bodyAs(Marcapasos.class);
        marcapasosCrud.save(marcapasos);
        return ok(marcapasos);
    }

    /**
     * Updates a marcapasos, we assume the request is done properly
     * @return updated marcapasos object
     */
    public Result update(String id) {
        Marcapasos marcapasos = bodyAs(Marcapasos.class);
        marcapasosCrud.update(id, marcapasos);
        return ok(marcapasos);
    }


    /**
     * Finds all the (first 100) marcapasos
     * @return OK 200 with a list that may be empty if there are no marcapasos.
     */
    public Result listAll() {
        Iterable<Marcapasos> marcapasos = marcapasosCrud.collection().find().limit(100).as(Marcapasos.class);
        //Return a 200 response with all the hospitals serialized.
        return ok(marcapasos);
    }

    /**
     * Find a marcapasos with the given id
     * @param id
     * @return OK 200 if Marcapasos exists, 400 ERROR if it doesn't
     */
    public Result findById(String id) {
        Marcapasos marcapasos = null;
        try {
            marcapasos = marcapasosCrud.findById(id);
        } catch (Exception e) {
            return error("Object does not exist", 400);
        }
        return ok(marcapasos);
    }



}

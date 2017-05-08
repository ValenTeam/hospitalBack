package controllers;

import controllers.base.EPController;
import models.*;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import util.SecurityManager;
import util.TokenAuth;

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
    @With(TokenAuth.class)
    public Result update(String id) {
        try{
            SecurityManager.validatePermission("edit-marcapasos", (Http.Context.current.get().flash().get("token")));
        } catch (Exception e){ return error(e.getMessage()); }
        Marcapasos marcapasos = bodyAs(Marcapasos.class);
        return ok( marcapasosCrud.update(id, marcapasos) );
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
    @With(TokenAuth.class)
    public Result findById(String id) {
        try{
            SecurityManager.validatePermission("view-marcapasos", (Http.Context.current.get().flash().get("token")));
        } catch (Exception e){ return error(e.getMessage()); }
        Marcapasos marcapasos = null;
        try {
            marcapasos = marcapasosCrud.findById(id);
        } catch (Exception e) {
            return error("Object does not exist", 400);
        }
        return ok(marcapasos);
    }



}

package controllers.auth;

//import com.sun.org.apache.xpath.internal.operations.String;
import controllers.base.EPController;
import models.auth.UserGroup;
import play.mvc.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rogelio Garcia on 07-Apr-17.
 */
public class UserGroupController extends EPController {

    public Result create(){
        UserGroup userGroup = bodyAs(UserGroup.class);
        userGroupsCrud.save(userGroup);
        return ok(userGroup);
    }


    public Result deletePermission(String permission, String idGroup)  {
        UserGroup userGroup = userGroupsCrud.findById(idGroup);
        userGroup.getPermissions().remove(permission);
        userGroupsCrud.save(userGroup);
        return ok(userGroup);
    }

    public Result addPermission(String permission, String idGroup){
        UserGroup userGroup = userGroupsCrud.findById(idGroup);
        userGroup.addPermission(permission);
        userGroupsCrud.save(userGroup);
        return ok(permission);
    }

    public Result listPermissionsGroup(String idGroup){
        UserGroup userGroup = userGroupsCrud.findById(idGroup);
        if (userGroup == null)
            return error("Object does not exist", 400);
        if (userGroup.getPermissions()==null)
            return error("El grupo no tiene permisos");
        else{
            List<String> listaPermissions= userGroup.getPermissions();
            return ok(listaPermissions);
        }
    }

    public Result delete(String id)  {
        UserGroup userGroup = null;
        userGroup=userGroupsCrud.findById(id);
        try{
            userGroupsCrud.delete(id);
            return ok(userGroup);
        } catch (Exception e){
            return error("no se ha podido eliminar");
        }
    }



}

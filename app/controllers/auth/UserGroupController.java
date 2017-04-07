package controllers.auth;

import com.sun.org.apache.xpath.internal.operations.String;
import controllers.base.EPController;
import models.auth.UserGroup;

import javax.xml.transform.Result;
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

        UserGroup userGroup = null;
        userGroup=userGroupsCrud.findById(idGroup);
        List<String> permissionList = userGroup.getPermissions();
        boolean find=false;
        try{
            for (int i = 0; i < permissionList.size()&&!find; i++) {
                String perm = permissionList.get(i);
                if(permission.equals(perm)){
                    permissionList.remove(perm);
                    find=true;
                }

            }
            return ok(userGroup);
        }
        catch (Exception e) {return error("no se ha podido eliminar el permiso: ");};
    }

    public Result addPermission(String permission, String idGroup){
        UserGroup userGroup = null;
        userGroup=userGroupsCrud.findById(idGroup);
        userGroup.addPermission(permission);
        return ok(permission);
    }

    public Result listPermissionsGroup(String idGroup){
        UserGroup userGroup = null;
        userGroup=userGroupsCrud.findById(idGroup);

        if (userGroup == null) {
            return error("Object does not exist", 400);
        }
        if (userGroup.getPermissions()==null){
            return error("el grupo no tiene permisos");
        }
        else{
            List<String> listaPermissions=null;
            listaPermissions=userGroup.getPermissions();
            return ok(listaPermissions);
        }

    }

    public Result delete(String id)  {
        UserGroup userGroup = null;
        userGroup=userGroupsCrud.findById(idGroup);
        try{
            userGroupsCrud.delete(id);
            return ok(userGroup);
        }
        catch (Exception e)
        {
            return error("no se ha podido eliminar);
        }
    }



}

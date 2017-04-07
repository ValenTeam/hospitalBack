package models.auth;

import models.base.IdObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipeplazas on 4/7/17.
 */
public class UserGroup extends IdObject{

    private String name;

    private List<String> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(String permission){
        if (permissions == null) permissions = new ArrayList<String>();
        return permissions.contains(permission);
    }

    public void addPermission(String permission){
        if (permissions == null) permissions = new ArrayList<String>();
        permissions.add(permission);
    }
}

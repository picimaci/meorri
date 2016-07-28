package models.constants;

import models.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "picimaci_privilege")
public class Privilege extends Model {

    public final static String SYSUSER_PRIVILEGE_VIEW_LIST = "privilege.view-sysuserlist";
    public final static String SYSUSER_PRIVILEGE_EDIT = "privilege.edit-sysusers";
    public final static String SYSUSER_PRIVILEGE_IMPORT = "privilege.import-sysusers";
    public final static String SYSUSER_PRIVILEGE_EDIT_PROFILE = "privilege.edit-sysuserprofile";

    @Id
    public long id;

    @Column(name = "message_key", columnDefinition = "nvarchar(255)", nullable = false)
    public String messageKey;
}

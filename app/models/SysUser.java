package models;

import models.constants.Role;
import models.interfaces.Filterable;
import utils.queryexecutor.ComposeQueryWrapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "picimaci_sysuser")
public class SysUser extends Model implements Filterable{
    @Id
    @GeneratedValue
    public long id;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "password")
    public String password;

    @Column(name = "full_name", nullable = false)
    public String fullName;

    @Column(name = "country")
    public String country;

    @Column(name = "region")
    public String region;

    @Column(name = "city")
    public String city;

    @Column(name = "phone")
    public String phone;

    @Column(name = "language_code", length = 2, nullable = false)
    public String languageCode;

    @Column(name = "is_active", nullable = false)
    public boolean isActive;

    @Column(name = "last_login_date")
    public Date lastLoginDate;

    @Column(name = "session_id", length = 36)
    public String sessionId;

    @Column(name = "web_user", nullable = false)
    public boolean webUser;

    @Column(name = "reset_password_token")
    public String resetPasswordToken;

    @Column(name = "deleted")
    public boolean deleted;

    @ManyToMany
    @JoinTable(name = "picimaci_sysuser_role", joinColumns = @JoinColumn(name = "sysuser_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public List<Role> roles;

    public static String ID_FILTER = "id";
    public static String NAME_FILTER = "name";
    public static String EMAIL_FILTER = "email";
    public static String ACTIVE_FILTER = "active";
    public static String DELETED_FILTER = "deleted";

    private static final List<ComposeQueryWrapper> queryHelperList = new ArrayList<ComposeQueryWrapper>() {{
        //@formatter:off
		//                          KEY                 WHERE CONDITION                                             ORDED BY
        add(new ComposeQueryWrapper(ID_FILTER,          "",                                                         "su.id"));
        add(new ComposeQueryWrapper(NAME_FILTER,        "su.fullName LIKE CONCAT('%', :" +NAME_FILTER + ", '%')",   "su.fullName"));
        add(new ComposeQueryWrapper(EMAIL_FILTER,       "su.email LIKE CONCAT('%', :" +EMAIL_FILTER + ", '%')",     "su.email"));
        add(new ComposeQueryWrapper(ACTIVE_FILTER,      "su.isAactive = :" + ACTIVE_FILTER,                         "su.isActive"));
        add(new ComposeQueryWrapper(DELETED_FILTER,     "su.deleted = :" + DELETED_FILTER,                          "su.deleted"));
		//@formatter:on();
    }};
    @Override
    public List<ComposeQueryWrapper> getQueryHelperList() {
        return queryHelperList;
    }

    @Override
    public String getDefaultSortField() {
        return ID_FILTER;
    }

    @Override
    public boolean isDefaultSortOrderASC() {
        return false;
    }
}

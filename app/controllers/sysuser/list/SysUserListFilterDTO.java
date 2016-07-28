package controllers.sysuser.list;

import controllers.DTOs.core.AbstractListFilterDTO;
import controllers.interceptors.Pagination;
import utils.RequestUtils;

import java.util.Map;

public class SysUserListFilterDTO extends AbstractListFilterDTO {

    public String name;
    public String email;
    public boolean activeStatusAll;
    public boolean active;

    public static SysUserListFilterDTO fromRequestParams(Map<String, String[]> query, Pagination pagination) {
        SysUserListFilterDTO dto = new SysUserListFilterDTO();
        dto.setBaseParameters(query, pagination);
        dto.setFilterParameters(query);
        return dto;
    }

    public void setFilterParameters(Map<String, String[]> query) {
        this.name = RequestUtils.getParam(query, "name");
        this.email = RequestUtils.getParam(query, "email");
        String status = RequestUtils.getParam(query, "isActive");
        if (status.equals("ALL")) {
            this.activeStatusAll = true;
        } else {
            this.active = Boolean.parseBoolean(status);
        }
    }
}

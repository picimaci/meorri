package controllers.DTOs.core;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.DTOs.authorization.PrivilegeDTO;
import controllers.DTOs.authorization.RoleDTO;
import models.SysUser;
import models.constants.Role;
import utils.AppConf;

import java.util.ArrayList;
import java.util.List;

public class SysUserDTO implements Subject{
    public long id;

    public String fullName;

    public String email;

    public boolean isActive;

    public String languageCode;

    public String[] languages;

    public String phone;

    public String sessionId;

    public String country;
    public String region;
    public String city;

    public boolean webUser;

    @JsonIgnore
    public String password;

    public boolean sideBarOpen = true;

    public List<Long> roleIds;

    public List<RoleDTO> roleDTOs;

    public List<PrivilegeDTO> privilegeDTOs;

    public static SysUserDTO createFromModel(SysUser sysUser) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.id = sysUser.id;
        sysUserDTO.fullName = sysUser.fullName;
        sysUserDTO.email = sysUser.email;
        sysUserDTO.isActive = sysUser.isActive;
        sysUserDTO.sessionId = sysUser.sessionId;
        sysUserDTO.languageCode = sysUser.languageCode;
        sysUserDTO.languages = AppConf.getConfigStringArray("play.i18n.langs");
        sysUserDTO.phone = sysUser.phone;
        sysUserDTO.webUser = sysUser.webUser;

        sysUserDTO.country= sysUser.country;
        sysUserDTO.region= sysUser.region;
        sysUserDTO.city= sysUser.city;

        sysUserDTO.roleIds = new ArrayList<>();
        sysUserDTO.privilegeDTOs = new ArrayList<>();
        sysUserDTO.roleDTOs = RoleDTO.createFromModelList(sysUser.roles);
        for(Role role : sysUser.roles) {
            sysUserDTO.roleIds.add(role.id);
            sysUserDTO.privilegeDTOs.addAll(PrivilegeDTO.createFromModelList(role));
        }
        return sysUserDTO;
    }

    @Override
    @JsonIgnore
    public List<? extends Permission> getPermissions() {
        return privilegeDTOs;
    }

    @Override
    @JsonIgnore
    public List<? extends be.objectify.deadbolt.core.models.Role> getRoles() {
        return roleDTOs;
    }

    @Override
    @JsonIgnore
    public String getIdentifier() {
        return "" + id;
    }

    public SysUserDTO() {
        this.languages =  AppConf.getConfigStringArray("play.i18n.langs");
		this.languageCode = languages[0];
    }
}

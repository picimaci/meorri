package controllers.sysuser.list;

import models.SysUser;

import java.util.List;
import java.util.stream.Collectors;

public class SysUserListDTO {
    public long id;
    public String name;
    public String email;
    public boolean active;

    public static SysUserListDTO fromModel (SysUser sysUser) {
        SysUserListDTO sysUserListDTO = new SysUserListDTO();

        sysUserListDTO.id = sysUser.id;
        sysUserListDTO.name = sysUser.fullName;
        sysUserListDTO.email= sysUser.email;
        sysUserListDTO.active = sysUser.isActive;

        return sysUserListDTO;
    }

    public static List<SysUserListDTO> fromModel(List<SysUser> sysUsers) {
        return sysUsers.stream()
                .map(SysUserListDTO::fromModel)
                .collect(Collectors.toList());
    }
}

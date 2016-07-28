package controllers.sysuser.edit;

import models.SysUser;

public class SysUserEditDTO {
    public long id;
    public String name;
    public String email;
    public String phone;
    public boolean webUser;
    public String languageCode;

    public static SysUserEditDTO fromModel(SysUser sysUser) {
        SysUserEditDTO sysUserEditDTO = new SysUserEditDTO();

        sysUserEditDTO.id = sysUser.id;
        sysUserEditDTO.name = sysUser.fullName;
        sysUserEditDTO.email = sysUser.email;
        sysUserEditDTO.phone = sysUser.phone;
        sysUserEditDTO.webUser = sysUser.webUser;
        sysUserEditDTO.languageCode = sysUser.languageCode;

        return sysUserEditDTO;
    }

    public static void modifyModel(SysUser model, SysUserEditDTO editDTO) {
        model.fullName = editDTO.name;
        model.email = editDTO.email;
        model.phone = editDTO.phone;
        model.webUser = editDTO.webUser;
        model.languageCode = editDTO.languageCode;
    }
}

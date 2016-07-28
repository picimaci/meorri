package controllers.sysuser.profile;

import models.SysUser;

public class SysUserProfileEditDTO {
    public long id;
    public String name;
    public String email;
    public String phone;
    public String languageCode;
    public boolean isActive;

    public static SysUserProfileEditDTO fromModel(SysUser sysUser) {
        SysUserProfileEditDTO sysUserProfileEditDTO = new SysUserProfileEditDTO();

        sysUserProfileEditDTO.id = sysUser.id;
        sysUserProfileEditDTO.name = sysUser.fullName;
        sysUserProfileEditDTO.email = sysUser.email;
        sysUserProfileEditDTO.phone = sysUser.phone;
        sysUserProfileEditDTO.languageCode = sysUser.languageCode;
        sysUserProfileEditDTO.isActive = sysUser.isActive;

        return sysUserProfileEditDTO;
    }

    public static void modifyModel(SysUser model, SysUserProfileEditDTO editDTO) {
        model.fullName = editDTO.name;
        model.email = editDTO.email;
        model.phone = editDTO.phone;
        model.languageCode = editDTO.languageCode;
        model.isActive = editDTO.isActive;
    }
    
}

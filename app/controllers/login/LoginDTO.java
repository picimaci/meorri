package controllers.login;

import models.SysUser;

public class LoginDTO {
    public String email;
    public String password;
    public String city;
    public String country;
    public String region;
    public String path;

    public static LoginDTO fromModel(SysUser sysUser) {
        LoginDTO loginDTO = new LoginDTO();
        
        loginDTO.email = sysUser.email;
        loginDTO.password = sysUser.password;
        loginDTO.city = sysUser.city;
        loginDTO.country = sysUser.country;
        loginDTO.region = sysUser.region;
        
        return loginDTO;
    }

    public static void modifyModel(SysUser model, LoginDTO loginDTO) {
        model.email = loginDTO.email;
        model.password = loginDTO.password;
        model.city = loginDTO.city;
        model.country = loginDTO.country;
        model.region = loginDTO.region;
    }
}

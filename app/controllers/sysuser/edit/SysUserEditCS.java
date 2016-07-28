package controllers.sysuser.edit;

import controllers.core.ServiceError;
import daos.SysUserDAO;
import models.SysUser;
import play.libs.F;

import java.util.Optional;

import static controllers.core.ServiceError.SYSUSER_DUPLICATE;
import static controllers.core.ServiceError.SYSUSER_NOT_FOUND;

public class SysUserEditCS {
    public static boolean exists(long id) {
        return id < 0 || SysUserDAO.findById(id).isPresent();
    }
    
    public static F.Either<ServiceError, SysUserEditDTO> findById(Long id) {
        if (id < 1) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        Optional<SysUser> model = SysUserDAO.findById(id);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        SysUserEditDTO sysUserDTO = SysUserEditDTO.fromModel(model.get());

        return F.Either.Right(sysUserDTO);
    }

    public static F.Either<ServiceError,SysUser> saveSysUser(SysUserEditDTO editDTO) {
        SysUser sysUser;
        if(editDTO.id == -1){
            Optional<SysUser> duplicate = SysUserDAO.findByEmail(editDTO.email);
            if (duplicate.isPresent()) {
                return F.Either.Left(new ServiceError(SYSUSER_DUPLICATE));
            }
            sysUser = new SysUser();
            sysUser.isActive = true;
        } else {
            Optional<SysUser> model = SysUserDAO.findById(editDTO.id);
            if (!model.isPresent()) {
                return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
            }
            sysUser = model.get();
        }
        SysUserEditDTO.modifyModel(sysUser, editDTO);
        sysUser.persist();
        return F.Either.Right(sysUser);
    }
}

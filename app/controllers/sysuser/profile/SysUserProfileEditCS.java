package controllers.sysuser.profile;

import controllers.core.ServiceError;
import daos.SysUserDAO;
import models.SysUser;
import play.libs.F;

import java.util.Optional;

import static controllers.core.ServiceError.SYSUSER_DUPLICATE;
import static controllers.core.ServiceError.SYSUSER_NOT_FOUND;

public class SysUserProfileEditCS {

    public static F.Either<ServiceError, SysUserProfileEditDTO> findBySessionId(String sessionId) {
        Optional<SysUser> model = SysUserDAO.findBySessionId(sessionId);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        SysUserProfileEditDTO sysUserProfileDTO = SysUserProfileEditDTO.fromModel(model.get());

        return F.Either.Right(sysUserProfileDTO);
    }

    public static F.Either<ServiceError,SysUser> saveProfile(SysUserProfileEditDTO editDTO) {
        Optional<SysUser> model = SysUserDAO.findById(editDTO.id);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError(SYSUSER_NOT_FOUND));
        }
        SysUser sysUser = model.get();
        Optional<SysUser> duplicate = SysUserDAO.findByEmail(editDTO.email);
        if (duplicate.isPresent() && duplicate.get().id != editDTO.id) {
            return F.Either.Left(new ServiceError(SYSUSER_DUPLICATE));
        }

        SysUserProfileEditDTO.modifyModel(sysUser, editDTO);
        sysUser.persist();

        return F.Either.Right(sysUser);
    }
}

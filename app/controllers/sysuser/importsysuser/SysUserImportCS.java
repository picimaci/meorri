package controllers.sysuser.importsysuser;

import controllers.core.ServiceError;
import controllers.sysuser.service.SysUserServiceCS;
import daos.SysUserDAO;
import models.SysUser;
import play.libs.F;
import utils.csvparser.sysuser.SysUserCSV;

import java.util.*;

import static controllers.core.ServiceError.*;

public class SysUserImportCS {

    private static Set<String> emails;

    public static F.Either<ServiceError, List<SysUser>> importSysUsers(List<SysUserCSV> sysUserCSVList) {
        emails = new HashSet<>();
        List<SysUser> sysUsers = new ArrayList<>();
        F.Either<ServiceError, SysUser> e;

        for (SysUserCSV sysUserCSV : sysUserCSVList) {
            e = validateCSV(sysUserCSV);
            if (e.left.isDefined()) {
                return F.Either.Left(e.left.get());
            }
        }

        for (SysUserCSV sysUserCSV : sysUserCSVList) {
            e = createSysUserFromCSV(sysUserCSV);
            if (e.left.isDefined()) {
                return F.Either.Left(e.left.get());
            }
            sysUsers.add(e.right.get());
        }

        if (sysUsers.isEmpty())
            return F.Either.Left(new ServiceError(SYSUSER_IMPORT_NO_SYSUSER));

        persistSysUsers(sysUsers);
        return F.Either.Right(sysUsers);
    }

    public static F.Either<ServiceError, SysUser> validateCSV(SysUserCSV sysUserCSV) {
        if(emails.contains(sysUserCSV.email)) {
            return F.Either.Left(new ServiceError(SYSUSER_DUPLICATE));
        }
        if(!SysUserServiceCS.getLanguages().contains(sysUserCSV.languageCode)) {
            return F.Either.Left(new ServiceError(SYSUSER_LANGUAGE));
        }
        return F.Either.Right(null);
    }

    public static F.Either<ServiceError, SysUser> createSysUserFromCSV(SysUserCSV sysUserCSV) {

        Optional<SysUser> modelSysUser = SysUserDAO.findByEmail(sysUserCSV.email);

        SysUser sysUser;

        if (!modelSysUser.isPresent()) {
            sysUser = new SysUser();
            sysUser.email = sysUserCSV.email;
        } else {
            sysUser = modelSysUser.get();
        }

        sysUser.isActive = true;
        sysUser.deleted = false;
        sysUser.fullName = sysUserCSV.fullName;
        sysUser.phone = sysUserCSV.phone;
        sysUser.languageCode = sysUserCSV.languageCode;
        sysUser.webUser = sysUserCSV.webUser;

        return F.Either.Right(sysUser);
    }

    public static void persistSysUsers(List<SysUser> sysUsers) {
        for (SysUser sysUser : sysUsers) {
            sysUser.persist();
        }
    }
}

package controllers.sysuser.list;

import controllers.core.ServiceError;
import daos.SysUserDAO;
import models.SysUser;
import play.libs.F;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SysUserListCS {
    public static Map<String, Object> getFilters(SysUserListFilterDTO filterDTO) {
        Map<String, Object> filters = new HashMap<>();
        if(filterDTO.name != null && !filterDTO.name.isEmpty()) {
            filters.put(SysUser.NAME_FILTER, filterDTO.name);
        }
        if(filterDTO.email != null && !filterDTO.email.isEmpty()) {
            filters.put(SysUser.EMAIL_FILTER, filterDTO.email);
        }
        if(!filterDTO.activeStatusAll) {
            filters.put(SysUser.ACTIVE_FILTER, filterDTO.active);
        }
        filters.put(SysUser.DELETED_FILTER, false);
        return filters;
    }

    public static F.Either<ServiceError,List<SysUserListDTO>> findSysUsers(SysUserListFilterDTO filterDTO) {
        Map<String, Object> filters = getFilters(filterDTO);
        List<models.SysUser> modelList = SysUserDAO.findSysUsers(filters, filterDTO);
        List<SysUserListDTO> SysUserList = SysUserListDTO.fromModel(modelList);
        return F.Either.Right(SysUserList);
    }

    public static F.Either<ServiceError, Long> countSysUsers(SysUserListFilterDTO filterDTO) {
        Map<String, Object> filters = getFilters(filterDTO);
        return F.Either.Right(SysUserDAO.countSysUsers(filters, filterDTO));
    }

    public static F.Either<ServiceError, SysUser> activate(long id) {
        Optional<SysUser> model = SysUserDAO.activateById(id);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError());
        }
        return F.Either.Right(model.get());
    }

    public static F.Either<ServiceError, SysUser> deactivate(long id) {
        Optional<SysUser> model = SysUserDAO.deactivateById(id);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError());
        }
        return F.Either.Right(model.get());
    }

    public static F.Either<ServiceError, SysUser> deleteById(long id) {
        Optional<SysUser> model = SysUserDAO.deleteById(id);
        if (!model.isPresent()) {
            return F.Either.Left(new ServiceError());
        }
        return F.Either.Right(model.get());
    }
}

package controllers.DTOs.authorization;

import models.constants.Role;
import play.i18n.Messages;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO extends KeyValueDTO implements be.objectify.deadbolt.core.models.Role{
    public String name;

    public static RoleDTO createFromModel(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.id = role.id;
        roleDTO.messageKey = role.messageKey;
        roleDTO.label = Messages.get(role.messageKey);
        return roleDTO;
    }

    public static List<RoleDTO> createFromModelList(List<Role> roles) {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for(Role role : roles) {
            roleDTOList.add(createFromModel(role));
        }
        return roleDTOList;
    }

    @Override
    public String getName() {
        return messageKey;
    }
}
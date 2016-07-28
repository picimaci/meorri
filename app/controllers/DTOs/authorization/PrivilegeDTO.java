package controllers.DTOs.authorization;

import be.objectify.deadbolt.core.models.Permission;
import models.constants.Privilege;
import models.constants.Role;
import play.i18n.Messages;

import java.util.ArrayList;
import java.util.List;

public class PrivilegeDTO extends KeyValueDTO implements Permission {
    public String value;

    public static PrivilegeDTO createFromModel(Privilege privilege) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.id = privilege.id;
        privilegeDTO.messageKey = privilege.messageKey;
        privilegeDTO.label = Messages.get(privilege.messageKey);
        return privilegeDTO;
    }

    public static List<PrivilegeDTO> createFromModelList(Role role) {
        List<PrivilegeDTO> privilegeDTOList = new ArrayList<>();
        for(Privilege privilege : role.privileges) {
            privilegeDTOList.add(createFromModel(privilege));
        }
        return privilegeDTOList;
    }

    @Override
    public String getValue() {
        return messageKey;
    }
}


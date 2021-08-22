package obss.project.finalproject.service;

import obss.project.finalproject.model.Role;
import obss.project.finalproject.model.RoleType;

import java.util.Optional;

public interface RoleService {

    Role findRoleByName(RoleType name);

}
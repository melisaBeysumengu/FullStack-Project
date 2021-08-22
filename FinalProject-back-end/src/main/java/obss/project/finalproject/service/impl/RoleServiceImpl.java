package obss.project.finalproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import obss.project.finalproject.model.Role;
import obss.project.finalproject.model.RoleType;
import obss.project.finalproject.repository.RoleRepository;
import obss.project.finalproject.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(RoleType name) {
        Objects.requireNonNull(name, "role name cannot be null");

        if (roleRepository.findRoleByName(name) == null) {
            Role r = new Role();
            r.setName(name);
            roleRepository.save(r);
        }

        return roleRepository.findRoleByName(name);
    }
// .orElseThrow(RoleNotFoundException::new)


}

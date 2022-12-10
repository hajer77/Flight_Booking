package com.flight.booking.service.Impl;

import com.flight.booking.dto.RoleDto;
import com.flight.booking.model.Role;
import com.flight.booking.repository.RoleRepository;
import com.flight.booking.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());

        Role newRole = roleRepository.save(role);
        RoleDto roleResponse = new RoleDto();
        roleResponse.setRoleId(newRole.getRoleId());
        roleResponse.setName(newRole.getName());
        return roleResponse;
    }
}

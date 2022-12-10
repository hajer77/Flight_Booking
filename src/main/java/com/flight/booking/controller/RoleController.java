package com.flight.booking.controller;


import com.flight.booking.dto.RoleDto;
import com.flight.booking.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RoleController {
    private RoleService roleService;


    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("role/create")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        return new  ResponseEntity<>(roleService.createRole(roleDto),HttpStatus.CREATED);
    }
}

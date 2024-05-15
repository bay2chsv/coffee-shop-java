package com.example.restfulapi.service;

import com.example.restfulapi.entity.Role;
import com.example.restfulapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleSevice {

    private final RoleRepository roleRepository;

    public List<Role> getAll(){
        return roleRepository.findAll();
    }
}

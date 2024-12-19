package com.proof.events_system.controller;

import com.proof.events_system.dto.RoleEntityDTO;
import com.proof.events_system.payload.response.MessageResponse;
import com.proof.events_system.service.implement.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Validated
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getRoleById(@Min(1) @PathVariable Long id) {
        LOGGER.info("Getting role by id");
        RoleEntityDTO role = roleService.getRoleById(id);
        MessageResponse messageResponse = new MessageResponse("Role found successfully", role);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getAllRoles() {
        LOGGER.info("Getting all roles");
        List<RoleEntityDTO> roles = roleService.getAllRoles();
        MessageResponse messageResponse = new MessageResponse("Roles found successfully", roles);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> saveRole(@Valid @RequestBody RoleEntityDTO roleDTO) {
        LOGGER.info("Saving role");
        roleService.saveRole(roleDTO);
        MessageResponse messageResponse = new MessageResponse("Role saved successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRole(@Min(1) @PathVariable Long id, @Valid @RequestBody RoleEntityDTO roleDTO) {
        LOGGER.info("Updating role");
        RoleEntityDTO updatedRole = roleService.updateRole(id, roleDTO);
        MessageResponse messageResponse = new MessageResponse("Role updated successfully", updatedRole);
        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRole(@Min(1) @PathVariable Long id) {
        LOGGER.info("Deleting role");
        roleService.deleteRole(id);
        MessageResponse messageResponse = new MessageResponse("Role deleted successfully", null);
        return ResponseEntity.ok(messageResponse);
    }
}


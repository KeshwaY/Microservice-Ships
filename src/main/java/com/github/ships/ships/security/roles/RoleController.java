package com.github.ships.ships.security.roles;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.GenericCRUDController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends GenericCRUDController<RoleDTO, RoleDTO, RoleDTO, GenericResponseDto, RoleService> {
    protected RoleController(RoleService service) {
        super(service);
    }
}

package com.github.ships.ships.security.authorities;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.GenericCRUDController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/authorities")
public class AuthorityController extends
        GenericCRUDController<AuthorityDTO, AuthorityDTO, AuthorityDTO, GenericResponseDto, AuthorityService> {
    protected AuthorityController(AuthorityService service) {
        super(service);
    }
}

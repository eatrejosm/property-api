package mch.asset.controller;

import mch.asset.entity.MchProperty;
import mch.asset.model.PageResponse;
import mch.asset.model.Role;
import mch.asset.security.jwt.JwtTokenProvider;
import mch.asset.service.MchPropertyService;
import mch.asset.service.UserRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;


@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class PropertiesController {
    private final MchPropertyService mchPropertyService;
    private final UserRoleService userRoleService;


}

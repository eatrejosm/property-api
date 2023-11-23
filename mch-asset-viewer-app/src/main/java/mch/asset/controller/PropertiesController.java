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
import mch.asset.view.PropertyViewMapper;
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
    private final PropertyViewMapper propertyViewMapper;

    @GetMapping("all-properties")
    public Mono<PageResponse> getAllProperties(
            @AuthenticationPrincipal Principal authenticatedUser,
            @RequestHeader(name = "Authorization") String token,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "") String searchString,
            @RequestParam(defaultValue = "") String searchBy
    ){
        Role userRole = userRoleService.getUserPermissions(token);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Mono<Page<MchProperty>> properties = Mono.empty();

        log.info("searchBy {}", searchBy);

        if(searchBy.equals("profile")){
            log.info("1 {}", searchString);
            properties = mchPropertyService.findByProfile(searchString, paging);
        } else if(searchBy.equals("application")){
            log.info("2 {}", searchString);
                properties = mchPropertyService.findByApplication(searchString, paging);
        } else {
            log.info("3 {}", searchString);
            properties = mchPropertyService.findAll(paging);
        }

        return properties.map(propertyPage -> {
            var propertyList = propertyPage.stream().map(propertyViewMapper::fromModel).toList();
            return PageResponse.builder()
                    .properties(propertyList)
                    .totalItems(propertyPage.getTotalElements())
                    .currentPage(propertyPage.getNumber())
                    .totalPages(propertyPage.getTotalPages())
                    .build();
        });
    }

}

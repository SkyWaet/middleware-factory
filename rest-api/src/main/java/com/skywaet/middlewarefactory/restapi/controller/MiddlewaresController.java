package com.skywaet.middlewarefactory.restapi.controller;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.MiddlewareDto;
import com.skywaet.middlewarefactory.restapi.service.IMiddlewareService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.skywaet.middlewarefactory.restapi.controller.ControllerUtils.MIDDLEWARE_CONTROLLER;

@RestController
@AllArgsConstructor
public class MiddlewaresController {

    private final IMiddlewareService middlewareService;

    @GetMapping(MIDDLEWARE_CONTROLLER)
    //TODO add search parameter
    public Page<MiddlewareDto> list(Pageable pageable) {
        return middlewareService.list(pageable);
    }

    @PostMapping(MIDDLEWARE_CONTROLLER)
    public MiddlewareDto create(@Validated @RequestBody MiddlewareDto dto) {
        return middlewareService.create(dto);
    }
}

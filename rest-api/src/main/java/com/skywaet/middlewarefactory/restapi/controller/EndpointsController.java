package com.skywaet.middlewarefactory.restapi.controller;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.restapi.service.IEndpointService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.skywaet.middlewarefactory.restapi.controller.ControllerUtils.ENDPOINT_BY_ID;
import static com.skywaet.middlewarefactory.restapi.controller.ControllerUtils.ENDPOINT_CONTROLLER;

@RestController
@AllArgsConstructor
public class EndpointsController {

    private final IEndpointService endpointService;

    @GetMapping(ENDPOINT_CONTROLLER)
    //TODO add search parameter
    public Page<EndpointDto> list(Pageable pageable) {
        return endpointService.list(pageable);
    }

    @PostMapping(ENDPOINT_CONTROLLER)
    public EndpointExtendedDto create(@Validated @RequestBody EndpointExtendedDto dto) {
        return endpointService.create(dto);
    }

    @GetMapping(ENDPOINT_BY_ID)
    public EndpointExtendedDto findById(@PathVariable Long id) {
        return endpointService.findById(id);
    }

    @PutMapping(ENDPOINT_BY_ID)
    public EndpointExtendedDto updateById(@PathVariable Long id, @Validated @RequestBody EndpointExtendedDto dto) {
        return endpointService.updateById(id, dto);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public void deleteById(@PathVariable Long id) {
        endpointService.deleteById(id);
    }

}

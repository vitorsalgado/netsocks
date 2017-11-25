package com.netsocks.associatefan.resources.customers;

import com.netsocks.associatefan.domain.campaign.Campaign;
import com.netsocks.associatefan.domain.customer.CreateCommand;
import com.netsocks.associatefan.domain.customer.CustomerService;
import com.netsocks.associatefan.domain.customer.FindCampaignsCommand;
import com.netsocks.associatefan.domain.customer.UpdateCommand;
import com.netsocks.associatefan.libs.ErrorResponse;
import io.reactivex.schedulers.Schedulers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@Api(tags = "customers")
class CustomerController {
    private final CustomerService customerService;

    CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public DeferredResult<ResponseEntity<CustomerDto>> create(
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @RequestBody @Valid CreateOrUpdateCustomerDto dto) {
        DeferredResult<ResponseEntity<CustomerDto>> deferredResult = new DeferredResult<>();

        customerService.create(new CreateCommand(correlationId, dto.mapToDomain()))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        customer -> {
                            URI location = URI.create(String.format("/customers/%s", customer.getId()));
                            deferredResult.setResult(ResponseEntity.created(location).body(CustomerDto.mapToDto(customer)));
                        },
                        deferredResult::setErrorResult);

        return deferredResult;
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public DeferredResult<ResponseEntity<Void>> update(
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @PathVariable String id,
            @RequestBody @Valid CreateOrUpdateCustomerDto dto) {
        DeferredResult<ResponseEntity<Void>> deferredResult = new DeferredResult<>();

        customerService.update(new UpdateCommand(correlationId, id, dto.getFullName(), dto.getEmail(), dto.getBirthday(), dto.getFavoriteTeam()))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> deferredResult.setResult(ResponseEntity.noContent().build()),
                        deferredResult::setErrorResult);

        return deferredResult;
    }

    @GetMapping("/{email}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public DeferredResult<ResponseEntity<CustomerDto>> getByEmail(@RequestParam String id) {
        DeferredResult<ResponseEntity<CustomerDto>> deferredResult = new DeferredResult<>();

        customerService.getByEmail(id)
                .subscribeOn(Schedulers.io())
                .subscribe(customer -> deferredResult.setResult(ResponseEntity.ok(CustomerDto.mapToDto(customer))));

        return deferredResult;
    }

    @GetMapping("/{email}/campaigns")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public DeferredResult<ResponseEntity<List<CampaignDto>>> getCampaigns(
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @PathVariable String email) {
        DeferredResult<ResponseEntity<List<CampaignDto>>> deferredResult = new DeferredResult<>();

        List<Campaign> c = customerService.findCampaigns(new FindCampaignsCommand(correlationId, email))
                .blockingFirst();

        customerService.findCampaigns(new FindCampaignsCommand(correlationId, email))
                .subscribeOn(Schedulers.io())
                .subscribe(campaigns ->
                        deferredResult.setResult(ResponseEntity.ok(campaigns
                                .stream()
                                .map(CampaignDto::mapToDto)
                                .collect(Collectors.toList()))));

        return deferredResult;
    }
}

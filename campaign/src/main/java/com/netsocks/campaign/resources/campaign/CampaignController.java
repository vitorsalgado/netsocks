package com.netsocks.campaign.resources.campaign;

import com.netsocks.campaign.domain.Campaign;
import com.netsocks.campaign.domain.CampaignService;
import com.netsocks.campaign.domain.SearchCriteria;
import com.netsocks.campaign.libs.errors.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/campaigns")
@Api(tags = "campaigns")
class CampaignController {
    private final CampaignService campaignService;

    CampaignController(final CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public ResponseEntity<CampaignDto> create(@RequestBody @Valid CreateOrUpdateCampaignDto dto) {
        final Campaign campaign =
                campaignService.addOrUpdate(dto.mapToDomain());

        return ResponseEntity.created(URI.create(String.format("/campaigns/%s", campaign.getId())))
                .body(CampaignDto.mapDomain(campaign));
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public ResponseEntity<CampaignDto> update(@RequestParam String id, @RequestBody @Valid CreateOrUpdateCampaignDto dto) {
        Optional<Campaign> campaign = campaignService.getById(id);

        if (campaign.isPresent()) {
            campaignService.addOrUpdate(campaign.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CampaignDto.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public ResponseEntity<CampaignDto> get(@PathVariable String id) {
        final Optional<Campaign> optional = campaignService.getById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(CampaignDto.mapDomain(optional.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CampaignDto>> search(
            @RequestParam(required = false) String teamId,
            @RequestParam(required = false) List<String> ids) {
        SearchCriteria searchCriteria = new SearchCriteria(teamId, ids);

        List<CampaignDto> campaigns = campaignService.search(searchCriteria)
                .stream()
                .map(CampaignDto::mapDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(campaigns);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        campaignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

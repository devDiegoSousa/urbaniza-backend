package com.urbaniza.authapi.controller.admin;

import com.urbaniza.authapi.dto.city.CityResponseDTO;
import com.urbaniza.authapi.service.CityLoaderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/admin")
public class CityController {

  private final CityLoaderService cityLoaderService;

  public CityController(CityLoaderService cityLoaderService) {
    this.cityLoaderService = cityLoaderService;
  }

  @GetMapping("/cities/uf/{uf}")
  public Flux<CityResponseDTO> getCitiesByUf(@PathVariable String uf) {
    return cityLoaderService.importCitiesByUf(uf);
  }

  @GetMapping("/cities/uf/{uf}/city/{name}")
  public Flux<CityResponseDTO> getCityByNameAndUf(
      @PathVariable String uf,
      @PathVariable String name
  ) {
    return cityLoaderService.importCityByNameAndUf(uf, name);
  }

}

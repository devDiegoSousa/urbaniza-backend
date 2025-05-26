package com.urbaniza.authapi.service;

import com.urbaniza.authapi.dto.city.CityResponseDTO;
import com.urbaniza.authapi.model.City;
import com.urbaniza.authapi.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityLoaderService {

  private final WebClient webClient;
  private final CityRepository cityRepository;

  public CityLoaderService(WebClient webClient, CityRepository cityRepository) {
    this.webClient = webClient;
    this.cityRepository = cityRepository;
  }

  // Importa todas as cidades do Brasil
  public Flux<CityResponseDTO> importAllCities() {
    return webClient.get()
        .uri("/municipios")
        .retrieve()
        .bodyToFlux(CityResponseDTO.class);
  }

  // Importa cidades de um estado (UF)
  public Flux<CityResponseDTO> importCitiesByUf(String uf) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/estados/{uf}/municipios").build(uf))
        .retrieve()
        .bodyToFlux(CityResponseDTO.class);
  }

  // Importa uma cidade pelo nome e UF
  public Flux<CityResponseDTO> importCityByNameAndUf(String uf, String cityName) {
    return importCitiesByUf(uf)
        .filter(city -> city.getNome().equalsIgnoreCase(cityName))
        .flatMap(city -> {
          City entity = new City(city.getId(), city.getNome(), uf.toUpperCase());
          cityRepository.save(entity);
          return Mono.just(city);
        });
  }
}

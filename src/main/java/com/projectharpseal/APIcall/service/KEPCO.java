package com.projectharpseal.APIcall.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectharpseal.APIcall.DateUtils;
import com.projectharpseal.APIcall.Dto.Date_Return;
import com.projectharpseal.APIcall.Entity.Electricity;
import com.projectharpseal.APIcall.Entity.Location;
import com.projectharpseal.APIcall.repository.ElectricityRepository;
import com.projectharpseal.APIcall.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class KEPCO {

    private final Public_service publicService;
    private final ElectricityRepository electricityRepository;
    private final LocationRepository locationRepository;
    private static final Logger logger = LoggerFactory.getLogger(KEPCO.class);
    private final Date_Return LY = DateUtils.calculateLastYearMonth();
    private static final String[] KEPCO_Code = {"11", "26", "27", "28", "29", "30", "31", "36", "41", "43", "44", "45", "46", "47", "48", "50", "51"};

    private final ObjectMapper objectMapper = new ObjectMapper();

    public KEPCO(Public_service publicService, ElectricityRepository electricityRepository, LocationRepository locationRepository) {
        this.publicService = publicService;
        this.electricityRepository = electricityRepository;
        this.locationRepository = locationRepository;
    }

    public void parseApiCall() {
        for (String code : KEPCO_Code) {
            Mono<String> apiCallResult = publicService.makeApiCall(
                    "KEPCO_URL",
                    "KEPCO_Path",
                    "KEPCO_Key",
                    "year=" + LY.getYear(),
                    "month=" + LY.getMonth(),
                    "metroCd=" + code,
                    "apiKey="
            );
            apiCallResult.subscribe(response -> {
                try {
                    JsonNode Data = objectMapper.readTree(response);
                    JsonNode node = Data.get("data");

                    for (JsonNode rootNode : node) {
                        String metro = rootNode.path("metro").asText();
                        String city = rootNode.path("city").asText().replace(" ", "");
                        String town = "";
                        Optional<Location> LocationValue = locationRepository.findByLocationCityAndLocationCountryAndLocationTown(metro, city, town);
                        if (LocationValue.isPresent()) {
                            Location location = LocationValue.get();

                            // Electricity 엔티티 생성 및 데이터베이스 저장
                            Electricity electricity = Electricity.builder()
                                    .electricityYear(rootNode.path("year").asText())
                                    .electricityMonth(rootNode.path("month").asText())
                                    .electricityPopulation(rootNode.path("houseCnt").asInt())
                                    .electricityAverageUsage(rootNode.path("powerUsage").asDouble())
                                    .electricityAverageBill(rootNode.path("bill").asInt())
                                    .locationKey(location.getLocationKey())
                                    .build();

                            electricityRepository.save(electricity);
                        } else {
                            logger.warn("metro, city에서 오류 발생: {} , {}", metro, city);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error parsing JSON response", e);
                }
            });
        }
    }
}
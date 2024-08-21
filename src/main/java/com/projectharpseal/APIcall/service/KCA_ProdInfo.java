package com.projectharpseal.APIcall.service;

import com.projectharpseal.APIcall.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class KCA_ProdInfo {

    private final Public_service publicService;
    private static final Logger logger = LoggerFactory.getLogger(KCA_ProdPrice.class);

    public KCA_ProdInfo(Public_service publicService) {
        this.publicService = publicService;
    }

    public void parseApiCall() {
        // API 호출
        Mono<String> apiCallResult = publicService.makeApiCall(
                "KCA_URL",
                "KCA_ProdInfo_Path",
                "DATA_Key",
                "ServiceKey="
        );
        logger.info("result: {}", apiCallResult.block());
    }

}

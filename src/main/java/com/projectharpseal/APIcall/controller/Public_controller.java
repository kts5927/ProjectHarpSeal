package com.projectharpseal.APIcall.controller;

import com.projectharpseal.APIcall.service.KCA_ProdInfo;
import com.projectharpseal.APIcall.service.KCA_ProdPrice;
import com.projectharpseal.APIcall.service.KCA_StoreInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Public_controller {

    private final KCA_ProdPrice kcaProdPrice;
//    private final KCA_ProdInfo kcaProdInfo;
//    private final KCA_StoreInfo kcaStoreInfo;



    public Public_controller( KCA_ProdPrice kcaProdPrice ) {
        this.kcaProdPrice = kcaProdPrice;
//        this.kcaProdInfo = kcaProdInfo;
//        this.kcaStoreInfo = kcaStoreInfo;
    }

    @GetMapping("/test")
    public void getTest() {
        kcaProdPrice.parseApiCall();
    }

}

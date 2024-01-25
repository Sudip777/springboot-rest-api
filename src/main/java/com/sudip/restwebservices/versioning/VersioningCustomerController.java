package com.sudip.restwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Versioning of the APi
@RestController
public class VersioningCustomerController {


    // Version 1 : using URI
    @GetMapping("/v1/customer")
    public CustomerV1 getFirstVersionUser(){
        return new CustomerV1("John Snow");
    }
    // Version 2 : using URI
    @GetMapping("/v2/customer")
    public CustomerV2 getSecondVersionUser(){
        return new CustomerV2(new Name("John", "Snow"));
    }


    // Version 1 : using RequestParameter
    @GetMapping(value = "/customer", params = "version=1")
    public CustomerV1 getFirstVersionOfCustomerRequestParameter(){
        return new CustomerV1("John Snow");
    }
    // Version 2 : using RequestParameter
    @GetMapping(value = "/customer", params = "version=2")
    public CustomerV2 getSecondVersionofCustomerRequestParameter(){
        return new CustomerV2(new Name("John", "Snow"));
    }

    // Version 1 : using Header
    @GetMapping(value = "/customer/header", headers = "X-API-VERSION=1")
    public CustomerV1 getFirstVersionOfCustomerRequestHeader(){
        return new CustomerV1("John Snow");
    }
    // Version 2 : using Header
    @GetMapping(value = "/customer/header", headers = "X-API-VERSION=2")
    public CustomerV2 getSecondVersionOfRequestHeader(){
        return new CustomerV2(new Name("John", "Snow"));
    }

    // Version 1 : using Media Type
    @GetMapping(value = "/customer/accept", produces = "application/vnd.company.app-v1+json")
    public CustomerV1 getFirstVersionOfRCustomerAcceptHeader(){
        return new CustomerV1("John Snow");
    }

    // Version 2 : using Media Type
    @GetMapping(value = "/customer/accept", produces = "application/vnd.company.app-v2+json")
    public CustomerV2 getSecondVersionOfCustomerAcceptHeader(){
        return new CustomerV2(new Name("John", "Snow"));
    }


}

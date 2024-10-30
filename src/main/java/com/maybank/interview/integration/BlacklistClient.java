package com.maybank.interview.integration;


import com.maybank.interview.integration.generated.api.BlacklistApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${integration.blacklist.name:blacklist-client}",
        url = "${integration.blacklist.url}", configuration = IntegrationConfiguration.class)
public interface BlacklistClient extends BlacklistApi {
}

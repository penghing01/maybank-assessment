package com.maybank.interview.config;

import com.maybank.interview.persistence.entity.BankAccount;
import com.maybank.interview.web.generated.model.CreateAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfiguration {
    public static final String REGISTERING_MAPPING_FOR = "Registering mapping for {} : {} ";

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        this.registerBankAccountEntity(modelMapper);
        return modelMapper;
    }

    private void registerBankAccountEntity(ModelMapper modelMapper) {
        log.info(REGISTERING_MAPPING_FOR, CreateAccountRequest.class, BankAccount.class);
        TypeMap<CreateAccountRequest, BankAccount> myTypeMap =
                modelMapper.createTypeMap(CreateAccountRequest.class, BankAccount.class);
        myTypeMap.addMappings(new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setAccountHolder(source.getAccountHolder());
            }
        });
    }
}

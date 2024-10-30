package com.maybank.interview.config;

import com.maybank.interview.persistence.entity.BankAccount;
import com.maybank.interview.web.generated.model.CreateAccountRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    public static final String REGISTERING_MAPPING_FOR = "Registering mapping for {} : {} ";
    private static final Logger LOGGER = LogManager.getLogger(ApplicationConfiguration.class);

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        this.registerBankAccountEntity(modelMapper);
        return modelMapper;
    }

    private void registerBankAccountEntity(ModelMapper modelMapper) {
        LOGGER.info(REGISTERING_MAPPING_FOR, CreateAccountRequest.class, BankAccount.class);
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

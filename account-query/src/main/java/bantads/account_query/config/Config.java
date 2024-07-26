package bantads.account_query.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.account_query.mapper.CustomMapper;

@Configuration
public class Config {
  @Bean
  public ModelMapper createModelMapper(){
    return new ModelMapper();
  }

  @Bean
  public CustomMapper createCustomMapper(){
    return new CustomMapper();
  }

  @Bean
  public ObjectMapper createObjectMapper(){
    return new ObjectMapper();
  }
}

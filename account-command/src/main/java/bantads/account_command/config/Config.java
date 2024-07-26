package bantads.account_command.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bantads.account_command.mapper.CustomMapper;

@Configuration
public class Config {
  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }

  @Bean
  public CustomMapper customMapper(){
    return new CustomMapper();
  }
}

package bantads.msclient.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {
  @Bean
  public ModelMapper createModelMapper(){
    return new ModelMapper();
  }
}

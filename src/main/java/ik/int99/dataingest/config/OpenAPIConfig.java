package ik.int99.dataingest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("anthonydonx@gmail.com");
        contact.setName("Asanka Anthony");

        Info info = new Info()
                .title("Data Ingest API")
                .version("1.0")
                .contact(contact);

        return new OpenAPI().info(info);
    }


}
package fi.sdeska.citybike.data;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Component
public class JodaMapper extends ObjectMapper {

    JodaMapper() {
        registerModule(new JodaModule());
    }
    
}

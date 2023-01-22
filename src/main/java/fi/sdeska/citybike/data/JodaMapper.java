package fi.sdeska.citybike.data;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * This class allows Joda's DateType objects to be mapped into a database.
 */
@Component
public class JodaMapper extends ObjectMapper {

    /**
     * The constructor registers a new JodaModule for the application.
     */
    JodaMapper() {
        registerModule(new JodaModule());
    }
    
}

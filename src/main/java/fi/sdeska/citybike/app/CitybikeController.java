package fi.sdeska.citybike.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CitybikeController {

    @RequestMapping("/")
    public String home() {
        return "No place like home";
    }

}

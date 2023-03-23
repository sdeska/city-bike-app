package fi.sdeska.citybike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is the top layer of the application. Spring launches the application through this class.
 */
@SpringBootApplication
public class CitybikeApplication {

	/**
	 * The main method.
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CitybikeApplication.class, args);
	}

}

package faisu.net.Weather_API;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class WeatherService {

    private final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private final String API_KEY = "bb8260a516c8c5009f32dd610a17cc48";
    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ForecastData getForecastData(double lat, double lon) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String url = String.format("%s?lat=%f&lon=%f&appid=%s", WEATHER_API_URL, lat, lon, API_KEY);

        ResponseEntity<ForecastData> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                ForecastData.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve forecast data");
        }
    }
}

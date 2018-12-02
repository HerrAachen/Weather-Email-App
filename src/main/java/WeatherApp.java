import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest getWeatherHtml = HttpRequest
                .newBuilder()
                .uri(URI.create("https://weather.gc.ca/forecast/hourly/bc-74_metric_e.html"))
                .GET()
                .build();
        String weatherNetworkHourlyPageString = client.send(getWeatherHtml, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(weatherNetworkHourlyPageString);
    }
}

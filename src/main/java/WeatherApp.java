import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class WeatherApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest getWeatherHtml = HttpRequest
                .newBuilder()
                .uri(URI.create("https://weather.gc.ca/forecast/hourly/bc-74_metric_e.html"))
                .GET()
                .build();
        String weatherGcHourly = client.send(getWeatherHtml, HttpResponse.BodyHandlers.ofString()).body();
        String[] lines = weatherGcHourly.split("\n");
        List<Forecast> hourlyForecasts = new LinkedList<>();
        Forecast currentForecast = null;
        for(String line: lines) {
            if (line.contains("<td headers=\"header1\" class=\"text-center\">")) {
                currentForecast = new Forecast();
                currentForecast.setTime(getLocalTime(line));
            }
            if (line.contains("<td headers=\"header2\" class=\"text-center\">")) {
                currentForecast.setDegreesCelsius(getDegrees(line));
                hourlyForecasts.add(currentForecast);
            }
        }
        hourlyForecasts.stream().forEach(System.out::println);
    }

    private static double getDegrees(String line) {
        String degreesString = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
        return Double.parseDouble(degreesString);
    }

    private static LocalTime getLocalTime(String line) {
        String timeString = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
        return LocalTime.parse(timeString);
    }
}

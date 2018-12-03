import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class WeatherApp {
    public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException, XPathExpressionException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest getWeatherHtml = HttpRequest
                .newBuilder()
                .uri(URI.create("https://weather.gc.ca/forecast/hourly/bc-74_metric_e.html"))
                .GET()
                .build();
        String weatherGcHourly = client.send(getWeatherHtml, HttpResponse.BodyHandlers.ofString()).body();
        String[] lines = weatherGcHourly.split("\n");
        Arrays.stream(lines).forEach(line -> {
            if (line.contains("<td headers=\"header1\" class=\"text-center\">")) {
                System.out.println(line);
            }
            if (line.contains("<td headers=\"header2\" class=\"text-center\">")) {
                System.out.println(line);
            }
        });
    }
}

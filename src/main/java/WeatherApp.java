import com.amazonaws.services.lambda.runtime.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class WeatherApp implements RequestHandler<Request, Response> {
    public static void main(String[] args) {
        WeatherApp weatherApp = new WeatherApp();
        weatherApp.handleRequest(new Request(), new Context() {
            @Override
            public String getAwsRequestId() {
                return null;
            }

            @Override
            public String getLogGroupName() {
                return null;
            }

            @Override
            public String getLogStreamName() {
                return null;
            }

            @Override
            public String getFunctionName() {
                return null;
            }

            @Override
            public String getFunctionVersion() {
                return null;
            }

            @Override
            public String getInvokedFunctionArn() {
                return null;
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return null;
            }
        });
    }


    @Override
    public Response handleRequest(Request request, Context context) {
        try {
            URL url = new URL("https://weather.gc.ca/forecast/hourly/bc-74_metric_e.html");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.getResponseCode();
            String weatherGcHourly = readResponse(connection);
            String[] lines = weatherGcHourly.split("\n");
            List<Forecast> hourlyForecasts = new LinkedList<>();
            Forecast currentForecast = null;
            for (String line : lines) {
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
            return new Response();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine + "\r\n");
        }
        in.close();
        return content.toString();
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

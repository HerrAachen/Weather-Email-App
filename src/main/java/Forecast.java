import java.time.LocalTime;

public class Forecast {
    private LocalTime time;
    private double degreesCelsius;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getDegreesCelsius() {
        return degreesCelsius;
    }

    public void setDegreesCelsius(double degreesCelsius) {
        this.degreesCelsius = degreesCelsius;
    }

    @Override
    public String toString() {
        return "Forecast{" + "time=" + time + ", degreesCelsius=" + degreesCelsius + '}';
    }
}

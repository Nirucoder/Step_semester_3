package access_modifiers.class_problems;
import java.util.Arrays;

public class PatientVitals {
    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {
        this.readings = new double[500];
        this.count = 0;
        if (initialReadings != null) {
            for (double r : initialReadings) {
                recordReading(r);
            }
        }
    }

    public void recordReading(double reading) {
        if (reading > 0 && reading <= 45.0 && count < readings.length) {
            this.readings[count++] = reading;
        }
    }

    public double getAverage() {
        if (count == 0) return 0.0;
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }
        return sum / count;
    }

    public double[] getAllReadings() {
        return Arrays.copyOf(readings, count);
    }
}

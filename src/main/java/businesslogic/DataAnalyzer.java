package businesslogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Our DataAnalyzer is responsible for reading a numeric dataset from a file
 * and computing a few key statistical measures including count, mean,
 * standard deviation, and root mean square (RMS).
 *
 * This class is designed to efficiently process large datasets
 * (up to ~1,000,000 values) using a single-pass algorithm
 * without storing all values in memory.
 *
 * It uses Welford’s algorithm for numerically stable calculation
 * of mean and standard deviation.
 *
 * Author: Abu Jallow
 */
public class DataAnalyzer {

    // Total number of values processed from the file
    private int count = 0;

    // Running mean (average) of the dataset
    private double mean = 0.0;

    // Sum of squares of all values (used for RMS calculation)
    private double sumSquares = 0.0;

    // Accumulator used in Welford’s algorithm for variance calculation
    private double m2 = 0.0;

    /**
     * anaylzeFile reads a text file containing one numeric value per line and computes
     * statistical measures including count, mean, standard deviation, and RMS.
     * <p>
     * The file is processed line-by-line to handle large datasets efficiently
     * without storing all values in memory.
     * <p>
     * This method uses Welford’s algorithm for stable and accurate
     * calculation of mean and variance.
     *
     * @param filePath the path to the input data file
     * @throws IOException if an error occurs while reading the file
     */

    public void analyzeFile(String filePath) throws IOException {

        // Reset values in case method is called multiple times
        count = 0;
        mean = 0.0;
        sumSquares = 0.0;
        m2 = 0.0;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        double delta;

        // Read file line-by-line to handle large datasets efficiently
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            // Convert each line into a numeric value
            double x = Double.parseDouble(line.trim());

            count++;

            // Update running mean and variance using Welford’s algorithm
            delta = x - mean;
            mean += delta / count;
            m2 += delta * (x - mean);

            // Accumulate sum of squares for RMS calculation
            sumSquares += x * x;
        }

        reader.close();
    }

    /**
     * @return the total number of values processed
     */
    public int getCount() {
        return count;
    }

    /**
     * @return mean (average) of the dataset
     */
    public double getMean() {
        return mean;
    }

    /**
     * @return standard deviation of the dataset
     */
    public double getStandardDeviation() {
        if (count < 2) return 0.0;
        return Math.sqrt(m2 / (count - 1));
    }

    /**
     * @return root mean square (RMS) of the dataset
     */
    public double getRMS() {
        if (count == 0) return 0.0;
        return Math.sqrt(sumSquares / count);
    }

    /**
     * @return formatted string containing all computed statistics
     */
    public String getFormattedResults() {
        return "Count: " + getCount() + "\n" +
                "Mean: " + getMean() + "\n" +
                "Standard Deviation: " + getStandardDeviation() + "\n" +
                "RMS: " + getRMS();
    }
}
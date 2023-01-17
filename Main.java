import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * -- Online Sources used --
 * https://www.tutorialspoint.com/java/lang/system_nanotime.htm
 * https://www.w3schools.com/java/java_files_create.asp
 */

public class Main {
    public static void main(String[] args) {
        double base = 3.14159265359;

        try {
            File file = new File("output.csv");
            FileWriter writer = new FileWriter("output.csv");
            long prevItTime = 0;
            long prevReTime = 0;

            for (int i = 0; i < 35000; i++) {
                long iterStart = System.nanoTime();
                iterativePower(base, i);
                long iterEnd = System.nanoTime();

                long recStart = System.nanoTime();
                recursivePower(base, i);
                long recEnd = System.nanoTime();

                // Sometimes calculating the time the function takes to execute using System.nanoTime() for an operation would yield obvious outliers.
                // Every single time this happened it was an outlier greater than the rest of the data, never less than. Hence why I only have a > statement.
                // This block of code is meant to prevent outliers.
                if ((recEnd - recStart) - prevReTime > 3000) { // Duration of current recursive function call is over 3000 nanoseconds higher than the previous. So try again.
                    i--;
                } else if ((iterEnd - iterStart) - prevItTime > 3000){ // Duration of the current iterative function call is over 3000 nanoseconds higher than the previous. So try again.
                    i--;
                } else { // No outliers, so print write the data to the .csv file.
                    writer.write(i + ",");
                    writer.write((iterEnd - iterStart) + ",");
                    writer.write((recEnd - recStart) + "\n");
                    prevItTime = (iterEnd - iterStart);
                    prevReTime = (recEnd - recStart);
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error");
        }
    }

    public static double iterativePower(double base, int exp) {
        double returnVal = 1.0;
        if (exp < 0) {
            return 1.0 / iterativePower(base, -exp);
        } else {
            for (int i = 0; i < exp; i++) {
                returnVal *= base;
            }
        }
        return returnVal;
    }

    public static double recursivePower(double base, int exp) {
        if (exp < 0) {
            return 1.0 / recursivePower(base, -exp);
        } else if (exp == 0) {
            return 1.0;
        } else {
            return base * recursivePower(base, exp - 1);
        }
    }
}
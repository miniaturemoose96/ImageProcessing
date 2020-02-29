package Project1;

import java.io.*;

public class Runner {

    public static void main(String[] args) {

        String filePath = "src/Project1/Images/blue_bird.ppm";
        String outputFile = "src/Project1/Images/out.ppm";

        PPMImage image = new PPMImage(new File(filePath));
        image.hideData("Hello there, my name is Erik. How are you doing?\0");
        image.writeImage(new File(outputFile));
    }
}
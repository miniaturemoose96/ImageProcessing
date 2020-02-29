package Project1;

import java.io.*;

public class PPMImage {

    private char[][][] raster;
    private String magicNumber = "";
    private int height;
    private int width;
    private int maxColorValue;

    public PPMImage(File image) {
        readImage(image);
    }

    private void readImage(File image) {
        // Get magic number, width, height, max value color
        try {
            InputStream inputStream = new FileInputStream(image);
            // Buffered Reader to get the header options
            // Get Magic number by reading char by char
            char first;

            StringBuilder sb = new StringBuilder();

            while ((first = (char) inputStream.read()) != '\n') {
                sb.append(first);
            }
            magicNumber = sb.toString();
            // System.out.println(magicNumber);

            // Split and make a number width and another height
            // String to int for height and Width
            char second;
            StringBuilder sb2 = new StringBuilder();
            String wh = "";
            while ((second = (char) inputStream.read()) != '\n') {
                sb2.append(second);
            }
            wh = sb2.toString();
            String[] splitWH = wh.split(" ");

            width = Integer.parseInt(splitWH[0]);
            height = Integer.parseInt(splitWH[1]);

            // Get the max value
            char third;
            String mCV = "";
            StringBuilder sb3 = new StringBuilder();
            while ((third = (char) inputStream.read()) != '\n') {
                sb3.append(third);
            }
            mCV = sb3.toString();
            maxColorValue = Integer.parseInt(mCV);

            // Create the 3d array using the information
            raster = new char[height][width][3];

            // Fill array with the pixel data
            for (int i = 0; i < raster.length; i++) {
                for (int j = 0; j < raster[i].length; j++) {
                    for (int k = 0; k < raster[i][j].length; k++) {
                        raster[i][j][k] = (char) inputStream.read();
                    }
                }
            }
            // TODO Do bit manipulation here...
            // TODO Call write image on the Runner class
            //writeImage(new File("src/Project1/Images/out.ppm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeImage(File filename) {
        try {
            OutputStream writer = new FileOutputStream(filename);

            writer.write(String.format("%s\n", magicNumber).getBytes());
            writer.write(String.format("%d %d\n", width, height).getBytes());
            writer.write(String.format("%d\n", maxColorValue).getBytes());

            for (int i = 0; i < raster.length; i++) {
                for (int j = 0; j < raster[i].length; j++) {
                    for (int k = 0; k < raster[i][j].length; k++) {
                        writer.write(raster[i][j][k]);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hideData(String message) {
        // TODO Check that requiredHeight is less than the image height: Image is too small
        char[] characters = message.toCharArray();

        int messageLength = message.length();
        int requiredSpace = messageLength * 8; // 8 bits
        int remainingBits = requiredSpace % 3; // for RGB channels
        int bits = requiredSpace / 3;

        int requiredWidth = bits + remainingBits;
        int requiredHeight = requiredWidth / raster.length + 1;

        int line = 0;

        for (int i = 0; i < requiredHeight; i++) {
            for (int j = 0; j < requiredWidth; j++) {
                for (int k = 0; k < 3; k++) {
                    if (remainingBits > 0 && messageLength == line / 8) {
                        break;
                    }

                    char character = characters[line / 8];
                    int bitPosition = line++ % 8;
                    int bit = character >> bitPosition & 1;

                    raster[i][j][k] |= bit;
                }
            }
        }
    }

    public void recoverData() {

    }
}

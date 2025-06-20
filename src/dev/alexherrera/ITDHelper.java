package dev.alexherrera;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;


// Created Sunday, October 13th 2024
//
// Image to Display helper.
// Helper class aids in file handling and conversion of images
// to work with e-ink (monochrome) displays.
// Note: All functions are chainable.
public class ITDHelper {

    private boolean IS_VERBOSE = false;

    // Where input and output files are located
    //
    private String WORKING_DIRECTORY = "";
    private String INPUT_FILE_NAME = "";
    private String OUTPUT_FILE_NAME = "";
    //
    // Dimensions of input image file.
    // Note: Image dimensions must be the same as the e-ink display
    //
    // In pixels
    private int DIMEN_IMAGE_WIDTH = 296;
    private int DIMEN_IMAGE_HEIGHT = 128;
    //
    // Render image to console using simple characters.
    // NOTE: If output is wrong; skewed; aggregate-looking, adjust RENDER_DELIMITER.
    private int RENDER_DELIMITER = DIMEN_IMAGE_HEIGHT;
    private int RENDER_THRESHOLD = 255;
    //
    // Where the converted image data is stored
    private StringBuilder stringBuilderExport;

    // ---------- Getters ----------
    public int getImageWidth() {
        return this.DIMEN_IMAGE_WIDTH;
    }
    public int getImageHeight() {
        return this.DIMEN_IMAGE_HEIGHT;
    }
    public int getRenderDelimiter() {
        return this.RENDER_DELIMITER;
    }
    public String getWorkingDirectory() {
        return this.WORKING_DIRECTORY;
    }
    public String getInputFileName() {
        // Guard
        if (INPUT_FILE_NAME == null || INPUT_FILE_NAME.length() < 1) { throw new RuntimeException("Input file name is not set. Was #.setInputFileName() called?");}
        return this.INPUT_FILE_NAME;
    }
    public ITDHelper getContext() {
        return this;
    }
    public String getRender() {
        // Guard
        if (stringBuilderExport == null || stringBuilderExport.length() < 1) { throw new RuntimeException("Could not get render. Was #.render() called?");}
        return stringBuilderExport.toString();
    }
    public String getOutputFileName() {
        // Guard
        if (OUTPUT_FILE_NAME == null || OUTPUT_FILE_NAME.length() < 1) { throw new RuntimeException("Output file name is not set. Was #.setOutputFileName() called?");}
        return this.OUTPUT_FILE_NAME;
    }
    public int getRenderThreshold() {
        return this.RENDER_THRESHOLD;
    }

    // ---------- Setters ----------
    public ITDHelper setImageWidth(int IMAGE_WIDTH) {
        if (IMAGE_WIDTH >= 0 && IMAGE_WIDTH < Integer.MAX_VALUE) {
            this.DIMEN_IMAGE_WIDTH = IMAGE_WIDTH;
        }
        return this;
    }
    public ITDHelper setImageHeight(int IMAGE_HEIGHT) {
        if (IMAGE_HEIGHT >= 0 && IMAGE_HEIGHT < Integer.MAX_VALUE) {
            this.DIMEN_IMAGE_HEIGHT = IMAGE_HEIGHT;
        }
        return this;
    }
    public ITDHelper setRenderDelimiter(int RENDER_DELIMITER) {
        if (RENDER_DELIMITER < Integer.MAX_VALUE) {
            this.RENDER_DELIMITER = RENDER_DELIMITER;
        }
        return this;
    }
    public ITDHelper setWorkingDirectory(String WORKING_DIRECTORY) {
        if (WORKING_DIRECTORY.length() > 0) {
            this.WORKING_DIRECTORY = WORKING_DIRECTORY;
        }
        return this;
    }
    public ITDHelper setInputFileName(String INPUT_FILE_NAME) {
        if (INPUT_FILE_NAME.length() > 0) {
            this.INPUT_FILE_NAME = INPUT_FILE_NAME;
        }
        return this;
    }
    public ITDHelper verbose() {
        this.IS_VERBOSE = true;
        return this;
    }
    public ITDHelper setOutputFileName(String OUTPUT_FILE_NAME) {
        if (OUTPUT_FILE_NAME.length() > 0) {
            this.OUTPUT_FILE_NAME = OUTPUT_FILE_NAME;
        }
        return this;
    }
    public ITDHelper setRenderThreshold(int RENDER_THRESHOLD) {
        if (RENDER_THRESHOLD >= 0 && RENDER_THRESHOLD <= 255) {
            this.RENDER_THRESHOLD = RENDER_THRESHOLD;
        } else {
            throw new RuntimeException("Threshold must be between 0 and 255!");
        }
        return this;
    }

    public ITDHelper() {
        // No arg constructor
    }

    public ITDHelper(String INPUT_FILE_NAME) {
        this.INPUT_FILE_NAME = INPUT_FILE_NAME;
    }

    public ITDHelper(String INPUT_FILE_NAME, String WORKING_DIRECTORY) {
        this.INPUT_FILE_NAME = INPUT_FILE_NAME;
        this.WORKING_DIRECTORY = WORKING_DIRECTORY;
    }

    public ITDHelper(String INPUT_FILE_NAME, String WORKING_DIRECTORY, int IMAGE_WIDTH, int IMAGE_HEIGHT) {
        this.INPUT_FILE_NAME = INPUT_FILE_NAME;
        this.WORKING_DIRECTORY = WORKING_DIRECTORY;
        this.DIMEN_IMAGE_WIDTH = IMAGE_WIDTH;
        this.DIMEN_IMAGE_HEIGHT = IMAGE_HEIGHT;
        this.RENDER_DELIMITER = DIMEN_IMAGE_HEIGHT;
    }

    public ITDHelper(String INPUT_FILE_NAME, String WORKING_DIRECTORY, int IMAGE_WIDTH, int IMAGE_HEIGHT, int RENDER_DELIMITER) {
        this.INPUT_FILE_NAME = INPUT_FILE_NAME;
        this.WORKING_DIRECTORY = WORKING_DIRECTORY;
        this.DIMEN_IMAGE_WIDTH = IMAGE_WIDTH;
        this.DIMEN_IMAGE_HEIGHT = IMAGE_HEIGHT;
        this.RENDER_DELIMITER = RENDER_DELIMITER;
    }


    // Renders the input image into hex. Ready for export or print to console.
    // NOTE: This only works for black and white images.
    public ITDHelper render() throws Exception {
        // Guard
        if (WORKING_DIRECTORY == null || WORKING_DIRECTORY.length() < 1) { throw new RuntimeException("Working directory is not set. Was #.setWorkingDirectory() called?");}
        if (INPUT_FILE_NAME == null || INPUT_FILE_NAME.length() < 1) { throw new RuntimeException("Input file name is not set. Was #.setInputFileName() called?");}

        if (IS_VERBOSE) {
            System.out.println("Rendering: " + DIMEN_IMAGE_WIDTH + "x" + DIMEN_IMAGE_HEIGHT + "\n" +
                    "From Directory: " + WORKING_DIRECTORY + "\n" +
                    "File: " + INPUT_FILE_NAME + "\n" +
                    "Render Delimiter: " + RENDER_DELIMITER + "\n" +
                    "Render Threshold: " + RENDER_THRESHOLD);
        }

        if (!INPUT_FILE_NAME.endsWith(".png")) {
            if (IS_VERBOSE) {
                System.out.println("For best results, use *.png files.");
            }
        }

        File fileImage = new File(WORKING_DIRECTORY, INPUT_FILE_NAME);
        BufferedImage bufferedImage = ImageIO.read(fileImage);

        // Image input checks
        //
        // Expected image to match width
        if (bufferedImage.getWidth() != DIMEN_IMAGE_WIDTH) {
            if (IS_VERBOSE) {
                System.err.println("Image " + INPUT_FILE_NAME + " has a width of " + bufferedImage.getWidth() + ". Expected " + DIMEN_IMAGE_WIDTH);
            }
        }

        // Expected image to match height
        if (bufferedImage.getHeight() != DIMEN_IMAGE_HEIGHT) {
            if (IS_VERBOSE) {
                System.err.println("Image " + INPUT_FILE_NAME + " has a height of " + bufferedImage.getHeight() + ". Expected " + DIMEN_IMAGE_HEIGHT);
            }
        }


        // Render image to console using simple characters.
        int new_line_delimiter = 0;

        // String to write to export file.
        stringBuilderExport = new StringBuilder();

        stringBuilderExport.append("\n");
        stringBuilderExport.append("const unsigned char ITD" + getInputFileName() + "[] = {\n");

        // Initialize a variable to accumulate binary digits
        int binaryAccumulator = 0;
        int pixelCount = 0;

        // For each pixel
        for (int index_width = 0; index_width < DIMEN_IMAGE_WIDTH; index_width++) {
            for (int index_height = 0; index_height < DIMEN_IMAGE_HEIGHT; index_height++) {

                // Grab color from pixel
                int col = bufferedImage.getRGB(index_width, index_height);
                int colShift = (col & 0x00FF0000) >> 16;  // Extracting the red component (assuming white is 255)

                if (IS_VERBOSE) {
                    System.out.print(colShift >= RENDER_THRESHOLD ? " " : "█");
                }

                // Add binary representation to the accumulator
                binaryAccumulator <<= 1;  // Shift left by 1 to make space for the new bit
                if (colShift >= RENDER_THRESHOLD) {
                    binaryAccumulator |= 1;  // Set the least significant bit to 1 (white)
                }

                pixelCount++;

                // Once we have accumulated 8 pixels, convert them to hex
                if (pixelCount == 8) {
                    String hexString = String.format("0x%02X", binaryAccumulator);
                    stringBuilderExport.append(hexString.concat(","));

                    // Reset accumulator and pixel count
                    binaryAccumulator = 0;
                    pixelCount = 0;
                }

                // Handle newline after certain pixels (like after every row)
                if (new_line_delimiter == RENDER_DELIMITER) {
                    stringBuilderExport.append("\n");
                    if (IS_VERBOSE) {
                        System.out.println();
                    }
                    new_line_delimiter = 0;
                }

                new_line_delimiter++;
            }
        }

        // If there are leftover pixels (less than 8), convert them to hex
        if (pixelCount > 0) {
            // Shift the remaining bits to the left so they represent the correct binary value
            binaryAccumulator <<= (8 - pixelCount);
            String hexString = String.format("0x%02X", binaryAccumulator);
            stringBuilderExport.append(hexString + " ");
        }

        stringBuilderExport.append("};\n");

        return this;
    }

    // Creates the file for the data to be written to
    public ITDHelper export() throws Exception{
        if (WORKING_DIRECTORY == null || WORKING_DIRECTORY.length() < 1) { throw new RuntimeException("Working directory is not set. was #.setWorkingDirectory() called?");}
        if (INPUT_FILE_NAME == null || INPUT_FILE_NAME.length() < 1) { throw new RuntimeException("Input file name is not set. Was #.setInputFileName() called?");}
        if (stringBuilderExport == null || stringBuilderExport.length() < 1) { throw new RuntimeException("Could not export. Was #.render() called?");}
        if (OUTPUT_FILE_NAME == null || OUTPUT_FILE_NAME.length() < 1) { throw new RuntimeException("Output file name is not set. Was #.setOutputFileName() called?");}

        File output_file = new File(WORKING_DIRECTORY, OUTPUT_FILE_NAME);
        if (output_file.exists()) {
            output_file = new File(WORKING_DIRECTORY, OUTPUT_FILE_NAME + UUID.randomUUID());
        }

        if (!output_file.createNewFile()) {
            throw new Exception("Failed to create new file for output.");
        }

        try (FileWriter fileWriter = new FileWriter(output_file)) {
            fileWriter.write(stringBuilderExport.toString());
            fileWriter.flush();
        }


        return this;
    }

}

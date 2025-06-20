# Image to Hex Converter API

## Overview
The **Image to Hex Converter API** (`ITDHelper`) is a Java-based helper class designed to process image files and convert them into a monochrome hexadecimal representation suitable for e-ink displays. This API simplifies handling, rendering, and exporting image data into a format that can be used for embedded systems and display applications.

## Features
- Convert black-and-white images to a hexadecimal format
- Adjustable rendering parameters such as threshold and delimiter
- Support for different image dimensions
- Verbose mode for detailed logging
- Chainable function calls for streamlined operations

## Installation
To use `ITDHelper`, ensure that your Java project includes the required dependencies for image processing:

```java
import dev.alexherrera.ITDHelper;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
```

## Usage

### 1. Initialize the Helper
You can initialize `ITDHelper` with or without parameters:

```java
// Default constructor
ITDHelper helper = new ITDHelper();

// With parameters
ITDHelper helper = new ITDHelper("input.png", "./images", 296, 128);
```

### 2. Configure Settings
Set the working directory, file names, and rendering properties as needed:

```java
helper.setWorkingDirectory("./images")
      .setInputFileName("input.png")
      .setOutputFileName("output.txt")
      .setRenderThreshold(200) // Adjust threshold for better contrast
      .verbose(); // Enable logging
```

### 3. Render the Image to Hex
Convert the image into a hexadecimal format:

```java
helper.render();
```

### 4. Export the Rendered Data
Save the converted data to a file:

```java
helper.export();
```

### 5. Retrieve Rendered Data
If you want to access the rendered hex output as a string:

```java
String hexOutput = helper.getRender();
System.out.println(hexOutput);
```

## Configuration Options
| Method | Description |
|--------|-------------|
| `setImageWidth(int width)` | Set the expected width of the image. |
| `setImageHeight(int height)` | Set the expected height of the image. |
| `setRenderThreshold(int threshold)` | Define the grayscale threshold (0-255). |
| `setWorkingDirectory(String path)` | Specify the folder containing input/output files. |
| `setInputFileName(String filename)` | Set the input image filename. |
| `setOutputFileName(String filename)` | Define the output file for hex data. |
| `verbose()` | Enable verbose logging for debugging. |

## Notes
- The input image must be black and white for proper conversion.
- Ensure that the image dimensions match the expected display size.
- The exported hex data can be used for embedded systems or e-ink displays.

## Example Output
A sample hexadecimal output from an image:

```c
const unsigned char ITDinput[] = {
    0xFF, 0xF0, 0x0F, 0x00, // Sample data
    0x00, 0xF0, 0xFF, 0x00,
    ...
};
```

## License
This project is open-source and free to use. Contributions are welcome!



package dev.alexherrera;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;

public class Main {

    // Entry point
    //
    public static void main(String[] args){
        new Main().init(args);
    }



    // Main dish. Bone app the teeth.
    //
    public void init(String[] args) {

        // Helper class
        final ITDHelper itdHelper = new ITDHelper();
        // Set the root directory where the input and exported
        // file will be stored
        itdHelper.setWorkingDirectory("//Users//User//Desktop//");

        itdHelper.setInputFileName("example_image.png");
        itdHelper.setOutputFileName("example_code.cpp");

        try {
            // Converting from a file with transparency works better than jpg.
            // Less artifacts around pixels.
            itdHelper.verbose().render().export();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

package com.example.vitae;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public final class FileUtils {

    public static void writeFile(Context context, String filename, Set<String> text) {
        try {
            FileOutputStream outputStream =
                    context.openFileOutput(filename, Context.MODE_PRIVATE);
            for (String i : text) {
                outputStream.write(i.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<String> readFile(Context context, String filename) {
        Set<String> lines = new HashSet<>();
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (FileNotFoundException e) {
            lines.add("Rip");
        }
        catch (IOException e) {
            lines.add("Ripperino");
        }
        return lines;
    }
}

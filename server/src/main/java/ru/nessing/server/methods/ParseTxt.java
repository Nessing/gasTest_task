package ru.nessing.server.methods;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ParseTxt {
    public static ArrayList<String> parse(String format, File fileEntry, Pattern pattern) {
        if (!format.equals("txt")) return null;
        ArrayList<String> strings = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileEntry));
            strings.add(fileEntry.getAbsolutePath());
            while (reader.ready()) {
                Matcher matcher = pattern.matcher(reader.readLine());
                while (matcher.find()) {
                    strings.add(matcher.group());
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }
}

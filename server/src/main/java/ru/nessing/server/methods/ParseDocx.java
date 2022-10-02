package ru.nessing.server.methods;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import ru.nessing.server.interfaces.ParseInterface;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseDocx implements ParseInterface {
    public ArrayList<String> parse(String format, File fileEntry, Pattern pattern) {
        if (!format.equals("docx")) return null;
        ArrayList<String> strings = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileEntry.getAbsolutePath());
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);

            strings.add(fileEntry.getAbsolutePath());
            Matcher matcher = pattern.matcher(extractor.getText());
            while (matcher.find()) {
                strings.add(matcher.group());
            }
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strings;
    }
}

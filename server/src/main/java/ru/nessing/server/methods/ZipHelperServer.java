package ru.nessing.server.methods;

import org.apache.commons.io.FilenameUtils;
import ru.nessing.server.interfaces.CollectInterface;
import ru.nessing.server.interfaces.ParseFolderInterface;
import ru.nessing.server.interfaces.UnzipInterface;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipHelperServer implements CollectInterface, ParseFolderInterface, UnzipInterface {
    private static List<ArrayList<String>> arrayNames = new ArrayList<>();
    private final Pattern pattern = Pattern.compile("([A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+)");

    @Override
    public void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> parseFolder(File folder) {
        if (!folder.exists()) {
            try {
                throw new FileNotFoundException("File not found");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                parseFolder(fileEntry);
            } else {
                String format = FilenameUtils.getExtension(fileEntry.getAbsolutePath());
                arrayNames.add(ParseDocx.parse(format, fileEntry, pattern));
                arrayNames.add(ParseTxt.parse(format, fileEntry, pattern));
            }
        }
        arrayNames.removeAll(Collections.singleton(null));
        return collect(arrayNames);
    }

    public List<List<String>> collect(List<ArrayList<String>> array) {
        List<List<String>> all = new ArrayList<>();
        List<String> innerList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).size() < 4) break;
            ArrayList<String> list = array.get(i);

            for (int l = i + 1; l < array.size(); l++) {
                ArrayList<String> listCheck = array.get(l);
                Set<String> names = new HashSet<>();
                int count = 0;

                for (int j = 1; j < list.size(); j++) {
                    String firstName = list.get(j);

                    for (int k = 1; k < listCheck.size(); k++) {
                        String secondName = listCheck.get(k);
                        if (firstName.equals(secondName)) {
                            count++;
                            names.add(firstName);
                            if (count >= 3) {
                                innerList.add(list.get(0));
                                innerList.add(listCheck.get(0));

                                names.stream().forEach(e -> innerList.add(e));

                                all.add(new ArrayList<>(innerList));
                                innerList.clear();
                                count = 0;
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return all;
    }
}

package ru.nessing.server.interfaces;

import java.io.File;
import java.util.List;

public interface ParseFolderInterface {
    List<List<String>> parseFolder(File folder);
}

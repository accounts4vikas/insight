package org.insight.test;


import java.util.*;
import java.io.*;


public final class FileListings {

  public List<File> getFileListing(File dirPath) throws FileNotFoundException {
    validateDirectory(dirPath);
    List<File> result = getFileListingNoSort(dirPath);
    Collections.sort(result);
    return result;
  }

  private List<File> getFileListingNoSort(File dirPath) throws FileNotFoundException {
    List<File> result = new ArrayList();
    File[] filesAndDirs = dirPath.listFiles();
    List<File> filesDirs = Arrays.asList(filesAndDirs);
    for(File file : filesDirs) {
      if(file.isFile() && !file.isHidden()) {
          result.add(file);
      }
    }
    return result;
  }

  private void validateDirectory (File dirPath) throws FileNotFoundException {
    if (dirPath == null) {
      throw new IllegalArgumentException("Directory should not be null.");
    }
    if (!dirPath.exists()) {
      throw new FileNotFoundException("Directory does not exist: " + dirPath);
    }
    if (!dirPath.isDirectory()) {
      throw new IllegalArgumentException("Is not a directory: " + dirPath);
    }
    if (!dirPath.canRead()) {
      throw new IllegalArgumentException("Directory cannot be read: " + dirPath);
    }
  }
} 
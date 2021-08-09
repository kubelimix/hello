package com.limix.tool;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipDir {

    public static void main(String[] args){
        System.out.println(args[0]);
        File file = new File(args[0]);
        listJarFile(file);
    }

    public static void listJarFile(File file) {
        if (!file.exists()) return;
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()){
                    System.out.print(entry.getName() + "\n");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return;
    }
}

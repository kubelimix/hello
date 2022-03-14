package com.limix.tool;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Pom信息提取
 */
public class PomExtractor {
    private final StringBuilder stringBuilder = new StringBuilder(10240);
    private String groupId;
    private String artifactId;
    private String version;

    private PomExtractor() {
    }

    /**
     * 设计模式 单例模式
     * @return
     */
    public static PomExtractor getInstance() {
        return PomExtractorHolder.pomExtractor;
    }

    private static class PomExtractorHolder {
        private final static PomExtractor pomExtractor = new PomExtractor();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("入参是jar所在的文件夹!");
            return;
        }
        File cwd = new File(args[0]);
        File[] archives = cwd.listFiles(new JarFilter());
        PomExtractor pomExtractor = PomExtractor.getInstance();
        for (int j = 0; j < archives.length; j++) {
            pomExtractor.commence();
            pomExtractor.analyzeJarFile(archives[j]);
        }
    }

    public void commence() {
        stringBuilder.setLength(0);
    }

    private void resetData() {
        groupId = null;
        artifactId = null;
        version = null;
    }

    public void analyzeJarFile(File file) {
        if (!file.exists()) return;
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) { // 设计模式 迭代器模式
                ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith("pom.xml")) {
                    resetData();
                    boolean hasParent = false;
                    boolean enterParent = false;
                    try (Scanner scanner = new Scanner(zipFile.getInputStream(entry), "US-ASCII")) {
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            if (line.contains("parent")) {
                                enterParent = !enterParent;
                                hasParent = true;
                            } else if (line.contains("<groupId>")) {
                                groupId = line;
                            } else if (!enterParent && line.contains("<artifactId>")) {
                                artifactId = line;
                            } else if (line.contains("<version>")) {
                                version = line;
                            } else if (!hasParent && groupId != null && artifactId != null && version != null) {
                                break;
                            } else if (line.contains("<dependencies>") || line.contains("<dependency>") || line.contains("<properties>") || line.contains("<profiles>") || line.contains("<plugins>")) {
                                break;
                            }
                        }
                        if (groupId != null && artifactId != null && version != null) {
                            System.out.println("<dependency>");
                            System.out.println(groupId);
                            System.out.println(artifactId);
                            System.out.println(version);
                            System.out.println("</dependency>");
                        } else {
                            stringBuilder.append("pom.xml解析异常，当前jar文件是" + file.getCanonicalPath() + ",解析失败的文件是" + entry.getName());
                        }
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return;
    }


    static class JarFilter implements FileFilter {
        public boolean accept(File pathName) {
            String upcase = pathName.getName().toUpperCase();
            return upcase.endsWith(".JAR");
        }
    }
}
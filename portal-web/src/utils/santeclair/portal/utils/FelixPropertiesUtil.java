package com.mmdi.projet.pivo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FelixPropertiesUtil {

    private static final SortedSet<String> exportPackageSet = new TreeSet<>();

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String configFile = "";
        if (args.length != 0) {
            configFile = args[0];
        }

        String jarFilePath = "";
        String jarVersion = "";
        String basePackage = "";
        if (configFile.length() > 0) {
            File propertiesXmlFile = new File(configFile);
            try (FileInputStream propertiesXmlFileInputStream = new FileInputStream(
                            propertiesXmlFile)) {
                Properties properties = new Properties();
                properties.loadFromXML(propertiesXmlFileInputStream);
                for (Object key : properties.keySet()) {
                    StringTokenizer propertieTokenizer = new StringTokenizer(
                                    properties.getProperty((String) key), "|");
                    int index = 0;
                    while (propertieTokenizer.hasMoreElements()) {
                        switch (index) {
                        case 0:
                            jarFilePath = propertieTokenizer.nextToken();
                            break;
                        case 1:
                            jarVersion = propertieTokenizer.nextToken();
                            break;
                        case 2:
                            basePackage = propertieTokenizer.nextToken();
                            break;
                        default:
                            String defaultToken = propertieTokenizer
                                            .nextToken();
                            System.err.println("Le token : " + defaultToken
                                            + " � l'index : " + index
                                            + " n'est pas g�r� par le syst�me.");
                            break;
                        }
                        index++;
                    }
                    printFelixProperties(jarFilePath, basePackage, jarVersion);
                }
            }

        } else {

            try (Scanner scanIn = new Scanner(System.in)) {
                System.out.println("Enter jar file absolute path here : ");
                jarFilePath = scanIn.nextLine();

                System.out.println("Enter jar version : ");
                jarVersion = scanIn.nextLine();

                System.out.println("Enter base package jar version : ");
                basePackage = scanIn.nextLine();
                printFelixProperties(jarFilePath, basePackage, jarVersion);
            }
        }
        for (String exportPackage : exportPackageSet) {
            System.out.println(exportPackage);
        }

    }

    private static void printFelixProperties(String jarFilePath,
                    String basePackage, String jarVersion) throws IOException {
        try (JarFile jarFile = new JarFile(new File(jarFilePath))) {
            basePackage = basePackage.replaceAll("\\.", "/");
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                if (jarEntry.isDirectory()) {
                    String jarEntryName = jarEntry.getName();
                    if (!jarEntryName.contains("META-INF/")
                                    && jarEntryName.startsWith(basePackage)) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(jarEntryName.substring(0,
                                        jarEntryName.length() - 1).replaceAll("/", "."));
                        sb.append("; ");
                        sb.append("version=").append(jarVersion).append(",\\");
                        exportPackageSet.add(sb.toString());
                    }
                } else if (!jarEntry.isDirectory()) {
                    String jarClassEntryname = jarEntry.getName();
                    if (jarClassEntryname.endsWith("class") && !jarClassEntryname.startsWith("java/")) {
                        String jarEntryName = jarClassEntryname.substring(0, jarEntry.getName().lastIndexOf("/") + 1);
                        if (!jarEntryName.contains("META-INF")) {
                            StringBuffer sb = new StringBuffer();
                            sb.append(jarEntryName.substring(0,
                                            jarEntryName.length() - 1).replaceAll("/", "."));
                            sb.append("; ");
                            sb.append("version=").append(jarVersion).append(",\\");
                            exportPackageSet.add(sb.toString());
                        }
                    }
                }
            }
        }
    }

}

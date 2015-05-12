package io.minetweak.instalauncher;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

/**
 * @author Logan Gorence
 */
public class MergeJars {

    public static void mergeJars() throws ZipException {
        ZipParameters zps = new ZipParameters();
        zps.setIncludeRootFolder(false);
        ZipFile minecraftZip = new ZipFile("minecraft_server.1.8.jar");
        ZipFile minetweakZip = new ZipFile("mtrelease.zip");
        minecraftZip.extractAll("mergeDir/");
        minetweakZip.extractAll("mergeDir/");
        new ZipFile("minecraft_server.jar").createZipFileFromFolder(
                "mergeDir/",
                zps,
                false,
                0L
        );
    }

}

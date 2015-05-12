package io.minetweak.instalauncher;

import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Logan Gorence
 */
public class Instalauncher {

    private static final String MC_URL = "https://s3.amazonaws.com/Minecraft.Download/versions/1.8.4/minecraft_server.1.8.4.jar";
    private static final String LW_URL = "http://nexus.directcode.org/nexus/service/local/repositories/snapshots/content/io/minetweak/launchwrapper/1.0-SNAPSHOT/launchwrapper-1.0-20150512.025255-1.jar";
    private static final String MT_URL = "https://ci.directcode.org/guestAuth/repository/download/Minetweak_Minetweak/.lastSuccessful/mtrelease.zip";

    public static void main(String[] args) {
        File minecraft_server = new File("minecraft_server.1.8.jar");
        File launchwrapper = new File("launchwrapper.jar");
        File mtrelease = new File("mtrelease.zip");

        if (!minecraft_server.exists()) {
            try {
                FileUtils.copyURLToFile(new URL(MC_URL), minecraft_server);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }

        if (!launchwrapper.exists()) {
            try {
                FileUtils.copyURLToFile(new URL(LW_URL), launchwrapper);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        if (!mtrelease.exists()) {
            try {
                FileUtils.copyURLToFile(new URL(MT_URL), mtrelease);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        // TODO Check for updates with Minetweak, and signal a repatch if there is one
        if (!new File("minecraft_server.jar").exists()) {
            try {
                MergeJars.mergeJars();
            } catch (ZipException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("java",
                "-cp",
                "launchwrapper.jar:" +
                        "minecraft_server.jar",
                "net.minecraft.launchwrapper.Launch",
                "--tweakClass",
                "io.minetweak.launch.MinetweakTweaker"
        );
        pb.inheritIO();
        try {
            pb.start().waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

package test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class UpdateThread implements Runnable {

    public static String version = "";

    public UpdateThread() {
        Thread thread = new Thread(this, "checker");
        thread.start();
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            in = new URL("https://raw.githubusercontent.com/DoctorFTB/customupdatechecker/master/update.txt").openStream();
            version = IOUtils.readLines(in, StandardCharsets.UTF_8).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
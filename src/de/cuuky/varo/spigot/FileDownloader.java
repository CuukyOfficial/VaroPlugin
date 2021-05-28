package de.cuuky.varo.spigot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileDownloader {

    protected String link, path;

    public FileDownloader(String link, String path) {
        this.link = link;
        this.path = path;
    }

    private void startDownload(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("User-Agent", "Mozilla");
        conn.setInstanceFollowRedirects(true);
        HttpURLConnection.setFollowRedirects(true);

        boolean redirect = false;
        int status = conn.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true;
        }

        if (redirect)
            this.startDownload(conn.getHeaderField("Location"));
        else {
            new File(this.path).getParentFile().mkdirs();
            ReadableByteChannel readableByteChannel = Channels.newChannel(conn.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(this.path);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }

        conn.disconnect();
    }

    public void startDownload() throws IOException {
        this.startDownload(this.link);
    }
}
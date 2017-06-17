package dai.gurren.dan.utils;

//import dai.gurren.dan.pdf.builder.gui.elements.GuiController;
//import dai.gurren.dan.web.parser.app.services.DownloadService;

import java.io.*;
import java.net.URL;

import android.util.Log;

public class FileLoader implements Runnable {
    private static final String TAG = "CbaDownloaderPlugin";
    private String targetDir;
    private String fileUrl;
//    private DownloadService.ChapterProgressIncrementer incrementer;
    private int retryCount = 10;


    public FileLoader(String targetDir, String fileUrl/*, DownloadService.ChapterProgressIncrementer incrementer*/) {
        this.targetDir = targetDir;
        this.fileUrl = fileUrl;
//        this.incrementer = incrementer;
    }

    public FileLoader(String targetDir, String fileUrl/*, DownloadService.ChapterProgressIncrementer incrementer*/, int retryCount) {
        this.targetDir = targetDir;
        this.fileUrl = fileUrl;
//        this.incrementer = incrementer;
        this.retryCount = retryCount;
    }

    @Override
    public void run() {
        int retryCounter = 0;
        boolean success = false;
        while (retryCounter < retryCount && !success) {
            success = downloadFile();
            retryCounter++;
        }
//        incrementer.increment();
        Log.d(TAG, fileUrl);
        Log.d(TAG, "download success: " + success);
    }

    private boolean downloadFile() {
        try {
            URL url = new URL(fileUrl);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream(targetDir + File.separator + fileUrl.substring(fileUrl.lastIndexOf('/')));
            System.out.println(targetDir + File.separator + fileUrl.substring(fileUrl.lastIndexOf('/')));
            fos.write(response);
            fos.close();
            return true;
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            System.out.println(targetDir + fileUrl.substring(fileUrl.lastIndexOf('/')));
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return true;
        }

    }
}

package com.android.systemui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class AmySystemUIUtils {
    public static void copyFile(Context context, String inputPath, String inputFile, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            // write the output file
            out.flush();
            out.close();
            out = null;
            MediaScannerConnection.scanFile(
                context,
                new String[]{outputPath + inputFile},
                new String[]{"audio/mpeg"},
                null);
        }  catch (java.io.FileNotFoundException fnfe1) {
            Log.e(TAG, fnfe1.getMessage());
        }  catch (Exception e) {
            copyFile(context, inputPath, inputFile, outputPath);
            Log.e(TAG, e.getMessage());
        }
    }
}

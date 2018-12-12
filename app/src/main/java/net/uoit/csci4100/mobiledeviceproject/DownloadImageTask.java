package net.uoit.csci4100.mobiledeviceproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageDataListener imageDataListener;

    public void setImageDataListener(ImageDataListener imageDataListener){
        this.imageDataListener = imageDataListener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap bMap = null;
        try{
            URL url = new URL(strings[0]);
            Log.d("URL IN ASYNC", strings[0]);
            InputStream in = url.openStream();

            OutputStream out = new BufferedOutputStream(new FileOutputStream("profileImage.jpg"));
            while(in.read() != -1){
                out.write(in.read());
            }

            out.close();
            in.close();

            BufferedInputStream buf = new BufferedInputStream(in);
            bMap = BitmapFactory.decodeStream(buf);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }

            return bMap;

        }catch (Exception e){
            e.printStackTrace();
        }
        return bMap;
    }

    protected void onPostExecute(Bitmap bmap){imageDataListener.taskUpdater(bmap);}
}

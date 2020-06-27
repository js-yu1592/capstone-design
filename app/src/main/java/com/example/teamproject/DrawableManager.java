package com.example.teamproject;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class DrawableManager {
    private final Map<String, SoftReference<Drawable>> drawableMap;

    public DrawableManager() {

        drawableMap = new HashMap<String, SoftReference<Drawable>>();
    }


    public Drawable fetchDrawable(String urlString) {
        SoftReference<Drawable> drawableRef=drawableMap.get(urlString);
        if(drawableRef!=null) {
            Drawable drawable = drawableRef.get();
            if (drawable != null)
                return drawable;
            drawableMap.remove(urlString);
        }

        Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
        try {
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");
            drawableRef=new SoftReference<Drawable>(drawable);
            drawableMap.put(urlString,drawableRef);

            return drawableRef.get();
        } catch (MalformedURLException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        }
    }

    public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
        SoftReference<Drawable> drawableRef=drawableMap.get(urlString);
        if(drawableRef!=null){
            Drawable drawable=drawableRef.get();
            if(drawable!=null){
                imageView.setImageDrawable(drawableRef.get());
                return;
            }
            drawableMap.remove(urlString);
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                imageView.setImageDrawable((Drawable) message.obj);
            }
        };



        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
                Drawable drawable = fetchDrawable(urlString);
                Message message = handler.obtainMessage(1, drawable);
                handler.sendMessage(message);
            }
        };
        thread.start();
    }
    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }

}
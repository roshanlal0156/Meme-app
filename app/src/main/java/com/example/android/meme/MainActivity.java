package com.example.android.meme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String API = "https://meme-api.herokuapp.com/gimme";
    ImageView img;
    String shareUrl="";
    ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar=findViewById(R.id.loader);
        img=findViewById(R.id.myimgmeme);
        new MemeTask().execute();

    }

    public void nextMethod(View view) {
        img.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        new MemeTask().execute();
    }

    public void shareMethod(View view) {
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_TEXT,"hey checkout this cool meme...."+shareUrl );
        startActivity(Intent.createChooser(intent,"share using..."));

    }

    class MemeTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected String doInBackground(String... args) {
            String response = HttpConnection.excuteGet(API);
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            try {

                Log.e(String.valueOf(MainActivity.this),response+"this is mmmm");
                JSONObject obj = new JSONObject(response);
                String ans = obj.getString("url");
                shareUrl+=ans;
                Glide.with(MainActivity.this).load(ans).into(img);
                img.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }



    }
}

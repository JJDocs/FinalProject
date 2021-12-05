package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=453d6b03-37ef-4e78-8f95-0ce2d831e5c1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    JSONObject USD;
                    JSONObject quote;
                    JSONObject name;
                    JSONArray data;


                    try {
                        data = response.getJSONArray("data");

                       for (int i = 0; i < data.length() ; i++) {
                       //     JSONObject bi = data.getJSONObject(i);
                            String Cry_name =  data.getJSONObject(i).getString("name");
                            String symbol =  data.getJSONObject(i).getString("symbol");
                           quote = data.getJSONObject(i).getJSONObject("quote");
                            JSONObject quote_to_USD = quote.getJSONObject("USD");
                            String coin_to_USD = quote_to_USD.getString("price");


                            System.out.println("\n"+Cry_name);
                            System.out.println(symbol);
                            System.out.println(coin_to_USD);
                       }
                        } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }, error -> {
                });
        queue.add(jsonObjectRequest);
    }

}


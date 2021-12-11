package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class News_Activity extends Fragment {

    public News_Activity() {
        // Required empty public constructor
    }
    ListView listv;
    List<HashMap<String, String>> newsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news__activity, container, false);
        listv = rootView.findViewById(R.id.list_news_layout);

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=453d6b03-37ef-4e78-8f95-0ce2d831e5c1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    JSONObject quote;
                    JSONArray data;
                    try {
                        data = response.getJSONArray("data");

                        for (int i = 0; i < 10; i++) {
                            String news_name = data.getJSONObject(i).getString("name");
                            quote = data.getJSONObject(i).getJSONObject("quote");
                            JSONObject quote_to_USD = quote.getJSONObject("USD");

                            String Market_dom = (quote_to_USD.getString("market_cap_dominance"));
                            String Vol_change = (quote_to_USD.getString("volume_change_24h"));
                            String Per_change_1hr = (quote_to_USD.getString("percent_change_1h"));
                            String Per_change_24hr = (quote_to_USD.getString("percent_change_24h"));
                            String Per_change_7d = (quote_to_USD.getString("percent_change_7d"));
                            String Per_change_30d = (quote_to_USD.getString("percent_change_30d"));


                            HashMap<String, String> news_data = new HashMap<>();

                            news_data.put("name", news_name);
                            news_data.put("market_cap_dominance", Market_dom);
                            news_data.put("volume_change_24h",Vol_change);
                            news_data.put("percent_change_1h", Per_change_1hr);
                            news_data.put("percent_change_24h", Per_change_24hr);
                            news_data.put("percent_change_7d", Per_change_7d);
                            news_data.put("percent_change_30d", Per_change_30d);

                            newsList.add(news_data);

                        }
                        String[] from = {"name",              "market_cap_dominance",
                                         "volume_change_24h", "percent_change_1h",
                                         "percent_change_24h","percent_change_7d",
                                         "percent_change_30d"};

                        int[] to = {R.id.news_name,         R.id.market_cap_dominance,
                                    R.id.volume_change_24h, R.id.percent_change_1h,
                                    R.id.percent_change_24h,R.id.percent_change_7d,
                                    R.id.percent_change_30d};

                        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), newsList, R.layout.news_list, from, to);
                        listv.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, error -> {
                });
        queue.add(jsonObjectRequest);

        return rootView;
    }
}
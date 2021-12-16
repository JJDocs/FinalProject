package com.example.finalproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

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

@SuppressWarnings("ALL")
public class Home_Activity extends Fragment {

    public Home_Activity() {
        // Required empty public constructor
    }
    private SimpleAdapter adapter;
    ListView lv;
    EditText editSearch;
    List<HashMap<String,String>> marketList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home__activity, container, false);
        lv = rootView.findViewById(R.id.list_news_layout);
        editSearch = rootView.findViewById(R.id.searchCrypto);

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=453d6b03-37ef-4e78-8f95-0ce2d831e5c1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    JSONObject quote;
                    JSONArray data;
                    try {
                        data = response.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            String Cry_name = data.getJSONObject(i).getString("name");
                            String symbol ="-" + data.getJSONObject(i).getString("symbol");

                            quote = data.getJSONObject(i).getJSONObject("quote");
                            JSONObject quote_to_USD = quote.getJSONObject("USD");
                            String coin_to_USD = quote_to_USD.getString("price");

                            HashMap<String, String> market = new HashMap<>();
                            market.put("name", Cry_name);
                            market.put("symbol",symbol);
                            market.put("price", coin_to_USD);
                            marketList.add(market);
                            String[] from = new String[]{"name", "price", "symbol"};
                            int[] to = new int[] {R.id.name, R.id.price, R.id.symbol};
                            adapter = new SimpleAdapter(getActivity().getBaseContext(), marketList, R.layout.market_list, from, to);
                            lv.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                });

        queue.add(jsonObjectRequest);

        editSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Home_Activity.this.adapter.getFilter().filter(charSequence);
            }
            public void afterTextChanged(Editable editable) { }
        });
        return rootView;
    }
}

package com.example.login.ui.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.login.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Earthquake;
import Util.Constants;

public class NotificationsFragment extends Fragment {
    private ArrayList<String> arrayList;
    private ListView listView;
    private RequestQueue queue;
    private ArrayAdapter arrayAdapter;

    private List<Earthquake> quakeList;
    private int contentView;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notifications);

        quakeList = new ArrayList<>();
        listView  = getActivity().findViewById(R.id.listview);

        queue =  Volley.newRequestQueue(getActivity().getApplicationContext());

        arrayList = new ArrayList<>();

        getAllQuakes(Constants.URL);

    }

    private void setContentView(int fragment_notifications) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        return view;
    }


    void getAllQuakes(String url) {



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Earthquake earthquake = new Earthquake();

                        try {
                            JSONArray features = response.getJSONArray("features");
                            for (int i = 0; i < Constants.LIMIT; i++) {
                                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");
                                JSONObject geometry = features.getJSONObject(i).getJSONObject("geometry");

                                JSONArray coordinates = geometry.getJSONArray("coordinates");

                                double lon = coordinates.getDouble(0);
                                double lat = coordinates.getDouble(1);

                                //Log.d("Quake", lon + ", " + lat);
                                Log.d("Lat", properties.getString("place"));

                                earthquake.setPlace(properties.getString("place"));
                                earthquake.setType(properties.getString("type"));
                                earthquake.setTime(properties.getLong("time"));
                                earthquake.setMagnitude(properties.getDouble("mag"));
                                earthquake.setLon(lon);
                                earthquake.setLat(lat);

                                arrayList.add(earthquake.getPlace());

                            }
                            final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1, arrayList);
                            listView.setAdapter(arrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //Toast.makeText(getApplicationContext(), "Clicked " + position, Toast.LENGTH_LONG).show();
                                    Toast.makeText(getActivity(), "Clicked"+ position,Toast.LENGTH_LONG).show();
                                }
                            });
                            arrayAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        queue.add(jsonObjectRequest);
    }

}












    /*private NotificationsViewModel notificationsViewModel;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;*/









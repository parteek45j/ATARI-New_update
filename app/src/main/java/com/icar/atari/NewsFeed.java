package com.icar.atari;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeed extends Fragment {

    CustomPostAdapter adapter;
    ListView listView;
    ArrayList<StatusBean> arrayList;
    StatusBean bean;
    RequestQueue requestQueue;
    String postId,name,location,status,date,time,statusPicture,profilePicture,userId;
    ProgressDialog pd;
    public NewsFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_feed, container, false);
        pd=new ProgressDialog(getActivity());
        pd.setMessage("Reterieving...");
        pd.setCancelable(false);
        listView=(ListView)view.findViewById(R.id.listView);
        arrayList=new ArrayList<>();
        requestQueue= Volley.newRequestQueue(getActivity());
        reterieveStatus();
        return view;
    }
    void reterieveStatus(){
        pd.show();
        StringRequest request=new StringRequest(Request.Method.GET, Util.reterieveStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("students");
                    String message=object.getString("message");
                    if (message.contains("Sucessful")) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            postId=jsonObject.getString("PostId");
                          name=jsonObject.getString("Name");
                           location=jsonObject.getString("City");
                            status=jsonObject.getString("Status");
                            date=jsonObject.getString("Date");
                            statusPicture=jsonObject.getString("Picture");
                            //profilePicture=jsonObject.getString("ProfilePicture");
                            userId=jsonObject.getString("UserId");
                           arrayList.add(new StatusBean(name,location,date,statusPicture,status));
                        }
                      adapter=new CustomPostAdapter(getActivity(),R.layout.post_items,arrayList);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        pd.dismiss();
                    }else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}

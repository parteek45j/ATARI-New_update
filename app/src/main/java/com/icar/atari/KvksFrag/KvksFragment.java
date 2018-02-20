package com.icar.atari.KvksFrag;


import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.icar.atari.R;
import com.icar.atari.RegisterActivity;
import com.icar.atari.StatusBean;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KvksFragment extends android.support.v4.app.ListFragment  {
    Spinner statess;
    TextView tt;
    String [] kvk;



    LinearLayout ll;
    CardView cd;

    String [] adrs = new String[]{"avcd","dsdsds","dddd","dsdsdsd"};
    ArrayAdapter<CharSequence> adapterstatess;
    kvkadapter kadapter;

        // Required empty public constructor

    public KvksFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_kvks, container, false);
        cd = (CardView)v.findViewById(R.id.carview1);
        tt = (TextView)v.findViewById(R.id.kvkname) ;
        ll = (LinearLayout)v.findViewById(R.id.lln);

       ll.setVisibility(View.GONE);
        statess = v.findViewById(R.id.sts);
        adapterstatess = ArrayAdapter.createFromResource(getContext(), R.array.states_array,R.layout.spinner_text);
        adapterstatess.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statess.setAdapter(adapterstatess);

       // kvk = getResources().getStringArray(R.array.punjab_kvks_array);

        statess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(adapterstatess.getItem(position).equals("Punjab"))
                {
                    cd.setVisibility(View.GONE);
                   ll.setVisibility(View.VISIBLE);
                    Context context = getActivity().getApplicationContext();
                    Resources resources = context.getResources();
                    TypedArray address = resources.obtainTypedArray(R.array.punjab_kvks_array);
                    kvk = getResources().getStringArray(R.array.punjab_address);
                    // setListAdapter(new ImageAndTr(context, R.layout.secondary_layout, ninja, ninja_icons));
                    setListAdapter(new kvkadapter(context,R.layout.kvk_items,kvk,address));




                }
                else if (adapterstatess.getItem(position).equals("Himachal Pradesh")) {
                    cd.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    Context context = getActivity().getApplicationContext();
                    Resources resources = context.getResources();
                    TypedArray address = resources.obtainTypedArray(R.array.himachal_kvks_array);
                    kvk = getResources().getStringArray(R.array.hp_address);
                    // setListAdapter(new ImageAndTr(context, R.layout.secondary_layout, ninja, ninja_icons));
                    setListAdapter(new kvkadapter(context,R.layout.kvk_items,kvk,address));




                }
                else if (adapterstatess.getItem(position).equals("Jammu & Kashmir")) {
                    cd.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    Context context = getActivity().getApplicationContext();
                    Resources resources = context.getResources();
                    TypedArray address = resources.obtainTypedArray(R.array.jk_kvks_array);
                    kvk = getResources().getStringArray(R.array.jk_address                                   );
                    // setListAdapter(new ImageAndTr(context, R.layout.secondary_layout, ninja, ninja_icons));
                    setListAdapter(new kvkadapter(context,R.layout.kvk_items,kvk,address));



                }
                else if (adapterstatess.getItem(position).equals("Utrakhand")) {
                    cd.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    Context context = getActivity().getApplicationContext();
                    Resources resources = context.getResources();
                    TypedArray address = resources.obtainTypedArray(R.array.utrakhand_kvks_array);
                    kvk = getResources().getStringArray(R.array.uk_address                                   );
                    // setListAdapter(new ImageAndTr(context, R.layout.secondary_layout, ninja, ninja_icons));
                    setListAdapter(new kvkadapter(context,R.layout.kvk_items,kvk,address));



                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        return v;
    }


}

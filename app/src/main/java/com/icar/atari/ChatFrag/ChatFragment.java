package com.icar.atari.ChatFrag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.icar.atari.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{
    Button hp, pb, uk, jk;



    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        hp = (Button)v.findViewById(R.id.hp2);
        pb = (Button)v.findViewById(R.id.pb2);
        uk = (Button)v.findViewById(R.id.uk2);
        jk = (Button)v.findViewById(R.id.jk2);
        hp.setOnClickListener(this);
        jk.setOnClickListener(this);
        pb.setOnClickListener(this);
        uk.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(),HpActivity.class);
        Bundle b  = new Bundle();

        if(v.getId()==R.id.hp2)
        {
            b.putInt("hp",1);
            i.putExtras(b);
            startActivity(i);

            // intnt();



        }
        else if (v.getId()==R.id.jk2)
        {

            b.putInt("hp",2);
            i.putExtras(b);
            startActivity(i);

            // intnt();


        }
        else if(v.getId()==R.id.pb2)
        {

            b.putInt("hp",3);
            i.putExtras(b);
            startActivity(i);

            //intnt();

        }
        else {
            b.putInt("hp",4);
            i.putExtras(b);
            startActivity(i);

        }





    }

}

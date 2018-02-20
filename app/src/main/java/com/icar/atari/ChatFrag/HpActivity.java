package com.icar.atari.ChatFrag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.icar.atari.R;
import com.icar.atari.Util;

import java.sql.DatabaseMetaData;
import java.text.DateFormat;
import java.util.Date;

public class HpActivity extends AppCompatActivity {
    static  int hpc =0;
    {

        hpc++;
        Log.e("fuck","+hpc");

    }


        private String mDisplayName, mCity, mTime;
        SharedPreferences preferences;
        private ListView mChatListView;
        private EditText mInputText;
        private ImageButton mSendButton;
        private ChatListAdapter adapter;
        private DatabaseReference mdatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hp);
        preferences=getSharedPreferences(Util.loginPrefs,MODE_PRIVATE);
        mDisplayName = preferences.getString(Util.username,"");
        mCity=preferences.getString(Util.city,"");
        mTime= DateFormat.getTimeInstance().format(new Date());
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);
        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0){
                    mSendButton.setBackgroundResource(R.drawable.circle_shape);
                }else {
                    mSendButton.setBackgroundResource(R.drawable.circle_shape1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Intent i = getIntent();
        Bundle b = i.getExtras();
     int ii = b.getInt("hp");
     if(ii==1)
     {
         mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Himachal");


     }
     else if(ii==2)
     {
         mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Jammu");
     }
     else if (ii==3)
     {
         mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Punjab");
     }
     else if(ii==4)
     {
         mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Uttarakhand");
     }



        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }

        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();


            }
        });



    }

     public void sendMessage() {
         String input = mInputText.getText().toString();
         if(!input.equals(""))
         {
             BeanMessage chat = new BeanMessage(input,mDisplayName,mCity,mTime);
             mdatabaseReference.push().setValue(chat);
             mInputText.setText("");
         }



    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ChatListAdapter(this, mdatabaseReference, mDisplayName);
        mChatListView.setAdapter(adapter);

    }




    @Override
    public void onStop() {
        super.onStop();
        adapter.CleanUpResources();
    }


}

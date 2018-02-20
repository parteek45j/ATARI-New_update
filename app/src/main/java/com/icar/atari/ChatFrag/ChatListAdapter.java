package com.icar.atari.ChatFrag;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.icar.atari.R;

import java.util.ArrayList;

/**
 * Created by Suraj on 25-01-2018.
 */

public class ChatListAdapter extends BaseAdapter {
    static  int obj =0;
    {

        obj++;
        Log.e("fuck","+obj");

    }
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    String mDsplayName;
    private ArrayList<DataSnapshot> mSnapshotList;
    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public ChatListAdapter(Activity activity, DatabaseReference ref, String name)
    {
        mActivity = activity;
        mDsplayName = name;
        mDatabaseReference = ref;
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();


    }

    static class ViewHolder{
        TextView authorName, body,time;
        LinearLayout.LayoutParams params;

    }


    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public BeanMessage getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(BeanMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_message,parent,false);

            final ViewHolder holder = new ViewHolder();

            holder.authorName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.messagels);
            holder.time = (TextView) convertView.findViewById(R.id.timmss);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            holder.params= (LinearLayout.LayoutParams)holder.time.getLayoutParams();
            convertView.setTag(holder);
        }
        final BeanMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        boolean isMe = message.getAuther().equals(mDsplayName);
        CharRowNuSetKrnleye(isMe,holder);

        String auher = message.getAuther();
        holder.authorName.setText(auher);

        String msg = message.getMessage();
        holder.body.setText(msg);

        String tmg = message.getTime();
        holder.time.setText(tmg);
        return convertView;


    }
    private void CharRowNuSetKrnleye(boolean isItMe, ViewHolder holder)
    {
        if(isItMe)
        {
            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.GREEN);
            holder.body.setBackgroundResource(R.drawable.bubble2);
        }
        else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }
        holder.authorName.setLayoutParams(holder.params);
        holder.time.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);
    }
    public void CleanUpResources()
    {
        mDatabaseReference.removeEventListener(mListener);

    }
}

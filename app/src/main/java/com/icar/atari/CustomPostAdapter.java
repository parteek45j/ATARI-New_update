package com.icar.atari;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Parteek on 12/16/2017.
 */

public class CustomPostAdapter extends ArrayAdapter<StatusBean> {
    Context context;
    int resource;
    ArrayList<StatusBean> arrayList;
    StatusBean bean;
    public CustomPostAdapter(@NonNull Context context, int resource, @NonNull ArrayList<StatusBean> arrayList) {
        super(context, resource, arrayList);
        this.context=context;
        this.resource=resource;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=null;
        bean=arrayList.get(position);
        view=LayoutInflater.from(context).inflate(resource,parent,false);
        TextView textViewName,textViewLocation,textViewDate,textViewTime,textViewStatus;
        CircleImageView circleImageViewProfilePicture;
        ImageView imageViewStatusPicture;
        textViewName=(TextView)view.findViewById(R.id.txtUserName);
        textViewLocation=(TextView)view.findViewById(R.id.postCity);
        textViewTime=(TextView)view.findViewById(R.id.txt_tweet_time);
        textViewDate=(TextView)view.findViewById(R.id.txt_tweet_date);
        textViewStatus=(TextView)view.findViewById(R.id.txt_tweet);
        circleImageViewProfilePicture=(CircleImageView)view.findViewById(R.id.picture_path);
        imageViewStatusPicture=(ImageView)view.findViewById(R.id.tweet_picture);
        textViewName.setText(bean.getName());
        textViewLocation.setText(bean.getLocation());
        textViewTime.setText(bean.getTime());
        textViewDate.setText(bean.getDate());
     //   if (bean.getProfilePicture().length()>0 && !(bean.getProfilePicture().contains("null"))) {
       //     Glide.with(context).load(bean.getProfilePicture()).into(circleImageViewProfilePicture);
        //}
        if (bean.getStatusPicture().length()>0) {
            Glide.with(context).load(bean.getStatusPicture()).into(imageViewStatusPicture);
        }
        if (bean.getStatus().equals("null") || bean.getStatus().length()<0){

            //Glide.with(context).load(bean.getStatusPicture()).into(imageViewStatusPicture);
        }

        else {

            textViewStatus.setText(bean.getStatus());
        }
        return view;
    }
}

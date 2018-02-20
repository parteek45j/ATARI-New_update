package com.icar.atari.KvksFrag;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.icar.atari.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraj on 18-01-2018.
 */

public class kvkadapter extends ArrayAdapter<String>
{
    private LayoutInflater mInflater;

    private String[] arrayAddress;
    private TypedArray arrayState;

    private int mViewResourceId;
    Context mContext;


    public kvkadapter(Context ctx, int viewResourceId,
                               String[] names, TypedArray address) {
        super(ctx, viewResourceId, names);

        mInflater = (LayoutInflater)ctx.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mContext=ctx;
        arrayAddress = names;
        arrayState = address;

        mViewResourceId = viewResourceId;
    }
    public int getCount() {
        return arrayAddress.length;
    }
    public String getItem(int position) {
        return arrayAddress[position];
    }
    public long getItemId(int position) {
        return 0;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        TextView address  = (TextView) convertView.findViewById(R.id.kvkname);
        address.setText(arrayState.getText(position));
       // iv.setImageDrawable(mIcons.getDrawable(position));

        TextView names = (TextView)convertView.findViewById(R.id.address);
        names.setText(arrayAddress[position]);

        Button button=(Button)convertView.findViewById(R.id.call);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number="";
                String[] tel=null;
                if (arrayAddress[position].contains(":")) {
                    tel = arrayAddress[position].split(":");
                    number = tel[1];
                }else {
                    Toast.makeText(mContext, "Details will be Available Soon", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        Button buttonShare=(Button)convertView.findViewById(R.id.share);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setPackage("com.whatsapp");
                intent.putExtra(Intent.EXTRA_TEXT, arrayAddress[position]);
                intent.setType("text/plain");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                try {
                    if (!(arrayAddress[position].contains("Not Available"))) {
                        mContext.startActivity(intent);
                    }else {
                        Toast.makeText(mContext, "Details will be Available Soon", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(mContext, "Whatsapp is not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonSMS=(Button)convertView.findViewById(R.id.sms);
        buttonSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("smsto:"+"0"));
                intent.putExtra("sms_body", arrayAddress[position]);
                intent.setType("vnd.android-dir/mms-sms");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (!(arrayAddress[position].contains("Not Available"))){
                    mContext.startActivity(intent);
                }else {
                    Toast.makeText(mContext, "Details will be Available Soon", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        return convertView;
    }


}
package com.icar.atari;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CircleImageView circleImageViewProfile, circleImageViewStatusProfile;
    TextView textViewName, textViewStatusName, textViewStatusDate, textViewStatusTime, textViewStatusPost, textViewCity, textViewinfo;
    String id, name, Email, phone, profile, date, time, city, state, postedImage = "";
    SharedPreferences preferences;
    ImageView imageViewAttach, imageViewSend, imageViewStatusPicture, imageViewCancel;
    int IMAGE_UPLOAD = 9;
    EditText editTextWriteStatus;
    Bitmap bitmap,bitmap2;
    StorageReference reference;
    LinearLayout linearLayout, linearLayout1;
    RequestQueue requestQueue;
    ProgressDialog pd;
    Uri resultUri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    CardView cardView;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("Posting.....");
        circleImageViewProfile = (CircleImageView) view.findViewById(R.id.profilePhoto);
        textViewName = (TextView) view.findViewById(R.id.profieName);
        imageViewAttach = (ImageView) view.findViewById(R.id.attach);
        imageViewSend = (ImageView) view.findViewById(R.id.send);
        imageViewCancel = (ImageView) view.findViewById(R.id.cancelImage);
        editTextWriteStatus = (EditText) view.findViewById(R.id.writeStatus);

        circleImageViewStatusProfile = (CircleImageView) view.findViewById(R.id.picture_path);
        textViewStatusName = (TextView) view.findViewById(R.id.txtUserName);
        textViewStatusDate = (TextView) view.findViewById(R.id.txt_tweet_date);
        textViewStatusTime = (TextView) view.findViewById(R.id.txt_tweet_time);
        imageViewStatusPicture = (ImageView) view.findViewById(R.id.tweet_picture);
        textViewStatusPost = (TextView) view.findViewById(R.id.txt_tweet);
        linearLayout = (LinearLayout) view.findViewById(R.id.includeLayou);
        linearLayout1 = (LinearLayout) view.findViewById(R.id.linear);
        textViewCity = (TextView) view.findViewById(R.id.postCity);
        textViewinfo = (TextView) view.findViewById(R.id.textttt);
        cardView = (CardView) view.findViewById(R.id.card);
       // reference = FirebaseStorage.getInstance().getReference();

        imageViewAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete Action Using"), IMAGE_UPLOAD);
            }
        });
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageViewStatusPicture != null) {
                    imageViewStatusPicture.setVisibility(View.GONE);
                }
            }
        });

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextWriteStatus.getText().length() > 0) {

                    postStatus();
                } else {
                    Toast.makeText(getActivity(), "Can't Post without Picture or Status", Toast.LENGTH_SHORT).show();
                }
            }

        });
        editTextWriteStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                linearLayout.setVisibility(View.VISIBLE);
                textViewStatusPost.setText(editTextWriteStatus.getText());
                if (imageViewStatusPicture == null) {
                    imageViewStatusPicture.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        firebaseStorage = FirebaseStorage.getInstance();
       storageReference = firebaseStorage.getReference().child("userpost");
        requestQueue = Volley.newRequestQueue(getActivity());
        preferences = getActivity().getSharedPreferences(Util.loginPrefs, Context.MODE_PRIVATE);
        id = String.valueOf(preferences.getInt(Util.id, 0));
        name = preferences.getString(Util.username, "");
        profile = preferences.getString(Util.profile, "");
        city = preferences.getString(Util.city, "");
        state = preferences.getString(Util.state, "");
        textViewName.setText(name);
        if (profile.length() > 0 && !(profile.contains("null"))) {
            Glide.with(getActivity()).load(profile).into(circleImageViewProfile);
        }
        date = DateFormat.getDateInstance().format(new Date());
        time = DateFormat.getTimeInstance().format(new Date());
        imageViewStatusPicture.setVisibility(View.GONE);
        includelayout();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_UPLOAD && resultCode == RESULT_OK) {
          Uri  uri = data.getData();
            CropImage.activity(uri)
                    .start(getContext(), this);
        }


            if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if(resultCode==RESULT_OK)
                {
                    pd.show();
                    resultUri = result.getUri();




                    try {
                         bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                         bitmap.compress(Bitmap.CompressFormat.JPEG,30,byteArrayOutputStream);
                         byte [] BYTE = byteArrayOutputStream.toByteArray();
                         bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
                        uploadImage(resultUri); // Uploading Finish

                        imageViewStatusPicture.setVisibility(View.VISIBLE);
                        imageViewStatusPicture.setImageBitmap(bitmap);
                        linearLayout.setVisibility(View.VISIBLE);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }




    }

    void includelayout() {
        textViewStatusName.setText(name);
        textViewStatusTime.setText(time);
        textViewStatusDate.setText(date);
        textViewCity.setText("@" + city + "," + state);
        if (profile.length() > 0 && !(profile.contains("null"))) {
            Glide.with(getActivity()).load(profile).into(circleImageViewStatusProfile);
        }
    }

   public void postStatus() {
        StringRequest request = new StringRequest(Request.Method.POST, Util.postStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int success = object.getInt("success");
                    String message = object.getString("message");
                    if (success > 0) {
                        pd.dismiss();
                        editTextWriteStatus.setText("");
                        Toast.makeText(getActivity(), "Status Posted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Status Posting failed", Toast.LENGTH_SHORT).show();
                    }
                    pd.dismiss();
                } catch (JSONException e) {
                    pd.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                //map.put("Name", name);
                //map.put("City", city + "," + state);
                map.put("Status", editTextWriteStatus.getText().toString());
                //map.put("Date", date);
                //map.put("Time", time);
                map.put("Picture", postedImage);
                //map.put("ProfilePicture", profile);
                map.put("UserId", id);
                return map;
            }
        };
        requestQueue.add(request);
    }

    void uploadImage(Uri resultUri)
    {
        reference = storageReference.child(resultUri.getLastPathSegment());
        reference.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                String url = task.getResult().getDownloadUrl().toString();
                Toast.makeText(getContext(), ""+url, Toast.LENGTH_SHORT).show();
                postedImage = url;
                pd.dismiss();


            }
        });
    }
}

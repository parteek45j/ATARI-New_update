package com.icar.atari;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfile extends Fragment implements View.OnClickListener {

    CircleImageView imageButton;
    int IMAGE_UPLOAD=6;
    Bitmap bitmap;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri imagUri,uri;
    String url="";
    AppCompatButton updateButton,passwordButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String name,phone,email,password,profile;
    int id;
    AppCompatEditText editTextName,editTextPhone,editTextEmail;
    boolean valdiate=false;
    RequestQueue requestQueue;
    ProgressDialog pd;
    Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        imageButton=(CircleImageView) view.findViewById(R.id.imageUpload);
        editTextName=(AppCompatEditText) view.findViewById(R.id.update_name);
        editTextPhone=(AppCompatEditText) view.findViewById(R.id.update_phone);
        editTextEmail=(AppCompatEditText) view.findViewById(R.id.update_email);
        imageButton.setOnClickListener(this);
        requestQueue= Volley.newRequestQueue(getActivity());
        pd=new ProgressDialog(getActivity());
        pd.setMessage("Updating Details..");
        pd.setCancelable(false);
        handler=new Handler();
        preferences=getActivity().getSharedPreferences(Util.loginPrefs, Context.MODE_PRIVATE);
        editor=preferences.edit();
        id=preferences.getInt(Util.id,0);
        name=preferences.getString(Util.username,"");
        phone=preferences.getString(Util.phone,"");
        email=preferences.getString(Util.email,"");
        password=preferences.getString(Util.password,"");
        profile=preferences.getString(Util.profile,"");
        updateButton=(AppCompatButton)view.findViewById(R.id.update_detail);
//        updateButton.setEnabled(true);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValdiate()) {
                    uploadImage();
                    pd.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (url.length()>0) {
                                updateDetails();
                            }else {
                                url=profile;
                                updateDetails();
                            }
                        }
                    },6000);
                }
            }
        });

        Log.e("data",id+name+phone+email+profile);
        editTextName.setText(name);
        editTextPhone.setText(phone);
        editTextEmail.setText(email);
        passwordButton=(AppCompatButton)view.findViewById(R.id.update_password);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatButton appCompatButton=null;
                EditText oldPass=null,newPass=null;
                final Dialog dialog=new Dialog(getActivity());
                dialog.setTitle("Change Password");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.password_custom);
                oldPass=(EditText) dialog.findViewById(R.id.update_password1);
                oldPass=(EditText) dialog.findViewById(R.id.update_password2);
                appCompatButton=(AppCompatButton)dialog.findViewById(R.id.confirm);
                appCompatButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Confirm", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("atariprofile");
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(Intent.createChooser(intent,"Complete Action Using"),IMAGE_UPLOAD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMAGE_UPLOAD && resultCode==RESULT_OK){
//            updateButton.setEnabled(false);
            imagUri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imagUri);
                imageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isValdiate(){
        valdiate=true;
        String n=editTextName.getText().toString();
        String p=editTextPhone.getText().toString();
        String e=editTextEmail.getText().toString();
        if (n.isEmpty()){
            valdiate=false;
            editTextName.setError("Enter Your Full Name");
        }if(p.isEmpty()){
            valdiate=false;
            editTextPhone.setError("Enter your Name");
        }if(e.isEmpty()){
            valdiate=false;
            editTextEmail.setError("Enter your Email");
        }else if (!e.contains("@")){
            valdiate=false;
            editTextEmail.setError("Enter a Valid Email");
        }
        return valdiate;
    }
    void updateDetails(){
        Log.e("upload",id+name+phone+email+url);
        StringRequest request=new StringRequest(Request.Method.POST, Util.updateDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String message=object.getString("message");
                    int success=object.getInt("success");
                    if (success>0){
                        pd.dismiss();
                        editor.putString(Util.username,editTextName.getText().toString());
                        editor.putString(Util.phone,editTextPhone.getText().toString());
                        editor.putString(Util.email,editTextEmail.getText().toString());
                        editor.putString(Util.profile,url);
                        editor.commit();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }else{
                        pd.dismiss();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    pd.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("Id", String.valueOf(id));
                map.put("Name",editTextName.getText().toString());
                map.put("Phone",editTextPhone.getText().toString());
                map.put("Email",editTextEmail.getText().toString());
                map.put("Profile", url);
                return map;
            }
        };
        requestQueue.add(request);
    }
    void uploadImage(){
        StorageReference reference=storageReference.child(imagUri.getLastPathSegment());
        reference.putFile(imagUri).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uri=taskSnapshot.getDownloadUrl();
//                    updateButton.setEnabled(true);
                    url=String.valueOf(uri);
                    Log.e("Url",url);
            }
        });
    }
}

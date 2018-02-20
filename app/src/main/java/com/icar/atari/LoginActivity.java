package com.icar.atari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail,editTextPassword;
    RequestQueue requestQueue;
    AppCompatButton buttonSignIn,buttonSignUp;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog pd;
    boolean validate=false;
    String name,phone,email,password,profile,state,city;
    int id;
    void views(){
        editTextEmail=(EditText)findViewById(R.id.input_name);
        editTextPassword=(EditText)findViewById(R.id.input_password);
        buttonSignIn=(AppCompatButton)findViewById(R.id.input_signin);
        buttonSignUp=(AppCompatButton)findViewById(R.id.input_signup);
        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
        requestQueue= Volley.newRequestQueue(this);
        preferences=getSharedPreferences(Util.loginPrefs,MODE_PRIVATE);
        editor=preferences.edit();
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Signing In....");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        views();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.input_signin){
            if (isValidate()) {
                loginUser();
            }
        }else if (id==R.id.input_signup){
            startActivity(new Intent(this,RegisterActivity.class));
            finish();
        }
    }
    void loginUser(){
        pd.show();
        StringRequest request=new StringRequest(Request.Method.POST, Util.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("students");
                    String message=object.getString("message");
                    if (message.contains("Login Sucessful")){
                        for (int i=0;i<array.length();i++){
                            JSONObject object1=array.getJSONObject(i);
                            id=object1.getInt("Id");
                            name=object1.getString("Name");
                            phone=object1.getString("Phone");
                            email=object1.getString("Email");
                            password=object1.getString("Password");
                            profile=object1.getString("Profile");
                            state=object1.getString("State");
                            city=object1.getString("City");

                        }
                        Log.e("data",id+name+phone+email+profile+city+state);
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        editor.putBoolean(Util.booleanLogin,true);
                        editor.putInt(Util.id,id);
                        editor.putString(Util.username,name);
                        editor.putString(Util.phone,phone);
                        editor.putString(Util.email,email);
                        editor.putString(Util.password,password);
                        editor.putString(Util.profile,profile);
                        editor.putString(Util.state,state);
                        editor.putString(Util.city,city);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        finishAffinity();
                    }else{
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
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
                pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("Phone",editTextEmail.getText().toString());
                map.put("Password",editTextPassword.getText().toString());
                return map;
            }
        };
        requestQueue.add(request);
    }
    public boolean isValidate(){
        validate=true;
        String email=editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();
        if(email.isEmpty()){
            validate=false;
            editTextEmail.setError("Please Enter Your Details");
        }if(password.isEmpty()){
            validate=false;
            editTextPassword.setError("Please Enter Your Passwords");
        }
     return validate;
    }
}


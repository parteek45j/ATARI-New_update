package com.icar.atari;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Spinner states, kvks;

    ArrayAdapter<CharSequence> adapterstates, adapterkvks;
    boolean isValidated=false;
    EditText editTextName,editTextDesignation,editTextPhone,editTextEmail,editTextPassword,editTextConfirmPassword;
    AppCompatButton buttonRegister,buttonLogin;
    String designation,name,phone,email,password,confirmPassword;
    RequestQueue requestQueue;
    ProgressDialog pd;
    Bean bean;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    void views(){
        states = (Spinner) findViewById(R.id.sss);
        kvks = (Spinner) findViewById(R.id.kkk);
        editTextName=(EditText)findViewById(R.id.reg_name);
        editTextDesignation=(EditText)findViewById(R.id.reg_designaton);
        editTextPhone=(EditText)findViewById(R.id.reg_phone);
        editTextEmail=(EditText)findViewById(R.id.reg_email);
        editTextPassword=(EditText)findViewById(R.id.reg_password);
        editTextConfirmPassword=(EditText)findViewById(R.id.reg_cpassword);
        buttonRegister=(AppCompatButton)findViewById(R.id.register_sign_up_button);
        buttonLogin=(AppCompatButton)findViewById(R.id.register_signin);
        pd=new ProgressDialog(this);
        pd.setMessage("Registering User...");
        pd.setCancelable(false);
        bean=new Bean();
        preferences=getSharedPreferences(Util.loginPrefs,MODE_PRIVATE);
        editor=preferences.edit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        views();
        requestQueue= Volley.newRequestQueue(this);
        adapterstates = ArrayAdapter.createFromResource(this, R.array.states_array,R.layout.spinner_text);
        adapterstates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        states.setAdapter(adapterstates);
        states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(adapterstates.getItem(position).equals("Punjab"))
                        {
                            adapterkvks = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.punjab_kvks_array,R.layout.spinner_text);
                            abc();
                        }
                else if (adapterstates.getItem(position).equals("Himachal Pradesh")) {

                    adapterkvks = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.himachal_kvks_array, R.layout.spinner_text);
                   abc();
                }
                else if (adapterstates.getItem(position).equals("Jammu & Kashmir")) {

                    adapterkvks = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.jk_kvks_array, R.layout.spinner_text);
                    abc();
                }
                else if (adapterstates.getItem(position).equals("Utrakhand")) {

                    adapterkvks = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.utrakhand_kvks_array, R.layout.spinner_text);
                    abc();
                }else{
                    adapterkvks = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.kvks_array, R.layout.spinner_text);
                    abc();
                }
                if (position!=0) {
                    bean.setState(adapterstates.getItem(position).toString());
                    Toast.makeText(RegisterActivity.this, bean.getState(), Toast.LENGTH_SHORT).show();
                }
                kvks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i!=0){
                            bean.setCity(adapterkvks.getItem(i).toString());
                            Toast.makeText(RegisterActivity.this, bean.getCity(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setDesignation(editTextDesignation.getText().toString());
                bean.setName(editTextName.getText().toString());
                bean.setPhone(editTextPhone.getText().toString());
                bean.setEmail(editTextEmail.getText().toString());
                bean.setPassword(editTextConfirmPassword.getText().toString());
                if (isValidate()){
                    insert();
                }
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();
                if (!password.equals(confirmPassword)){
                    editTextConfirmPassword.setError("Passwords don't match");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void abc()
    {
        adapterkvks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kvks.setAdapter(adapterkvks);
    }
    public boolean isValidate(){
        isValidated=true;
        designation=editTextDesignation.getText().toString();
        name=editTextName.getText().toString();
        phone=editTextPhone.getText().toString();
        email=editTextEmail.getText().toString();
        password=editTextPassword.getText().toString();
        confirmPassword=editTextConfirmPassword.getText().toString();
        if (designation.isEmpty()){
            isValidated=false;
            editTextDesignation.setError("Please Enter The Designation");
        }
        if (name.isEmpty()){
            isValidated=false;
            editTextName.setError("Please Enter Your Name");
        }
        if (phone.isEmpty()){
            isValidated=false;
            editTextPhone.setError("Please Enter Your Phone");
        }if (email.isEmpty()){
            isValidated=false;
            editTextEmail.setError("Please Enter Your Email");
        }else if (!email.contains("@")){
            isValidated=false;
            editTextEmail.setError("Please Enter a Valid Email");
        }if (password.isEmpty()){
            isValidated=false;
            editTextPassword.setError("Please Enter your Password");
        }
//        else if (password.length()<8){
//            isValidated=false;
//            editTextPassword.setError("Password must have atleast 8 Chars");
//        }
        return isValidated;
    }
    void insert()
    {
        pd.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Util.insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    int success=object.getInt("success");
                    String message=object.getString("message");
                    int userid=object.getInt("UserId");
                    if(success>0){
                        Log.e("register",bean.getName()+bean.getPhone()+bean.getEmail()+bean.getState()+bean.getCity());
                        pd.dismiss();

                       Toast.makeText(RegisterActivity.this, message+userid, Toast.LENGTH_SHORT).show();
                       editor.putBoolean(Util.booleanLogin,true);
                        editor.putInt(Util.id,userid);
                        editor.putString(Util.username,bean.getName());
                        editor.putString(Util.phone,bean.getPhone());
                        editor.putString(Util.email,bean.getEmail());
                        editor.putString(Util.state,bean.getState());
                        editor.putString(Util.city,bean.getCity());
                        editor.putString(Util.password,bean.getPassword());
                        editor.commit();
                        startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                        finishAffinity();

                    }else{
                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this, "Response "+message, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    pd.dismiss();
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("Designation",bean.getDesignation());
                map.put("Name",bean.getName());
                map.put("Phone",bean.getPhone());
                map.put("Email",bean.getEmail());
                map.put("State",bean.getState());
                map.put("Kvk",bean.getCity());
                map.put("Password",bean.getPassword());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}


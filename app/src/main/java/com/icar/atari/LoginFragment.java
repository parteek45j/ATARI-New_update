package com.icar.atari;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText editTextName,editTextPassword;
    AppCompatButton sinupButton;
    TextView textView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean admin=false;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        preferences=getActivity().getSharedPreferences(Util.loginPrefs, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editTextName=(EditText)view.findViewById(R.id.adminname);
        editTextPassword=(EditText)view.findViewById(R.id.admin_password);
        sinupButton=(AppCompatButton)view.findViewById(R.id.admin_signin);
        textView=(TextView)view.findViewById(R.id.textview03);
        sinupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText().toString().equals("admin")&&editTextPassword.getText().toString().equals("admin")){
                    textView.setVisibility(View.GONE);
                    HomeFragment fragment=new HomeFragment();
                    FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragme,fragment);
                    transaction.commit();
                    editor.putBoolean(Util.admin,true);
                    editor.commit();
                }else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Sorry Id or Password is not Correct");
                }
            }
        });
        return view;
    }
}

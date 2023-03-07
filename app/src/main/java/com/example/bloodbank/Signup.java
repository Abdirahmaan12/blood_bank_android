package com.example.bloodbank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView already=findViewById(R.id.allready);

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gologin=new Intent(Signup.this, login.class);
                startActivity(gologin);
            }
        });

        EditText txtusername=findViewById(R.id.txtusername);
        EditText txtpassword=findViewById(R.id.txtpass);
        EditText txtconfirm=findViewById(R.id.txtconfirm);
        Button Btnseef=findViewById(R.id.btnsignup);

        Btnseef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();
                String confirm = txtconfirm.getText().toString();

                if(!password.equals(confirm)){
                    Log.i("19k","not matching");
                    Toast.makeText(Signup.this,"two password does not matching",Toast.LENGTH_LONG).show();
                    return;
                }

                savecustomer(username, password, confirm);

            }
        });

    }

    private void savecustomer(String username, String password, String confirm) {

        String url ="http://192.168.222.19/bloodbank/customer_register.php";
        StringRequest sr = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Signup.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Signup.this, "error"+error.getMessage(), Toast.LENGTH_LONG).show();

            }


        }

        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("confirm_password", confirm);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(Signup.this);
        queue.add(sr);



    }
    }

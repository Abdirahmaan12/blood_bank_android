package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gosignup=new Intent(login.this, Signup.class);
                startActivity(gosignup);
            }
        });

        EditText txtusername = findViewById(R.id.txtUsername);
        EditText txtpassword = findViewById(R.id.txtPass);

        Button btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();
                checkcustomer(username, password);
            }
        });

    }

    void checkcustomer(String username , String password ){
        String url ="http://192.168.222.19/bloodbank/login.php?username="+username+"&password="+password;

        JsonArrayRequest ja = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject ob = response.getJSONObject(0);
                            if (ob.getString("code").equals("yes")){

                                Intent godonetor = new Intent(login.this, MainActivity.class);
                                startActivity(godonetor);
                            }
                            else   {
                                Toast.makeText(login.this, "username or password is incorect",Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this, "error"+error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }

        );

        RequestQueue queue = Volley.newRequestQueue(login.this);
        queue.add(ja);

    }
}
package com.example.bloodbank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class DonatorRegister extends AppCompatActivity {

    EditText txtdname,txtdphone,txtdaddress;
    Spinner spinner;
    Button btnsave;


    String selected_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donator_register);

        //init
        txtdname=findViewById(R.id.txtdname);
        txtdphone=findViewById(R.id.txtphone);
        txtdaddress=findViewById(R.id.txtaddress);
        spinner=findViewById(R.id.sptype);
        btnsave=findViewById(R.id.buttonsave);

        loadbloodtype(spinner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(DonatorRegister.this, adapterView.getItemAtPosition(i).toString(),
//                        Toast.LENGTH_SHORT).show();
                selected_type= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=txtdname.getText().toString().trim();
                String phone=txtdphone.getText().toString().trim();
                String address=txtdaddress.getText().toString().trim();

                if(selected_type.isEmpty() || selected_type.equals("")){
                    Toast.makeText(DonatorRegister.this, "select your blood type", Toast.LENGTH_SHORT).show();

                    return;
                }
                SaveDonetor(name, phone,address,selected_type);
            }
        });

    }

    private void SaveDonetor(String name, String phone, String address, String selected_type) {

        String url="http://192.168.222.19/bloodbank/donetorregister.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("yes"))
                        {
                            Toast.makeText(DonatorRegister.this, "new Donetor Registered", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(DonatorRegister.this, "Donetor not Registered", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(DonatorRegister.this, "error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap params=new HashMap<>();
                params.put("name", name);
                params.put("phone", phone);
                params.put("address", address);
                params.put("type_name", selected_type);
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(DonatorRegister.this);
        queue.add(stringRequest);
    }


    void loadbloodtype(Spinner sp)
    {
        ArrayAdapter bloodtype=ArrayAdapter.createFromResource(DonatorRegister.this,R.array.bloodtype,
                android.R.layout.simple_spinner_item);
        bloodtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(bloodtype);

    }
}
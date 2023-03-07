package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView don_rec;
    RecyclerView.LayoutManager layoutManager;
    donetorAdapter adapter;
    ArrayList<Donetorsmodal> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        don_rec=findViewById(R.id.don_rec);
        layoutManager=new LinearLayoutManager(MainActivity.this);
        don_rec.setLayoutManager(layoutManager);

        readAlldonetors();

    }

    private void readAlldonetors() {

        String url="http://192.168.222.19/bloodbank/alldonators.php";

        JsonArrayRequest ja= new JsonArrayRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject object=response.getJSONObject( 0);

                            if(object.getString("code").equals("yes"))
                            {
//
                                data.clear();
                                for (int i=0; i<response.length(); i++)
                                {
                                    JSONObject ob=response.getJSONObject(i);
                                    Donetorsmodal ml=new Donetorsmodal(ob.getString("id"),
                                            ob.getString("name"), ob.getString("type"),
                                            ob.getString("img"), ob.getString("address"),
                                            ob.getString("phone"));
                                    data.add(ml);
                                }


                                adapter=new donetorAdapter(data,MainActivity.this);
                                don_rec.setAdapter(adapter);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue q= Volley.newRequestQueue(MainActivity.this);
        q.add(ja);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem search=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Addnew:

                startActivity(new Intent(MainActivity.this, DonatorRegister.class));

                break;

            case R.id.search:

                break;


            case R.id.refresh:

                readAlldonetors();
                break;

            case  R.id.logout:
                startActivity(new Intent(MainActivity.this, login.class));
                break;


        }
        return true;
    }
}
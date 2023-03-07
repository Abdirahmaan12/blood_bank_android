package com.example.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class donetorAdapter extends RecyclerView.Adapter<donetorAdapter.MyViewHolder> implements Filterable {
    ArrayList<Donetorsmodal> res;
    ArrayList<Donetorsmodal> resbackup;
    Context ctx;
    public donetorAdapter(ArrayList<Donetorsmodal> response, MainActivity mainActivity) {
        res=response;
        resbackup=new ArrayList<>(response);
        ctx=mainActivity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.donsinglerow,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            holder.txtname.setText(res.get(position).getName());
            holder.txtphone.setText(res.get(position).getPhone());
            holder.txtaddress.setText(res.get(position).getAddress());



            String imagurl="http://192.168.222.19/bloodbank/"+res.get(position).getImg();


            Glide.with(ctx).load(imagurl).into(holder.img);

            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String callphone=res.get(position).getPhone();

                    Uri number=Uri.parse("tel:"+callphone);
                    Intent call=new Intent(Intent.ACTION_DIAL, number);
                    ctx.startActivity(call);
                }
            });



    }



    @Override
    public int getItemCount() {
        return res.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence key) {
                ArrayList<Donetorsmodal> filtereddata=new ArrayList<>();

                if(key==null || key.length()==0)
                {
                    filtereddata.addAll(resbackup);
                }

                else{
                    for(Donetorsmodal dm:resbackup){
                        if(dm.getType().toLowerCase().contains(key.toString().toLowerCase()))
                        {

                            filtereddata.add((dm));

                        }
                    }


                }

                FilterResults filterResults=new FilterResults();
                filterResults.values=filtereddata;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                res.clear();
                res.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtname, txtphone,txtaddress;
        ConstraintLayout parent;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.txtdon_name);
            txtphone=itemView.findViewById(R.id.txtdon_phone);
            txtaddress=itemView.findViewById(R.id.txtdon_address);
            img=itemView.findViewById(R.id.img);
            parent=itemView.findViewById(R.id.parent);
        }
    }
}

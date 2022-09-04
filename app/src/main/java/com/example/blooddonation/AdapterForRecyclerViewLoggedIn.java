package com.example.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;

public class AdapterForRecyclerViewLoggedIn extends RecyclerView.Adapter<ViewHolderForRecyclerViewLoggedIn> {
    Context context;
    List<DataTypeForRecyclerView_ActivitySearchResult> list;

    public AdapterForRecyclerViewLoggedIn(Context context, List<DataTypeForRecyclerView_ActivitySearchResult>list)
    {
        this.context=context;
        this.list=list;
    }



    @NonNull
    @Override
    public ViewHolderForRecyclerViewLoggedIn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewOfRecyclerLayout= LayoutInflater.from(context).
                inflate(R.layout.card_search_result,parent,false);

        ViewHolderForRecyclerViewLoggedIn viewHolder=
                new ViewHolderForRecyclerViewLoggedIn(viewOfRecyclerLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForRecyclerViewLoggedIn holder, int position) {
        String name=list.get(position).Name;
        holder.TextView_ViewHolder_Name.setText(name);
        //
        String phoneNumber=list.get(position).PhoneNumber;
        holder.TextView_ViewHolder_PhoneNumber.setText(phoneNumber);
        //
        String email=list.get(position).Email;
        holder.TextView_ViewHolder_Email.setText(email);
        //
        String dis=list.get(position).District;
        String subDis=list.get(position).SubDistrict;
        holder.TextView_ViewHolder_District.setText(dis+","+subDis);
        holder.sendCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=MainActivity.model.getSignUserInfo().getValue().get("Email");
                if(email.equals("null"))
                {

                    Snackbar.make(holder.sendCall,"Login,Please",Snackbar.LENGTH_SHORT).show();
                }
                else {
                    String number =holder.TextView_ViewHolder_PhoneNumber.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    context.startActivity(intent);
                }



            }
        });
holder.sendEmail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email=MainActivity.model.getSignUserInfo().getValue().get("Email");
        if(email.equals("null"))
        {

            Snackbar.make(holder.sendCall,"Login,Please",Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            String Email=holder.TextView_ViewHolder_Email.getText().toString().trim();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", email, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
            context.startActivity(Intent.createChooser(emailIntent, null));
        }


    }
});



    }

    @Override
    public int getItemCount() {
        return list.size();

    }
}

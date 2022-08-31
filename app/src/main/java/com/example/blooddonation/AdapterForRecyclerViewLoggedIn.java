package com.example.blooddonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.TextView_ViewHolder_District.setText(dis);




    }

    @Override
    public int getItemCount() {
        return list.size();

    }
}

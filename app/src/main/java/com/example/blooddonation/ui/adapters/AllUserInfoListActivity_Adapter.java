package com.example.blooddonation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.R;
import com.example.blooddonation.ui.datatypes.AllUserInfoListActivity_DataType;
import com.example.blooddonation.ui.datatypes.DataTypeForRecyclerView_ActivitySearchResult;
import com.example.blooddonation.ui.viewholders.AllUserInfoListActivity_ViewHolder;
import com.example.blooddonation.ui.viewholders.ViewHolderForRecyclerViewLoggedIn;

import java.util.List;

public class AllUserInfoListActivity_Adapter extends RecyclerView.Adapter<AllUserInfoListActivity_ViewHolder> {
    Context context;
    List<AllUserInfoListActivity_DataType> list;

    public AllUserInfoListActivity_Adapter(Context context, List<AllUserInfoListActivity_DataType> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllUserInfoListActivity_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewOfRecyclerLayout = LayoutInflater.from(context).
                inflate(R.layout.layout_recycler_all_user_info_activity, parent, false);

        AllUserInfoListActivity_ViewHolder viewHolder =
                new AllUserInfoListActivity_ViewHolder(viewOfRecyclerLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserInfoListActivity_ViewHolder holder, int position) {
        String name = list.get(position).Name;
        holder.TV_AllUserInfoListActivity_VH_Name.setText(name);
        String phoneNumber = list.get(position).PhoneNumber;
        holder.TV_AllUserInfoListActivity_VH_Phone.setText(phoneNumber);
        String email = list.get(position).Email;
        holder.TV_AllUserInfoListActivity_VH_Email.setText(email);
        String dis = list.get(position).District;
        String subDis = list.get(position).SubDistrict;
        holder.TV_AllUserInfoListActivity_VH_Location.setText(dis + "," + subDis);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

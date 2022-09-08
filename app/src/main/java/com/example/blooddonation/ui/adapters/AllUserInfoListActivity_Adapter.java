package com.example.blooddonation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.MainActivity;
import com.example.blooddonation.R;
import com.example.blooddonation.ui.datatypes.AllUserInfoListActivity_DataType;
import com.example.blooddonation.ui.viewholders.AllUserInfoListActivity_ViewHolder;
import com.google.android.material.snackbar.Snackbar;

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
    //    String email = list.get(position).Email;
       // holder.TV_AllUserInfoListActivity_VH_Email.setText(email);
        String gender = list.get(position).Gender;
        holder.TV_AllUserInfoListActivity_VH_Gender.setText(gender);
        String bloodGroup = list.get(position).BloodGroup;
        holder.TV_AllUserInfoListActivity_VH_Blood_Group.setText(bloodGroup);
        String age=list.get(position).Age;
        holder.TV_AllUserInfoListActivity_VH_Age.setText(age);
        String dis = list.get(position).District;
        String subDis = list.get(position).SubDistrict;
        holder.TV_AllUserInfoListActivity_VH_Location.setText(dis + "," + subDis);

        String CurrentUserEmail = MainActivity.model.getSignUserInfo().getValue().get("Email");
        if(CurrentUserEmail.equals("null"))
        {

            holder.TV_AllUserInfoListActivity_VH_Phone.setText("Available");
//            holder.TV_AllUserInfoListActivity_VH_Email.setText("Available");
        }
        ///
        holder.TV_AllUserInfoListActivity_VH_sendCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CurrentUserEmail.equals("null")) {

                    Snackbar.make(holder.TV_AllUserInfoListActivity_VH_sendCall, "Login,Please", Snackbar.LENGTH_SHORT).show();
                } else {
                    String number = holder.TV_AllUserInfoListActivity_VH_Phone.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    context.startActivity(intent);
                }


            }
        });
//        holder.TV_AllUserInfoListActivity_VH_sendEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = MainActivity.model.getSignUserInfo().getValue().get("Email");
//                if (email.equals("null")) {
//
//                    Snackbar.make(holder.TV_AllUserInfoListActivity_VH_sendEmail, "Login,Please", Snackbar.LENGTH_SHORT).show();
//                } else {
//                    String Email = holder.TV_AllUserInfoListActivity_VH_Email.getText().toString().trim();
//                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                            "mailto", email, null));
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
//                    context.startActivity(Intent.createChooser(emailIntent, null));
//                }
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

package com.example.blooddonation.ui.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.blooddonation.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllUserInfoListActivity_ViewHolder extends RecyclerView.ViewHolder {
    //VH=ViewHolder
    //TV=TextView
    public TextView TV_AllUserInfoListActivity_VH_Name;
  //  public TextView TV_AllUserInfoListActivity_VH_Email;
    public TextView TV_AllUserInfoListActivity_VH_Phone;
    public TextView TV_AllUserInfoListActivity_VH_Gender;
    public TextView TV_AllUserInfoListActivity_VH_Blood_Group;
    public TextView TV_AllUserInfoListActivity_VH_Location;
    public TextView TV_AllUserInfoListActivity_VH_Age;

    public ImageButton TV_AllUserInfoListActivity_VH_sendCall;
 //   public ImageButton TV_AllUserInfoListActivity_VH_sendEmail;


    public AllUserInfoListActivity_ViewHolder(@NonNull View itemView) {
        super(itemView);

        TV_AllUserInfoListActivity_VH_Name=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_Name);
    //    TV_AllUserInfoListActivity_VH_Email=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_Email);
        TV_AllUserInfoListActivity_VH_Phone=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_PhoneNumber);
        TV_AllUserInfoListActivity_VH_Gender=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_Gender);
        TV_AllUserInfoListActivity_VH_Blood_Group=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_BloodGroup);
        TV_AllUserInfoListActivity_VH_Location=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_Location);
        TV_AllUserInfoListActivity_VH_sendCall=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_call);
        TV_AllUserInfoListActivity_VH_Age=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_Age);
      //  TV_AllUserInfoListActivity_VH_sendEmail=itemView.findViewById(R.id.RecyclerLayout_AllUserInfoList_sendEmail);

    }
}

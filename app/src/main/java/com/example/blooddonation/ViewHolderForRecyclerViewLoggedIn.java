package com.example.blooddonation;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderForRecyclerViewLoggedIn extends RecyclerView.ViewHolder {
        public TextView TextView_ViewHolder_Name;
        public TextView TextView_ViewHolder_PhoneNumber;
        public TextView TextView_ViewHolder_Email;
        public TextView TextView_ViewHolder_BloodGroup;
        public TextView TextView_ViewHolder_District;
        public TextView TextView_ViewHolder_SubDistrict;
        public  ViewHolderForRecyclerViewLoggedIn(@NonNull View itemView) {
            super(itemView);

            TextView_ViewHolder_Name=itemView.findViewById(R.id.Label_RecyclerViewLayout_SearchOnLoggedIn_Name);
            TextView_ViewHolder_PhoneNumber=itemView.findViewById(R.id.Label_RecyclerViewLayout_SearchOnLoggedIn_PhoneNumber);
            TextView_ViewHolder_Email=itemView.findViewById(R.id.Label_RecyclerViewLayout_SearchOnLoggedIn_Email);
            TextView_ViewHolder_District=itemView.findViewById(R.id.Label_RecyclerViewLayout_SearchOnLoggedIn_Location);

        }


}

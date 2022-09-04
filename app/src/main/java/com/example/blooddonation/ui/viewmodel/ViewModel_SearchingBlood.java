package com.example.blooddonation.ui.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModel_SearchingBlood {

   public  HashMap<String, HashMap<String, String>> UserInfoListByEmail = new HashMap<>();
    public  HashMap<String, List<String>> UserInfoListByDistrict = new HashMap<>();
    public  HashMap<String, List<String>> UserInfoListBySubDistrict = new HashMap<>();
    public  HashMap<String, List<String>> UserInfoListByBloodGroup = new HashMap<>();


    public void AllUserInfoList() {



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserInfo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = (String) document.get("Name");
                                //  String Username=(String)document.get("UserName");
                                String phoneNumber = (String) document.get("PhoneNumber");
                                String email = (String) document.get("Email");
                                String bloodGroup = (String) document.get("BloodGroup");
                                String gender = (String) document.get("Gender");
                                String district = (String) document.get("District");
                                String subDistrict = (String) document.get("SubDistrict");
                                String age = (String) document.get("Age");
                                // Log.i("User:",name+"->"+phoneNumber+"->"+email+"->"+bloodGroup+"->"+gender+"->"+district+"->"+subDistrict+"->"+age);
                                HashMap<String, String> data = new HashMap<String, String>();
                                data.put("Name", name);
                                data.put("PhoneNumber", phoneNumber);
                                data.put("BloodGroup", bloodGroup);
                                data.put("Gender", gender);
                                data.put("District", district);
                                data.put("SubDistrict", subDistrict);
                                data.put("Age", age);
                                data.put("Email", email);

                                //
                                List<String>l=new ArrayList<>();
                                l.add(email);
                                UserInfoListByEmail.put(email, data);
                                if (district!= null)
                                {
                                    if(UserInfoListByDistrict.get(district)==null)
                                        UserInfoListByDistrict.put(district,new ArrayList<>());
                                    UserInfoListByDistrict.get(district).addAll(l);

                                }
                                if (subDistrict != null)
                                {
                                    if(UserInfoListBySubDistrict.get(subDistrict)==null)
                                        UserInfoListBySubDistrict.put(subDistrict,new ArrayList<>());
                                    UserInfoListBySubDistrict.get(subDistrict).addAll(l);

                                }

                                if (bloodGroup!=null)
                                {
                                    if(UserInfoListByBloodGroup.get(bloodGroup)==null)
                                        UserInfoListByBloodGroup.put(bloodGroup,new ArrayList<>());
                                    UserInfoListByBloodGroup.get(bloodGroup).addAll(l);
                                }

                                Log.i("User By Email", String.valueOf(UserInfoListByEmail));
                                Log.i("User By Dis", String.valueOf(UserInfoListByDistrict));
                                Log.i("User By subDis", String.valueOf(UserInfoListBySubDistrict));
                                Log.i("User By Blood", String.valueOf(UserInfoListByBloodGroup));

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }

                });


    }


}

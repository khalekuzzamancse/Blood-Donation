package com.example.blooddonation.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  ViewModel_UserProfileInfo extends ViewModel {
    private FirebaseFirestore db;
    private  FirebaseAuth auth;
    FirebaseUser currentUser;
    private MutableLiveData<HashMap<String,String>>SignUserInfo;
//
    public ViewModel_UserProfileInfo() {

        HashMap<String,String> tmp=new HashMap<>();
        tmp.put("Email","null");
        tmp.put("isDonor","null");
        SignUserInfo=new MutableLiveData<>(tmp);
       auth = FirebaseAuth.getInstance();

       FirebaseUser currentUser = auth.getCurrentUser();
       if (currentUser!=null)
        {
            db = FirebaseFirestore.getInstance();
            db.collection("UserInfo")
                    .document(currentUser.getEmail())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot q, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                //
                            } else {

                                HashMap<String,String> tmp=new HashMap<>();
                                DocumentSnapshot document =q;
                                String name= (String) document.get("Name");
                                tmp.put("Name",name);
                                String email= (String) document.get("Email");
                                tmp.put("Email",email);
                                String phone= (String) document.get("PhoneNumber");
                                tmp.put("PhoneNumber",phone);
                                String UserName= (String) document.get("UserName");
                                tmp.put("UserName",UserName);
                                String dis= (String) document.get("District");
                                tmp.put("District",dis);
                                String subDis= (String) document.get("SubDistrict");
                                tmp.put("SubDistrict",subDis);
                                String isDonor=(String) document.get("isDonor");
                                tmp.put("isDonor",isDonor);
                                String gender= (String) document.get("Gender");
                                tmp.put("Gender",gender);
                                String age=(String) document.get("Age");
                                tmp.put("Age",age);
                                String bloodGroup= (String) document.get("BloodGroup");
                                tmp.put("BloodGroup",bloodGroup);

                                SignUserInfo.postValue(tmp);

                            }
                        }
                    });

        }

    }

    public MutableLiveData<HashMap<String,String>> getSignUserInfo() {

        return SignUserInfo;
    }





}

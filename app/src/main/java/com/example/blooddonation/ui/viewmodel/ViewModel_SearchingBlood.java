package com.example.blooddonation.ui.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewModel_SearchingBlood extends ViewModel {

    private MutableLiveData<HashMap<String, HashMap<String, String>>> UserInfoListByEmail;
//    public  HashMap<String, List<String>> UserInfoListByDistrict = new HashMap<>();
//
    private MutableLiveData<HashMap<String, List<String>>>UserInfoListByBloodGroup;
    public   MutableLiveData<HashMap<String, List<String>>> UserInfoListBySubDistrict;



    public ViewModel_SearchingBlood() {
        //initializing UserInfoList to avoid null pointer exception
        HashMap<String,String>ini1=new HashMap<>();
        String s="null";
        HashMap<String, HashMap<String, String>>ini=new HashMap<>();
        ini.put(s,ini1);
        UserInfoListByEmail = new MutableLiveData<>(ini);
        //initializing BloodGroupList to avoid null pointer exception
        List<String>l=new ArrayList<>();
        l.add("null");
        HashMap<String, List<String>>hm=new HashMap<>();
        hm.put("null",l);
        UserInfoListByBloodGroup=new MutableLiveData<>(hm);
        //initializing UserInfoListBySubDistrict to avoid null pointer exception
        UserInfoListBySubDistrict=new MutableLiveData<>(hm);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("UserInfo")
                .addSnapshotListener((QuerySnapshot q, FirebaseFirestoreException e) -> {
                    if (e != null) {
                        //
                    } else {
//                        List<AllUserInfoListActivity_DataType> l = new ArrayList<>();
//                        l = q.toObjects(AllUserInfoListActivity_DataType.class);
//                        Log.i("Alhamdulliah,Size", String.valueOf(l.size()));

                        for (DocumentSnapshot document : q) {
                            String name = (String) document.get("Name");
                            //  String Username=(String)document.get("UserName");
                            String phoneNumber = (String) document.get("PhoneNumber");
                            String email = (String) document.get("Email");
                            String bloodGroup = (String) document.get("BloodGroup");
                            String gender = (String) document.get("Gender");
                            String district = (String) document.get("District");
                            String subDistrict = (String) document.get("SubDistrict");
                            String age = (String) document.get("Age");
                        //    Log.i("Alhamdulliah,User:", name + "->" + phoneNumber + "->" + email + "->" + bloodGroup + "->" + gender + "->" + district + "->" + subDistrict + "->" + age);
                            HashMap<String, String> data = new HashMap<String, String>();
                            data.put("Name", name);
                            data.put("PhoneNumber", phoneNumber);
                            data.put("BloodGroup", bloodGroup);
                            data.put("Gender", gender);
                            data.put("District", district);
                            data.put("SubDistrict", subDistrict);
                            data.put("Age", age);
                            data.put("Email", email);
                            HashMap<String, HashMap<String, String>> tmp = new HashMap<>();
                                tmp = UserInfoListByEmail.getValue();
                                tmp.put(email, data);
                                UserInfoListByEmail.postValue(tmp);
                                //getting the email and concert it to list
                                List<String>tmpEmail=new ArrayList<>();
                                tmpEmail.add(email);
                                //creating a temparary hashmap
                            HashMap<String, List<String>>tmpHm=new HashMap<>();

//                            //broupgroup
//                            tmpHm=UserInfoListByBloodGroup.getValue();
//                            List<String>tmpEmail2=tmpHm.get(bloodGroup);
//                            if(tmpEmail2==null)
//                                tmpEmail2=new ArrayList<>();
//                            tmpEmail2.addAll(tmpEmail);
//                            tmpHm.put(bloodGroup,tmpEmail2);
//                            UserInfoListByBloodGroup.postValue(tmpHm);

                            //adding district wise
                            HashMap<String,List<String>>exitingHashMap=new HashMap<>();
                            //pulling out the hashmap form Livedata
                            exitingHashMap=UserInfoListBySubDistrict.getValue();
                            List<String>existingList=new ArrayList<>();
                            //pulling out the existing List form the hashmap for the currently find subDistrict
                            existingList=exitingHashMap.get(subDistrict);
                            if(existingList==null)
                                existingList=new ArrayList<>();
                            existingList.add(email);
                            Log.i("Alhamdulliah,Exis", subDistrict+"->"+String.valueOf(existingList));
                            //adding  the new list to the hashmap
                            exitingHashMap.put(subDistrict,existingList);
                            //posting the recetly modified hashmap to Livedata
                            Log.i("Alhamdulliah,ExisHashmap", String.valueOf(exitingHashMap));
                            UserInfoListBySubDistrict.postValue(exitingHashMap);

                        }
                    }
                });

    }

    private void AllUserInfoList() {


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
                                List<String> l = new ArrayList<>();
                                l.add(email);
//                                UserInfoListByEmail.put(email, data);
//                                if (district!= null)
//                                {
//                                    if(UserInfoListByDistrict.get(district)==null)
//                                        UserInfoListByDistrict.put(district,new ArrayList<>());
//                                    UserInfoListByDistrict.get(district).addAll(l);
//
//                                }
//                                if (subDistrict != null)
//                                {
//                                    if(UserInfoListBySubDistrict.get(subDistrict)==null)
//                                        UserInfoListBySubDistrict.put(subDistrict,new ArrayList<>());
//                                    UserInfoListBySubDistrict.get(subDistrict).addAll(l);
//
//                                }

//                                if (bloodGroup!=null)
//                                {
//                                    if(UserInfoListByBloodGroup.get(bloodGroup)==null)
//                                        UserInfoListByBloodGroup.put(bloodGroup,new ArrayList<>());
//                                    UserInfoListByBloodGroup.get(bloodGroup).addAll(l);
//                                }

//                                Log.i("User By Email", String.valueOf(UserInfoListByEmail));
//                                Log.i("User By Dis", String.valueOf(UserInfoListByDistrict));
//                                Log.i("User By subDis", String.valueOf(UserInfoListBySubDistrict));

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }

                });


    }

    public MutableLiveData<HashMap<String, HashMap<String, String>>> getUserInfoListByEmail() {
        return UserInfoListByEmail;
    }
    public MutableLiveData<HashMap<String, List<String>>> getUserInfoListByBloodGroup() {
        return UserInfoListByBloodGroup;
    }

    public MutableLiveData<HashMap<String, List<String>>> getUserInfoListBySubDistrict() {
        return UserInfoListBySubDistrict;
    }
}

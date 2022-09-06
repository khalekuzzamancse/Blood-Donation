package com.example.blooddonation.ui.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blooddonation.MainActivity;
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
    private MutableLiveData<HashMap<String, List<String>>> UserInfoListByDistrict;
    private MutableLiveData<HashMap<String, List<String>>> UserInfoListByBloodGroup;
    private MutableLiveData<HashMap<String, List<String>>> UserInfoListBySubDistrict;
    private MutableLiveData<HashMap<String, List<String>>>DistrictListHashMap;



    public ViewModel_SearchingBlood() {
        //initializing UserInfoList to avoid null pointer exception
        HashMap<String, String> ini1 = new HashMap<>();
        String s = "";
        HashMap<String, HashMap<String, String>> ini = new HashMap<>();
        ini.put(s, ini1);
        UserInfoListByEmail = new MutableLiveData<>(ini);


        //initializing BloodGroupList to avoid null pointer exception

//        //initializing UserInfoListBySubDistrict to avoid null pointer exception
        initializeHashMap_District();
        initializeHashMap_SubDistrict();
        initializeHashMap_BloodGroup();
        initializeDistrictListHashMap();


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


                            String CurrentUserEmail = MainActivity.model.getSignUserInfo().getValue().get("Email");
                            //we do not want to show the current singed in user information in the list
                            updateDistrictListHashMap(district,subDistrict);
                           if(!CurrentUserEmail.equals(email))
                            {
                                HashMap<String, HashMap<String, String>> tmp = new HashMap<>();
                                tmp = UserInfoListByEmail.getValue();
                                tmp.put(email, data);
                                UserInfoListByEmail.postValue(tmp);
                                //getting the email and concert it to list

                                //
                                updateDistrictHashMap(email, district);

                                //
                                updateBloodGroupWiseHashMap(email, bloodGroup);
                                //  Log.i("Alhamdulliah,BloodExisHashmap", String.valueOf(existingHashMap_BloodGroup));
                                //subDistrictWise
                                updateSubDistrictHashMap(email, subDistrict);
                                //

                            }

                        }
                    }
                });

    }

    private void initializeHashMap_District() {
        List<String> List4 = new ArrayList<>();
        List4.add("");
        HashMap<String, List<String>> HashMap4 = new HashMap();
        HashMap4.put("", List4);
        UserInfoListByDistrict = new MutableLiveData<>(HashMap4);

    }

    private void initializeHashMap_SubDistrict() {
        List<String> List3 = new ArrayList<>();
        List3.add("");
        HashMap<String, List<String>> HashMap3 = new HashMap();
        HashMap3.put("", List3);
        UserInfoListBySubDistrict = new MutableLiveData<>(HashMap3);
    }

    private void initializeHashMap_BloodGroup() {
        List<String> l = new ArrayList<>();
        l.add("");
        HashMap<String, List<String>> hm = new HashMap<>();
        hm.put("", l);
       // initializeHashMap_District();
        UserInfoListByBloodGroup = new MutableLiveData<>(hm);
    }

    private void initializeDistrictListHashMap() {
        List<String> L5 = new ArrayList<>();
        L5.add("");
        HashMap<String, List<String>> HM = new HashMap<>();
        HM.put("", L5);
      //  initializeHashMap_District();
        DistrictListHashMap = new MutableLiveData<>(HM);

    }
    private void updateDistrictListHashMap(String district,String subDistrict)
    {
        HashMap<String, List<String>> exitingHashMap = new HashMap<>();
        //pulling out the hashmap form Livedata
        exitingHashMap = DistrictListHashMap.getValue();
        List<String> existingList = new ArrayList<>();
        //pulling out the existing List form the hashmap for the currently find subDistrict
        existingList = exitingHashMap.get(district);
        if (existingList == null)
            existingList = new ArrayList<>();
        existingList.add(subDistrict);
        //     Log.i("Alhamdulliah,Exis", subDistrict+"->"+String.valueOf(existingList));
        //adding  the new list to the hashmap
        exitingHashMap.put(district, existingList);
        Log.i("OKAY", String.valueOf(exitingHashMap));
        DistrictListHashMap.postValue(exitingHashMap);
    }


    private void updateDistrictHashMap(String email, String district) {
        //adding district wise
        HashMap<String, List<String>> exitingHashMap = new HashMap<>();
        //pulling out the hashmap form Livedata
        exitingHashMap = UserInfoListByDistrict.getValue();
        List<String> existingList = new ArrayList<>();
        //pulling out the existing List form the hashmap for the currently find subDistrict
        existingList = exitingHashMap.get(district);
        if (existingList == null)
            existingList = new ArrayList<>();
        existingList.add(email);
        //     Log.i("Alhamdulliah,Exis", subDistrict+"->"+String.valueOf(existingList));
        //adding  the new list to the hashmap
        exitingHashMap.put(district, existingList);
        //posting the recetly modified hashmap to Livedata
        //  Log.i("Alhamdulliah,subDisExisHashmap", String.valueOf(exitingHashMap));
        UserInfoListByDistrict.postValue(exitingHashMap);
    }

    private void updateSubDistrictHashMap(String email, String district) {
        //adding district wise
        HashMap<String, List<String>> exitingHashMap = new HashMap<>();
        //pulling out the hashmap form Livedata
        exitingHashMap = UserInfoListBySubDistrict.getValue();
        List<String> existingList = new ArrayList<>();
        //pulling out the existing List form the hashmap for the currently find subDistrict
        existingList = exitingHashMap.get(district);
        if (existingList == null)
            existingList = new ArrayList<>();
        existingList.add(email);
        //     Log.i("Alhamdulliah,Exis", subDistrict+"->"+String.valueOf(existingList));
        //adding  the new list to the hashmap
        exitingHashMap.put(district, existingList);
        //posting the recetly modified hashmap to Livedata
        //  Log.i("Alhamdulliah,subDisExisHashmap", String.valueOf(exitingHashMap));
        UserInfoListBySubDistrict.postValue(exitingHashMap);
    }

    private void updateBloodGroupWiseHashMap(String email, String bloodGroup) {
        HashMap<String, List<String>> existingHashMap_BloodGroup = new HashMap<>();
        //pulling out the HashMap form the LiveData
        existingHashMap_BloodGroup = UserInfoListByBloodGroup.getValue();
        List<String> existingList_BloodGroup = existingHashMap_BloodGroup.get(bloodGroup);
        //adding the recent email to the list
        if (existingList_BloodGroup == null)
            existingList_BloodGroup = new ArrayList<>();
        existingList_BloodGroup.add(email);
        //adding the list to the existing  hashmap
        existingHashMap_BloodGroup.put(bloodGroup, existingList_BloodGroup);
        //adding the modified hashmap to the livedata
        UserInfoListByBloodGroup.postValue(existingHashMap_BloodGroup);
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

    public MutableLiveData<HashMap<String, List<String>>> getUserInfoListByDistrict() {
        return UserInfoListByDistrict;
    }

    public MutableLiveData<HashMap<String, List<String>>> getDistrictListHashMap() {
        return DistrictListHashMap;
    }
}

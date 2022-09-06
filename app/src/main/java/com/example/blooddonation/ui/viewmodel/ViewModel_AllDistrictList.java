package com.example.blooddonation.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blooddonation.MainActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewModel_AllDistrictList extends ViewModel {
    private MutableLiveData<HashMap<String, List<String>>> DistrictListHashMap;

    public ViewModel_AllDistrictList() {



    }


    private void initializeDistrictListHashMap() {
        List<String> L5 = new ArrayList<>();
        L5.add("");
        HashMap<String, List<String>> HM = new HashMap<>();
        HM.put("", L5);
        //  initializeHashMap_District();
        DistrictListHashMap = new MutableLiveData<>(HM);

    }

    private void updateDistrictListHashMap(String district, String subDistrict) {
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

    public MutableLiveData<HashMap<String, List<String>>> getDistrictListHashMap() {
        return DistrictListHashMap;
    }
}

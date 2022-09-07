package com.example.blooddonation.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blooddonation.ui.datatypes.DataType_DistrictList;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewModel_AllDistrictList extends ViewModel {
    private MutableLiveData<HashMap<String, List<String>>> DistrictListHashMap;
    private MutableLiveData<List<String>>DistrictList;

    public ViewModel_AllDistrictList() {
        List<String> L5 = new ArrayList<>();
        L5.add("");
        HashMap<String, List<String>> HM = new HashMap<>();
        HM.put("", L5);
        //  initializeHashMap_District();
        DistrictListHashMap = new MutableLiveData<>(HM);
        //initialize district list;
        List<String>L=new ArrayList<>();
        DistrictList=new MutableLiveData<>(L);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db = FirebaseFirestore.getInstance();
            db.collection("DistrictList")
                    .addSnapshotListener((QuerySnapshot q, FirebaseFirestoreException e) -> {
                        if (e != null) {
                            //
                        } else {

                                DataType_DistrictList d=new DataType_DistrictList();
                                List<DataType_DistrictList> list = new ArrayList<>();
                                list = q.toObjects(DataType_DistrictList.class);
                                setData(list);

                        }
                    });


    }

    public MutableLiveData<List<String>> getDistrictList() {
        return DistrictList;
    }


    public MutableLiveData<HashMap<String, List<String>>> getDistrictListHashMap() {
        return DistrictListHashMap;
    }
    private void setData(List<DataType_DistrictList>list)
    {

      for(int i=0;i<list.size();i++)
      {
          List<String>existingDistrictList=new ArrayList<>();
          List<String> SubDistrictList=list.get(i).SubDistrict;
          existingDistrictList=DistrictList.getValue();
          String district=list.get(i).Id;
          existingDistrictList.add(district);
          DistrictList.postValue(existingDistrictList);
          Log.i("Getting,MM", String.valueOf(existingDistrictList));
          HashMap<String,List<String>>tmp=new HashMap<>();
          tmp=DistrictListHashMap.getValue();
          tmp.put(district,SubDistrictList);
          Log.i("Getting,M", String.valueOf(tmp));
          DistrictListHashMap.postValue(tmp);

      }


    }

}

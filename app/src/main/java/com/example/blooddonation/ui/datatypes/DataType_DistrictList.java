package com.example.blooddonation.ui.datatypes;
import com.google.firebase.firestore.DocumentId;
import java.util.List;
public class DataType_DistrictList {

    @DocumentId
  public   String Id;
    public List<String>SubDistrict;
}

package com.example.blooddonation.database;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.UUID;

public class WritingDocument  {
    CallbackResult callbackResult;
    FirebaseFirestore db;
    private static final String collectionName = "UserInfo";

    public WritingDocument() {
        db = FirebaseFirestore.getInstance();
    }
    OnCompleteListener<Void> callbackAddDoc = task -> {
        if (!task.isSuccessful())
           callbackResult.isSuccess(false);
        else
           callbackResult.isSuccess(true);
    };

    public void updateDocument(String documentName, HashMap<String, Object> updatedData,CallbackResult callbackResult) {
        this.callbackResult=callbackResult;
        CollectionReference cRef = db.collection(collectionName);
        DocumentReference docRef = cRef.document(documentName);
        Task<Void> snapshotTask = docRef.set(updatedData, SetOptions.merge());
        snapshotTask.addOnCompleteListener(callbackAddDoc);
    }
    public void updateDocument(String documentName, HashMap<String, Object> updatedData) {
        CollectionReference cRef = db.collection(collectionName);
        DocumentReference docRef = cRef.document(documentName);
       docRef.set(updatedData, SetOptions.merge());

    }
    public void updateLocation(HashMap<String, Object> updatedData) {
        CollectionReference cRef = db.collection("Location");
        DocumentReference docRef = cRef.document(UUID.randomUUID().toString());
        docRef.set(updatedData, SetOptions.merge());

    }

}

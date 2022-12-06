package com.example.blooddonation.database;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.List;

public class FirebaseAuthCustom {
    private CallbackUserProfile callback;
private     FirebaseFirestore db;
 private    FirebaseUser user;
    private static final String collection = "UserInfo";

    public FirebaseAuthCustom() {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

   private EventListener<DocumentSnapshot> callbackDocSnapShot = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {

            if (document != null && document.exists()) {
                //  Log.i("Fetched-Doc ", String.valueOf(document.getData()));
                DomainUserInfo info = document.toObject(DomainUserInfo.class);
                Log.i("UserInfo", info.toString());
                callback.getProfile(info);
            }
        }
    };

    public void getUserInfo(CallbackUserProfile callback) {
        this.callback = callback;
        if (user != null) {
            DocumentReference docRef = db.collection("UserInfo").document(user.getEmail());
            docRef.addSnapshotListener(callbackDocSnapShot);
        }

    }

    public String getUserEmail() {
        if (user != null)
            return user.getEmail();
        else
            return "";
    }
    public FirebaseUser getUser()
    {
        if(user==null)
            return null;
        return user;
    }

}

package com.eve_coding.firebasecrud.service;

import com.eve_coding.firebasecrud.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private static final String COLLECTION_NAME = "users";

    public String saveUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = db.collection(COLLECTION_NAME).document().set(user);

        return apiFuture.get().getUpdateTime().toString();
    }

    public List<User> users() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReferences = db.collection(COLLECTION_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReferences.iterator();

        List<User> users = new ArrayList<>();
        User user = null;

        while (iterator.hasNext()){
            DocumentReference documentReference = iterator.next();
            ApiFuture<DocumentSnapshot> snapshotApiFuture = documentReference.get();
            DocumentSnapshot documentSnapshot = snapshotApiFuture.get();

            user = documentSnapshot.toObject(User.class);
            users.add(user);
        }

        return users;
    }

    public User user(String name) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> apiFuture = db.collection(COLLECTION_NAME).whereEqualTo("name",name).get();
        if(apiFuture.get().isEmpty()){
            return null;
        }else{
            QueryDocumentSnapshot queryDocumentSnapshot = apiFuture.get().getDocuments().get(0);

            return queryDocumentSnapshot.toObject(User.class);
        }
    }
}

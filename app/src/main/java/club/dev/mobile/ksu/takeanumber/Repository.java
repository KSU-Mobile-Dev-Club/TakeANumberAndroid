package club.dev.mobile.ksu.takeanumber;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private FirebaseDatabase mDatabase;
    private static Repository mSoleInstance;
    private DatabaseReference mReference;
    private LiveData<List<HelpSession>> mHelpSessions;

    public static Repository getInstance() {
        if (mSoleInstance == null) {
            mSoleInstance = new Repository();
        }
        return mSoleInstance;
    }

    private Repository() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        mHelpSessions = Transformations.map(
                new HelpSessionLiveData(mReference.child("HelpSession2")),
                new HelpSessionDeserializer());
    }

    public void addHelpSession(HelpSession session) {
        String key = mReference.child("HelpSession2").push().getKey();
        session.setFirebaseKey(key);
        mReference.child("HelpSession2").child(key).setValue(session);
    }

    public LiveData<List<HelpSession>> getHelpSessions() {
        return mHelpSessions;
    }

    private class HelpSessionDeserializer implements Function<DataSnapshot, List<HelpSession>> {

        @Override
        public List<HelpSession> apply(DataSnapshot input) {
            List<HelpSession> sessionList = new ArrayList<>();
            for (DataSnapshot snap : input.getChildren()) {
                HelpSession session = snap.getValue(HelpSession.class);
                sessionList.add(session);
            }
            return sessionList;
        }
    }

}

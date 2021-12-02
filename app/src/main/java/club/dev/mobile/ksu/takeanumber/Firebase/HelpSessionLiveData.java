package club.dev.mobile.ksu.takeanumber.Firebase;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;

public class HelpSessionLiveData extends LiveData<DataSnapshot> {
    private List<HelpSession> mSessions = new ArrayList<>();
    private Query mQuery;
    private MyEventListener mListener = new MyEventListener();

    public HelpSessionLiveData(Query q) {
        mQuery = q;
    }

    @Override
    protected void onActive() {
        mQuery.addValueEventListener(mListener);
    }

    @Override
    protected void onInactive() {
        mQuery.removeEventListener(mListener);
    }

    private class MyEventListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot == null) return;

            setValue(dataSnapshot);
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                HelpSession session = snap.getValue(HelpSession.class);
                session.setFirebaseKey(snap.getKey());
                mSessions.add(session);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
package club.dev.mobile.ksu.takeanumber;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
                mSessions.add(session);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
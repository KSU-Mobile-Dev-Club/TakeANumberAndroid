package club.dev.mobile.ksu.takeanumber;

import com.google.firebase.database.FirebaseDatabase;

public class Repository {
    private FirebaseDatabase mDatabase;
    private static Repository mSoleInstance;

    public static Repository getInstance() {
        if (mSoleInstance == null) {
            mSoleInstance = new Repository();
        }
        return mSoleInstance;
    }

    private Repository() {
        mDatabase = FirebaseDatabase.getInstance();
    }

}

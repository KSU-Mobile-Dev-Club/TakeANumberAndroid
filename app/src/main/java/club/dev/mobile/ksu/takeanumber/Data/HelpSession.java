package club.dev.mobile.ksu.takeanumber.Data;

import com.google.firebase.database.Exclude;

public class HelpSession {
    private String name;
    private String firebaseKey;

    public HelpSession(String name) {
        this.name = name;
    }

    public HelpSession() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}

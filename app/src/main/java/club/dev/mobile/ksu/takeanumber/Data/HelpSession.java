package club.dev.mobile.ksu.takeanumber.Data;

import com.google.firebase.database.Exclude;

public class HelpSession {
    private String name;
    private String firebaseKey;
    private String taUserKey;

    public HelpSession(String name, String taUserKey) {
        this.name = name;
        this.taUserKey = taUserKey;
    }

    public HelpSession() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaUserKey() {
        return taUserKey;
    }

    public void setTaUserKey(String taUserKey) {
        this.taUserKey = taUserKey;
    }

    @Exclude
    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}

package club.dev.mobile.ksu.takeanumber.Data;

import com.google.firebase.database.Exclude;

public class Student {

    private String name;
    private long dateTime;
    private int currentStatus; //0: waiting, 1: being helped, 2: already helped
    private String firebaseKey;
    private boolean pressNo;
    private int helpTime;

    // Required for Firebase - do not remove
    public Student() {}

    public Student (String n, long dt, int t) {
        name = n;
        dateTime = dt;
        currentStatus = 0;
        helpTime = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public long getDateTime(){
        return dateTime;
    }

    public void setDateTime(long dt) {
        dateTime = dt;
    }

    public int getStatus() {
        return currentStatus;
    }

    public void setStatus(int s) {
        currentStatus = s;
    }

    public int getTime() { return helpTime; }

    public void setTime(int t) { helpTime = t; }

    @Exclude
    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public boolean equals(Student alternate) {
        if (alternate != null) {
            return firebaseKey.equals(alternate.firebaseKey);
        }
        else {
            return false;
        }
    }

    public boolean isPressNo() {
        return pressNo;
    }

    public void setPressNo(boolean pressNo) {
        this.pressNo = pressNo;
    }
}


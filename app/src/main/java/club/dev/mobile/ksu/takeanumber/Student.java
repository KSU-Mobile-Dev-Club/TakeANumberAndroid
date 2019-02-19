package club.dev.mobile.ksu.takeanumber;

import com.google.firebase.database.Exclude;

public class Student {

    private String name;
    private int dateTime;
    private int currentStatus; //0: waiting, 1: being helped, 2: already helped
    private String firebaseKey;

    Student(){

    }

    Student (String n, int dt) {
        name = n;
        dateTime = dt;
        currentStatus = 0;
    }

    private String getName() {
        return name;
    }

    public void setName(String s){
        name = s;
    }

    public int getDateTime(){
        return dateTime;
    }

    public void setDateTime(int dt) {
        dateTime = dt;
    }

    public int getStatus() {
        return currentStatus;
    }

    public void setStatus(int s) {
        currentStatus = s;
    }

    @Exclude
    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

}


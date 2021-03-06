package club.dev.mobile.ksu.takeanumber.Firebase;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.Data.Student;

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

    public void addStudentToQueue(Student student, String sessionKey) {
        DatabaseReference ref = mReference.child("HelpSession2").child(sessionKey).child("queue");
        String key = ref.push().getKey();
        student.setFirebaseKey(key);
        ref.child(key).setValue(student);
    }

    public LiveData<List<Student>> getStudentQueue(String sessionKey) {
        return Transformations.map(new StudentQueueLiveData(
                mReference.child("HelpSession2").child(sessionKey).child("queue").orderByChild("dateTime")),
                new StudentQueueDeserializer()
        );
    }

    public void deleteStudent(String sessionName, String studentKey)
    {
        mReference.child("HelpSession2").child(sessionName).child("queue").child(studentKey).removeValue();
    }

    public void updateStudent(String sessionName, Student student)
    {
        mReference.child("HelpSession2").child(sessionName).child("queue").child(student.getFirebaseKey()).setValue(student);
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
                session.setFirebaseKey(snap.getKey());
                sessionList.add(session);
            }
            return sessionList;
        }
    }

    private class StudentQueueDeserializer implements Function<DataSnapshot, List<Student>> {

        @Override
        public List<Student> apply(DataSnapshot input) {
            List<Student> students = new ArrayList<>();
            for (DataSnapshot snap : input.getChildren()) {
                Student student = snap.getValue(Student.class);
                student.setFirebaseKey(snap.getKey());
                students.add(student);
            }
            return students;
        }
    }
}

package club.dev.mobile.ksu.takeanumber.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.Firebase.Repository;

public class StudentQueueViewModel extends AndroidViewModel {
    private Repository mRepository;

    public StudentQueueViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
    }

    //Returns the student wait queue associated with the given Help Session
    //The list that is returned will be sorted in ascending order by DateTime
    public LiveData<List<Student>> getStudentQueue(String sessionKey) {
        return mRepository.getStudentQueue(sessionKey);
    }

    public void addStudentToQueue(Student student, String sessionKey) {
        mRepository.addStudentToQueue(student, sessionKey);
    }

    public void removeStudent(String sessionName, String studentKey) {
        mRepository.deleteStudent(sessionName, studentKey);
    }

    public void updateStudent(String sessionName, Student student) {
        mRepository.updateStudent(sessionName, student);
    }
}

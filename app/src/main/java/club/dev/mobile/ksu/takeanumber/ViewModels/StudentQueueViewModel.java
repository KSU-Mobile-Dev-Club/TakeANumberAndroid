package club.dev.mobile.ksu.takeanumber.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.Firebase.Repository;

public class StudentQueueViewModel extends AndroidViewModel {
    private Repository mRepository;

    public StudentQueueViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
    }

    public LiveData<List<Student>> getStudentQueue(HelpSession session) {
        return mRepository.getStudentQueue(session);
    }

    public void addStudentToQueue(Student student, HelpSession session) {
        mRepository.addStudentToQueue(student, session);
    }
}

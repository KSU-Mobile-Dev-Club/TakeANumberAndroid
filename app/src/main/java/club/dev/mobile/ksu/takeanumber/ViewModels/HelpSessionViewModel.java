package club.dev.mobile.ksu.takeanumber.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.Firebase.Repository;

public class HelpSessionViewModel extends AndroidViewModel {
    private Repository mRepository;

    public HelpSessionViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
    }

    public LiveData<List<HelpSession>> getHelpSessions() {
        return mRepository.getHelpSessions();
    }

    public void addHelpSession(HelpSession session) {
        mRepository.addHelpSession(session);
    }
}

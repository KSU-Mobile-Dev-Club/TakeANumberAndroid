package club.dev.mobile.ksu.takeanumber.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import club.dev.mobile.ksu.takeanumber.Data.Student
import club.dev.mobile.ksu.takeanumber.Firebase.Repository
import club.dev.mobile.ksu.takeanumber.Firebase.Repository.Companion.instance

class StudentQueueViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: Repository? = instance

    //Returns the student wait queue associated with the given Help Session
    //The list that is returned will be sorted in ascending order by DateTime
    fun getStudentQueue(sessionKey: String): LiveData<List<Student>> {
        return mRepository!!.getStudentQueue(sessionKey)
    }

    fun addStudentToQueue(student: Student, sessionKey: String) {
        if (!mRepository!!.isStudentInQueue(student, sessionKey)) {
            mRepository.addStudentToQueue(student, sessionKey)
        }
    }

    fun removeStudent(sessionName: String, studentKey: String) {
        mRepository!!.deleteStudent(sessionName, studentKey)
    }

    fun updateStudent(sessionName: String, student: Student) {
        mRepository!!.updateStudent(sessionName, student)
    }
}
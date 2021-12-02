package club.dev.mobile.ksu.takeanumber.Firebase

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import club.dev.mobile.ksu.takeanumber.Data.HelpSession
import club.dev.mobile.ksu.takeanumber.Data.Student
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Repository private constructor() {

    private val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val mReference: DatabaseReference = mDatabase.reference
    val helpSessions: LiveData<List<HelpSession>>

    fun addHelpSession(session: HelpSession) {
        val key = mReference.child("HelpSession").push().key
        session.firebaseKey = key
        mReference.child("HelpSession").child(key!!).setValue(session)
    }

    fun addStudentToQueue(student: Student, sessionKey: String) {
        val ref = mReference.child("HelpSession").child(sessionKey).child("queue")
        val key = ref.push().key
        student.firebaseKey = key
        ref.child(key!!).setValue(student)
    }

    fun getStudentQueue(sessionKey: String): LiveData<List<Student>> {
        return Transformations.map<DataSnapshot, List<Student>>(
            StudentQueueLiveData(
                mReference.child("HelpSession").child(sessionKey).child("queue")
                    .orderByChild("dateTime")
            ),
            StudentQueueDeserializer()
        )
    }

    fun deleteStudent(sessionName: String, studentKey: String) {
        mReference.child("HelpSession").child(sessionName).child("queue").child(studentKey)
            .removeValue()
    }

    fun updateStudent(sessionName: String, student: Student) {
        mReference.child("HelpSession").child(sessionName).child("queue")
            .child(student.firebaseKey).setValue(student)
    }

    private inner class HelpSessionDeserializer : Function<DataSnapshot, List<HelpSession>> {
        override fun apply(input: DataSnapshot): List<HelpSession> {
            val sessionList: MutableList<HelpSession> = ArrayList()
            for (snap in input.children) {
                val session = snap.getValue(HelpSession::class.java)!!
                session.firebaseKey = snap.key
                sessionList.add(session)
            }
            return sessionList
        }
    }

    private inner class StudentQueueDeserializer : Function<DataSnapshot, List<Student>> {
        override fun apply(input: DataSnapshot): List<Student> {
            val students: MutableList<Student> = ArrayList()
            for (snap in input.children) {
                val student = snap.getValue(Student::class.java)!!
                student.firebaseKey = snap.key
                students.add(student)
            }
            return students
        }
    }

    companion object {
        private var mSoleInstance: Repository? = null
        @JvmStatic
        val instance: Repository?
            get() {
                if (mSoleInstance == null) {
                    mSoleInstance = Repository()
                }
                return mSoleInstance
            }
    }

    init {
        helpSessions = Transformations.map<DataSnapshot, List<HelpSession>>(
            HelpSessionLiveData(mReference.child("HelpSession")),
            HelpSessionDeserializer()
        )
    }
}
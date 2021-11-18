package club.dev.mobile.ksu.takeanumber.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager

import club.dev.mobile.ksu.takeanumber.Data.HelpSession
import club.dev.mobile.ksu.takeanumber.R
import club.dev.mobile.ksu.takeanumber.ViewModels.HelpSessionViewModel

import com.google.firebase.auth.FirebaseAuth

class AddHelpSessionActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_help_session)

        mAuth = FirebaseAuth.getInstance()

        val createButton = findViewById<Button>(R.id.CreateHelpSessionButton)
        createButton.setOnClickListener {
            // Add a new help session to the database
            val mViewModel = ViewModelProviders.of(this).get(HelpSessionViewModel::class.java)
            val helpSessionTitle = findViewById<EditText>(R.id.NameOfHelpSession).text.toString()
            val helpSession = HelpSession(helpSessionTitle, mAuth.currentUser!!.uid)
            mViewModel.addHelpSession(helpSession)

            // Open the TA View to that help session
            val sessionIntent = Intent(this, TaHelpSessionActivity::class.java)
            intent.putExtra("sessionKey", helpSession.firebaseKey)
            startActivity(sessionIntent)
        }
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser == null) {
            // Sign in the user anonymously.
            mAuth.signInAnonymously().addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in successful

                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
        val NameOfCreator = findViewById<EditText>(R.id.NameOfCreator)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.contains("key_user_name")) {
            NameOfCreator.setText(sharedPreferences.getString("key_user_name", ""))
        }
    }
}

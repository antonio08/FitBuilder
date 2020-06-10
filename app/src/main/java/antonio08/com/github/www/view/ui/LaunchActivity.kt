/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import antonio08.com.github.www.R
import antonio08.com.github.www.log.Log
import antonio08.com.github.www.viewmodel.LaunchViewModel
import com.google.firebase.auth.FirebaseAuth

class LaunchActivity : AppCompatActivity() {
    private val logTag = LaunchActivity::class.java.canonicalName
    private lateinit var mViewModel: LaunchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        mViewModel = ViewModelProvider(this).get(LaunchViewModel::class.java)
        //Deprecated of(this).get(LaunchViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        // This is just a test to logout
        // FirebaseAuth.getInstance().signOut()

        val currentUser = mViewModel.lastFirebaseUserSignedIn

        // If there is already an account proceed to the Dashboard; else proceed to the Login Activity
        if (currentUser != null) {
            mViewModel.saveUserData(currentUser)
            Log.logMessage(logTag, "onStart()", "User logged, proceed to Dashboard")
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            Log.logMessage(logTag, "onStart()", "No user found, starting LoginActivity")
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}


/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import antonio08.com.github.www.R
import antonio08.com.github.www.viewmodel.LaunchViewModel
import com.google.firebase.auth.FirebaseAuth

class LaunchActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth

    lateinit var mLaunchViewModel: LaunchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        mLaunchViewModel = ViewModelProvider(this).get(LaunchViewModel::class.java)
            //Deprecated of(this).get(LaunchViewModel::class.java)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        // This is jus a test to logout
        //FirebaseAuth.getInstance().signOut()

        val currentUser = mAuth.currentUser

        // If there is already an account proceed to the Dashboard; else proceed to the Login Activity
        if (currentUser != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}


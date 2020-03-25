/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import antonio08.com.github.www.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LaunchActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)

        // If there is already and account proceed to the Dashboard; else proceed to the Login Activity
        if (googleSignInAccount != null)
        {

        }
        else
        {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}


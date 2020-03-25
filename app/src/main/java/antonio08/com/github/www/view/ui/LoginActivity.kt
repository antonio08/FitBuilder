/**
 * Created by Antonio Lozano on 2020-03-23.
 */

package antonio08.com.github.www.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import antonio08.com.github.www.R
import antonio08.com.github.www.service.network.Authentication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_GOOGLE_SIGN_IN = 0
    private val mClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.mSignInButton -> proceedWithGoogleLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialize()

        mSignInButton.setOnClickListener(mClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {
            RC_GOOGLE_SIGN_IN -> handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun initialize() {
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, Authentication.mGoogleSignInOptions)

        mSignInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    private fun proceedWithGoogleLogin() {
        val sigInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(sigInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val googleSignInAccount = task.getResult(ApiException::class.java)
            takeUserToDashboard()
        }
        catch (exception : ApiException)
        {
            Snackbar.make(mSignInButton, resources.getString(R.string.login_massage_failed), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun takeUserToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}
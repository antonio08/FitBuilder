/**
 * Created by Antonio Lozano on 2020-03-23.
 */

package antonio08.com.github.www.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import antonio08.com.github.www.R
import antonio08.com.github.www.contract.ILoginContract
import antonio08.com.github.www.contract.ILoginContract.RequestCodeConstants.RC_GOOGLE_SIGN_IN
import antonio08.com.github.www.log.Log
import antonio08.com.github.www.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ILoginContract {
    private val logTag = LoginActivity::class.java.canonicalName
    private lateinit var mViewModel: LoginViewModel
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInIntent: Intent

    private val mClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.mSignInButton -> proceedWithGoogleLogin()
        }
    }
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialize()

        mSignInButton.setOnClickListener(mClickListener)

        observeLoginResult()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mViewModel.result(requestCode, resultCode, data)
    }

    private fun initialize() {
        // Initialize Google Sig In Client
        val idToken = resources.getString(R.string.client_id)
        mGoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(idToken)
                .requestEmail()
                .requestProfile()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient =
            GoogleSignIn.getClient(this, mGoogleSignInOptions)

        // Initialize google Intent
        mGoogleSignInIntent = mGoogleSignInClient.signInIntent

        mSignInButton.setSize(SignInButton.SIZE_STANDARD)

        // Initialize View Model
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


    }

    private fun proceedWithGoogleLogin() {
        Log.logMessage(logTag, "proceedWithGoogleLogin()", "Starting Google login process")
        startActivityForResult(mGoogleSignInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun observeLoginResult() {
        mViewModel.getUser().observe(this, Observer { credentials ->
            // If we have the credentials then
            if (credentials != null) {
                // Sign in the user into Firebase
                mAuth.signInWithCredential(credentials).addOnCompleteListener(this) { task ->
                    // Save the user data if the authentication was successful and
                    // the result is not null
                    if (task.isSuccessful && task.result != null) {
                        mViewModel.saveUserData(task.result!!)
                        takeUserToDashboard()
                    } else {
                        Log.logMessage(
                            logTag,
                            "observeLoginResult()",
                            "Firebase login failed ${task.exception.toString()}"
                        )
                        displaySnackBar(getString(R.string.login_massage_failed))
                    }
                }
            } else {
                Log.logMessage(logTag, "observeLoginResult()", "Credentials are empty")
                displaySnackBar(getString(R.string.login_massage_failed))
            }
        })
    }

    private fun takeUserToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }

    override fun Activity.displaySnackBar(message: String) {
        Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}
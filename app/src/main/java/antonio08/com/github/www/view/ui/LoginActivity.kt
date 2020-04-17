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
import antonio08.com.github.www.viewmodel.LoginViewModel
import com.google.android.gms.common.SignInButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ILoginContract {

    lateinit var mLoginViewModel: LoginViewModel
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

        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        mSignInButton.setOnClickListener(mClickListener)

        observeLoginResult()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mLoginViewModel.result(requestCode, resultCode, data)
    }

    private fun initialize() {
        mSignInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    private fun proceedWithGoogleLogin() {
        startActivityForResult(mLoginViewModel.mGoogleSignInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun observeLoginResult() {
        mLoginViewModel.getUser().observe(this, Observer { credentials ->
            // If we have the credentials then
            if(credentials != null) {
                // Sign in the user into Firebase
                mAuth.signInWithCredential(credentials).addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        takeUserToDashboard()
                    }
                    else{
                        displaySnackBar(getString(R.string.login_massage_failed))
                    }
                }
            }
            else{
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
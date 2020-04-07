/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import antonio08.com.github.www.R
import antonio08.com.github.www.contract.ILoginContract
import antonio08.com.github.www.model.User
import antonio08.com.github.www.service.network.Authentication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    //var mApplication: Application = application

    // Build a GoogleSignInClient with the options specified by gso.
    private val mGoogleSignInClient: GoogleSignInClient =
        GoogleSignIn.getClient(application.applicationContext, Authentication.mGoogleSignInOptions)

    val mGoogleSignInIntent: Intent = mGoogleSignInClient.signInIntent

    private val mUserMutableLiveData = MutableLiveData<Boolean>()

    /**
     * Handles the result from Login Activity
     */
    fun result(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ILoginContract.RequestCodeConstants.RC_GOOGLE_SIGN_IN -> handleSignInResult(
                GoogleSignIn.getSignedInAccountFromIntent(
                    data
                )
            )
        }
    }

    fun getUser(): MutableLiveData<Boolean> {
        return mUserMutableLiveData;
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {

            val googleSignInAccount = task?.getResult(ApiException::class.java)
            val userEmail: String? = googleSignInAccount?.email
            if (userEmail.isNullOrEmpty()) {
                updateLoginResult(false)
                return
            }

            val user = User(userEmail)
            updateLoginResult(true)
        } catch (exception: ApiException) {
            updateLoginResult(false)
            (R.string.login_massage_failed)

        }
    }

    private fun updateLoginResult(isLoginSuccessful : Boolean)
    {
        mUserMutableLiveData.value = isLoginSuccessful
    }
}
/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.viewmodel

import android.app.Application
import android.content.Intent
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import antonio08.com.github.www.R
import antonio08.com.github.www.contract.ILoginContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val mApplication: Application = application

    private val idToken = mApplication.baseContext.getString(R.string.client_id)
    private val googleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(idToken)
            .requestEmail().build()

    // Build a GoogleSignInClient with the options specified by gso.
    private val mGoogleSignInClient: GoogleSignInClient =
        GoogleSignIn.getClient(application.applicationContext, googleSignInOptions)

    private val mUserMutableLiveData = MutableLiveData<AuthCredential>()


    val mGoogleSignInIntent: Intent = mGoogleSignInClient.signInIntent


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

    /**
     * Gets the current user login state
     */
    fun getUser(): MutableLiveData<AuthCredential> {
        return mUserMutableLiveData;
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {

            val googleSignInAccount = task?.getResult(ApiException::class.java)

            proceedWithFirebaseLogin(googleSignInAccount)

        } catch (exception: ApiException) {
            updateLoginResult(null)
        }
    }

    private fun updateLoginResult(@Nullable credentials: AuthCredential?) {
        mUserMutableLiveData.value = credentials
    }

    private fun proceedWithFirebaseLogin(googleSignInAccount: GoogleSignInAccount?) {
        val credentials = GoogleAuthProvider.getCredential(googleSignInAccount?.idToken, null)
        updateLoginResult(credentials)
    }
}
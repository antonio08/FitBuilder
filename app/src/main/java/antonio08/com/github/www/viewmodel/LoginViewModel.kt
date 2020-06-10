/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.viewmodel

import android.content.Intent
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import antonio08.com.github.www.contract.ILoginContract
import antonio08.com.github.www.log.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class LoginViewModel() : ViewModel() {
    private val logTag = LoginViewModel::class.java.canonicalName
    private val mUserMutableLiveData = MutableLiveData<AuthCredential>()

    @Inject
    lateinit var mUser: FirebaseUser

    /**
     * Handles the result from Login Activity
     */
    fun result(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ILoginContract.RequestCodeConstants.RC_GOOGLE_SIGN_IN -> {
                if (resultCode == ILoginContract.ResultCodeConstants.RESULT_OK) {
                    // Proceed with Login result
                    handleSignInResult(
                        GoogleSignIn.getSignedInAccountFromIntent(
                            data
                        )
                    )
                } else {
                    Log.logMessage(logTag, "result()", "Google sign in failed")
                    // Notify UI that login process failed
                    updateLoginResult(null)
                }
            }
        }
    }

    /**
     * Gets the current user login state
     */
    fun getUser(): MutableLiveData<AuthCredential> {
        return mUserMutableLiveData;
    }

    fun saveUserData(authResult: AuthResult) {
        Log.logMessage(logTag, "saveUserData()", "Initializing User")
        mUser = authResult.user!!
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            val googleSignInAccount = task?.getResult(ApiException::class.java)
            Log.logMessage(logTag, "handleSignInResult()", "Google sign in completed")
            proceedWithFirebaseLogin(googleSignInAccount)
        } catch (exception: ApiException) {
            Log.reportNonFatalError(exception)

            // Notify UI that login process failed
            updateLoginResult(null)
        }
    }

    private fun updateLoginResult(@Nullable credentials: AuthCredential?) {
        mUserMutableLiveData.value = credentials
    }

    private fun proceedWithFirebaseLogin(googleSignInAccount: GoogleSignInAccount?) {
        Log.logMessage(logTag, "proceedWithFirebaseLogin()", "Login user into Firebase")
        val credentials = GoogleAuthProvider.getCredential(googleSignInAccount?.idToken, null)

        // Notify UI that Firebase login process has been completed
        updateLoginResult(credentials)
    }
}
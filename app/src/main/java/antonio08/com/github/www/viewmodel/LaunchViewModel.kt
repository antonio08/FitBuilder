/**
 * Created by Antonio Lozano on 2020-03-25.
 */

package antonio08.com.github.www.viewmodel

import androidx.lifecycle.ViewModel
import antonio08.com.github.www.log.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LaunchViewModel : ViewModel() {
    private val logTag = LaunchViewModel::class.java.canonicalName
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val lastFirebaseUserSignedIn: FirebaseUser? = mAuth.currentUser

    @Inject
    lateinit var mUser: FirebaseUser

    fun saveUserData(firebaseUser: FirebaseUser) {
        Log.logMessage(logTag, "saveUserData()", "Initializing User")
        mUser = firebaseUser
    }
}

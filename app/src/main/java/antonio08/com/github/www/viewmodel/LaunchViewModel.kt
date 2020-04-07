/**
 * Created by Antonio Lozano on 2020-03-25.
 */

package antonio08.com.github.www.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LaunchViewModel(application: Application) : AndroidViewModel(application) {

    var mApplication: Application = application

    /**
     * Gets the latest google signed in account
     * @return the latest google signed in account
     */
    fun lastGoogleSignedInAccount(): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(mApplication.applicationContext)
    }
}
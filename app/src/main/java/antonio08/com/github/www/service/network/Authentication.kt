/**
 * Created by Antonio Lozano on 2020-03-22.
 */

package antonio08.com.github.www.service.network

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object Authentication {
    val mGoogleSignInOptions: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

}

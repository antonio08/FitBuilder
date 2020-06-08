/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.model

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class User @Inject constructor(user : FirebaseUser) {

    val displayName = user.displayName
    val email = user.email
    val phoneNumber = user.phoneNumber
    val profilePictureUrl = user.photoUrl
}
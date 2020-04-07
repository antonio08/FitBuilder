/**
 * Created by Antonio Lozano on 2020-03-16.
 */

package antonio08.com.github.www.model

import android.provider.ContactsContract
import androidx.annotation.NonNull

class User(@NonNull email: String) {

    var mUserEmail = email
        get() = field

    var mIsUserLoggedIn: Boolean = false


}
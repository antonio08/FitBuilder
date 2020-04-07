/**
 * Created by Antonio Lozano on 2020-03-30.
 */

package antonio08.com.github.www.contract

import android.app.Activity

interface ILoginContract {

    /**
     * Contains the request codes for each action executed at Login
     */
    object RequestCodeConstants {
        const val RC_GOOGLE_SIGN_IN = 0
    }

    fun Activity.displaySnackBar(message : String)

}
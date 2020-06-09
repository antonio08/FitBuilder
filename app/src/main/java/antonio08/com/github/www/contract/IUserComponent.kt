/**
 * Created by Antonio Lozano on 2020-04-27.
 */

package antonio08.com.github.www.contract

import antonio08.com.github.www.data.User
import antonio08.com.github.www.module.UserModule
import dagger.Component

@Component(modules = [UserModule::class])
interface IUserComponent {
    fun getUser() : User
}
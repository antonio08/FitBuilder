/**
 * Created by Antonio Lozano on 2020-04-28.
 */


package antonio08.com.github.www.module

import antonio08.com.github.www.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    fun provideFirebaseUser() = FirebaseAuth.getInstance().currentUser!!

    @Provides
    fun provideUser(user : FirebaseUser) = User(user)
}
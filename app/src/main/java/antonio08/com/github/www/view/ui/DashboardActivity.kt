/**
 * Created by Antonio Lozano on 2020-03-23.
 */

package antonio08.com.github.www.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import antonio08.com.github.www.R
import antonio08.com.github.www.contract.DaggerIUserComponent
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val userComponent = DaggerIUserComponent.create()
        val user = userComponent.getUser()

        helloWorld.text = user.displayName

        helloWorld.setOnClickListener { _ ->
            throw RuntimeException("Test Crash") // Force a crash
        }
    }
}

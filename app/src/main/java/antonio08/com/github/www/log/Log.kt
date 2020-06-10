/*
 * Created by Antonio Lozano on 6/9/2020.
 */
package antonio08.com.github.www.log

import androidx.annotation.NonNull
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.lang.Exception

object Log {
    private val crashlyticsInstance = FirebaseCrashlytics.getInstance()

    /**
     * Record custom Crashlytics log and send it to Crashlytics console
     */
    fun logMessage(@NonNull className: String, @NonNull method: String, @NonNull message: String) {
        crashlyticsInstance.log(formatLogMessage(className, method, message))
    }

    /**
     * Record non-fatal exceptions and send it to Crashlytics console
     */
    fun reportNonFatalError(@NonNull exception: Exception) {
        crashlyticsInstance.recordException(exception)
    }

    private fun formatLogMessage(
        @NonNull className: String,
        @NonNull method: String,
        @NonNull message: String
    ): String {
        return "class: $className\n [method: $method]; $message"
    }
}
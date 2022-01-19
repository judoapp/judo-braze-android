package app.judo.braze.sample

import android.app.Application
import app.judo.braze.brazeBeforeInAppMessageDisplayed
import app.judo.braze.integrateWithBraze
import app.judo.sdk.api.Judo
import com.braze.BrazeActivityLifecycleCallbackListener
import com.braze.models.inappmessage.IInAppMessage
import com.braze.ui.inappmessage.BrazeInAppMessageManager
import com.braze.ui.inappmessage.InAppMessageOperation
import com.braze.ui.inappmessage.listeners.DefaultInAppMessageManagerListener

class JudoBrazeSampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Judo.initialize(
            application = this,
            accessToken = "<JUDO ACCESS TOKEN>",
            "dogwalking.judo.app"
        )

        Judo.integrateWithBraze(this)

        BrazeInAppMessageManager.getInstance().setCustomInAppMessageManagerListener(
            object : DefaultInAppMessageManagerListener() {
                override fun beforeInAppMessageDisplayed(inAppMessage: IInAppMessage?): InAppMessageOperation {
                    return Judo.brazeBeforeInAppMessageDisplayed(this@JudoBrazeSampleApplication, inAppMessage) ?: super.beforeInAppMessageDisplayed(inAppMessage)
                }
            }
        )

        registerActivityLifecycleCallbacks(BrazeActivityLifecycleCallbackListener(true, true))
    }
}
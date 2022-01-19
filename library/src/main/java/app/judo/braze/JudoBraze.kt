package app.judo.braze

import android.app.Application
import android.content.Intent
import app.judo.sdk.api.Judo
import com.braze.Braze
import com.braze.models.inappmessage.IInAppMessage
import com.braze.models.outgoing.BrazeProperties
import com.braze.ui.inappmessage.InAppMessageOperation

/**
 * Call this method to automatically track Judo events into Braze.
 * Note to enable IAM integration there is further work to do, please see the documentation
 * included with this module.
 */
fun Judo.integrateWithBraze(application: Application) {
    addScreenViewedCallback { event ->
        Braze.getInstance(application).logCustomEvent(
            "Judo Screen Viewed",
            BrazeProperties()
                .addProperty("screenID", event.screen.id)
                .addProperty("screenName", event.screen.name)
                .addProperty("experienceID", event.experience.id)
                .addProperty("experienceName", event.experience.name)
        )
    }
}

/**
 * This method is a helper for launching Judo Experiences when a `judo-experience` extra is added to
 * a Braze In-App message, which you can use from a
 * `IInAppMessageManagerListener`/`DefaultInAppMessageManagerListener` implementation. See the
 * documentation included with this module for more details.
 */
fun Judo.brazeBeforeInAppMessageDisplayed(application: Application, inAppMessage: IInAppMessage?): InAppMessageOperation? {
    val experienceUrl = inAppMessage?.extras?.get("judo-experience")
    return if(experienceUrl != null) {
        val intent = makeIntent(application, experienceUrl)
        intent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
        inAppMessage.logImpression()
        application.startActivity(intent)
        InAppMessageOperation.DISCARD
    } else {
        null
    }
}

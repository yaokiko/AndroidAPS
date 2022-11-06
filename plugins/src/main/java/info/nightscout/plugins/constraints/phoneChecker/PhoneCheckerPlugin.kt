package info.nightscout.plugins.constraints.phoneChecker

import android.content.Context
import android.os.Build
import com.scottyab.rootbeer.RootBeer
import dagger.android.HasAndroidInjector
import info.nightscout.androidaps.interfaces.Constraints
import info.nightscout.androidaps.interfaces.PluginBase
import info.nightscout.interfaces.PluginDescription
import info.nightscout.interfaces.PluginType
import info.nightscout.androidaps.interfaces.ResourceHelper
import info.nightscout.plugins.R
import info.nightscout.rx.logging.AAPSLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhoneCheckerPlugin @Inject constructor(
    injector: HasAndroidInjector,
    aapsLogger: AAPSLogger,
    rh: ResourceHelper,
    private val context: Context
) : PluginBase(
    PluginDescription()
    .mainType(PluginType.CONSTRAINTS)
    .neverVisible(true)
    .alwaysEnabled(true)
    .showInList(false)
    .pluginName(R.string.phone_checker),
    aapsLogger, rh, injector
), Constraints {

    var phoneRooted: Boolean = false
    var devMode: Boolean = false
    val phoneModel: String = Build.MODEL
    val manufacturer: String = Build.MANUFACTURER

    private fun isDevModeEnabled(): Boolean {
        return android.provider.Settings.Secure.getInt(context.contentResolver,
            android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
    }

    override fun onStart() {
        super.onStart()
        phoneRooted = RootBeer(context).isRooted
        devMode = isDevModeEnabled()
    }
}
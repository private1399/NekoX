package tw.nekomimi.nekogram

import com.v2ray.ang.util.Utils
import org.telegram.messenger.MessagesController
import org.telegram.tgnet.ConnectionsManager

object DataCenter {

    @JvmStatic
    fun applyOfficalDataCanter(account: Int) {

        MessagesController.getMainSettings(account).edit().putInt("network", 0).apply()
        MessagesController.getMainSettings(account).edit().putBoolean("custom_dc", false).apply()

        if (ConnectionsManager.native_isTestBackend(account) != 0) {

            ConnectionsManager.getInstance(account).switchBackend()

        }

        applyDataCanter(account, 1, "149.154.175.50")
        applyDataCanter(account, 1, "2001:b28:f23d:f001:0000:0000:0000:000a")

        applyDataCanter(account, 2, "149.154.167.51")
        applyDataCanter(account, 2, "2001:67c:4e8:f002:0000:0000:0000:000a")

        applyDataCanter(account, 3, "149.154.175.100")
        applyDataCanter(account, 3, "2001:b28:f23d:f003:0000:0000:0000:000a")

        applyDataCanter(account, 4, "149.154.167.91")
        applyDataCanter(account, 4, "2001:67c:4e8:f004:0000:0000:0000:000a")

        applyDataCanter(account, 5, "149.154.171.5")
        applyDataCanter(account, 5, "2001:67c:4e8:f005:0000:0000:0000:000a")

    }

    @JvmStatic
    fun applyTestDataCenter(account: Int) {

        MessagesController.getMainSettings(account).edit().putInt("network", 0).apply()
        MessagesController.getMainSettings(account).edit().putBoolean("custom_dc", false).apply()

        if (ConnectionsManager.native_isTestBackend(account) == 0) {

            ConnectionsManager.getInstance(account).switchBackend()

        }

    }

    @JvmStatic
    fun applyCustomDataCenter(account: Int, ipv4Address: String = "", ipv6Address: String = "", port: Int) {

        MessagesController.getMainSettings(account).edit().putBoolean("custom_dc", true).apply()

        if (ConnectionsManager.native_isTestBackend(account) != 0) {

            ConnectionsManager.getInstance(account).switchBackend()

        }

        var networkType = 0

        if (ipv4Address.isNotBlank()) {

            for (dc in 1..5) applyDataCanter(account, dc, ipv4Address, port)

            if (ipv6Address.isBlank()) {

                networkType = 4

            }

        }

        if (ipv6Address.isNotBlank()) {

            for (dc in 1..5) applyDataCanter(account, dc, ipv6Address, port)

            if (ipv4Address.isBlank()) {

                networkType = 6

            }

        }

        MessagesController.getMainSettings(account).edit().putInt("network", networkType).apply()

    }

    private fun applyDataCanter(account: Int, dataCenter: Int, address: String, port: Int = 443) {

        ConnectionsManager.getInstance(account).applyDatacenterAddress(dataCenter, address, port, if (Utils.isIpv6Address(address)) 1 else 0)

    }

}
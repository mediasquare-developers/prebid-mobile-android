package org.prebid.mobile.rendering.models.openrtb.msqRequests

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid
import org.prebid.mobile.rendering.models.openrtb.msqRequests.codes.Banner
import org.prebid.mobile.rendering.models.openrtb.msqRequests.codes.Video

class
MediaTypes: BaseBid() {

    // MARK: - Properties

    var banner: Banner? = null

    // MARK: - Methods

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        banner?.let {
            toJSON(
                jsonObject,
                "banner",
                it.getJsonObject()
            )
        }
        return jsonObject
    }
}
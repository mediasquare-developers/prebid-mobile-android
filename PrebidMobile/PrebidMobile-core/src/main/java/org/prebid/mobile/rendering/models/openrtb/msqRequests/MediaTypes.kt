package org.prebid.mobile.rendering.models.openrtb.msqRequests

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid
import org.prebid.mobile.rendering.models.openrtb.msqRequests.codes.Banner
import org.prebid.mobile.rendering.models.openrtb.msqRequests.codes.Video

class
MediaTypes: BaseBid() {

    // MARK: - Properties

    var banner: Banner? = null
    var video: Video? = null
    var native: Native? = null

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

        video?.let {
            toJSON(
                jsonObject,
                "video",
                it.getJsonObject()
            )
        }

        native?.let {
            toJSON(
                jsonObject,
                "native",
                it.getJsonObject()
            )
        }

        return jsonObject
    }
}
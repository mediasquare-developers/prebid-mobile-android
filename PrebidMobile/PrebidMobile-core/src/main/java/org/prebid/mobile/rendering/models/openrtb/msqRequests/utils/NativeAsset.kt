package org.prebid.mobile.rendering.models.openrtb.msqRequests.utils

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid

class NativeAsset : BaseBid() {

    //MARK: - Properties

    var required: Boolean = false
    var len: Int? = null
    var aspectRatios: AssetRatio? = null

    //MARK: - Methods

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        toJSON(jsonObject, "required", required)
        if (len != null) toJSON(jsonObject, "len", len)
        if (aspectRatios != null) toJSON(
            jsonObject,
            "aspect_ratios",
            aspectRatios!!.getJsonObject()
        )

        return jsonObject
    }
}
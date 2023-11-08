package org.prebid.mobile.rendering.models.openrtb.msqRequests.utils

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid

class AssetRatio : BaseBid() {

    //MARK: - Properties

    var minWidth: Int = 0
    var minHeight: Int = 0
    var ratioWidth: Int = 0
    var ratioHeight: Int = 0

    //MARK: - Methods

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        toJSON(jsonObject, "min_width", minWidth)
        toJSON(jsonObject, "min_height", minHeight)
        toJSON(jsonObject, "ratio_width", ratioWidth)
        toJSON(jsonObject, "ratio_height", ratioHeight)

        return jsonObject
    }
}
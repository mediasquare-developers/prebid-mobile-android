package org.prebid.mobile.rendering.models.openrtb.msqRequests

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid

class Floor : BaseBid() {

    // MARK: - Properties


    // MARK: - Methods

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        return jsonObject
    }
}
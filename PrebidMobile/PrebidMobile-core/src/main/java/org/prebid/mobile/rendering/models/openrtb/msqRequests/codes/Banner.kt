package org.prebid.mobile.rendering.models.openrtb.msqRequests.codes

import org.json.JSONArray
import org.json.JSONObject
import org.prebid.mobile.AdSize
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid
import org.prebid.mobile.rendering.models.openrtb.msqRequests.getJsonArray

class Banner : BaseBid() {

    //MARK: - Properties

    var sizes: ArrayList<AdSize> = arrayListOf()
    var position: Int = 1

    //MARK: - Methods

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        if (sizes.size > 0) {
            val jsonArray = JSONArray()

            for (size in sizes) {
                jsonArray.put(size.getJsonArray())
            }

            toJSON(
                jsonObject,
                "sizes",
                jsonArray
            )
        }

        toJSON(
            jsonObject,
            "pos",
            position
        )

        return jsonObject
    }
}
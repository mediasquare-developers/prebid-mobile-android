package org.prebid.mobile.rendering.models.openrtb.msqRequests.codes

import org.json.JSONArray
import org.json.JSONObject
import org.prebid.mobile.AdSize
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid
import org.prebid.mobile.rendering.models.openrtb.msqRequests.getJsonArray

class Video : BaseBid() {

    var context: String = "outstream"
    var playerSizes: ArrayList<AdSize> = arrayListOf()
    var mimes: ArrayList<String> = arrayListOf()

    //var position:Int = 1 --> pas nécessaire pour le web à voir pour le mobile

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        toJSON(jsonObject, "context", context)

        if (playerSizes.size > 0) {
            val jsonArray = JSONArray()

            for (size in playerSizes) {
                jsonArray.put(size.getJsonArray())
            }

            toJSON(jsonObject, "playerSize", jsonArray)
        }

        if (mimes.size > 0) {
            val jsonArray = JSONArray()

            for (mime in mimes) {
                jsonArray.put(mime)
            }

            toJSON(jsonObject, "mimes", jsonArray)
        }

        return jsonObject
    }
}
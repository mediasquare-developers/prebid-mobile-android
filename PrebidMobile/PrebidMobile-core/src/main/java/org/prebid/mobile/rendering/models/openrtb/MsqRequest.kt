package org.prebid.mobile.rendering.models.openrtb

import org.json.JSONArray
import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid
import org.prebid.mobile.rendering.models.openrtb.msqRequests.Code
import org.prebid.mobile.rendering.models.openrtb.msqRequests.Gdpr

class MsqRequest : BaseBid() {

    // MARK: - Properties

    var codes: ArrayList<Code> = arrayListOf()

    var referer: String? = null

    var pbjs: String? = null

    var gdpr: Gdpr = Gdpr()

    var debug: Boolean = false

    // MARK: - Methods

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        if (codes.size > 0) {
            val jsonArray = JSONArray()

            for (code in codes) {
                jsonArray.put(code.getJsonObject())
            }

            toJSON(jsonObject, "codes", jsonArray)
        }

        toJSON(
            jsonObject,
            "referer",
            if (!referer.isNullOrEmpty()) referer!! else null
        )

        toJSON(
            jsonObject,
            "pbjs",
            if (!pbjs.isNullOrEmpty()) pbjs!! else null
        )

        toJSON(
            jsonObject,
            "gdpr",
            if (gdpr != null) gdpr!!.getJsonObject() else null
        )

        toJSON(
            jsonObject,
            "debug",
            debug
        )

        return jsonObject
    }
}
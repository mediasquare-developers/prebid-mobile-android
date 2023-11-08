package org.prebid.mobile.rendering.models.openrtb.msqRequests

import org.json.JSONArray
import org.prebid.mobile.AdSize

fun AdSize.getJsonArray() : JSONArray {
    val jsonArray = JSONArray()

    jsonArray.put(width)
    jsonArray.put(height)

    return jsonArray
}
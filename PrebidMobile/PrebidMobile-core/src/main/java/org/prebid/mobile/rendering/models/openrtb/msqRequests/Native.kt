package org.prebid.mobile.rendering.models.openrtb.msqRequests

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid
import org.prebid.mobile.rendering.models.openrtb.msqRequests.utils.AssetRatio
import org.prebid.mobile.rendering.models.openrtb.msqRequests.utils.NativeAsset

class Native: BaseBid() {

    //MARK: - Properties

    var title: NativeAsset? = null
    var body:NativeAsset? = null
    var brand:NativeAsset? = null
    var icon:NativeAsset? = null
    var image:NativeAsset? = null
    var clickUrl:NativeAsset? = null

    //MARK: - Methods

    //TODO: - Delete this default test configuration when tested as functional
    init {
        title = NativeAsset()
        title?.let {
            it.required = true
            it.len = 80
        }

        body = NativeAsset()
        body!!.required = true

        brand = NativeAsset()

        icon = NativeAsset()
        icon!!.aspectRatios = AssetRatio()
        icon!!.aspectRatios?.let {
            it.minWidth = 50
            it.minHeight = 50
            it.ratioWidth = 2
            it.ratioHeight = 3
        }

        image = NativeAsset()
        image!!.aspectRatios = AssetRatio()
        image!!.aspectRatios?.let {
            it.minWidth = 300
            it.minHeight = 200
            it.ratioWidth = 2
            it.ratioHeight = 3
        }

        clickUrl = NativeAsset()
        clickUrl!!.required = true
    }

    fun getJsonObject(): JSONObject {
        val  jsonObject = JSONObject()

        if(title != null) toJSON(jsonObject, "title", title!!.getJsonObject())
        if(body != null) toJSON(jsonObject, "body", body!!.getJsonObject())
        if(brand != null) toJSON(jsonObject, "brand", brand!!.getJsonObject())
        if(icon != null) toJSON(jsonObject, "icon", icon!!.getJsonObject())
        if(clickUrl != null) toJSON(jsonObject, "clickUrl", clickUrl!!.getJsonObject())


        return jsonObject
    }

}
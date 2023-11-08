package org.prebid.mobile.rendering.models.openrtb.msqRequests

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid
import java.util.*

class Code : BaseBid() {

    // MARK: - Properties

    var owner: String? = null

    var code: String? = null

    var adUnit:String? = null

    var bidId:String? = null

    var auctionId:String? = null

    var transactionId:String? = null

    var mediaTypes:MediaTypes = MediaTypes()

    var floor:Floor = Floor()

    var isInterstitial:Boolean = false

    // MARK: - Methods

    fun getJsonObject(): JSONObject {
        val jsonObject = JSONObject()

        toJSON(
            jsonObject,
            "owner",
            if(!owner.isNullOrEmpty()) owner!! else null
        )

        toJSON(
            jsonObject,
            "code",
            if(!code.isNullOrEmpty()) code!! else null
        )

        toJSON(
            jsonObject,
            "adUnit",
            if(!adUnit.isNullOrEmpty()) adUnit!! else null
        )

        toJSON(
            jsonObject,
            "bidId",
            if(!bidId.isNullOrEmpty()) bidId!! else generateUUID()
        )

        toJSON(
            jsonObject,
            "auctionId",
            if(!auctionId.isNullOrEmpty()) auctionId!! else generateUUID()
        )

        toJSON(
            jsonObject,
            "transactionId",
            if(!transactionId.isNullOrEmpty()) transactionId!! else null
        )

        toJSON(
            jsonObject,
            "mediaTypes",
            mediaTypes.getJsonObject()
        )

        toJSON(
            jsonObject,
            "floor",
            floor.getJsonObject()
        )

        toJSON(
            jsonObject,
            "instl",
            if (isInterstitial) 1 else 0
        )

        return jsonObject
    }

    // MARK: - Utils

    private fun generateUUID() = UUID.randomUUID().toString()
}
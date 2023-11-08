package org.prebid.mobile.rendering.models.openrtb.msqRequests

import org.json.JSONObject
import org.prebid.mobile.rendering.models.openrtb.bidRequests.BaseBid

class Gdpr : BaseBid() {

    /*
    Test value :
    "CPagOwAPagOwAAKApAENCTCsAP_AAH_AAAqII0Nd_X__bX9j-_5_bft0eY1P9_r37uQzjhfFs-8F3L_W_LwXw2E7NF36pq4
    KuR4Eu3LBIQNlHMHUTUmwaokVrzHsak2cpyNKJ7LEknMZO2dYGH9Pn9lDuYKY7_5___bx3D-t_t_-39T378Xf3_d5_2_--vC
    fV599jbn9fV_7_9nP___9v-_8__________wQpAJMNS4AC7MscGSSMIoUQIQrCQqAUAFFAMLRFYAMDgp2VgEOoIGACAVIRgR
    AgxBRgwCAAASAJCIgJACwQCIAiAQAAgAQAIQAETAILACwMAgAFANCxACgAECQgyICI5TAgKgSiglsrAEoK9jTCAMo8AKBRGR
    UACJJIQSAgJCwcxwAAAAgAAEAAAAA.f_gAAAAAAAAA"
     */

    // MARK: - Properties

    var consentString: String? = "CPagOwAPagOwAAKApAENCTCsAP_AAH_AAAqII0Nd_X__bX9j-_5_bft0eY1P9_r37uQzjhfFs-8F3L_W_LwXw2E7NF36pq4" +
            "KuR4Eu3LBIQNlHMHUTUmwaokVrzHsak2cpyNKJ7LEknMZO2dYGH9Pn9lDuYKY7_5___bx3D-t_t_-39T378Xf3_d5_2_--vC" +
            "fV599jbn9fV_7_9nP___9v-_8__________wQpAJMNS4AC7MscGSSMIoUQIQrCQqAUAFFAMLRFYAMDgp2VgEOoIGACAVIRgR" +
            "AgxBRgwCAAASAJCIgJACwQCIAiAQAAgAQAIQAETAILACwMAgAFANCxACgAECQgyICI5TAgKgSiglsrAEoK9jTCAMo8AKBRGR" +
            "UACJJIQSAgJCwcxwAAAAgAAEAAAAA.f_gAAAAAAAAA"
    var consentRequired: Boolean = true

    // MARK: - Methods

    fun getJsonObject(): JSONObject {
        var jsonObject = JSONObject()

        toJSON(
            jsonObject,
            "consent_string",
            if (!consentString.isNullOrEmpty()) consentString else null
        )

        toJSON(
            jsonObject,
            "consent_required",
            consentRequired
        )

        return jsonObject
    }
}
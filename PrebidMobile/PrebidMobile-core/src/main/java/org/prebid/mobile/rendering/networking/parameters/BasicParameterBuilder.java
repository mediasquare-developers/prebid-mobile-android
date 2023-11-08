/*
 *    Copyright 2018-2021 Prebid.org, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.prebid.mobile.rendering.networking.parameters;

import static org.prebid.mobile.PrebidMobile.SDK_VERSION;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;
import org.prebid.mobile.AdSize;
import org.prebid.mobile.BannerParameters;
import org.prebid.mobile.DataObject;
import org.prebid.mobile.ExternalUserId;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.Signals;
import org.prebid.mobile.TargetingParams;
import org.prebid.mobile.VideoParameters;
import org.prebid.mobile.api.data.AdFormat;
import org.prebid.mobile.configuration.AdUnitConfiguration;
import org.prebid.mobile.rendering.bidding.data.bid.Prebid;
import org.prebid.mobile.rendering.models.PlacementType;
import org.prebid.mobile.rendering.models.openrtb.BidRequest;
import org.prebid.mobile.rendering.models.openrtb.bidRequests.Imp;
import org.prebid.mobile.rendering.models.openrtb.bidRequests.User;
import org.prebid.mobile.rendering.models.openrtb.bidRequests.devices.Geo;
import org.prebid.mobile.rendering.models.openrtb.bidRequests.imps.Video;
import org.prebid.mobile.rendering.models.openrtb.bidRequests.source.Source;

import org.prebid.mobile.rendering.models.openrtb.MsqRequest;
import org.prebid.mobile.rendering.models.openrtb.msqRequests.Code;
import org.prebid.mobile.rendering.models.openrtb.msqRequests.Native;
import org.prebid.mobile.rendering.models.openrtb.msqRequests.codes.Banner;

import org.prebid.mobile.rendering.session.manager.OmAdSessionManager;
import org.prebid.mobile.rendering.utils.helpers.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BasicParameterBuilder extends ParameterBuilder {

    public static final String[] SUPPORTED_VIDEO_MIME_TYPES = new String[]{
            "video/mp4",
            "video/3gpp",
            "video/webm",
            "video/mkv"};

    static final String DISPLAY_MANAGER_VALUE = "prebid-mobile";
    static final String KEY_OM_PARTNER_NAME = "omidpn";
    static final String KEY_OM_PARTNER_VERSION = "omidpv";
    static final String KEY_DEEPLINK_PLUS = "dlp";

    // 2 - VAST 2.0
    // 5 - VAST 2.0 Wrapper
    static final int[] SUPPORTED_VIDEO_PROTOCOLS = new int[]{2, 5};

    // 2 - On Leaving Viewport or when Terminated by User
    static final int VIDEO_INTERSTITIAL_PLAYBACK_END = 2;
    //term to say cached locally as per Mopub & dfp - approved by product
    static final int VIDEO_DELIVERY_DOWNLOAD = 3;
    static final int VIDEO_LINEARITY_LINEAR = 1;
    static final int API_OPEN_MEASUREMENT = 7;

    /**
     * 1 - VPAID 1.0
     * 2 - VPAID 2.0
     * 3 - MRAID-1
     * 4 - ORMMA
     * 5 - MRAID-2
     * 6 - MRAID-3
     */
    private static final List<Integer> SUPPORTED_MRAID_VERSIONS = Arrays.asList(3, 5, 6);

    private final AdUnitConfiguration adConfiguration;
    private final boolean browserActivityAvailable;
    private final Resources resources;

    public BasicParameterBuilder(
            AdUnitConfiguration adConfiguration,
            Resources resources,
            boolean browserActivityAvailable
    ) {
        this.adConfiguration = adConfiguration;
        this.browserActivityAvailable = browserActivityAvailable;
        this.resources = resources;
    }

    @Override
    public void appendBuilderParameters(AdRequestInput adRequestInput) {
        final String uuid = UUID.randomUUID().toString();

        configureMsqRequest(adRequestInput.getMsqRequest(), uuid);

        ArrayList<Code> codesArrayList = adRequestInput.getMsqRequest().getCodes();

        Code newCode = new Code();
        configureCodeObject(newCode, uuid);

        codesArrayList.add(newCode);
    }

    private void configureCodeObject(Code code, String uuid) {
        code.setTransactionId(uuid);

        if (adConfiguration != null) {
            code.setCode(adConfiguration.getConfigId());

            setCommonCodeValues(code, uuid);

            if (adConfiguration.isAdType(AdFormat.BANNER)
                    || adConfiguration.isAdType(AdFormat.INTERSTITIAL)) {
                setBannerCodeValues(code);
            } else if (adConfiguration.isAdType(AdFormat.VAST)) {
                setVideoCodeValues(code);
            }

        }
    }

    private void configureMsqRequest(MsqRequest msqRequest, String uuid) {
        // TODO: - Extract values to a string value file ?
        msqRequest.setReferer("https%3A%2F%2Fdebug.mediasquare.fr%2Fdebug%2Fprebid%2Fmsq_desktop.html%3Fpbjs_debug%3Dtrue");
        msqRequest.setPbjs("7.17.0");
    }

    private void setBannerCodeValues(Code code) {
        Banner banner = new Banner();

        if (adConfiguration.isAdType(AdFormat.BANNER)) {
            for (AdSize size : adConfiguration.getSizes()) {
                banner.getSizes().add(size);
            }
        } else if (adConfiguration.isAdType(AdFormat.INTERSTITIAL)) {
            // TODO: - Unique size for testing phase
            banner.getSizes().add(new AdSize(320, 480));
        }

        if (adConfiguration.isAdPositionValid()) {
            banner.setPosition(adConfiguration.getAdPositionValue());
        }

        code.getMediaTypes().setBanner(banner);
    }

    private void setVideoCodeValues(Code code) {
        org.prebid.mobile.rendering.models.openrtb.msqRequests.codes.Video video = new org.prebid.mobile.rendering.models.openrtb.msqRequests.codes.Video();

        //Common values for all video reqs
        video.setMimes(new ArrayList<>(Arrays.asList(SUPPORTED_VIDEO_MIME_TYPES)));

        //Add a default player size
        if (adConfiguration.getSizes().isEmpty()) {
            adConfiguration.addSize(new AdSize(640, 480));
        }
        for (AdSize size : adConfiguration.getSizes()) {
            video.getPlayerSizes().add(size);
        }

        code.getMediaTypes().setVideo(video);
    }


        boolean isNotOriginalApi = !adConfiguration.isOriginalAdUnit();

    private void setCommonCodeValues(Code code, String uuid) {
        // TODO: - Extract values to a string value file ? Are those fixed or able to change ?
        code.setOwner("test");
        code.setCode("publishername_atf_desktop_rg_pave");

        code.setInterstitial(adConfiguration.isAdType(AdFormat.VAST)
                || adConfiguration.isAdType(AdFormat.INTERSTITIAL));
    }


    private void configureSource(Source source, String uuid) {
        String userDefinedPartnerName = TargetingParams.getOmidPartnerName();
        String userDefinedPartnerVersion = TargetingParams.getOmidPartnerVersion();
        String usedPartnerName = OmAdSessionManager.PARTNER_NAME;
        String usedPartnerVersion = OmAdSessionManager.PARTNER_VERSION;

        if (userDefinedPartnerName != null && !userDefinedPartnerName.isEmpty()) {
            usedPartnerName = userDefinedPartnerName;
        }
        if (userDefinedPartnerVersion != null && !userDefinedPartnerVersion.isEmpty()) {
            usedPartnerVersion = userDefinedPartnerVersion;
        }

        source.setTid(uuid);
        source.getExt().put(KEY_OM_PARTNER_NAME, usedPartnerName);
        source.getExt().put(KEY_OM_PARTNER_VERSION, usedPartnerVersion);
    }

    private void appendUserTargetingParameters(AdRequestInput adRequestInput) {
        final BidRequest bidRequest = adRequestInput.getBidRequest();
        final User user = bidRequest.getUser();

        user.id = TargetingParams.getUserId();
        user.keywords = TargetingParams.getUserKeywords();
        user.customData = TargetingParams.getUserCustomData();
        user.buyerUid = TargetingParams.getBuyerId();
        user.ext = TargetingParams.getUserExt();

        ArrayList<DataObject> userData = adConfiguration.getUserData();
        if (!userData.isEmpty()) {
            user.dataObjects = userData;
        }

        int yearOfBirth = TargetingParams.getYearOfBirth();
        if (yearOfBirth != 0) {
            user.yob = TargetingParams.getYearOfBirth();
        }

        TargetingParams.GENDER gender = TargetingParams.getGender();
        if (gender != TargetingParams.GENDER.UNKNOWN) {
            user.gender = gender.getKey();
        }

        final Map<String, Set<String>> userDataDictionary = TargetingParams.getUserDataDictionary();
        if (!userDataDictionary.isEmpty()) {
            user.getExt().put("data", Utils.toJson(userDataDictionary));
        }

        List<ExternalUserId> extendedIds = TargetingParams.fetchStoredExternalUserIds();
        if (extendedIds != null && extendedIds.size() > 0) {
            JSONArray idsJson = new JSONArray();
            for (ExternalUserId id : extendedIds) {
                if (id != null) {
                    idsJson.put(id.getJson());
                }
            }
            user.getExt().put("eids", idsJson);
        }

        final Pair<Float, Float> userLatLng = TargetingParams.getUserLatLng();
        if (userLatLng != null) {
            final Geo userGeo = user.getGeo();
            userGeo.lat = userLatLng.first;
            userGeo.lon = userLatLng.second;
        }
    }

    private void setDisplayManager(Imp imp) {
        imp.displaymanager = DISPLAY_MANAGER_VALUE;
        imp.displaymanagerver = SDK_VERSION;
    }

    private int[] getApiFrameworks() {
        List<Integer> supportedApiFrameworks = new ArrayList<>();

        // If MRAID is on, then add api(3,5)
        if (PrebidMobile.sendMraidSupportParams) {
            supportedApiFrameworks.addAll(SUPPORTED_MRAID_VERSIONS);
        }

        // Add OM support
        supportedApiFrameworks.add(API_OPEN_MEASUREMENT);

        // If list of supported frameworks is not empty, set api field
        if (!supportedApiFrameworks.isEmpty()) {
            // Remove duplicates
            supportedApiFrameworks = new ArrayList<>(new HashSet<>(supportedApiFrameworks));

            // Create api array
            int[] result = new int[supportedApiFrameworks.size()];
            for (int i = 0; i < supportedApiFrameworks.size(); i++) {
                result[i] = supportedApiFrameworks.get(i);
            }

            return result;
        } else {
            return null;
        }
    }
}

package com.github.kiulian.downloader.downloader.request;

import com.github.kiulian.downloader.downloader.client.ClientType;
import com.github.kiulian.downloader.model.videos.VideoInfo;

public class RequestVideoInfo extends Request<RequestVideoInfo, VideoInfo> {

    private final String videoId;
    private boolean allowWebParsing = true;

    public RequestVideoInfo(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    /**
     * Sets whether web parsing is allowed or not.
     * <p>
     * Web parsing is slower than using clients and ideally should not be used,
     * however the API request via a client may fail.
     * Due to that, web parsing is used in this type of request as a fallback in case the initial request fails.
     * This value is {@code true} by default, and generally should be left like that.
     * It's useful to disable this when trying to find out why a client failed, because
     * if web parsing is used as a fallback it will shadow the error from the previous request.
     * There is also no guarantee that the request won't fail if web parsing is used.
     * </p>
     *
     * @throws IllegalArgumentException if this request's {@code ClientType} was set to null, and {@code false} is passed
     *                                  to this method.
     */
    public RequestVideoInfo allowWebParsing(boolean val) {
        if (!val && getClientType() == null) {
            throw new IllegalArgumentException("Either web parsing has to be allowed or a client has to be specified.");
        }
        this.allowWebParsing = val;
        return this;
    }

    public boolean allowsWebParsing() {
        return this.allowWebParsing;
    }


    /**
     * @throws IllegalArgumentException if the passed client is null and {@code allowWebParsing} is set to false
     */
    @Override
    public RequestVideoInfo clientType(ClientType client) {
        if (!allowWebParsing && client == null) {
            throw new IllegalArgumentException("Either web parsing has to be allowed or a client has to be specified.");
        }
        return super.clientType(client);
    }
}

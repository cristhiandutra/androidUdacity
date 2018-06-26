package com.android.theguadiannews;

import java.util.Date;

public class News {

    private String mTitle;
    private String mSection;
    private String mUrl;
    private String mUrlThumbnail;
    private Date mPublication;

    public News(String title, String section, String url, String urlThumbnail, Date publication) {
        this.mTitle = title;
        this.mSection = section;
        this.mUrl = url;
        this.mUrlThumbnail = urlThumbnail;
        this.mPublication = publication;
    }

    /**
     * Return title
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Return section
     * @return
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Return url
     * @return
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Return url thumbnail
     * @return
     */
    public String getUrlThumbnail() {
        return mUrlThumbnail;
    }

    /**
     * Return publication date
     * @return
     */
    public Date getPublication() {
        return mPublication;
    }
}

package com.android.booklist;

import java.util.List;

public class Book {

    private String mTitle;
    private List<String> mAuthor;
    private String mThumbnailUrl;
    private String mInfoLinkUrl;

    public Book(String title, List<String> author, String thumbnailUrl, String infoLinkUrl) {
        mTitle = title;
        mAuthor = author;
        mThumbnailUrl = thumbnailUrl;
        mInfoLinkUrl = infoLinkUrl;
    }

    /**
     * Return title
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Return author
     * @return
     */
    public List<String> getAuthor() {
        return mAuthor;
    }

    /**
     * Return thumbnail url
     * @return
     */
    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    /**
     * Return infoLink url
     * @return
     */
    public String getmInfoLinkUrl() {
        return mInfoLinkUrl;
    }
}

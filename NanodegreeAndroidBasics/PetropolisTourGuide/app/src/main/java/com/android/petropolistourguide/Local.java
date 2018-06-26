package com.android.petropolistourguide;

public class Local {

    private String mName;
    private String mAddress;
    private int mImageResourceId;

    public Local(String name, int imageResourceId, String address) {
        mName = name;
        mImageResourceId = imageResourceId;
        mAddress = address;
    }

    /**
     * Return the name
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     * Return the Image Resource Id
     * @return
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Return the Adress
     * @return
     */
    public String getAddress() {
        return mAddress;
    }
}

package com.konnectshift.frnd.data.retrofit;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public final class MultipartParams {

    private HashMap<String, RequestBody> map;


    private MultipartParams(final Builder builder) {
        this.map = builder.map;

    }

    static String getMimeType(final File file) {
        String mimeType = "audio/*";
        try {
            Uri selectedUri = Uri.fromFile(file);
            String fileExtension
                    = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            mimeType
                    = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        } catch (Exception e) {
            Log.e("Error", e.toString());

        }
        if (mimeType != null)
            return mimeType;
        else
            return "audio/*";
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public HashMap<String, RequestBody> getMap() {
        return map;
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        private HashMap<String, RequestBody> map = new HashMap<>();

        public Builder() {
        }

        public Builder addFile(final String key, final File mFile) {
            if (mFile == null) {
                return this;
            }
            map.put(key + "\"; filename=\"" + mFile.getName(), RequestBody.create(MediaType.parse(getMimeType(mFile)), mFile));
            return this;
        }

        public MultipartParams build() {
            return new MultipartParams(this);
        }
    }
}

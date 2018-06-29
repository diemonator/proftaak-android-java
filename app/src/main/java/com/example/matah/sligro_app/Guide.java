package com.example.matah.sligro_app;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Emil Karamihov on 14-Dec-17.
 */

public class Guide implements Serializable {
    private String title;
    private Integer img;
    private String content;

    public Guide(String title, Integer img, String content) {
        this.img = img;
        this.title = title;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public Integer getImg() {
        return img;
    }

    public String getContent() {
        return content;
    }
}

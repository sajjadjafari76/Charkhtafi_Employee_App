package www.charkhtafi.com.charkhtafi.Model;

import android.graphics.drawable.Drawable;


public class NavModel {
    private Drawable image;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}

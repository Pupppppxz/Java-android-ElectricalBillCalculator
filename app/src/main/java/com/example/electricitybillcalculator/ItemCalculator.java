package com.example.electricitybillcalculator;

import android.widget.ImageView;

public class ItemCalculator extends Item{
    private boolean isClick;
    private ImageView imageView;

    public ItemCalculator(String name, int icon, ImageView imageView) {
        super(name, icon);
        this.isClick = false;
        this.imageView = imageView;
    }

    public ItemCalculator(String name, int icon) {
        super(name, icon);
        this.isClick = false;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void toggleBG(boolean click) {
        if (click) {
            this.imageView.setBackgroundResource(R.drawable.bg_button_two_layer);
        } else {
            this.imageView.setBackgroundResource(R.drawable.bg_two_layer_orange);
        }
    }
}

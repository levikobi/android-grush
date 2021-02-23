package com.comas.grush.ui.product;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class ProductViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private Bitmap bitmap;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public boolean selectedPicture() {
        return this.bitmap != null;
    }
}
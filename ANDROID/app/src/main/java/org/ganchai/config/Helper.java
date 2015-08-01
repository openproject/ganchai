package org.ganchai.config;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.GenericDraweeView;

public class Helper {

    public static void displayDraweeView(String url, GenericDraweeView genericDraweeView) {
        Uri uri = Uri.parse(url);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        genericDraweeView.setController(controller);
    }
}

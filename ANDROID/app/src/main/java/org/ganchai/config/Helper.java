package org.ganchai.config;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class Helper {

    public static void displayDraweeView(String url, GenericDraweeView genericDraweeView) {
        displayDraweeView(url, genericDraweeView, false);
    }

    public static void displayDraweeView(String url, GenericDraweeView genericDraweeView, boolean progressive) {
        Uri uri = Uri.parse(url);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        genericDraweeView.setController(controller);
    }

}

package me.echodev.resizer.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by K.K. Ho on 3/9/2017.
 */

public class ImageUtils {

    private static final String TAG = "ImageUtils";

    public static File getScaledImage(int targetWidth, int targetHeight, int quality,boolean aspectRatioFlag, Bitmap.CompressFormat compressFormat,
            String outputDirPath, String outputFilename, File sourceImage) throws IOException {
        File directory = new File(outputDirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Prepare the new file name and path
        String outputFilePath = FileUtils.getOutputFilePath(compressFormat, outputDirPath, outputFilename, sourceImage);

        // Write the resized image to the new file
        Bitmap scaledBitmap = getScaledBitmap(targetWidth, targetHeight, aspectRatioFlag, sourceImage);
        FileUtils.writeBitmapToFile(scaledBitmap, compressFormat, quality, outputFilePath);

        return new File(outputFilePath);
    }

    public static Bitmap getScaledBitmap(int targetWidth,int targetHeight, boolean aspectRatioFlag, File sourceImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(sourceImage.getAbsolutePath(), options);

        // Get the dimensions of the original bitmap
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        float aspectRatio = (float) originalWidth / originalHeight;

        // Calculate the target dimensions if aspect ratio is true
        if(aspectRatioFlag){
            if (originalWidth > originalHeight) {
                targetHeight = Math.round(targetWidth / aspectRatio);
            } else {
                aspectRatio = 1 / aspectRatio;
                targetWidth = Math.round(targetHeight / aspectRatio);
            }
        }

        Log.d(TAG, "getScaledBitmap: targetWidth "+targetWidth);
        Log.d(TAG, "getScaledBitmap: targetHeight "+targetHeight);
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
    }
}

package com.example.technovation24;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.technovation24.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.schema.Model;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Locale;

public class CameraRec extends AppCompatActivity {

    TextView result, confidence;
    ImageView imageView;
    Button picture;
    int imageSize = 224;
    Bitmap capturedImage;
    private Interpreter tflite;

    TextView classifiedText;
    TextView confidenceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_rec);

        ImageView homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(CameraRec.this, HomeActivity.class);
                // start activity
                startActivity(aboutIntent);
            }
        });

        // Initialize UI elements
        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);

        classifiedText = findViewById(R.id.classified);
        confidenceText = findViewById(R.id.confidencesText);

        // Set click listener for the picture button
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        // Restore state if available
        if (savedInstanceState != null) {
            // Restore the captured image
            capturedImage = savedInstanceState.getParcelable("captured_image");
            if (capturedImage != null) {
                imageView.setImageBitmap(capturedImage);
                // If an image was captured, classify it
                classifyImage(capturedImage);
            }
        }

        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the captured image
        outState.putParcelable("captured_image", capturedImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageView.setVisibility(View.VISIBLE);
            classifiedText.setVisibility(View.VISIBLE);
            confidenceText.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);
            confidence.setVisibility(View.VISIBLE);

            Bitmap image = (Bitmap) data.getExtras().get("data");

            // Fix image orientation if needed
            image = fixImageOrientation(image);

            // Proceed with classification
            int dimension = Math.min(image.getWidth(), image.getHeight());
            capturedImage = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(capturedImage);

            image = Bitmap.createScaledBitmap(capturedImage, imageSize, imageSize, false);
            classifyImage(image);
        }
    }

    // Method to fix image orientation
    // Method to fix image orientation
    private Bitmap fixImageOrientation(Bitmap image) {
        ExifInterface exif;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);

            exif = new ExifInterface(byteArrayInputStream);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(270);
                    break;
                default:
                    return image;
            }
            return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
            return image;
        }
    }

    // Method to classify the captured image
    public void classifyImage(Bitmap image) {
        // Preprocess the image
        ByteBuffer inputBuffer = preprocessImage(image);

        // Run inference
        float[][] outputScores = new float[1][3];
        tflite.run(inputBuffer, outputScores);

        // Postprocess the inference result
        String[] classes = {"No Faces", "1 Face", "Several Faces"}; // Replace with your class labels
        displayResult(outputScores[0], classes);
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("model_unquant.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    private ByteBuffer preprocessImage(Bitmap image) {
        int inputSize = 224; // Adjust this size based on your model's input requirements
        int[] pixels = new int[inputSize * inputSize];
        float[] inputValues = new float[inputSize * inputSize * 3];
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, inputSize, inputSize, false);
        scaledBitmap.getPixels(pixels, 0, scaledBitmap.getWidth(), 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

        // Normalize pixel values and write them into inputValues
        for (int i = 0; i < pixels.length; ++i) {
            final int val = pixels[i];
            inputValues[i * 3] = ((val >> 16) & 0xFF) / 255.0f;
            inputValues[i * 3 + 1] = ((val >> 8) & 0xFF) / 255.0f;
            inputValues[i * 3 + 2] = (val & 0xFF) / 255.0f;
        }

        // Create a ByteBuffer and write the inputValues into it
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(4 * inputValues.length);
        inputBuffer.order(ByteOrder.nativeOrder());
        for (float inputVal : inputValues) {
            inputBuffer.putFloat(inputVal);
        }
        inputBuffer.rewind(); // Reset the buffer's position to 0

        return inputBuffer;
    }

    private void displayResult(float[] scores, String[] classes) {
        // Set a threshold for considering a prediction valid
        float threshold = 0.5f; // You can adjust this value as needed

        // Check if any class exceeds the threshold
        int maxIndex = -1;
        float maxScore = 0;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > threshold) {
                if (scores[i] > maxScore) {
                    maxIndex = i;
                    maxScore = scores[i];
                }
            }
        }

        // If no class exceeds the threshold, default to "No Faces"
        if (maxIndex == -1) {
            result.setText("No Faces");
            confidence.setText("N/A");
        } else {
            // Display the result of the class with the highest score
            result.setText(classes[maxIndex]);
            confidence.setText(String.format(Locale.getDefault(), "%.2f%%", maxScore * 100));
        }
    }

}

/*import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.model.Model;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CameraRec extends AppCompatActivity {

    TextView result, confidence;
    ImageView imageView;
    Button picture;
    int imageSize = 224;
    Bitmap capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_rec);

        // Initialize UI elements
        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);

        // Set click listener for the picture button
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        // Restore state if available
        if (savedInstanceState != null) {
            // Restore the captured image
            capturedImage = savedInstanceState.getParcelable("captured_image");
            if (capturedImage != null) {
                imageView.setImageBitmap(capturedImage);
                // If an image was captured, classify it
                classifyImage(capturedImage);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the captured image
        outState.putParcelable("captured_image", capturedImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            capturedImage = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(capturedImage);

            image = Bitmap.createScaledBitmap(capturedImage, imageSize, imageSize, false);
            classifyImage(image);
        }
    }

    // Method to classify the captured image
    public void classifyImage(Bitmap image) {
        // Implement image classification logic here
    }
}*/

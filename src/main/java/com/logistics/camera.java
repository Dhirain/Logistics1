package com.logistics;


import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;


import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class camera extends ActionBarActivity {
    Button b1, b2;
    ImageView iv;
    static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);




        b1 = (Button) findViewById(R.id.button);
        iv = (ImageView) findViewById(R.id.imageView);

        b2 = (Button) findViewById(R.id.Gallery);

        /*
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });*/
    }

    public void openCamera(View view)
    {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);

        /*String TAG="camera";

        Log.d(TAG, "in open camera");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.print("error");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 0);
            }
        }
        */
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                iv.setImageBitmap(bp);

            }
        }


        // When an Image is picked
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                && null != data) {


            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;

    }
}
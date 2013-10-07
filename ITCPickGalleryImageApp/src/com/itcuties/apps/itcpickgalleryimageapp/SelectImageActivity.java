package com.itcuties.apps.itcpickgalleryimageapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * This activity displays the gallery image picker. 
 * It displays the image that was picked.
 * 
 * @author ITCuties
 *
 */
public class SelectImageActivity extends Activity implements OnClickListener {

	// Image loading result to pass to startActivityForResult method.
	private static int LOAD_IMAGE_RESULTS = 1;
	
	// GUI components
	private Button button;	// The button
	private ImageView image;// ImageView
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_image);
		
		// Find references to the GUI objects
		button = (Button)findViewById(R.id.button);
		image = (ImageView)findViewById(R.id.image);
		
		// Set button's onClick listener object.
		button.setOnClickListener(this);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// Here we need to check if the activity that was triggers was the Image Gallery.
		// If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
		// If the resultCode is RESULT_OK and there is some data we know that an image was picked.
		if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
			// Let's read picked image data - its URI
			Uri pickedImage = data.getData();
			// Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            
            // Now we need to set the GUI ImageView data with data read from the picked file.
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            
            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
		}
	}
	
	@Override
	public void onClick(View v) {
		// Create the Intent for Image Gallery.
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		// Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
	}

}

package com.ranchosoftware.diningjoint;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.ranchosoftware.diningjoint.Model.RestaurantFormatter;
import com.ranchosoftware.diningjoint.Server.Server;
import com.ranchosoftware.diningjoint.utility.Permissions;
import com.sandiegorestaurantsandbars.diningjoint.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FunPixActivity extends DiningJointActivity
{
  private static final String TAG = FunPixActivity.class.getSimpleName();

  private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

  private enum RequestCode
  {
    TakePicture, ChooseFromPhone
  };

  private Uri imageUri = null;

  private ImageView ivLogo;
  private int logoSide;
  private RelativeLayout rlSnapshot;
  private TextView tvDragMessage;
 // private String pathToBeforePictureFile;
  private int xDelta;
  private int yDelta;

  private int which;
  private boolean firstLayoutDone = false;

  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fun_pix);
    rlSnapshot = (RelativeLayout) findViewById(R.id.rlSnapshot);
    ivLogo = (ImageView) findViewById(R.id.ivLogo);
    ivLogo.setVisibility(View.INVISIBLE);

    Display display = getWindowManager().getDefaultDisplay();
    Point size =  new Point();
    display.getSize(size);
    int shortSide = Math.min(size.x, size.y);
    logoSide = (int) ( 0.3 * shortSide);

    Restaurant restaurant = getModel().getSelectedRestaurant();
    Server.getInstance().getWatermarkImage(
            restaurant.getId(),
            new Server.ImageSuccessCallback() {
               @Override
               public void success(Bitmap bitmap) {
                 ivLogo.setImageBitmap(bitmap);

                }},
             new Server. FailureCallback(){
               @Override
               public void failure(String message) {

        }
      }
    );

    tvDragMessage = (TextView) findViewById(R.id.tvDragMessage);
    tvDragMessage.setVisibility(View.INVISIBLE);
    ViewTreeObserver vto = rlSnapshot.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        if (!firstLayoutDone){
          firstLayoutDone = true;

          RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivLogo.getLayoutParams();
          RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams((ViewGroup.LayoutParams)params);
          int i = rlSnapshot.getWidth();
          int i2= rlSnapshot.getHeight();
          p2.leftMargin = rlSnapshot.getWidth() - logoSide;
          p2.topMargin = rlSnapshot.getHeight() - logoSide;
          p2.width = logoSide;
          p2.height = logoSide;
          ivLogo.setLayoutParams(p2);
        }
      }
    });


    ivLogo.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent event) {

        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
          case MotionEvent.ACTION_DOWN:
            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            xDelta = x - lParams.leftMargin;
            yDelta = y - lParams.topMargin;
            break;
          case MotionEvent.ACTION_UP:
            break;
          case MotionEvent.ACTION_POINTER_DOWN:
            break;
          case MotionEvent.ACTION_POINTER_UP:
            break;
          case MotionEvent.ACTION_MOVE:
            tvDragMessage.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                    .getLayoutParams();
            layoutParams.leftMargin = x - xDelta;
            layoutParams.topMargin = y - yDelta;
            layoutParams.rightMargin = -250;
            layoutParams.bottomMargin = -250;
            layoutParams.width = logoSide;
            layoutParams.height = logoSide;
            view.setLayoutParams(layoutParams);
            break;
        }
        rlSnapshot.invalidate();
        return true;
      }
    });

    Button takePicture = (Button) this.findViewById(R.id.btnTakePicture);
    takePicture.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        if (Permissions.hasPermission(thisActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
          AlertDialog.Builder builder = new AlertDialog.Builder(FunPixActivity.this);
          builder.setTitle("Options");
          String[] s = {"Take Picture", "Choose From Phone"};
          builder.setItems(s, new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
              FunPixActivity.this.which = which;
              if (which == 0) {
                launchCameraForResult();
              } else if (which == 1) {
                choosePictureFromPhone();
              }
            }
          });
          builder.show();
        } else {
          // request perm
          Permissions.requestPermission(thisActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                  PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);

        }



      }
    });

    Button sharePicture = (Button) findViewById(R.id.btnSharePicture);
    sharePicture.setOnClickListener(new OnClickListener()
    {

      @Override
      public void onClick(View v)
      {

        if (imageUri == null) {
          AlertDialog.Builder builder = new AlertDialog.Builder(FunPixActivity.this);

          builder.setTitle("Forgot Picture");
          builder.setMessage("You must have a picture to send");
          builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {

            }
          });
          builder.show();
        } else {
          tvDragMessage.setVisibility(View.INVISIBLE);
          imageUri = createPictureToShare(rlSnapshot, (ImageView) FunPixActivity.this.findViewById(R.id.ivSnapshot), ivLogo, imageUri);

          AlertDialog.Builder builder = new AlertDialog.Builder(FunPixActivity.this);
          builder.setTitle("Options");
          String[] s = { "Send as Email", "Post to Facebook", "Tweet Picture", "Open In Instagram", "Save To Pictures"}; //,

          share();

        }
      }
    });
  }

  private void share(){

      Intent intent = new Intent(android.content.Intent.ACTION_SEND);

      Restaurant restaurant = getModel().getSelectedRestaurant();
      RestaurantFormatter fmt = new RestaurantFormatter(restaurant);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
      intent.putExtra(android.content.Intent.EXTRA_SUBJECT, fmt.name() + " via Dining Joint");
      intent.putExtra(android.content.Intent.EXTRA_TEXT, "Check this out!");
      intent.setType("image/*");

      Uri external = copyToExternalStorage(imageUri);
      Uri fileUri = FileProvider.getUriForFile(thisActivity, "com.myfileprovider", new File(imageUri.getPath()));

      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      intent.putExtra(Intent.EXTRA_STREAM, fileUri);


      startActivity(Intent.createChooser(intent, "How do you want to share?"));
  }

  private Uri createPictureToShare(RelativeLayout parent, ImageView ivPhoto, ImageView ivLogo, Uri imageUri){
    try
    {

      parent.setDrawingCacheEnabled(true);
      Bitmap b = parent.getDrawingCache();

      File file = getImageFile();

      OutputStream outStream = new FileOutputStream(file);
      b.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
      outStream.flush();
      outStream.close();

      Uri contentUri = Uri.fromFile(file);
      return contentUri;

      //ivLogo.setVisibility(View.VISIBLE);
      //tvDragMessage.setVisibility(View.VISIBLE);
    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }
  private boolean saveToCameraRoll()
  {
    try {
      File file =  getImageFile();
      String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
      Date d = new Date();
      File newFile = new File(directoryPath, "dj_" + Long.toString(d.getTime())+".jpg");
      boolean result = newFile.createNewFile();
    
      if (result) {
        copyFile(file, newFile);
      
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(newFile.getAbsolutePath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
      }
      return true; 
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
    return false;
  }
  
  private void copyFile(File src, File dest)
  {
    try {
      FileInputStream in = new FileInputStream(src);
      FileOutputStream out = new FileOutputStream(dest);
      
      in.getChannel().transferTo(0, in.getChannel().size(), out.getChannel());
      in.close();
      out.close(); 
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void finish() {
    // TODO Auto-generated method stub
    super.finish();
    deleteImageFile();
}



  private File createImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";

    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
    );
    return image;
  }
  private void launchCameraForResult() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // Ensure that there's a camera activity to handle the intent
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      // Create the File where the photo should go
      File photoFile = null;
      try {
        photoFile = createImageFile();

      } catch (IOException ex) {
        // Error occurred while creating the File
        int x = 5;
      }
      // Continue only if the File was successfully created
      if (photoFile != null) {

        //pathToBeforePictureFile = photoFile.toString();
        imageUri = Uri.fromFile(photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(takePictureIntent, RequestCode.TakePicture.ordinal());
      }
    }
  }

  private void choosePictureFromPhone()
  {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult(intent, RequestCode.ChooseFromPhone.ordinal());
  }


  private void handleTakePictureResult(int resultCode, Intent data)
  {
    if (resultCode == RESULT_OK)
    {
     // File imageFile = new File(imageUri.toString());
      try {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
        //Bitmap bitmap = BitmapFactory.decodeFile(pathToBeforePictureFile);

        ExifInterface ei = new ExifInterface(imageUri.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch(orientation) {
          case ExifInterface.ORIENTATION_ROTATE_90:
            bitmap = rotateImage(bitmap, 90);
            break;
          case ExifInterface.ORIENTATION_ROTATE_180:
            bitmap = rotateImage(bitmap, 180);
            break;
          case ExifInterface.ORIENTATION_ROTATE_270:
            bitmap = rotateImage(bitmap, 270);
            break;
        }
        // etc.
        //imageUri = data.getData();
        //Bitmap pickedBitmap = (Bitmap) data.getExtras().get("data");
        Bitmap pickedBitmap = scaleBitmapIfNecessary(bitmap);

        //File file = new File(pathToBeforePictureFile);
        File file = new File(imageUri.getPath());
        OutputStream outStream = new FileOutputStream(file);
        pickedBitmap.compress(Bitmap.CompressFormat.PNG, 90, outStream);
        outStream.flush();
        outStream.close();

        //pathToBeforePictureFile = Uri.fromFile(file).toString();
        launchCropActivity(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    else
    {
      Toast toast = Toast.makeText(this, "picture not taken", Toast.LENGTH_LONG);
      toast.show();
    }
  }

  private Bitmap rotateImage(Bitmap source, float angle){
    Matrix matrix = new Matrix();
    matrix.postRotate(angle);
    return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
  }

  private Bitmap scaleBitmapIfNecessary(Bitmap bitmap){
    int maxSize = Math.max(bitmap.getWidth(), bitmap.getHeight());
    // bitmap to be at most 800
    int newWidth = 0;
    int newHeight = 0;
    if (maxSize > 1000){
      if (bitmap.getWidth() > bitmap.getHeight()){
        newWidth = 1000;
        newHeight = (int) (bitmap.getHeight() * (1000.0/ (double)bitmap.getWidth()));
      } else {
        newHeight = 1000;
        newWidth = (int) (bitmap.getWidth() * (1000.0/(double) bitmap.getHeight()));
      }
      bitmap = bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }
    return bitmap;
  }

  private void handleChooseFromPhoneResult(int resultCode, Intent data)
  {
    if (resultCode == RESULT_OK)
    {


      try {
        imageUri = data.getData();
        Bitmap pickedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        pickedBitmap = scaleBitmapIfNecessary(pickedBitmap);

        File file = getImageFile();

        OutputStream outStream = new FileOutputStream(file);
        pickedBitmap.compress(Bitmap.CompressFormat.PNG, 90, outStream);
        outStream.flush();
        outStream.close();
        imageUri = Uri.fromFile(file);
        launchCropActivity(file);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    else
    {
      Toast toast = Toast.makeText(this, "picture not taken", Toast.LENGTH_LONG);
      toast.show();
    }
  }

  private void handleCropPictureResult(int resultCode, Intent data){
      if (resultCode == RESULT_OK) {
        Bitmap croppedPhoto = null;
        try {
          //Uri uri = Uri.fromFile(new File(pathToBeforePictureFile));
          croppedPhoto = MediaStore.Images.Media.getBitmap(FunPixActivity.this.getContentResolver(),imageUri );
          imageUri = Crop.getOutput(data);
          ImageView pic = (ImageView) FunPixActivity.this.findViewById(R.id.ivSnapshot);
          pic.setImageBitmap(croppedPhoto);
          ivLogo.setVisibility(View.VISIBLE);
          tvDragMessage.setVisibility(View.VISIBLE);
        } catch (IOException e) {
          e.printStackTrace();
        }

      } else {
        String message = Crop.getError(data).getMessage();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
      }
  }
  private File getImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";

    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
    );
    return image;
  }
  
  private void deleteImageFile()
  {
    File file = null;
    try {
      file = getImageFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    boolean b = file.delete(); 
    if (b)
    {
      @SuppressWarnings("unused")
      int i = 5; 
    }
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    if (requestCode == RequestCode.TakePicture.ordinal())
    {

      handleTakePictureResult(resultCode, data);
    }
    else if (requestCode == RequestCode.ChooseFromPhone.ordinal())
    {
      handleChooseFromPhoneResult(resultCode, data);
    }
    else if (requestCode == Crop.REQUEST_CROP)
    {
      handleCropPictureResult(resultCode, data);
    }
  }



  private void launchCropActivity(File file)  {



    Uri destUri = Uri.fromFile(file);
    Uri srcUri = Uri.fromFile(file);

    Crop crop = Crop.of(srcUri, destUri);
    crop.start(this);


  }

  public void copy(File src, File dst) throws IOException {
    InputStream in = new FileInputStream(src);
    OutputStream out = new FileOutputStream(dst);

    // Transfer bytes from in to out
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    in.close();
    out.close();
  }

  public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  private Uri copyToExternalStorage(Uri from){
    if (isExternalStorageWritable()){
      File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "funpix.jpg");


      File dest = file;
      File src = new File(from.getPath());
      try {
        copy(src, dest);
      } catch (IOException e) {
        e.printStackTrace();
      }


      return Uri.fromFile(dest);
    }
    return null;
  }



  private boolean isAppInstalled(String packageName) {
    PackageManager pm = getPackageManager();
    boolean installed = false;
    try {
      pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
      installed = true;
    } catch (PackageManager.NameNotFoundException e) {
      installed = false;
    }
    return installed;
  }


  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          if (which == 0) {
            launchCameraForResult();
          } else if (which == 1) {
            choosePictureFromPhone();
          }

        } else {

          // permission denied, boo! Disable the
          // functionality that depends on this permission.
        }
        return;
      }

      // other 'case' lines to check for other
      // permissions this app might request
    }
  }
}

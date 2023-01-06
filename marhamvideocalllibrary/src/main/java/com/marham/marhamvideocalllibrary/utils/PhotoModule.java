package com.marham.marhamvideocalllibrary.utils;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.R;

public class PhotoModule {

    public Uri cameraPictureUri;
    private AppCompatActivity activity;
    private Fragment fragment;
    private View parentLayout;
    private String currentPhotoPath;
    private HashMap<String, String> permissionsList = new HashMap<>();


    public PhotoModule() {

    }

    private static String getPathFromExtSD(String[] pathData) {
        final String type = pathData[0];
        final String relativePath = "/" + pathData[1];
        String fullPath = "";

        // on my Sony devices (4.4.4 & 5.1.1), `type` is a dynamic string
        // something like "71F8-2C0A", some kind of unique id per storage
        // don't know any API that can get the root path of that storage based on its id.
        //
        // so no "primary" type, but let the check here for other devices
        if ("primary".equalsIgnoreCase(type)) {
            fullPath = Environment.getExternalStorageDirectory() + relativePath;
//            if (fileExists(fullPath)) {
//                return fullPath;
//            }
        }

        // Environment.isExternalStorageRemovable() is `true` for external and internal storage
        // so we cannot relay on it.
        //
        // instead, for each possible path, check if file exists
        // we'll start with secondary storage as this could be our (physically) removable sd card
        fullPath = System.getenv("SECONDARY_STORAGE") + relativePath;
//        if (fileExists(fullPath)) {
//            return fullPath;
//        }

        fullPath = System.getenv("EXTERNAL_STORAGE") + relativePath;
//        if (fileExists(fullPath)) {
//            return fullPath;
//        }

        return fullPath;
    }

    public static String getPath(Context context, Uri uri) {
        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String selection = null;
        String[] selectionArgs = null;
        // DocumentProvider
        if (isKitKat) {
            // ExternalStorageProvider

            if (StorageAccessClass.isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                String fullPath = getPathFromExtSD(split);
                if (fullPath != "") {
                    return fullPath;
                } else {
                    return null;
                }
            }


            // DownloadsProvider
            if (StorageAccessClass.isDownloadsDocument(uri)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final String id;
                    Cursor cursor = null;
                    try {
                        cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String fileName = cursor.getString(0);
                            String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            if (!TextUtils.isEmpty(path)) {
                                return path;
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }
                    id = DocumentsContract.getDocumentId(uri);
                    if (!TextUtils.isEmpty(id)) {
                        if (id.startsWith("raw:")) {
                            return id.replaceFirst("raw:", "");
                        }
                        String[] contentUriPrefixesToTry = new String[]{
                                "content://downloads/public_downloads",
                                "content://downloads/my_downloads"
                        };
                        for (String contentUriPrefix : contentUriPrefixesToTry) {
                            try {
                                final Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));


                                return StorageAccessClass.getDataColumn(context, contentUri, null, null);
                            } catch (NumberFormatException e) {
                                //In Android 8 and Android P the id is not a number
                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                            }
                        }


                    }
                } else {
                    final String id = DocumentsContract.getDocumentId(uri);

                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    try {
                        uri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (uri != null) {

                        return StorageAccessClass.getDataColumn(context, uri, null, null);
                    }
                }
            }


            // MediaProvider
            if (StorageAccessClass.isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;

                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    contentUri = MediaStore.Files.getContentUri("external");
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};

                return StorageAccessClass.getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }

            if (isGoogleDriveUri(uri)) {
                return getDriveFilePath(context, uri);
            }

            if (isWhatsAppFile(uri)) {
                return getFilePathForWhatsApp(context, String.valueOf(uri));
            }


            if ("content".equalsIgnoreCase(uri.getScheme())) {

                if (StorageAccessClass.isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                if (isGoogleDriveUri(uri)) {
                    return getDriveFilePath(context, uri);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    // return getFilePathFromURI(context,uri);
                    return copyFileToInternalStorage(context, uri, "userfiles");
                    // return getRealPathFromURI(context,uri);
                } else {
                    return StorageAccessClass.getDataColumn(context, uri, null, null);
                }

            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else {

            if (isWhatsAppFile(uri)) {
                return getFilePathForWhatsApp(context, String.valueOf(uri));
            }

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver()
                            .query(uri, projection, selection, selectionArgs, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }

    private static String getFilePathForWhatsApp(Context context, String uri) {
        return copyFileToInternalStorage(context, Uri.parse(uri), "whatsapp");
    }

    private static String copyFileToInternalStorage(Context context, Uri uri, String newDirName) {
        Uri returnUri = uri;

        Cursor returnCursor = context.getContentResolver().query(returnUri, new String[]{
                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
        }, null, null, null);


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if (!newDirName.equals("")) {
            File dir = new File(context.getFilesDir() + "/" + newDirName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            output = new File(context.getFilesDir() + "/" + newDirName + "/" + name);
        } else {
            output = new File(context.getFilesDir() + "/" + name);
        }
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(output);
            int read = 0;
            int bufferSize = 1024;
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {

            Log.e("Exception", e.getMessage());
        }

        return output.getPath();
    }

    public static boolean isWhatsAppFile(Uri uri) {
        return "com.whatsapp.provider.media".equals(uri.getAuthority());
    }

    private static String getDriveFilePath(Context context, Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    private static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }


    public PhotoModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    public PhotoModule(AppCompatActivity activity, View parentLayout) {
        this.activity = activity;
        this.parentLayout = parentLayout;
    }

    public PhotoModule(AppCompatActivity activity, Fragment fragment, View parentLayout) {
        this.activity = activity;
        this.fragment = fragment;
        this.parentLayout = parentLayout;
    }

    public boolean hasUserGrantedCameraPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

//    public boolean hasUserGrantedGalleryPermission(Context context) {
//        if (((ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED)) && ((ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED))) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean hasUserGrantedGalleryPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasUserGrantedFilesPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }


    public void handleCameraPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                Dexter.withContext(activity).withPermissions(Manifest.permission.CAMERA).withListener(cameraPermissionListener).check();
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                Dexter.withContext(activity).withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(cameraPermissionListener).check();
            } else {
                Dexter.withContext(activity).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(cameraPermissionListener).check();
            }
        } else {
            goToCamera();
        }
    }

    public void handleGalleryPermission() {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Dexter.withContext(activity).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(galleryPermissionListener).check();
        } else {
            goToGallery();
        }
    }

    private void goToGallery() {
        if (fragment == null) {
            openGallery();
        } else {
            openGallery(fragment);
        }
    }

    public void goToCamera() {
        if (fragment == null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    openCamera();
                } else {
                    openCamera();
                }
            } else {
                openCamera();
            }
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    openCamera();
                } else {
                    openCameraForAndroidTenForFragment(fragment);
                }
            } else {
                openCamera(fragment);
            }
        }
    }

    private void openCameraForAndroidTenForFragment(Fragment fragment) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(activity);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                fragment.startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile(AppCompatActivity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCameraForAndroidTen() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            activity.startActivityForResult(takePictureIntent, 1);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    public void goToCameraForMedicineDelivery() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            activity.startActivityForResult(takePictureIntent, 1);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }


    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG");
            cameraPictureUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            activity.startActivityForResult(intent, 1);
        } else {
            Toast.makeText(activity, "Could not get Image !", Toast.LENGTH_LONG).show();
        }
    }


    public Uri getCameraPictureUri() {
        return cameraPictureUri;
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(pickPhoto, 0);
    }

    private void openCamera(Fragment context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getActivity().getPackageManager()) != null) {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            cameraPictureUri = context.getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.startActivityForResult(intent, 1);
        } else {
            Toast.makeText(context.getActivity(), "Could not get Image !", Toast.LENGTH_LONG).show();
        }
    }


    private void openGallery(Fragment context) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(pickPhoto, 0);
    }


    public Uri getImageUri(Context context, Intent data) {
        if (data != null) {
            return data.getData();
        } else {
            return cameraPictureUri;
        }
    }

    public Uri getCameraPictureUri(Context context, Intent data) {
        if (data != null) {
            return data.getData();
        } else {
            return null;
        }
    }


//    public static MultipartBody.Part compressAndConvertFileToFormatRequiredByWebService(Context context, Uri uri, String fileNameInAPI) {
//        try {
//            if (uri != null) {
//                File originalFile = new File(getRealPathFromURI(context, uri));
//                if (originalFile != null) {
//                    File compressedFile = getCompressedFile(context, originalFile);
//
////            int file_size1 = Integer.parseInt(String.valueOf(originalFile.length() / 1024));
////            int file_size2 = Integer.parseInt(String.valueOf(compressedFile.length() / 1024));
//
////            Toast.makeText(context, "original size :" + String.valueOf(file_size1) + "\ncompressed size : " + String.valueOf(file_size2), Toast.LENGTH_LONG).show();
//
//                    return convertFileToFormatRequiredByWebService(compressedFile, fileNameInAPI);
//                } else {
//                    Toast.makeText(context, "Could attach picture", Toast.LENGTH_LONG).show();
//                    return null;
//                }
//
//            } else {
//                Toast.makeText(context, "Could not attach picture", Toast.LENGTH_LONG).show();
//                return null;
//            }
//        } catch (Exception e) {
////            Toast.makeText(context, "Could not attach picture", Toast.LENGTH_LONG).show();
//            return null;
//        }
//    }

//    public static File getCompressedFile(Context context, File originalFile) {
////        try {
//        File compressedFile = null;
//        try {
//            compressedFile = new Compressor(context).compressToFile(originalFile);
//            return compressedFile;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(context, " 1. There was a problem while compressing the file", Toast.LENGTH_SHORT).show();
//            return originalFile;
//        }
//
//
////        } catch (Exception e) {
////            Toast.makeText(context, "There was a problem while compressing the file", Toast.LENGTH_SHORT).show();
////            return null;
////        }
//    }

    public static MultipartBody.Part convertFileToFormatRequiredByWebService(File file, String fileNameInAPI) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData(fileNameInAPI, file.getName(), requestBody);

    }

    public static MultipartBody.Part convertFileToFormatRequiredByWebService(Context context, Uri uri, String fileNameInAPI) {
        try {
            File file = new File(getRealPathFromURI(context, uri));
            if (file != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                return MultipartBody.Part.createFormData(fileNameInAPI, file.getName(), requestBody);
            } else {
                return null;
            }
        } catch (Exception e) {
//            Toast.makeText(context, "Could attach picture", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] projections = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, projections, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }


    ///////////


    private final MultiplePermissionsListener cameraPermissionListener = new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
            if (report.areAllPermissionsGranted()) {
                goToCamera();
            } else {
                Snackbar snackbar = Snackbar.make(parentLayout, R.string.msg_on_camera_permission_denied, Snackbar.LENGTH_LONG).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MarhamUtils.getInstance().openPermissionSetting(activity);
                    }
                });
                snackbar.show();
            }
            permissionsList = MarhamUtils.getInstance().getPermissionsList(activity);
//            MarhamUtils.getInstance().updateUserPermissionsStatusToServer(activity, permissionsList);
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
            token.continuePermissionRequest();
        }

    };

    private final MultiplePermissionsListener cameraPermissionListenerMedicine = new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
            if (report.areAllPermissionsGranted()) {
                goToCameraForMedicineDelivery();
            } else {
                Snackbar snackbar = Snackbar.make(parentLayout, R.string.msg_on_camera_permission_denied, Snackbar.LENGTH_LONG).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MarhamUtils.getInstance().openPermissionSetting(activity);
                    }
                });
                snackbar.show();
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
            token.continuePermissionRequest();
        }
    };

    private PermissionListener galleryPermissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
            goToGallery();
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
            if (activity != null && parentLayout != null) {
                Snackbar snackbar = Snackbar.make(parentLayout, activity.getResources().getString(R.string.msg_on_storage_permission_denied), Snackbar.LENGTH_LONG).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MarhamUtils.getInstance().openPermissionSetting(activity);
                    }
                });
                snackbar.show();
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            token.continuePermissionRequest();
        }
    };

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public Uri getUri(Context context, Bitmap image) {
        //TODO - Should be processed in another thread
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context, "com.codepath.fileprovider_telenor", file);

        } catch (IOException e) {
            Log.d("TAG", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }


    public void saveImageToGallery(Context context, Bitmap bitmap) {
        OutputStream fos;
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "Marham App");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show();
            } else {

                // Save image to gallery
                String savedImageURL = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Bird", "Image of bird");

                // Parse the gallery image url to uri
                Uri savedImageURI = Uri.parse(savedImageURL);

                Toast.makeText(context, "Image saved to internal!!", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {

            Toast.makeText(context, "Image not saved \n" + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    public void handleCameraPermissionHomeSamples() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Dexter.withActivity(activity).withPermissions(Manifest.permission.CAMERA).withListener(cameraPermissionListener).check();
        } else {
            goToCameraHomeSample();
        }
    }

    private void goToCameraHomeSample() {
        if (fragment == null) {
            openCameraHomeSample();
        } else {
            openCameraHomeSampple(fragment);
        }
    }

    public Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private void openCameraHomeSample() {
        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.parse("file:///sdcard/photo.jpg");
        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(photo, 1);
    }

    private void openCameraHomeSampple(Fragment fragment) {
        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.parse("file:///sdcard/photo.jpg");
        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(photo, 1);
    }

    public void handleCameraPermissionForMedicineDelivery() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Dexter.withActivity(activity).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(cameraPermissionListenerMedicine).check();
        } else {
            goToCameraMedicineDelivery();
        }
    }

    private void goToCameraMedicineDelivery() {
        goToCameraForMedicineDelivery();
    }

    public Uri getImageUrii(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public void handleGalleryPermissionForLiveStream() {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Dexter.withActivity(activity).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(galleryPermissionListener).check();
        } else {
            goToGalleryForLiveStreaming();
        }
    }

    private void goToGalleryForLiveStreaming() {
        if (fragment == null) {
            openGalleryForLiveStreaming();
        } else {
            openGalleryForLiveStreaming(fragment);
        }
    }

    private void openGalleryForLiveStreaming() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0); //activity result method call
    }


    private void openGalleryForLiveStreaming(Fragment context) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0); //activity result method call
    }


    public static String getMPath(Context context, String uri) {
        File file = new File(uri);//create path from uri
        final String[] split = file.getPath().split(":");//split the path.
        String filePath = split[1];//assign it to a string(your choice).
        return filePath;
    }

    public static String getNameFromContentUri(Context context, Uri uri) {
        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String selection = null;
        String[] selectionArgs = null;
        // DocumentProvider
        if (isKitKat) {
            // ExternalStorageProvider

            if (StorageAccessClass.isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                String fullPath = getPathFromExtSD(split);
                if (fullPath != "") {
                    return fullPath;
                } else {
                    return null;
                }
            }


            // DownloadsProvider
            if (StorageAccessClass.isDownloadsDocument(uri)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final String id;
                    String path;
                    Cursor cursor = null;
                    try {
                        cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String fileName = cursor.getString(0);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                path = copyFileToInternalStorage(context, uri, "userfiles");
                            } else {
                                path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            }
                            if (!TextUtils.isEmpty(path)) {
                                return path;
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }
                    id = DocumentsContract.getDocumentId(uri);
                    if (!TextUtils.isEmpty(id)) {
                        if (id.startsWith("raw:")) {
                            return id.replaceFirst("raw:", "");
                        }
                        String[] contentUriPrefixesToTry = new String[]{
                                "content://downloads/public_downloads",
                                "content://downloads/my_downloads"
                        };
                        for (String contentUriPrefix : contentUriPrefixesToTry) {
                            try {
                                final Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                                return StorageAccessClass.getDataColumn(context, contentUri, null, null);
                            } catch (NumberFormatException e) {
                                //In Android 8 and Android P the id is not a number
                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                            }
                        }


                    }
                } else {
                    final String id = DocumentsContract.getDocumentId(uri);

                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    try {
                        uri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (uri != null) {

                        return StorageAccessClass.getDataColumn(context, uri, null, null);
                    }
                }
            }


            // MediaProvider
            if (StorageAccessClass.isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;

                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        contentUri = MediaStore.getMediaUri(context, uri);
                    }
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};

                return StorageAccessClass.getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }

            if (isGoogleDriveUri(uri)) {
                return getDriveFilePath(context, uri);
            }

            if (isWhatsAppFile(uri)) {
                return getFilePathForWhatsApp(context, String.valueOf(uri));
            }


            if ("content".equalsIgnoreCase(uri.getScheme())) {

                if (StorageAccessClass.isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                if (isGoogleDriveUri(uri)) {
                    return getDriveFilePath(context, uri);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    // return getFilePathFromURI(context,uri);
                    return copyFileToInternalStorage(context, uri, "userfiles");
                    // return getRealPathFromURI(context,uri);
                } else {
                    return StorageAccessClass.getDataColumn(context, uri, null, null);
                }

            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else {

            if (isWhatsAppFile(uri)) {
                return getFilePathForWhatsApp(context, String.valueOf(uri));
            }

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver()
                            .query(uri, projection, selection, selectionArgs, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void shareBitmap(Context context, View view) {
        Bitmap bitmap = getBitmapFromView(view);
        Uri bmpUri = getUri(context, bitmap);
        if (bmpUri != null) {
            shareImageUri(context, bmpUri);
        } else {
        }
    }

    public void saveBitmap(Context context, View view) {
        Bitmap bitmap = getBitmapFromView(view);
        saveImageToGallery(context, bitmap);
    }

    private void shareImageUri(Context context, Uri uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        context.startActivity(intent);
    }

}

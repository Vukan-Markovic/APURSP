package vukan.com.apursp.ui.novioglasprozor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import vukan.com.apursp.MainActivity;
import vukan.com.apursp.R;
import vukan.com.apursp.ui.poruke.PorukeFragmentArgs;

import static android.app.Activity.RESULT_OK;

public class NovioglasprozorFragment extends Fragment {

  private Button btn_choose;
  private Button btn_add_new_product;
  private Button btn_delete;
  private ImageView imageView;
  private ImageView imageView1;
  private ImageView imageView2;
  private ImageView imageView3;
  private ImageView imageView4;
  private int counter=0;
  private Uri filePath;


  // request code
  private final int PICK_IMAGE_REQUEST = 22;

  // instance for firebase storage and StorageReference
  FirebaseStorage storage;
  StorageReference storageReference;

  public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


      NovioglasprozorViewModel novioglasprozorViewModel = ViewModelProviders.of(this).get(NovioglasprozorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_novioglasprozor, container, false);
        final TextView textView = root.findViewById(R.id.text_novioglasprozor);
        btn_choose = (Button) root.findViewById(R.id.btn_choose);
      btn_add_new_product = (Button) root.findViewById(R.id.add_new_product);
    btn_delete= (Button) root.findViewById(R.id.btn_deletephoto);
      imageView = (ImageView) root.findViewById(R.id.myImage);
    imageView1 = (ImageView) root.findViewById(R.id.myImage1);
    imageView2 = (ImageView) root.findViewById(R.id.myImage2);
    imageView3 = (ImageView) root.findViewById(R.id.myImage3);
    imageView4 = (ImageView) root.findViewById(R.id.myImage4);
      // get the Firebase  storage reference
    storage = FirebaseStorage.getInstance();
    storageReference = storage.getReference();


    btn_add_new_product.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            uploadImage();
        }
      });


    btn_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        deleteImage();
      }
    });

      btn_choose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        chooseImage();
        }
      });
      novioglasprozorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
    }

  private void deleteImage() {


    if (counter==1)
      imageView.setImageBitmap(null);

    else if (counter==2)
      imageView1.setImageBitmap(null);

    else if (counter==3)
      imageView2.setImageBitmap(null);

    else if (counter==4)
      imageView3.setImageBitmap(null);

    else if (counter==5)
      imageView4.setImageBitmap(null);

    counter--;

    if (counter<0)
      counter=0;

  }

  private void chooseImage() {

//    Intent intent= new Intent();
//    intent.setType("image/*");
//    intent.setAction(Intent.ACTION_GET_CONTENT);
//    startActivityForResult(Intent.createChooser(intent, "select sliku"),1);
//

    // Defining Implicit Intent to mobile gallery
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(
      Intent.createChooser(
        intent,
        "Select Image from here..."),
      PICK_IMAGE_REQUEST);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // checking request code and result code
    // if request code is PICK_IMAGE_REQUEST and
    // resultCode is RESULT_OK
    // then set image in the image view
    if (requestCode == PICK_IMAGE_REQUEST
      && resultCode == RESULT_OK
      && data != null
      && data.getData() != null) {

      // Get the Uri of data
      filePath = data.getData();
      try {

        // Setting image on image view using Bitmap
        Bitmap bitmap = MediaStore
          .Images
          .Media
          .getBitmap(
            getActivity().getContentResolver(),
            filePath);

        if (counter==0)
          imageView.setImageBitmap(bitmap);
        else if (counter==1)
          imageView1.setImageBitmap(bitmap);

        else if (counter==2)
          imageView2.setImageBitmap(bitmap);

        else if (counter==3)
          imageView3.setImageBitmap(bitmap);

        else if (counter==4)
          imageView4.setImageBitmap(bitmap);

        counter++;
      }

      catch (IOException e) {
        // Log the exception
        e.printStackTrace();
      }
    }
  }


  private void uploadImage()
  {
    if (filePath != null) {

      // Code for showing progressDialog while uploading
      ProgressDialog progressDialog = new ProgressDialog(getContext());
      progressDialog.setTitle("Uploading...");
      progressDialog.show();

      // Defining the child of storageReference
      StorageReference ref
        = storageReference
        .child(
          "images/"
            + UUID.randomUUID().toString());

      // adding listeners on upload
      // or failure of image
      ref.putFile(filePath)
        .addOnSuccessListener(
          new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(
              UploadTask.TaskSnapshot taskSnapshot)
            {

              // Image uploaded successfully
              // Dismiss dialog
              progressDialog.dismiss();
              Toast
                .makeText(getActivity(),
                  "Image Uploaded!!",
                  Toast.LENGTH_SHORT)
                .show();
            }
          })

        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e)
          {

            // Error, Image not uploaded
            progressDialog.dismiss();
            Toast
              .makeText(getActivity(),
                "Failed " + e.getMessage(),
                Toast.LENGTH_SHORT)
              .show();
          }
        })
        .addOnProgressListener(
          new OnProgressListener<UploadTask.TaskSnapshot>() {

            // Progress Listener for loading
            // percentage on the dialog box
            @Override
            public void onProgress(
              UploadTask.TaskSnapshot taskSnapshot)
            {
              double progress
                = (100.0
                * taskSnapshot.getBytesTransferred()
                / taskSnapshot.getTotalByteCount());
              progressDialog.setMessage(
                "Uploaded "
                  + (int)progress + "%");
            }
          });
    }
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    TextView textView = view.findViewById(R.id.text_novioglasprozor);
//    if(getArguments() != null)
//      textView.setText(PorukeFragmentArgs.fromBundle(getArguments()).getId() + "");
  }



}

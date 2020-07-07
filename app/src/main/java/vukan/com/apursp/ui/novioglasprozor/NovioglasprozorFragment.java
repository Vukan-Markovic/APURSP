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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import vukan.com.apursp.R;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;

import static android.app.Activity.RESULT_OK;

public class NovioglasprozorFragment extends Fragment {


    private CheckBox fiksna;
    private Button btn_choose;
    private Button btn_choosecam;
    private Button btn_add_new_product;
    private Button btn_delete;
    private RadioGroup radioValutaGroup;
    private RadioButton radioEurButton;
    private RadioButton radioDinButton;
    private RadioButton radioCurrentButton;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private EditText naslov;
    private EditText cena;
    private EditText opis;
    private int counter = 0;
    private Uri filePath;
    private Uri filePath1;
    private Uri filePath2;
    private Uri filePath3;
    private Uri filePath4;
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;
    private String uuid;
    private int category;
    private String productID;
    // Define the pic id
    private static final int CAMERA_REQUEST_CODE = 123;

    // Define the button and imageview type variable

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();

        NovioglasprozorViewModel novioglasprozorViewModel = ViewModelProviders.of(this).get(NovioglasprozorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_novioglasprozor, container, false);
        final TextView textView = root.findViewById(R.id.text_novioglasprozor);
        btn_choose = (Button) root.findViewById(R.id.btn_choose);
        btn_choosecam = (Button) root.findViewById(R.id.btn_choosecam);

        btn_add_new_product = (Button) root.findViewById(R.id.add_new_product);
        btn_delete = (Button) root.findViewById(R.id.btn_deletephoto);
        imageView = (ImageView) root.findViewById(R.id.myImage);
        imageView1 = (ImageView) root.findViewById(R.id.myImage1);
        imageView2 = (ImageView) root.findViewById(R.id.myImage2);
        imageView3 = (ImageView) root.findViewById(R.id.myImage3);
        imageView4 = (ImageView) root.findViewById(R.id.myImage4);

        naslov = (EditText) root.findViewById(R.id.naslov);
        cena = (EditText) root.findViewById(R.id.cena);
        opis = (EditText) root.findViewById(R.id.opis);
        fiksna = (CheckBox) root.findViewById(R.id.fiksna);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        radioValutaGroup = (RadioGroup) root.findViewById(R.id.radiogroup);

        // get selected radio button from radioGroup
        int selectedId = radioValutaGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioCurrentButton = (RadioButton) root.findViewById(selectedId);
        radioDinButton = (RadioButton) root.findViewById(R.id.dinari);
        radioEurButton = (RadioButton) root.findViewById(R.id.euri);


//    fiksna.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        radioCurrentButton = (RadioButton) root.findViewById(radioValutaGroup.getCheckedRadioButtonId());      }
//    });
//

        radioDinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioCurrentButton = (RadioButton) root.findViewById(radioValutaGroup.getCheckedRadioButtonId());
            }
        });

        radioEurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioCurrentButton = (RadioButton) root.findViewById(radioValutaGroup.getCheckedRadioButtonId());
            }
        });


        btn_add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opis.getText().toString().trim().length() > 0 && cena.getText().toString().trim().length() > 0 && naslov.getText().toString().trim().length() > 0) {

                    Product newProduct = new Product();

//fffffffffff

//fffffffffff


                    if (counter > 0) {
                        if (filePath == null) uploadImageBitmap(bitmap);
                        else uploadImage(filePath);
                        newProduct.setHomePhotoUrl(uuid);
                    }

                    Date date = new Date();

                    newProduct.setDatetime(new Timestamp(date));

                    newProduct.setCategoryID("2");


                    newProduct.setDescription(opis.getText().toString());
                    newProduct.setName(naslov.getText().toString());
                    newProduct.setPrice(Double.parseDouble(cena.getText().toString()));

                    long l = 0;
                    newProduct.setSeen(l);

                    //newProduct.setHomePhotoUrl("HnkGzuJqZBxWvKuHjvSp");
                    newProduct.setProductID("temp");
                    newProduct.setUserID(fire_user.getUid());
                    if (fiksna.isChecked() == true)
                        newProduct.setFixPrice(true);
                    else
                        newProduct.setFixPrice(false);
                    newProduct.setCurrency(radioCurrentButton.getText().toString());


                    if (getArguments() != null) {
                        category = NovioglasprozorFragmentArgs.fromBundle(getArguments()).getId();
                        newProduct.setCategoryID(category + "");

                    }

                    // newProduct.setFixPrice();
                    //newProduct.setCurrency();

                    //newProduct.setSenderID(fire_user.getUid());
                    productID = novioglasprozorViewModel.addProduct(newProduct);

                    ProductImage pi = new ProductImage();
                    pi.setImageUrl(uuid);
                    pi.setProductID(productID);
                    novioglasprozorViewModel.addProductImage(pi);


                    if (counter == 2) {
                        if (filePath1 == null) uploadImageBitmap(bitmap1);
                        else uploadImage(filePath1);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));

                    } else if (counter == 3) {
                        if (filePath1 == null) uploadImageBitmap(bitmap1);
                        else uploadImage(filePath1);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                        if (filePath2 == null) uploadImageBitmap(bitmap2);
                        else uploadImage(filePath2);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                    } else if (counter == 4) {
                        if (filePath1 == null) uploadImageBitmap(bitmap1);
                        else uploadImage(filePath1);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                        if (filePath2 == null) uploadImageBitmap(bitmap2);
                        else uploadImage(filePath2);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                        if (filePath3 == null) uploadImageBitmap(bitmap3);
                        else uploadImage(filePath3);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));

                    } else if (counter == 5) {
                        if (filePath1 == null) uploadImageBitmap(bitmap1);
                        else uploadImage(filePath1);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                        if (filePath2 == null) uploadImageBitmap(bitmap2);
                        else uploadImage(filePath2);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                        if (filePath3 == null) uploadImageBitmap(bitmap3);
                        else uploadImage(filePath3);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                        if (filePath4 == null) uploadImageBitmap(bitmap4);
                        else uploadImage(filePath4);
                        novioglasprozorViewModel.addProductImage(addPI(uuid, productID));
                    }

                } else {
                    Toast.makeText(getActivity(), "Popunite sva polja", Toast.LENGTH_SHORT).show();

                }
            }

        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage();
            }
        });

        btn_choosecam.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (counter < 5) {
                    Intent camera_intent
                            = new Intent(MediaStore
                            .ACTION_IMAGE_CAPTURE);

                    // Start the activity with camera_intent,
                    // and request pic id
                    startActivityForResult(camera_intent, CAMERA_REQUEST_CODE);
                }
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

    private ProductImage addPI(String uuid, String productID) {
        ProductImage pi = new ProductImage();
        pi.setImageUrl(uuid);
        pi.setProductID(productID);
        return pi;

    }


    private void deleteImage() {


        if (counter == 1)
            imageView.setImageBitmap(null);

        else if (counter == 2)
            imageView1.setImageBitmap(null);

        else if (counter == 3)
            imageView2.setImageBitmap(null);

        else if (counter == 4)
            imageView3.setImageBitmap(null);

        else if (counter == 5)
            imageView4.setImageBitmap(null);

        counter--;

        if (counter < 0)
            counter = 0;

    }

    private void chooseImage() {

//    Intent intent= new Intent();
//    intent.setType("image/*");
//    intent.setAction(Intent.ACTION_GET_CONTENT);
//    startActivityForResult(Intent.createChooser(intent, "select sliku"),1);
//

        if (counter < 5) {
            // Defining Implicit Intent to mobile gallery
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(
                            intent,
                            "Izaberite fotografiju"),
                    PICK_IMAGE_REQUEST);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //proverava da li je slika uslikana kamerom

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bitmap slika = (Bitmap) data.getExtras().get("data");

            if (counter == 0) {
                filePath = null;
                bitmap = slika;
            } else if (counter == 1) {
                filePath1 = null;
                bitmap1 = slika;
            } else if (counter == 2) {
                filePath2 = null;
                bitmap2 = slika;
            } else if (counter == 3) {
                filePath3 = null;
                bitmap3 = slika;
            } else if (counter == 4) {
                filePath4 = null;
                bitmap4 = slika;
            }

            if (counter == 0)
                imageView.setImageBitmap(bitmap);
            else if (counter == 1)
                imageView1.setImageBitmap(bitmap);

            else if (counter == 2)
                imageView2.setImageBitmap(bitmap);

            else if (counter == 3)
                imageView3.setImageBitmap(bitmap);

            else if (counter == 4)
                imageView4.setImageBitmap(bitmap);
            if (counter < 5)
                counter++;

        }

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        //proverava da li je dodata iz galerije
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {


            if (counter == 0)
                filePath = data.getData();
            else if (counter == 1)
                filePath1 = data.getData();
            else if (counter == 2)
                filePath2 = data.getData();
            else if (counter == 3)
                filePath3 = data.getData();
            else if (counter == 4)
                filePath4 = data.getData();


            // Get the Uri of data
            try {


                if (counter == 0) {
                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getActivity().getContentResolver(),
                                    filePath);
                    imageView.setImageBitmap(bitmap);
                } else if (counter == 1) {
                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getActivity().getContentResolver(),
                                    filePath1);
                    imageView1.setImageBitmap(bitmap);
                } else if (counter == 2) {
                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getActivity().getContentResolver(),
                                    filePath2);
                    imageView2.setImageBitmap(bitmap);
                } else if (counter == 3) {
                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getActivity().getContentResolver(),
                                    filePath3);
                    imageView3.setImageBitmap(bitmap);
                } else if (counter == 4) {
                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getActivity().getContentResolver(),
                                    filePath4);
                    imageView4.setImageBitmap(bitmap);
                }

                counter++;
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    private void uploadImage(Uri fajl) {
        if (fajl != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Postavljenje novog oglasa");
            progressDialog.show();
            uuid = UUID.randomUUID().toString();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            ""
                                    + uuid + ".jpg");

            // adding listeners on upload
            // or failure of image
            ref.putFile(fajl)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getActivity(),
                                                    "Oglas je postavljen",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

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
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });

        }
    }

    private void uploadImageBitmap(Bitmap fajl) {
        if (fajl != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Postavljenje novog oglasa");
            progressDialog.show();
            uuid = UUID.randomUUID().toString();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("" + uuid + ".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            fajl.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            // adding listeners on upload
            // or failure of image
            ref.putBytes(baos.toByteArray(), new StorageMetadata.Builder().setContentType("image/jpg").build())
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getActivity(),
                                                    "Oglas je postavljen",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

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
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
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

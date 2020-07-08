package vukan.com.apursp.ui.new_ad_window;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import vukan.com.apursp.R;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;

import static android.app.Activity.RESULT_OK;

public class NewAdWindowFragment extends Fragment {
    private CheckBox fiksna;
    private RadioGroup radioValutaGroup;
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
    private static final int CAMERA_REQUEST_CODE = 123;
    private final int PICK_IMAGE_REQUEST = 22;
    private StorageReference storageReference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_ad_window, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        NewAdWindowViewModel newAdWindowViewModel = new ViewModelProvider(this).get(NewAdWindowViewModel.class);
        Button btn_choose = view.findViewById(R.id.btn_choose);
        Button btn_choosecam = view.findViewById(R.id.btn_choosecam);
        Button btn_add_new_product = view.findViewById(R.id.add_new_product);
        Button btn_delete = view.findViewById(R.id.btn_deletephoto);
        imageView = view.findViewById(R.id.myImage);
        imageView1 = view.findViewById(R.id.myImage1);
        imageView2 = view.findViewById(R.id.myImage2);
        imageView3 = view.findViewById(R.id.myImage3);
        imageView4 = view.findViewById(R.id.myImage4);
        naslov = view.findViewById(R.id.naslov);
        cena = view.findViewById(R.id.cena);
        opis = view.findViewById(R.id.opis);
        fiksna = view.findViewById(R.id.fiksna);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        radioValutaGroup = view.findViewById(R.id.radiogroup);
        int selectedId = radioValutaGroup.getCheckedRadioButtonId();
        radioCurrentButton = view.findViewById(selectedId);
        RadioButton radioDinButton = view.findViewById(R.id.dinari);
        RadioButton radioEurButton = view.findViewById(R.id.euri);
        radioDinButton.setOnClickListener(view1 -> radioCurrentButton = view.findViewById(radioValutaGroup.getCheckedRadioButtonId()));
        radioEurButton.setOnClickListener(view2 -> radioCurrentButton = view.findViewById(radioValutaGroup.getCheckedRadioButtonId()));

        btn_add_new_product.setOnClickListener(view3 -> {
            if (opis.getText().toString().trim().length() > 0 && cena.getText().toString().trim().length() > 0 && naslov.getText().toString().trim().length() > 0) {
                Product newProduct = new Product();

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
                newProduct.setProductID("temp");
                newProduct.setUserID(Objects.requireNonNull(fire_user).getUid());
                if (fiksna.isChecked()) newProduct.setFixPrice(true);
                else newProduct.setFixPrice(false);
                newProduct.setCurrency(radioCurrentButton.getText().toString());

                if (getArguments() != null) {
                    category = NovioglasprozorFragmentArgs.fromBundle(getArguments()).getId();
                    newProduct.setCategoryID(category + "");
                }

                productID = newAdWindowViewModel.addProduct(newProduct);
                ProductImage pi = new ProductImage();
                pi.setImageUrl(uuid);
                pi.setProductID(productID);
                newAdWindowViewModel.addProductImage(pi);

                if (counter == 2) {
                    if (filePath1 == null) uploadImageBitmap(bitmap1);
                    else uploadImage(filePath1);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                } else if (counter == 3) {
                    if (filePath1 == null) uploadImageBitmap(bitmap1);
                    else uploadImage(filePath1);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                    if (filePath2 == null) uploadImageBitmap(bitmap2);
                    else uploadImage(filePath2);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                } else if (counter == 4) {
                    if (filePath1 == null) uploadImageBitmap(bitmap1);
                    else uploadImage(filePath1);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                    if (filePath2 == null) uploadImageBitmap(bitmap2);
                    else uploadImage(filePath2);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                    if (filePath3 == null) uploadImageBitmap(bitmap3);
                    else uploadImage(filePath3);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                } else if (counter == 5) {
                    if (filePath1 == null) uploadImageBitmap(bitmap1);
                    else uploadImage(filePath1);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                    if (filePath2 == null) uploadImageBitmap(bitmap2);
                    else uploadImage(filePath2);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                    if (filePath3 == null) uploadImageBitmap(bitmap3);
                    else uploadImage(filePath3);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                    if (filePath4 == null) uploadImageBitmap(bitmap4);
                    else uploadImage(filePath4);
                    newAdWindowViewModel.addProductImage(addPI(uuid, productID));
                }

            } else Toast.makeText(getActivity(), R.string.upozorenje, Toast.LENGTH_SHORT).show();
        });

        btn_delete.setOnClickListener(view4 -> deleteImage());

        btn_choosecam.setOnClickListener(view5 -> {
            if (counter < 5) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, CAMERA_REQUEST_CODE);
            }
        });

        btn_choose.setOnClickListener(view6 -> chooseImage());
        requireActivity().setTitle(getString(R.string.app_name));
    }

    private ProductImage addPI(String uuid, String productID) {
        ProductImage pi = new ProductImage();
        pi.setImageUrl(uuid);
        pi.setProductID(productID);
        return pi;
    }

    private void deleteImage() {
        if (counter == 1) imageView.setImageBitmap(null);
        else if (counter == 2) imageView1.setImageBitmap(null);
        else if (counter == 3) imageView2.setImageBitmap(null);
        else if (counter == 4) imageView3.setImageBitmap(null);
        else if (counter == 5) imageView4.setImageBitmap(null);
        counter--;
        if (counter < 0) counter = 0;
    }

    private void chooseImage() {
        if (counter < 5) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.izaberite_sliku)), PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

            if (counter == 0) imageView.setImageBitmap(bitmap);
            else if (counter == 1) imageView1.setImageBitmap(bitmap);
            else if (counter == 2) imageView2.setImageBitmap(bitmap);
            else if (counter == 3) imageView3.setImageBitmap(bitmap);
            else if (counter == 4) imageView4.setImageBitmap(bitmap);
            if (counter < 5) counter++;
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (counter == 0) filePath = data.getData();
            else if (counter == 1) filePath1 = data.getData();
            else if (counter == 2) filePath2 = data.getData();
            else if (counter == 3) filePath3 = data.getData();
            else if (counter == 4) filePath4 = data.getData();

            try {
                if (counter == 0) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                } else if (counter == 1) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath1);
                    imageView1.setImageBitmap(bitmap);
                } else if (counter == 2) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath2);
                    imageView2.setImageBitmap(bitmap);
                } else if (counter == 3) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath3);
                    imageView3.setImageBitmap(bitmap);
                } else if (counter == 4) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath4);
                    imageView4.setImageBitmap(bitmap);
                }

                counter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(Uri fajl) {
        if (fajl != null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle(getString(R.string.oglas_naslov));
            progressDialog.show();
            uuid = UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("" + uuid + ".jpg");

            ref.putFile(fajl)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), R.string.oglas_postavljen, Toast.LENGTH_SHORT).show();
                            })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), getString(R.string.greska) + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(getString(R.string.otpremljeno) + (int) progress + "%");
                            });
        }
    }

    private void uploadImageBitmap(Bitmap fajl) {
        if (fajl != null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle(getString(R.string.oglas_naslov));
            progressDialog.show();
            uuid = UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("" + uuid + ".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            fajl.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            ref.putBytes(baos.toByteArray(), new StorageMetadata.Builder().setContentType("image/jpg").build())
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), getString(R.string.oglas_postavljen), Toast.LENGTH_SHORT).show();
                            })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), getString(R.string.greska) + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(getString(R.string.otpremljeno) + (int) progress + "%");
                            });
        }
    }
}
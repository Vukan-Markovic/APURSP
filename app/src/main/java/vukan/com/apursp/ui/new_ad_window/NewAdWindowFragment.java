package vukan.com.apursp.ui.new_ad_window;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import androidx.navigation.Navigation;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.firebase.Storage;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.ui.product.ProductViewModel;

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
    private Animation mAnimation;
    private String product_ID;
    private Uri filePath3;
    private Uri filePath4;
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Storage firebaseStorage;
    private Bitmap bitmap3;
    private Bitmap bitmap4;
    private String uuid;
    private int category;
    private String productID;
    private static final int CAMERA_REQUEST_CODE = 123;
    private final int PICK_IMAGE_REQUEST = 22;
    private StorageReference storageReference;
    private Product newProduct;
    private List<ProductImage> productImageList;
    private NewAdWindowViewModel newAdWindowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_ad_window, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.new_ad));
        firebaseStorage = new Storage();
        productImageList = new ArrayList<>();
        newProduct = new Product();
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        newAdWindowViewModel = new ViewModelProvider(this).get(NewAdWindowViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade);
        mAnimation.setDuration(150);
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

        if (getArguments() != null) {
            product_ID = NewAdWindowFragmentArgs.fromBundle(getArguments()).getProductId();

            if (!product_ID.equals("0")) {
                productViewModel.getProductDetails(product_ID).observe(getViewLifecycleOwner(), product -> {
                    naslov.setText(product.getName());
                    cena.setText(String.format("%s", product.getPrice().toString()));
                    opis.setText(product.getDescription());
                    fiksna.setChecked(product.getFixPrice());
                    newProduct.setHomePhotoUrl(product.getHomePhotoUrl());
                    category = Integer.parseInt(product.getCategoryID());

                    if (product.getCurrency().equals("Din"))
                        radioDinButton.setChecked(true);
                    else radioEurButton.setChecked(true);

                    productViewModel.getProductImages(product_ID).observe(getViewLifecycleOwner(), productImages -> {
                        counter = productImages.size();
                        productImageList.addAll(productImages);

                        for (int i = 0; i < productImages.size(); i++) {
                            if (i == 0)
                                GlideApp.with(imageView.getContext()).load(firebaseStorage.getProductImage(productImages.get(0).getImageUrl())).into(imageView);
                            else if (i == 1)
                                GlideApp.with(imageView1.getContext()).load(firebaseStorage.getProductImage(productImages.get(1).getImageUrl())).into(imageView1);
                            else if (i == 2)
                                GlideApp.with(imageView2.getContext()).load(firebaseStorage.getProductImage(productImages.get(2).getImageUrl())).into(imageView2);
                            else if (i == 3)
                                GlideApp.with(imageView3.getContext()).load(firebaseStorage.getProductImage(productImages.get(3).getImageUrl())).into(imageView3);
                            else
                                GlideApp.with(imageView4.getContext()).load(firebaseStorage.getProductImage(productImages.get(4).getImageUrl())).into(imageView4);
                        }
                    });
                });
            }
        }

        btn_add_new_product.setOnClickListener(view3 -> {
            if (opis.getText().toString().trim().length() > 0 && cena.getText().toString().trim().length() > 0 && naslov.getText().toString().trim().length() > 0) {
                view3.startAnimation(mAnimation);

                if (!product_ID.equals("0"))
                    Toast.makeText(getActivity(), R.string.azuriranje_proizvoda, Toast.LENGTH_SHORT).show();

                if (counter > 0) {
                    if (filePath == null) uploadImageBitmap(bitmap);
                    else uploadImage(filePath);
                    if (bitmap != null || filePath != null) newProduct.setHomePhotoUrl(uuid);
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
                    category = NewAdWindowFragmentArgs.fromBundle(getArguments()).getId();
                    if (category != 0)
                        newProduct.setCategoryID(category + "");
                }

                productID = newAdWindowViewModel.addProduct(newProduct, product_ID);
                ProductImage pi = new ProductImage();
                pi.setImageUrl(uuid);
                pi.setProductID(productID);
                newAdWindowViewModel.addProductImage(pi);
                productImageList.add(pi);

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

                Navigation.findNavController(view3).navigate(NewAdWindowFragmentDirections.novioglasprozorToPocetnaFragmentAction());
            } else Toast.makeText(getActivity(), R.string.upozorenje, Toast.LENGTH_SHORT).show();
        });

        btn_delete.setOnClickListener(view4 -> {
            view4.startAnimation(mAnimation);
            deleteImage();
        });

        btn_choosecam.setOnClickListener(view5 -> {
            if (counter < 5) {
                view5.startAnimation(mAnimation);
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, CAMERA_REQUEST_CODE, ActivityOptions.makeCustomAnimation(
                        requireContext(),
                        R.anim.fade_in,
                        R.anim.fade_out
                ).toBundle());
            }
        });

        btn_choose.setOnClickListener(view6 -> {
            if (counter < 5) {
                view6.startAnimation(mAnimation);
                chooseImage();
            }
        });
    }

    private ProductImage addPI(String uuid, String productID) {
        ProductImage pi = new ProductImage();
        pi.setImageUrl(uuid);
        pi.setProductID(productID);
        productImageList.add(pi);
        return pi;
    }

    private void deleteImage() {
        if (counter - 1 < productImageList.size()) {
            newAdWindowViewModel.deleteProductImage(productImageList.get(counter - 1).getImageUrl());
            productImageList.remove(counter - 1);
        }

        if (counter == 1) imageView.setImageBitmap(null);
        else if (counter == 2) imageView1.setImageBitmap(null);
        else if (counter == 3) imageView2.setImageBitmap(null);
        else if (counter == 4) imageView3.setImageBitmap(null);
        else if (counter == 5) imageView4.setImageBitmap(null);
        counter--;
        if (counter < 0) counter = 0;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.izaberite_sliku)), PICK_IMAGE_REQUEST, ActivityOptions.makeCustomAnimation(
                requireContext(),
                R.anim.fade_in,
                R.anim.fade_out
        ).toBundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
            Bitmap slika = (Bitmap) data.getExtras().get("data");

            if (counter == 0) {
                filePath = null;
                bitmap = slika;
                GlideApp.with(imageView.getContext()).load(bitmap).into(imageView);
            } else if (counter == 1) {
                filePath1 = null;
                bitmap1 = slika;
                GlideApp.with(imageView1.getContext()).load(bitmap1).into(imageView1);
            } else if (counter == 2) {
                filePath2 = null;
                bitmap2 = slika;
                GlideApp.with(imageView2.getContext()).load(bitmap2).into(imageView2);
            } else if (counter == 3) {
                filePath3 = null;
                bitmap3 = slika;
                GlideApp.with(imageView3.getContext()).load(bitmap3).into(imageView3);
            } else if (counter == 4) {
                filePath4 = null;
                bitmap4 = slika;
                GlideApp.with(imageView4.getContext()).load(bitmap4).into(imageView4);
            }

            if (counter < 5) counter++;
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                if (counter == 0) {
                    filePath = data.getData();
                    GlideApp.with(imageView.getContext()).load(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath)).into(imageView);
                } else if (counter == 1) {
                    filePath1 = data.getData();
                    GlideApp.with(imageView1.getContext()).load(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath1)).into(imageView1);
                } else if (counter == 2) {
                    filePath2 = data.getData();
                    GlideApp.with(imageView2.getContext()).load(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath2)).into(imageView2);
                } else if (counter == 3) {
                    filePath3 = data.getData();
                    GlideApp.with(imageView3.getContext()).load(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath3)).into(imageView3);
                } else if (counter == 4) {
                    filePath4 = data.getData();
                    GlideApp.with(imageView4.getContext()).load(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath4)).into(imageView4);
                }

                if (counter < 5) counter++;
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

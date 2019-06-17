package net.hamba.android.Fragments;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import net.hamba.android.Adapters.CategoryRecyclerViewAdapter;
import net.hamba.android.Adapters.MallSpinnerAdapter;
import net.hamba.android.Animations.Animations;
import net.hamba.android.Customes.MultiSelectionSpinner;
import net.hamba.android.Customes.PresetRadioGroup;
import net.hamba.android.Instagram.CategoryModel;
import net.hamba.android.Models.MallModel;
import net.hamba.android.Models.StoreModel;
import net.hamba.android.R;
import net.hamba.android.Utils.FormUtils;
import net.hamba.android.Utils.PrefsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CreateStoreStepOneFragment extends Fragment implements View.OnClickListener, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener, AdapterView.OnItemSelectedListener, PresetRadioGroup.OnCheckedChangeListener {

    private final int PICK_IMAGE_REQUEST = 751;


    private RecyclerView recyclerView;
    private ArrayList<CategoryModel> data;
    private CategoryRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ImageView next;
    private ImageView previous;
    MapView mMapView,mMapView2;
    public GoogleMap googleMap,googleMap2;
    Context mContext;
    EditText mSearchEdittext;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    int AUTOCOMPLETE_REQUEST_CODE_2 = 2;
    private ImageView arrow1;
    private boolean is1up = false;
    private LinearLayout business_owner_head,business_owner_body,btn_done;
    private Animations animation;
    ValueAnimator slideAnimator =null;
    private LinearLayout poly;
    ArrayList<LatLng> latLngArrayList;
    private Spinner spn_mall;
    private MallSpinnerAdapter mallSpinnerAdapter;
    private ArrayList<MallModel> list;
    private MultiSelectionSpinner spinner;
    private PresetRadioGroup part_mall_group;
    private LinearLayout form;
    private TextView err_cat,err_image,err_malls,err_range;
    private LinearLayout select_image;
    private Uri filePath;
    private boolean isPartofMall = true;
    private boolean isSmartPricing = true;
    boolean isRangeSelected = false;
    private RadioButton k_1,k_5,k_10,k_100,k_500,k_1000;
    private ImageView imageView;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private EditText ET_storename,ET_description,ET_country,ET_phone,ET_email;
    private PresetRadioGroup store_types,smart_pricing_group,limit_no_limit;
    private String storeType= "Product";
    private String storeDeliveryRage = "";
    private String random = "";
    StoreModel storeModel = null;
    private RelativeLayout RL_poly;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setRetainInstance(true);

        View rootView = inflater.inflate(R.layout.create_store_step_one_fragment, parent, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView2 = (MapView) rootView.findViewById(R.id.mapView2);
        mMapView.onCreate(savedInstanceState);
        mMapView2.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        mMapView2.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;

            }
        });
        mMapView2.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap2 = googleMap;

            }
        });

        mContext = getContext();

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitUI(view);
        ET_storename.requestFocus();
        //PrefsUtils.SaveStore(null);
        setdata();

    }
    public void InitUI(View v){
        recyclerView        = v.findViewById(R.id.recycler_view);
        next                = v.findViewById(R.id.next);
        previous            = v.findViewById(R.id.previous);
        btn_done            = v.findViewById(R.id.btn_done);
        poly                = v.findViewById(R.id.poly);
        mSearchEdittext     = v.findViewById(R.id.search_et);
        arrow1              = v.findViewById(R.id.arrow1);
        business_owner_head = v.findViewById(R.id.business_owner_head);
        business_owner_body = v.findViewById(R.id.business_owner_body);
        spn_mall            = v.findViewById(R.id.spn_mall);
        spinner             = v.findViewById(R.id.malls);
        part_mall_group     = v.findViewById(R.id.part_mall_group);
        form                = v.findViewById(R.id.form);
        err_cat             = v.findViewById(R.id.err_cat);
        err_image           = v.findViewById(R.id.err_image);
        err_malls           = v.findViewById(R.id.err_malls);
        err_range           = v.findViewById(R.id.err_range);
        select_image        = v.findViewById(R.id.select_image);
        imageView           = v.findViewById(R.id.imageView);
        ET_description      = v.findViewById(R.id.ET_description);
        ET_country          = v.findViewById(R.id.ET_country);
        store_types         = v.findViewById(R.id.store_types);
        ET_phone            = v.findViewById(R.id.ET_phone);
        ET_email            = v.findViewById(R.id.ET_email);
        k_1                 = v.findViewById(R.id.k_1);
        k_5                 = v.findViewById(R.id.k_5);
        k_10                = v.findViewById(R.id.k_10);
        k_100               = v.findViewById(R.id.k_100);
        k_500               = v.findViewById(R.id.k_500);
        k_1000              = v.findViewById(R.id.k_1000);
        ET_storename        = v.findViewById(R.id.ET_storename);
        smart_pricing_group = v.findViewById(R.id.smart_pricing_group);
        limit_no_limit      = v.findViewById(R.id.limit_no_limit);
        RL_poly             = v.findViewById(R.id.RL_poly);
        part_mall_group.setOnCheckedChangeListener(this);
        limit_no_limit.setOnCheckedChangeListener(this);
        List<String> list1 = new ArrayList<String>();
        list1.add("Mall 1");
        list1.add("Mall 2");
        list1.add("Mall 3");
        list1.add("Mall 4");
        list1.add("Mall 5");
        list1.add("Mall 6");
        spinner.setItems(list1);
        list                = new ArrayList<>();
        prepareMall();
        mallSpinnerAdapter  = new MallSpinnerAdapter(getContext(),list,getLayoutInflater(),R.id.spinner_item);
        spn_mall.setOnItemSelectedListener(this);
        spn_mall.setAdapter(mallSpinnerAdapter);


        latLngArrayList     = new ArrayList<>();
        animation = new Animations();
        business_owner_head.setOnClickListener(this);

        layoutManager   =  new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        data               = new ArrayList<>();
        data.add(new CategoryModel("Food",false));
        data.add(new CategoryModel("Grocery",false));
        data.add(new CategoryModel("Pharmacy",false));
        data.add(new CategoryModel("Beauty",false));
        data.add(new CategoryModel("Health",false));
        data.add(new CategoryModel("Fashion",false));
        data.add(new CategoryModel("Accessories",false));
        data.add(new CategoryModel("Electronics",false));
        adapter            = new CategoryRecyclerViewAdapter(data, getContext(), R.layout.category_layout);
        recyclerView.setAdapter(adapter);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        mSearchEdittext.setOnClickListener(this);
        poly.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        select_image.setOnClickListener(this);
        k_1.setOnClickListener(this);
        k_5.setOnClickListener(this);
        k_10.setOnClickListener(this);
        k_100.setOnClickListener(this);
        k_500.setOnClickListener(this);
        k_1000.setOnClickListener(this);
        store_types.setOnCheckedChangeListener(this);
        smart_pricing_group.setOnCheckedChangeListener(this);
        auth                = FirebaseAuth.getInstance();
        storage             = FirebaseStorage.getInstance();
        database            = FirebaseDatabase.getInstance();
        storageReference    = storage.getReference();
        animation   = new Animations();

        // InitGoogleMaps(v);


    }
    public void createStore(){
        StoreModel model = null;
        if(storeModel!=null){
            model = storeModel;
        }else{
            model =new StoreModel();
        }
        model.setStoreName(FormUtils.getText(ET_storename));
        model.setStoreType(storeType);
        model.setStoreCategories(adapter.getSelected_cat());
        model.setStoreDescription(FormUtils.getText(ET_description));
        model.setStoreCountry(FormUtils.getText(ET_country));
        model.setStoreAddress(FormUtils.getText(mSearchEdittext));
        model.setStorePhone(FormUtils.getText(ET_phone));
        model.setStoreEmail(FormUtils.getText(ET_email));
        model.setStoreIsInMall(isPartofMall);
        model.setStoreMalls(spinner.getSelectedItemsAsString());
        model.setSmartPricing(isSmartPricing);
        model.setStoreDeliveryRange(storeDeliveryRage);
        model.setStoreImage(filePath.toString());
        PrefsUtils.SaveStore(new Gson().toJson(model));
        // uploadImage(model);
        CreateStoreMainFragment.stepOneComplete(getFragmentManager());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                Log.e( "onClick: ","next "+layoutManager.findLastVisibleItemPosition());
                if(layoutManager.findLastVisibleItemPosition()!= data.size())
                    recyclerView.scrollToPosition(layoutManager.findLastVisibleItemPosition()+1);
                break;
            case R.id.previous:
                Log.e( "onClick: ","prev "+layoutManager.findFirstVisibleItemPosition());
                if(layoutManager.findFirstVisibleItemPosition()!= 0)
                    recyclerView.scrollToPosition(layoutManager.findFirstVisibleItemPosition()-1);
                break;
            case R.id.search_et:
                ShowAutoComplete(AUTOCOMPLETE_REQUEST_CODE);
                break;
            case R.id.business_owner_head:
                if(!is1up){
                    animation.CollapsMenuAnimation(is1up,getContext(),business_owner_body,arrow1);
                    is1up = !is1up;

                }else{
                    animation.CollapsMenuAnimation(is1up,getContext(),business_owner_body,arrow1);
                    is1up = !is1up;

                }
//                if (!is1up) {
//                    is1up = true;
//                    arrow1.startAnimation(animate(is1up));
//                    slideAnimator = ValueAnimator
//                            .ofInt(160,0 )
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            business_owner_body.getLayoutParams().height = value.intValue();
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            business_owner_body.requestLayout();
//
//
//
//                        }
//                    });
//
//                } else {
//                    is1up = false;
//                    arrow1.startAnimation(animate(is1up));
//                    // animation.SlideInFromTop(getApplicationContext(),business_owner_body);
//                    slideAnimator = ValueAnimator
//                            .ofInt(0,160 )
//                            .setDuration(300);
//                    slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            // get the value the interpolator is at
//                            Integer value = (Integer) animation.getAnimatedValue();
//                            // I'm going to set the layout's height 1:1 to the tick
//                            business_owner_body.getLayoutParams().height = value.intValue();
//                            Log.e( "onClick: ",""+is1up);
//
//                            // force all layouts to see which ones are affected by
//                            // this layouts height change
//                            business_owner_body.requestLayout();
//                            business_owner_body.setVisibility(View.VISIBLE);
//
//                        }
//                    });
//
//
//                }
//                // create a new animationset
//                AnimatorSet set = new AnimatorSet();
//// since this is the only animation we are going to run we just use
//// play
//                set.play(slideAnimator);
//// this is how you set the parabola which controls acceleration
//                set.setInterpolator(new AccelerateDecelerateInterpolator());
//// start the animation
//                set.start();
                break;
            case R.id.poly:
                ShowAutoComplete(AUTOCOMPLETE_REQUEST_CODE_2);
                break;
            case R.id.btn_done:
                boolean isvalid = true;

                FormUtils.isValidTextInputParent(form);
                if(adapter.getSelected_cat().isEmpty()){
                    err_cat.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);

                }else {
                    err_cat.setVisibility(View.GONE);
                    isvalid = (isvalid && true);

                }
                if(select_image.getTag() == null){
                    select_image.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));
                    err_image.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);

                }else{
                    select_image.setBackground(getResources().getDrawable(R.drawable.white_rounded_border_bg));
                    err_image.setVisibility(View.GONE);
                    isvalid = (isvalid && true);
                }
                if(isPartofMall && spinner.getSelectedItemsAsString().isEmpty()){
                    spinner.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));
                    err_malls.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);
                }else{
                    spinner.setBackground(getResources().getDrawable(R.drawable.white_rounded_border_bg));
                    err_malls.setVisibility(View.GONE);
                    isvalid = (isvalid && true);

                }
                if(storeDeliveryRage.isEmpty()){
                    business_owner_head.setBackground(getResources().getDrawable(R.drawable.edit_text_focus_out_red));
                    err_range.setVisibility(View.VISIBLE);
                    isvalid = (isvalid && false);

                }else{
                    business_owner_head.setBackground(getResources().getDrawable(R.drawable.white_rounded_border_bg));
                    err_range.setVisibility(View.GONE);
                    isvalid = (isvalid && true);
                }
                if(isvalid){
                    createStore();


                }

                break;
            case R.id.select_image:
                chooseImage();
                break;
            case R.id.k_1:
                k_1.setChecked(true);
                k_5.setChecked(false);
                k_10.setChecked(false);
                k_100.setChecked(false);
                k_500.setChecked(false);
                k_1000.setChecked(false);
                isRangeSelected =true;
                storeDeliveryRage = "1Km";
                break;
            case R.id.k_5:
                k_1.setChecked(false);
                k_5.setChecked(true);
                k_10.setChecked(false);
                k_100.setChecked(false);
                k_500.setChecked(false);
                k_1000.setChecked(false);
                isRangeSelected =true;
                storeDeliveryRage = "5Km";
                break;
            case R.id.k_10:
                k_1.setChecked(false);
                k_5.setChecked(false);
                k_10.setChecked(true);
                k_100.setChecked(false);
                k_500.setChecked(false);
                k_1000.setChecked(false);
                isRangeSelected =true;
                storeDeliveryRage = "10Km";
                break;
            case R.id.k_100:
                k_1.setChecked(false);
                k_5.setChecked(false);
                k_10.setChecked(false);
                k_100.setChecked(true);
                k_500.setChecked(false);
                k_1000.setChecked(false);
                isRangeSelected =true;
                storeDeliveryRage = "100Km";
                break;
            case R.id.k_500:
                k_1.setChecked(false);
                k_5.setChecked(false);
                k_10.setChecked(false);
                k_100.setChecked(false);
                k_500.setChecked(true);
                k_1000.setChecked(false);
                isRangeSelected =true;
                storeDeliveryRage = "500Km";
                break;
            case R.id.k_1000:
                k_1.setChecked(false);
                k_5.setChecked(false);
                k_10.setChecked(false);
                k_100.setChecked(false);
                k_500.setChecked(false);
                k_1000.setChecked(true);
                isRangeSelected =true;
                storeDeliveryRage = "1000Km";
                break;

        }
    }


    public void ShowAutoComplete(int code){
        // Initialize the AutocompleteSupportFragment.
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(getActivity());
            startActivityForResult(intent,code );
        } catch (Exception e) {
            Log.e("tag", e.getStackTrace().toString());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AUTOCOMPLETE_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                googleMap.clear();

                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                LatLng coordinates = place.getLatLng(); // Get the coordinates from your place
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

                List<Address> addresses = null; // Only retrieve 1 address
                try {
                    addresses = geocoder.getFromLocation(
                            coordinates.latitude,
                            coordinates.longitude,
                            1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addresses.get(0);
                Log.e("onActivityResult: ",address.getCountryName() );
                mSearchEdittext.setText(place.getAddress());
                ET_country.setText(address.getCountryName());
                LatLng sydney = place.getLatLng();
                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title(place.getName().toString()));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(sydney)
                        .zoom(17).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
        if(requestCode == AUTOCOMPLETE_REQUEST_CODE_2){
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                LatLng sydney = place.getLatLng();
                googleMap2.addMarker(new MarkerOptions().position(sydney)
                        .title(place.getName().toString()));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(sydney)
                        .zoom(17).build();
                googleMap2.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                setpoly(sydney);
            }
        }
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                //  imageView.setImageBitmap(bitmap);
                select_image.setTag("done");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }
    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(getContext(), up ? R.anim.rotate_up_anim : R.anim.rotate_down_anim);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }
    public void setpoly(LatLng latLng){
        latLngArrayList.add(latLng);
        PolygonOptions polygonOptions = new PolygonOptions()
                .clickable(true);
        for (LatLng latLng1:latLngArrayList)
        {
            polygonOptions.add(latLng1);
        }
        polygonOptions.strokeColor(0xffdc4638);
        polygonOptions.fillColor(0x55dc4638);
        polygonOptions.strokeWidth(2);
        if(latLngArrayList.size()>=3) {

            Polygon polygon1 = googleMap2.addPolygon(polygonOptions);
// Store a data object with the polygon, used here to indicate an arbitrary type.
            polygon1.setTag("alpha");


            // Position the map's camera near Alice Springs in the center of Australia,
            // and set the zoom factor so most of Australia shows on the screen.
            googleMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

            // Set listeners for click events.
            googleMap2.setOnPolylineClickListener(this);
            googleMap2.setOnPolygonClickListener(this);
        }

    }
    public void prepareMall(){
        for(int i = 0; i<10;i++){
            MallModel mallModel = new MallModel();
            mallModel.setSelected(false);
            mallModel.setName("Mall "+i);
            list.add(mallModel);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView  textView = view.findViewById(R.id.text);
        textView.setText("5 malls Selected");


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
        switch (checkedId){
            case R.id.mall_no:
                isPartofMall = false;
                spinner.setVisibility(View.GONE);
                err_malls.setVisibility(View.GONE);
                break;
            case R.id.mall_yes:
                isPartofMall = true;
                spinner.setVisibility(View.VISIBLE);
                break;
            case R.id.product:
                storeType = "Product";
                break;
            case R.id.service:
                storeType = "Service";
                break;
            case R.id.delivery:
                storeType = "Delivery";
                break;
            case R.id.wholesale:
                storeType = "Wholesale";
                break;
            case R.id.smart_pricing_yes:
                isSmartPricing = true;
                break;
            case R.id.smart_pricing_no:
                isSmartPricing = false;
                break;
            case R.id.no_limit:
                RL_poly.setVisibility(View.GONE);
                break;
            case R.id.poly_only:
                RL_poly.setVisibility(View.VISIBLE);
                break;

        }

    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void uploadImage(StoreModel model) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Creating Store...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            model.setStoreImage(ref.getDownloadUrl().toString());
                            database.getReference("new_stores").child(UUID.randomUUID().toString()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    CreateStoreMainFragment.stepOneComplete(getFragmentManager());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            // progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }
    private void setdata(){
        String json =  PrefsUtils.getStore();
        if(json != null){
             storeModel = new Gson().fromJson(json,StoreModel.class);
            ET_storename.setText(storeModel.getStoreName());
            if(storeModel.getStoreType().equals("Product")){
                store_types.check(R.id.product);

            }else if(storeModel.getStoreType().equals("Service")){
                store_types.check(R.id.service);

            }else if(storeModel.getStoreType().equals("Delivery")){
                store_types.check(R.id.delivery);

            }else {
                store_types.check(R.id.wholesale);

            }
            int i = 0;
            for (CategoryModel item:data) {
                if(storeModel.getStoreCategories().contains(item.getCate_name())){
                    Log.e( "setdata: ", data.get(i).getCate_name());
                    data.get(i).setSelected(true);
                }
                i++;
            }
            adapter.notifyDataSetChanged();
            ET_description.setText(storeModel.getStoreDescription());
            ET_country.setText(storeModel.getStoreCountry());
            ET_email.setText(storeModel.getStoreEmail());
            ET_phone.setText(storeModel.getStorePhone());
            mSearchEdittext.setText(storeModel.getStoreAddress());
            if(storeModel.isStoreIsInMall()){
                part_mall_group.check(R.id.mall_yes);
                spinner.setSelection(storeModel.getStoreMalls().split(","));


            }else{
                part_mall_group.check(R.id.mall_no);

            }
            if(storeModel.isSmartPricing){
                smart_pricing_group.check(R.id.smart_pricing_yes);

            }else{
                smart_pricing_group.check(R.id.smart_pricing_no);
            }
            if(!storeModel.getStoreImage().isEmpty()){
                filePath = Uri.parse(storeModel.getStoreImage());
                select_image.setTag("done");
            }
            storeDeliveryRage = storeModel.getStoreDeliveryRange();
            if(storeModel.getStoreDeliveryRange().equals("1Km")){
                k_1.setChecked(true);

            }else if(storeModel.getStoreDeliveryRange().equals("5Km")){
                k_5.setChecked(true);

            }else if(storeModel.getStoreDeliveryRange().equals("10Km")){
                k_10.setChecked(true);

            }else if(storeModel.getStoreDeliveryRange().equals("100Km")){
                k_100.setChecked(true);

            }else if(storeModel.getStoreDeliveryRange().equals("500Km")){
                k_500.setChecked(true);

            }else{
                k_1000.setChecked(true);

            }



        }
    }

}



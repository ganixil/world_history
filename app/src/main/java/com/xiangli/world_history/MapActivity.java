package com.xiangli.world_history;

import android.app.Activity;

import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.davemorrissey.labs.subscaleview.decoder.DecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.ImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.ImageRegionDecoder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import helper.PicassoDecoder;
import helper.PicassoRegionDecoder;
import okhttp3.OkHttpClient;

import static java.security.AccessController.getContext;

/**
 * Created by xiangli on 3/4/18.
 */

public class MapActivity extends Activity {
    SubsamplingScaleImageView map;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    String[] eraString;
    ArrayAdapter<String> adapter;
    Spinner spn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);



        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        spn = findViewById(R.id.year);
        eraString = new String[]{"six civilizations","bronze age","middle age"};
        adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,eraString);
        spn.setAdapter(adapter);

        map =  findViewById(R.id.map);
        //map.setImage(ImageSource.resource(R.drawable.six_civil));

        StorageReference mapRef = mStorage.child("maps/six_civil.png");
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getItemAtPosition(position).toString()){
                    case "six civilizations":{
                        StorageReference imageRef = mStorage.child("maps/six_civil.png");
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                loadImagebyUrl(map,uri.toString(),new OkHttpClient());

                            }
                        });
                    }
                    case "bronze age": {
                        StorageReference imageRef = mStorage.child("maps/bronze.png");
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                loadImagebyUrl(map,uri.toString(),new OkHttpClient());
                            }
                        });
                    }
                    case "middle age": {
                        StorageReference imageRef = mStorage.child("maps/middle.png");
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                loadImagebyUrl(map,uri.toString(),new OkHttpClient());
                            }
                        });
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       /* mDatabase.child("year").child("four_civil").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("msg",dataSnapshot.getValue().toString());
                        String x = dataSnapshot.getValue().toString();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );*/






        //txt.setText(mDatabase.child("year").child("four_civil").getKey());


    }
    public void loadImagebyUrl(SubsamplingScaleImageView scaleImageView,final String url, final OkHttpClient okHttpClient) {
        scaleImageView.setMaxScale(5.0f);
        final Picasso picasso = Picasso.get();

        scaleImageView.setBitmapDecoderFactory(new DecoderFactory<ImageDecoder>() {
            @Override
            public ImageDecoder make() throws IllegalAccessException, java.lang.InstantiationException {

                return new PicassoDecoder(url, picasso);
            }
        });

        scaleImageView.setRegionDecoderFactory(new DecoderFactory<ImageRegionDecoder>() {
            @Override
            public ImageRegionDecoder make() throws IllegalAccessException, java.lang.InstantiationException {
                return new PicassoRegionDecoder(okHttpClient);
            }
        });
        //do some event
        scaleImageView.setImage(ImageSource.uri(url));
    }

}

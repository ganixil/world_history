package com.xiangli.world_history;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;
import android.widget.Spinner;



import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by xiangli on 3/4/18.
 */

public class MapActivity extends Activity {
    SubsamplingScaleImageView map;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        map = (SubsamplingScaleImageView) findViewById(R.id.map);
        map.setImage(ImageSource.resource(R.drawable.four_civil));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("year").child("four_civil").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("msg",dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        Spinner sv = (Spinner) findViewById(R.id.year);

        //txt.setText(mDatabase.child("year").child("four_civil").getKey());


    }

}

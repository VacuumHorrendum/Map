package com.example.mymap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {

    UserLocationLayer userLocationLayer;
    MapView mapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        MapKitFactory.setApiKey("0c1225a5-bb7e-477f-8635-3bb0b21f1fb1");
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_main);

        mapview = findViewById(R.id.mapview);
        mapview.getMap().setRotateGesturesEnabled(false);
        mapview.getMap().move(
                new CameraPosition(new Point(58.038518, 56.029757), 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        mapview.getMap().getMapObjects().addPlacemark(new Point(58.038518, 56.029757), ImageProvider.fromResource(MainActivity.this, R.drawable.icon_user));
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapview.getMapWindow());
        onLocation();
    }

    @Override
    protected void onStop(){
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart(){
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }

    public void onLocation(){



        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);

        userLocationLayer.setAutoZoomEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView){
        userLocationLayer.setAnchor(
                new PointF((float) (mapview.getWidth()*0.5), (float) (mapview.getHeight()*0.5)),
                new PointF((float) (mapview.getWidth()*0.5), (float) (mapview.getHeight()*0.5)));
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(this, R.drawable.icon_map));
        userLocationView.getPin().setIcon(ImageProvider.fromResource(this, R.drawable.icon_map));

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView){

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent){

    }
}
package fr.maaf.waterproof;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AccueilFragment extends Fragment implements LocationListener, AdapterView.OnItemSelectedListener {

    private static View view;
    /**
     * Note that this may be null if the Google Play services APK is not
     * available.
     */

    private static GoogleMap mMap;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static FragmentManager fragmentManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = (RelativeLayout) inflater.inflate(
                R.layout.fragment_accueil, container, false);
        // Passing harcoded values for latitude & longitude. Please change as
        // per your need. This is just used to drop a Marker on the Map

        setUpMapIfNeeded(); // For setting up the MapFragment

        Spinner map_type = (Spinner) view.findViewById(R.id.spinner);
        map_type.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.map_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        map_type.setAdapter(adapter);

        final ImageView addPoint = (ImageView) view.findViewById(R.id.addPoint);
        addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint();
            }
        });

        View dialogView = (View) inflater.inflate(R.layout.add_point_dialog, null);

        final ImageView addPicture = (ImageView) dialogView.findViewById(R.id.camera);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPicture();
            }
        });


        return view;
    }

    /**
     * ** Sets up the map if it is possible to do so ****
     */
    public static void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the
        // map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.location_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        addMarkes();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.location_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    /**
     * *
     * The mapfragment's id must be removed from the FragmentManager or else if
     * the same it is passed on the next time then app will crash
     * **
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            MainActivity.fragmentManager
                    .beginTransaction()
                    .remove(MainActivity.fragmentManager
                            .findFragmentById(R.id.location_map)).commit();
            mMap = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }
        String msg = String.format(
                getResources().getString(R.string.provider_disabled),
                newStatus);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        String msg = String.format(
                getResources().getString(R.string.provider_disabled), provider);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {
        switch ((int) id) {
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    /**
     * Permet de générer des points sur la carte
     */
    public static void addMarkes() {
        ArrayList<ArrayList> listMarker = new ArrayList<ArrayList>();
        ArrayList<Object> marker1 = new ArrayList<Object>();
        marker1.add("Titre1");
        marker1.add("Description1");
        marker1.add(48.926003);
        marker1.add(2.189145);
        listMarker.add(marker1);

        ArrayList<Object> marker2 = new ArrayList<Object>();
        marker2.add("Titre2");
        marker2.add("Description2");
        marker2.add(48.9259709);
        marker2.add(2.1153493);
        listMarker.add(marker2);


        for (ArrayList markerFromList : listMarker) {
            String title = (String) markerFromList.get(0);
            String snippet = (String) markerFromList.get(1);
            LatLng loc = new LatLng((Double) markerFromList.get(2), (Double) markerFromList.get(3));
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.snippet(snippet).title(title).position(loc);
            mMap.addMarker(markerOption);
        }

    }

    /**
     * Methode appelée lors d'un click sur le bouton Rouge
     */
    public void addPoint() {

        DialogFagment dialog = DialogFagment.newInstance();
        dialog.show(MainActivity.fragmentManager, "dialog");



//

    }

    public void addPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
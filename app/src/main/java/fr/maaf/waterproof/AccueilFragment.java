package fr.maaf.waterproof;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class AccueilFragment extends Fragment implements LocationListener, AdapterView.OnItemSelectedListener {

    private static View view;
    /**
     * Note that this may be null if the Google Play services APK is not
     * available.
     */

    private static GoogleMap mMap;
    private static Double latitude, longitude;

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
        latitude = 48.8894274;
        longitude = 2.2484253;

        setUpMapIfNeeded(); // For setting up the MapFragment


        view.findViewById(R.id.spinner);

        Spinner map_type = (Spinner) view.findViewById(R.id.spinner);
        map_type.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.map_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        map_type.setAdapter(adapter);

        return view;
    }

    /***** Sets up the map if it is possible to do so *****/
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
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)).title("My Home")
                .snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                latitude, longitude), 15.0f));
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

    /****
     * The mapfragment's id must be removed from the FragmentManager or else if
     * the same it is passed on the next time then app will crash
     ****/
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
        switch((int)id)
        {
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
        };
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }
	
}



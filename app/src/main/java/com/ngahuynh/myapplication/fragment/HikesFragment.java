package com.ngahuynh.myapplication.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.Hike;
import com.ngahuynh.myapplication.viewmodel.DetailViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class HikesFragment extends Fragment implements View.OnClickListener {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleMap map;
    double currentLat = 0, currentLong = 0;

    private DetailViewModel hikingVM;

    public HikesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_hikes, container, false);

        // Return view
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("TAG", "OnView");
        super.onViewCreated(view, savedInstanceState);
        hikingVM = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        hikingVM.getHikeData().observe(getViewLifecycleOwner(), hikeObserver );
//        TextView title = view.findViewById(R.id.tv_bar_title);
//        if (title != null) {
//            title.setText("Hikes");
//        }


        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);


        //Async Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                } else {
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

                map = googleMap;
                Log.i("TAG", "MAP Created");
                map.setMyLocationEnabled(true);

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i("TAG", "MAP SUCCESSES");
                        if (location != null) {
                            currentLocation = location;
                            currentLat = location.getLatitude();
                            currentLong = location.getLongitude();
                            Log.i("TAG", "MAP SUCCESS");
                            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?keyword=hikes" +
                                    "&location=" + currentLat + "," + currentLong + "&radius=5000" + "&sensor=true" + "&key=" +
                                    getResources().getString(R.string.google_maps_api_key);


                            //Execute place task method to download json data
                            new PlaceTask().execute(url);
                            //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));

                //When map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

//                        //When clicked on map
//                        //Initialize marker options
//                        MarkerOptions markerOptions = new MarkerOptions();
//
//                        //Set position of market
//                        markerOptions.position(latLng);
//
//                        //Set title of marker
//                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//                        //Remove all marker
//                        googleMap.clear();
//
//                        //Animating to zoom the marker
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                latLng, 5
//                        ));
//
//                        //Add marker on map
//                        googleMap.addMarker(markerOptions);


                    }
                });
            }
        });



    }

    final Observer<Hike> hikeObserver = new Observer<Hike>() {
        @Override
        public void onChanged(@Nullable final Hike appHike) {
            Log.i("TAG", "onChanged");
            List<HashMap<String, String>> hashMaps = appHike.getMapList();
            if(map != null) {
                map.clear();
                for (int i = 0; i < hashMaps.size(); i++) {
                    //Initialize hash map
                    HashMap<String, String> hashMapList = hashMaps.get(i);

                    //Get latitude
                    double lat = Double.parseDouble(hashMapList.get("lat"));

                    //Get longitude
                    double lng = Double.parseDouble(hashMapList.get("lng"));

                    //Get name
                    String name = hashMapList.get("name");

                    LatLng latLng = new LatLng(lat, lng);
                    MarkerOptions options = new MarkerOptions();
                    options.position(latLng);
                    options.title(name);
                    map.addMarker(options);
                    Log.i("TAG", "PASSES");
                }
            }
        }
    };

    @Override
    public void onClick(View view) {

    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;

            try {
                //Initialize data
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        //Initialize url
        URL url = new URL(string);

        //Initialize connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //Connect connection
        connection.connect();

        //Initialize input stream
        InputStream stream = connection.getInputStream();

        //Initialize buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        //Initialize string builder
        StringBuilder builder = new StringBuilder();

        //Initialize string variable

        String line = "";
        while ((line = reader.readLine()) != null){
            //Append line
            builder.append(line);
        }
        //Get append data
        String data = builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //Create json parser class
            JsonParser jsonParser = new JsonParser();
            //Initialize hash map list
            List<HashMap<String, String>> mapList = null;
            JSONObject object = null;
            try {
                //Initialize json object
                object = new JSONObject(strings[0]);
                //Parse json object
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            hikingVM.setHikeData(mapList);
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //Clear map
            map.clear();
            if(hashMaps == null){
                hashMaps = hikingVM.getHikeData().getValue().getMapList();
            }


            for (int i=0; i<hashMaps.size(); i++){
                //Initialize hash map
                HashMap<String, String> hashMapList = hashMaps.get(i);

                //Get latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));

                //Get longitude
                double lng = Double.parseDouble(hashMapList.get("lng"));

                //Get name
                String name = hashMapList.get("name");

                LatLng latLng = new LatLng(lat,lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                map.addMarker(options);
            }
        }
    }
}
package com.text.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.text.myapplication.Interface.APIPassID;
import com.text.myapplication.adapter.RecyclerAdapter;
import com.text.myapplication.entities.District;
import com.text.myapplication.entities.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.CountOfImagesWhenRemoved{

    private Spinner spinnerState, spinnerDistrict, spinnerSubDist;
    private ArrayList<String> getStateName = new ArrayList<String>();
    private ArrayList<String> getDistrictName = new ArrayList<String>();

    RecyclerView recyclerView;
    TextView textView;
    Button pick;

    ArrayList<Uri> uri = new ArrayList<Uri>();
    RecyclerAdapter adapter;

    private static final int Read_Permissions = 101;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        //spinnerSubDist = (Spinner) findViewById(R.id.spinnerSubDist);
        //getState();

        textView = (TextView) findViewById(R.id.tvTotalPhotos);
        recyclerView = (RecyclerView) findViewById(R.id.rv_gallery_images);
        pick = (Button) findViewById(R.id.btnPick);

        adapter = new RecyclerAdapter(uri, getApplicationContext(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        recyclerView.setAdapter(adapter);

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permissions);

                    return;
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });

    }

    private void getState() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIPassID.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        APIPassID apipassID  = retrofit.create(APIPassID.class);
        Call<String> call = apipassID.getState();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("## Response : ", response.body().toString());
                if(response.isSuccessful()){
                if (response.body() != null) {
                    Log.i("## Success : ", response.body().toString());
                    try {
                        String getResponse = response.body().toString();
                        List<State> getStateData = new ArrayList<State>();
                        JSONArray jsonArray = new JSONArray(getResponse);
                        getStateData.add(new State(-1, "---SELECT---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            State state = new State();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            state.setStid(jsonObject.getInt("sid"));
                            state.setStateName(jsonObject.getString("sname"));
                            getStateData.add(state);
                        }
                        for (int i = 0; i < getStateData.size(); i++) {
                            getStateName.add(getStateData.get(i).getStateName().toString());
                        }
                        ArrayAdapter<String> spinStateAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, getStateName);
                        spinStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerState.setAdapter(spinStateAdapter);
                        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                int getStatesID = getStateData.get(position).getStid();
                                get_District(getStatesID);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void get_District(int getStatesID) {

        getDistrictName.clear();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIPassID.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        APIPassID apipassID  = retrofit.create(APIPassID.class);
        Call<String> call = apipassID.getDistrict(getStatesID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("## Response : ", response.body().toString());
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("## Success : ", response.body().toString());
                        try{
                            String getResponse = response.body().toString();
                            List<District> getDistrictData = new ArrayList<District>();
                            JSONArray jsonArray = new JSONArray(getResponse);
                            getDistrictData.add(new District(-1, "---SELECT---"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                District district = new District();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                district.setDisID(jsonObject.getInt("distid"));
                                district.setDisName(jsonObject.getString("dname"));
                                getDistrictData.add(district);
                            }
                            for (int i = 0; i < getDistrictData.size(); i++) {
                                getDistrictName.add(getDistrictData.get(i).getDisName().toString());
                            }
                            ArrayAdapter<String> spinDistrictAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, getDistrictName);
                            spinDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDistrict.setAdapter(spinDistrictAdapter);
                            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data){
            if(data.getClipData() != null){

                //this part is for to get multiple images
                int countofImages = data.getClipData().getItemCount();
                for(int i=0; i<countofImages; i++){
                    //Limiting the number of images picked from gallery
                    if(uri.size() < 10){
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        uri.add(imageUri);
                    }else{
                        Toast.makeText(MainActivity.this, "Not Allowed to pick more than 10 images", Toast.LENGTH_LONG).show();
                    }

                }
                //notify the adapter
                adapter.notifyDataSetChanged();
                textView.setText("Photos ("+uri.size()+")");
            }else{
                //Limiting the number of images picked from gallery when single image is pick up
                if(uri.size() < 10) {
                    //this is for to get the SIngle images
                    Uri imageUri = data.getData();
                    //and add the code into the arraylist
                    uri.add(imageUri);
                }else{
                    Toast.makeText(MainActivity.this, "Not Allowed to pick more than 10 images", Toast.LENGTH_LONG).show();
                }
            }
            //notify the adapter
            adapter.notifyDataSetChanged();
            textView.setText("Photos ("+uri.size()+")");
        }else{
            //user not picked any image
            Toast.makeText(this, "You haven't Pick any Image", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void clicked(int getSize) {
        //Whenever images get removed adapter will get updated
        //and this will print the actual count
        textView.setText("Photos ("+uri.size()+")");
    }
}
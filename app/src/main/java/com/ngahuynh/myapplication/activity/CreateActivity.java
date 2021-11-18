package com.ngahuynh.myapplication.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ngahuynh.myapplication.helper.CustomToast;
import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.viewmodel.CreateViewModel;
import com.ngahuynh.myapplication.viewmodel.SignUpViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    String[] feetOptions = createStringArrayofInts(7);
    String[] inchesOptions = createStringArrayofInts(11);
    String[] lbsOptions = createStringArrayofInts(200);
    String[] decimalOptions = createStringArrayofInts(9);
    String gender, location, country, state, city;
    int feet, inches, lbs, decimal, checkedGender, countryPosition, statePosition, cityPosition;
    double height, weight, bmi;
    Bitmap profilePicture;

    TextView title, tVusername;
    RadioGroup radioGender;
    DatePickerDialog datePickerDialog;
    EditText eTname, dob;
    LocalDate enteredDOB;
    JSONArray jsonCountryArray;
    Spinner spinCountry, spinState, spinCity, spinFeet, spinInches, spinLbs, spinDec;
    ArrayAdapter<String> countryListAdapter, stateListAdapter, cityListAdapter, feetAdapter, inchesAdapter, lbsAdapter, decAdapter;
    ImageButton back, editProfPic;
    Button save;

    //Camera Stuff
    private ImageView circleImageView;
    private int GALLERY = 1, CAMERA = 2;

    private CreateViewModel createVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Get stuff
        title = findViewById(R.id.tv_bar_title);
        title.setText("Create My Profile");
        tVusername = findViewById(R.id.tv_username);
        eTname = findViewById(R.id.edt_name);
        dob = (EditText) findViewById(R.id.edt_dob);
        circleImageView = (ImageView) findViewById(R.id.img_profile_pic);
        radioGender = (RadioGroup) findViewById(R.id.radio_gender);
        spinFeet = (Spinner) findViewById(R.id.spn_ft);
        spinInches = (Spinner) findViewById(R.id.spn_in);
        spinLbs = (Spinner) findViewById(R.id.spn_lbs);
        spinDec = (Spinner) findViewById(R.id.spn_dcm_lbs);
        spinCountry = (Spinner) findViewById(R.id.spn_country);
        spinState = (Spinner) findViewById(R.id.spn_state);
        spinCity = (Spinner) findViewById(R.id.spn_city);
        back = findViewById(R.id.btn_menu);
        save = findViewById(R.id.btn_save);
        editProfPic = (ImageButton) findViewById(R.id.btn_update_pic);

        createVM = new ViewModelProvider(this).get(CreateViewModel.class);

        (createVM.getUserData()).observe(this, userObserver);

        //Set onClick listeners
        dob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        dob.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
                        enteredDOB = LocalDate.of(year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedGender = checkedId;
                RadioButton radioButton = findViewById(checkedId);
                gender = radioButton.getText().toString();
            }
        });

        feetAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, feetOptions);
        spinFeet.setAdapter(feetAdapter);
        spinFeet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                feet = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        inchesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, inchesOptions);
        spinInches.setAdapter(inchesAdapter);
        spinInches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inches = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        lbsAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, lbsOptions);
        spinLbs.setAdapter(lbsAdapter);
        spinLbs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lbs = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        decAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, decimalOptions);
        spinDec.setAdapter(decAdapter);
        spinDec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                decimal = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        populateLocationSpinner();

        back.setOnClickListener(this);
        save.setOnClickListener(this);
        editProfPic.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save: {
                if (eTname.getText().toString() != null) {
                    setAppUserName(eTname.getText().toString());
                }
                if (dob.getText().toString() != null) {
                    setAppUserDOB(dob.getText().toString());
                }
                if (profilePicture != null) {
                    setAppUserProfPic(getCroppedBitmap(profilePicture));
                }
                setAppUserFeet(feet);
                setAppUserInches(inches);
                setAppUserLBS(lbs);
                setAppUserDecimals(decimal);
                if (gender != null) {
                    setAppUserGender(gender);
                }
                setAppUserCheckedGender(checkedGender);
                setAppUserAge(String.valueOf(calcAge(enteredDOB, LocalDate.now())));
                setAppUserCity(city);
                setAppUserCityPosition(cityPosition);
                setAppUserState(state);
                setAppUserStatePosition(statePosition);
                setAppUserCountry(country);
                setAppUserCountryPosition(countryPosition);
                if(state.equals("None")) {
                    location = city + ", " + country;
                } else {
                    location = city + ", " + state + ", " + country;
                }
                setAppUserLocation(location);
                height = Double.parseDouble(feet + "." + inches);
                setAppUserHeight(height);
                weight = Double.parseDouble(lbs + "." + decimal);
                setAppUserWeight(weight);
                bmi = calcBMI(weight, feet, inches);
                setAppUserBMI(bmi);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_menu: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_update_pic: {
                showPictureDialog();
            }
        }
    }

    final Observer<User> userObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User appUser) {
            if (appUser != null) {
                if (appUser.getUsername() != null) {
                    tVusername.setText(appUser.getUsername());
                }
                if (appUser.getBirthday() != null) {
                    dob.setText(appUser.getBirthday());
                }
                if (appUser.getName() != null) {
                    eTname.setText(appUser.getName());
                }
                if (appUser.getProfPic() != null) {
                    circleImageView.setImageBitmap(appUser.getProfPic());
                }
                spinCity.setSelection(appUser.getCityPosition());
                spinState.setSelection(appUser.getStatePosition());
                spinCountry.setSelection(appUser.getCountryPosition());
                spinFeet.setSelection(appUser.getFeet());
                spinInches.setSelection(appUser.getInches());
                spinLbs.setSelection(appUser.getLbs());
                spinDec.setSelection(appUser.getDecimal());
            }
        }
    };

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    profilePicture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    circleImageView.setImageBitmap(profilePicture);
                } catch (IOException e) {
                    e.printStackTrace();
                    new CustomToast(this, "Failed!").show();
                }
            }

        } else if (requestCode == CAMERA) {
            profilePicture = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(profilePicture);
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public String[] createStringArrayofInts(int x) {
        int[] a = new int[x + 1];
        for (int i = 1; i < x + 1; ++i) {
            a[i] = i;
        }
        String[] b = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = String.valueOf(a[i]);
        }
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int calcAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    private void populateLocationSpinner() {
        try {
            jsonCountryArray = new JSONObject(loadJSONFromAsset()).optJSONArray("country");

            ArrayList<String> countryList = new ArrayList<>();
            for (int i = 0; i < jsonCountryArray.length(); i++) {
                countryList.add(jsonCountryArray.optJSONObject(i).optString("name"));
            }
            countryListAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, countryList);

            spinCountry.setAdapter(countryListAdapter);

            spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    country = parent.getItemAtPosition(position).toString();
                    countryPosition = position;

                    ArrayList<String> stateArray = new ArrayList<>();
                    final JSONArray jsonStateArray = jsonCountryArray.optJSONObject(position).optJSONArray("state");

                    for (int i = 0; i < jsonStateArray.length(); i++) {
                        stateArray.add(jsonStateArray.optJSONObject(i).optString("name"));
                    }

                    stateListAdapter = new ArrayAdapter<>(CreateActivity.this, R.layout.spinner_item, stateArray);

                    spinState.setAdapter(stateListAdapter);

                    spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            state = parent.getItemAtPosition(position).toString();
                            statePosition = position;

                            final ArrayList<String> cityArray = new ArrayList<>();
                            final JSONArray jsonCityArray = jsonStateArray.optJSONObject(position).optJSONArray("city");
                            for (int i = 0; i < jsonCityArray.length(); i++) {
                                cityArray.add(jsonCityArray.optJSONObject(i).optString("name"));
                            }

                            cityListAdapter = new ArrayAdapter<>(CreateActivity.this, R.layout.spinner_item, cityArray);

                            spinCity.setAdapter(cityListAdapter);
                            spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    city = parent.getItemAtPosition(position).toString();
                                    cityPosition = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Location", "error=" + e.getMessage());
        }
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("country.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public double calcBMI(double weight, int feet, int inches) {
        //STEP 1: Multiply the weight (in lbs) by 0.45 (metric conversion factor)
        weight *= 0.45359237;
        //STEP 2: Convert feet->inches and add remaining inches
        int totalInches = (feet * 12) + inches;
        //STEP 3: Multiply height (in inches) by 0.025 (metric conversion factor)
        double metHeight = totalInches * 0.0254;
        //STEP 4: Square the answer from STEP 3
        double squared = metHeight * metHeight;
        //Divide the answer from STEP 1 by answer from STEP 4
        double bmi = weight / squared;
        return bmi;
    }

//---------- New Methods ---------//
    //Doesn't need a getUserData() because they are updated through observers
    //Used to push data -> view model -> repo
    //ALL SET METHODS CALLED WHEN SAVE BUTTON IS CLICKED
    void setAppUserName(String name){
        createVM.setAppUserName(name);
    }
    void setAppUserDOB(String dob) {
        createVM.setAppUserDOB(dob);
    }
    void setAppUserProfPic(Bitmap profPic) {
        createVM.setAppUserProfPic(profPic);
    }
    void setAppUserFeet(int feet) {
        createVM.setAppUserFeet(feet);
    }
    void setAppUserInches(int inches) {
        createVM.setAppUserInches(inches);
    }
    void setAppUserLBS(int lbs) {
        createVM.setAppUserLBS(lbs);
    }
    void setAppUserDecimals(int decimals) {
        createVM.setAppUserDecimals(decimals);
    }
    void setAppUserGender(String gender) {
        createVM.setAppUserGender(gender);
    }
    void setAppUserCheckedGender(int checkedGender) {
        createVM.setAppUserCheckedGender(checkedGender);
    }
    void setAppUserAge(String age) {
        createVM.setAppUserAge(age);
    }
    void setAppUserCity(String city) {
        createVM.setAppUserCity(city);
    }
    void setAppUserCityPosition(int cityPosition) {
        createVM.setAppUserCityPosition(cityPosition);
    }
    void setAppUserState(String state) {
        createVM.setAppUserState(state);
    }
    void setAppUserStatePosition(int statePosition) {
        createVM.setAppUserStatePosition(statePosition);
    }
    void setAppUserCountry(String country) {
        createVM.setAppUserCountry(country);
    }
    void setAppUserCountryPosition(int countryPosition) {
        createVM.setAppUserCountryPosition(countryPosition);
    }
    void setAppUserHeight(double height) {
        createVM.setAppUserHeight(height);
    }
    void setAppUserWeight(double weight) {
        createVM.setAppUserWeight(weight);
    }
    void setAppUserBMI(double bmi) {
        createVM.setAppUserBMI(bmi);
    }
    void setAppUserLocation(String location) {
        createVM.setAppUserLocation(location);
    }
}

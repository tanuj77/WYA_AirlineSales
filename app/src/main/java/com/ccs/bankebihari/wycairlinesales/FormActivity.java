package com.ccs.bankebihari.wycairlinesales;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Banke Bihari on 10/5/2016.
 */
public class FormActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    EditText editTextCompanyID, editTextBranch;
    TextView textViewAgentBranch;
    Spinner spinnerType;
    TextView textViewStartDate, textViewEndDate;
    EditText editTextAirlineCode;
    Button buttonSubmit, buttonExcel;
    RadioGroup radioGroup;
    RadioButton rbBsp, rbXo, rbPurchase, rbAll;
    String cpCompanyID, branchName, customerBranch, agentBranch, startDate, strStartDate, cpStartDate, endDate, aResponse, cpCompanyUrl, cpPartyCode, mUserName, mPassword, check, res,
            makeExcelSheet = "false", state, airlineCode, cpMobileNo, dropDownAgentBranch = "", cpMultipleBranch, cpBranch;
    // CheckBox checkBoxBsp, checkBoxPurchase, checkBoxXo, checkBoxAll;
    String checkBsp = "", checkPurchase = "", checkXo = "", checkAll = "",strFinancialYear,cpFinancialYear;
    ProgressBar pbCircular;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        cd = new ConnectionDetector(getApplicationContext());

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Internet Connection Error");

            // Setting Dialog Message
            alertDialog.setMessage("Please connect to working Internet connection");
            // Setting alert dialog icon
            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FormActivity.this.finish();
                }
            });
            // Showing Alert Message
            alertDialog.show();

            return;
        }


        editTextCompanyID = (EditText) findViewById(R.id.et_companyID);
        editTextBranch = (EditText) findViewById(R.id.et_branch);
        //  textViewAgentBranch = (TextView) findViewById(R.id.tv_abranch);
        spinnerType = (Spinner) findViewById(R.id.spinner);

        textViewStartDate = (TextView) findViewById(R.id.tv_startdate);
        textViewEndDate = (TextView) findViewById(R.id.tv_enddate);
        editTextAirlineCode = (EditText) findViewById(R.id.et_airlinecode);
        buttonSubmit = (Button) findViewById(R.id.btn_submit);
        buttonExcel = (Button) findViewById(R.id.btn_downloadExcel);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        rbBsp = (RadioButton) findViewById(R.id.rb_bsp);
        rbXo = (RadioButton) findViewById(R.id.rb_xo);
        rbPurchase = (RadioButton) findViewById(R.id.rb_purchase);
        rbAll = (RadioButton) findViewById(R.id.rb_all);
        //      radioGroup.check(rbBsp.getId());
//        checkBoxBsp = (CheckBox) findViewById(R.id.cb_bsp);
//        checkBoxPurchase = (CheckBox) findViewById(R.id.cb_purchase);
//        checkBoxXo = (CheckBox) findViewById(R.id.cb_xo);
//        checkBoxAll = (CheckBox) findViewById(R.id.cb_all);
        pbCircular = (ProgressBar) findViewById(R.id.pbCirular);
        pbCircular.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("companyDetail", MODE_PRIVATE);
        cpCompanyID = sharedPreferences2.getString("companyID", null);
        cpStartDate = sharedPreferences2.getString("startDate", null);
        cpCompanyUrl = sharedPreferences2.getString("companyUrl", null);
        cpPartyCode = sharedPreferences2.getString("partyCode", null);
        mUserName = sharedPreferences2.getString("userName", null);
        mPassword = sharedPreferences2.getString("password", null);
        cpMobileNo = sharedPreferences2.getString("mobileNo", null);
        dropDownAgentBranch = sharedPreferences2.getString("dropDownAgentBranch", null);
        cpMultipleBranch = sharedPreferences2.getString("multipleBranch", null);
        cpBranch = sharedPreferences2.getString("branch", null);
        cpFinancialYear = sharedPreferences2.getString("financialYear", null);


        if (!cd.isConnectingToInternet()) {
            Toast.makeText(FormActivity.this, "Please connect to working Internet connection", Toast.LENGTH_LONG).show();
            return;
        }



        if (cpPartyCode == "Agent" || cpPartyCode.equalsIgnoreCase("Agent") || cpPartyCode.equals("Agent")) {
            editTextBranch.setText("");
            editTextBranch.setClickable(false);
            editTextBranch.setFocusableInTouchMode(false);
            editTextBranch.setFocusable(false);

            spinnerType.setClickable(true);
            spinnerType.setFocusableInTouchMode(true);
            spinnerType.setFocusable(true);
            spinnerType.setEnabled(true);
            //spinnerType.setVisibility(View.VISIBLE);
            // textViewAgentBranch.setVisibility(View.VISIBLE);
            if (dropDownAgentBranch.trim().equalsIgnoreCase("No Branch Found") || dropDownAgentBranch.trim().equals("No Branch Found") || dropDownAgentBranch == "No Branch Found"){
                spinnerType.setOnItemSelectedListener(this);
                List<String> categories = new ArrayList<String>();
                categories.add("No Branch");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(dataAdapter);
            }else {
                dropDownAgentBranch = dropDownAgentBranch.replace("[", "");
                dropDownAgentBranch = dropDownAgentBranch.replace("]", "");
                String[] branch = dropDownAgentBranch.split(",");

                spinnerType.setOnItemSelectedListener(this);
                List<String> categories = new ArrayList<String>();
                categories.add("Select Agent Branch");
                for (int i = 0; i < branch.length; i++) {

                    categories.add(branch[i]);
                }
                categories.add("All");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(dataAdapter);
            }

        } else {
            if (cpMultipleBranch == "yes" || cpMultipleBranch.equalsIgnoreCase("yes") || cpMultipleBranch.equals("yes")) {
                customerBranch = cpBranch;
                editTextBranch.setText(cpBranch);
                editTextBranch.setClickable(false);
                editTextBranch.setFocusableInTouchMode(false);
                editTextBranch.setFocusable(false);
                spinnerType.setClickable(false);
                spinnerType.setFocusableInTouchMode(false);
                spinnerType.setFocusable(false);
                spinnerType.setEnabled(false);
                // spinnerType.setVisibility(View.INVISIBLE);
                // textViewAgentBranch.setVisibility(View.INVISIBLE);
            } else {
                customerBranch = "";
                editTextBranch.setText("");
                editTextBranch.setClickable(false);
                editTextBranch.setFocusableInTouchMode(false);
                editTextBranch.setFocusable(false);
                spinnerType.setClickable(false);
                spinnerType.setFocusableInTouchMode(false);
                spinnerType.setFocusable(false);
                spinnerType.setEnabled(false);
                // spinnerType.setVisibility(View.INVISIBLE);
                // textViewAgentBranch.setVisibility(View.INVISIBLE);
            }

        }

        editTextCompanyID.setText(cpCompanyID);

        String dateFormat = cpStartDate;
        String[] dateAray = dateFormat.split("-");
        String year = dateAray[0];
        String month = dateAray[1];
        final String day = dateAray[2];
        // String  formatedStartDate.appe
        textViewStartDate.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year));
        startDate = String.valueOf(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
        //////////START////////Show calendar and select date ////////
        final Calendar myStartDateCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerListenerStartDAte = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myStartDateCalendar.set(Calendar.YEAR, year);
                myStartDateCalendar.set(Calendar.MONTH, month);
                myStartDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                textViewStartDate.setText(sdf.format(myStartDateCalendar.getTime()));
                startDate = String.valueOf(new StringBuilder().append(year).append("-").append(month).append("-").append(dayOfMonth)).trim();
            }
        };

        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FormActivity.this, datePickerListenerStartDAte, myStartDateCalendar
                        .get(Calendar.YEAR), myStartDateCalendar.get(Calendar.MONTH),
                        myStartDateCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                textViewEndDate.setText(sdf.format(myCalendar.getTime()));
                endDate = String.valueOf(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(dayOfMonth)).trim();
            }
        };

        textViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FormActivity.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
// ////////END////////Show calendar and select date ////////


        if (dropDownAgentBranch.trim().equalsIgnoreCase("No Branch Found") || dropDownAgentBranch.trim().equals("No Branch Found") || dropDownAgentBranch == "No Branch Found"){
            spinnerType.setOnItemSelectedListener(this);
            List<String> categories = new ArrayList<String>();
            categories.add("No Branch");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerType.setAdapter(dataAdapter);
        }else {
            dropDownAgentBranch = dropDownAgentBranch.replace("[", "");
            dropDownAgentBranch = dropDownAgentBranch.replace("]", "");
            String[] branch = dropDownAgentBranch.split(",");

            spinnerType.setOnItemSelectedListener(this);
            List<String> categories = new ArrayList<String>();
            categories.add("Select Agent Branch");
            for (int i = 0; i < branch.length; i++) {

                categories.add(branch[i]);
            }
            categories.add("All");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerType.setAdapter(dataAdapter);
        }


        radioGroup.check(rbBsp.getId());
        checkBsp = "y";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_bsp) {
                    checkBsp = "y";
                    checkXo = "";
                    checkPurchase = "";
                    checkAll = "";
                } else if (checkedId == R.id.rb_xo) {
                    checkXo = "y";
                    checkBsp = "";
                    checkPurchase = "";
                    checkAll = "";
                } else if (checkedId == R.id.rb_purchase) {
                    checkPurchase = "p";
                    checkBsp = "";
                    checkXo = "";
                    checkAll = "";
                } else if (checkedId == R.id.rb_all) {
                    checkAll = "y";
                    checkBsp = "";
                    checkXo = "";
                    checkPurchase = "";
                }

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeExcelSheet = "false";

                cpCompanyID = editTextCompanyID.getText().toString().trim();
                if (agentBranch.equalsIgnoreCase("Select Agent Branch") || agentBranch.equals("Select Agent Branch") || agentBranch == "Select Agent Branch") {
                    branchName = customerBranch;
                } else {
                    branchName = agentBranch;
                }

                strStartDate = textViewStartDate.getText().toString();
                String dateFormat = strStartDate;
                String[] dateAray = dateFormat.split("-");
                String day = dateAray[0];
                String month = dateAray[1];
                final String year = dateAray[2];
                strStartDate = String.valueOf(new StringBuilder().append(year).append("-").append(month).append("-").append(day));


                airlineCode = editTextAirlineCode.getText().toString().trim();


                if (textViewEndDate.getText().length() < 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Select End Date first");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (!dateValidation(strStartDate, endDate, "yyyy-MM-dd")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage(" End Date must be grater than start date");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (cpPartyCode == "Agent" || cpPartyCode.equalsIgnoreCase("Agent") || cpPartyCode.equals("Agent")) {

                    if (agentBranch.equalsIgnoreCase("Select Agent Branch") || agentBranch.equals("Select Agent Branch") || agentBranch == "Select Agent Branch") {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Select agent branch first");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else {
                        branchName = agentBranch;
                        pbCircular.setVisibility(View.VISIBLE);
                        new agentAsync().execute();
                    }
                } else {
                    branchName = customerBranch;
                    pbCircular.setVisibility(View.VISIBLE);
                    new customerAsync().execute();
                }



            }
        });
        buttonExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

////START/ Run time Permission to write file///////////
                if (ActivityCompat.checkSelfPermission(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //Show Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                        builder.setTitle("Need Storage Permission");
                        builder.setMessage("This app needs storage permission.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                        //Previously Permission Request was cancelled with 'Dont Ask Again',
                        // Redirect to Settings after showing Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                        builder.setTitle("Need Storage Permission");
                        builder.setMessage("This app needs storage permission.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                sentToSettings = true;
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else {
                        //just request the permission
                        ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }

                    SharedPreferences.Editor editor = permissionStatus.edit();
                    editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
                    editor.commit();


                } else {
                    //You already have the permission, just go ahead.
                    //proceedAfterPermission();

////END/ Run time Permission to write file////in above else block further code vl write ///////

                    makeExcelSheet = "True";
                    cpCompanyID = editTextCompanyID.getText().toString().trim();
                    airlineCode = editTextAirlineCode.getText().toString().trim();

                    strStartDate = textViewStartDate.getText().toString();
                    String dateFormat = strStartDate;
                    String[] dateAray = dateFormat.split("-");
                    String day = dateAray[0];
                    String month = dateAray[1];
                    final String year = dateAray[2];
                    strStartDate = String.valueOf(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
                    if (textViewEndDate.getText().length() < 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Select End Date first");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else if (!dateValidation(strStartDate, endDate, "yyyy-MM-dd")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                        builder.setTitle("Alert");
                        builder.setMessage(" End Date must be grater than start date");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }else if (cpPartyCode == "Agent" || cpPartyCode.equalsIgnoreCase("Agent") || cpPartyCode.equals("Agent")) {
                        if (agentBranch.equalsIgnoreCase("Select Agent Branch") || agentBranch.equals("Select Agent Branch") || agentBranch == "Select Agent Branch") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                            builder.setTitle("Alert");
                            builder.setMessage("Select agent branch first");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else {
                            branchName = agentBranch;
                            pbCircular.setVisibility(View.VISIBLE);
                            new agentAsync().execute();
                        }
                    } else {
                        branchName = customerBranch;
                        new customerAsync().execute();
                    }
                    // }
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Form Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ccs.bankebihari.wycairlinesales/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Form Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ccs.bankebihari.wycairlinesales/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
      if (spinner.getId() == R.id.spinner) {
          if (dropDownAgentBranch.trim().equalsIgnoreCase("No Branch Found") || dropDownAgentBranch.trim().equals("No Branch Found") || dropDownAgentBranch == "No Branch Found") {
              agentBranch = "NoBranch";
          } else {
              agentBranch = parent.getItemAtPosition(position).toString();
              agentBranch = String.valueOf(agentBranch);
          }
      }


        if (spinner.getId() == R.id.spinnerfinancialyear){
            strFinancialYear = parent.getItemAtPosition(position).toString();
            strFinancialYear = String.valueOf(strFinancialYear);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class agentAsync extends AsyncTask<Void, Void, Void> {
        String aResponse;

        @Override
        protected Void doInBackground(Void... voids) {
            //Create Webservice class object
            AgentWebservice agentWebservice = new AgentWebservice();

            //Call Webservice class method and pass values and get response
            aResponse = agentWebservice.getConvertedWeight(cpCompanyUrl, "GetValidUser", cpCompanyID, mUserName, mPassword);
            res = aResponse;
            Log.i("BBRRaResponse", aResponse);
            if (res == "Invalid User" || res.equalsIgnoreCase("Invalid User") || res.equals("Invalid User")) {
                check = "";
                startDate = "";
            } else {

                String[] strArray = res.split("%");
                check = strArray[0];
                startDate = strArray[1];
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            pbCircular.setVisibility(View.INVISIBLE);
            if (res == "Invalid User" || res.equalsIgnoreCase("Invalid User") || res.equals("Invalid User")) {
                Toast.makeText(FormActivity.this, "Do Registration Again", Toast.LENGTH_LONG).show();
            } else if (check.equals("Valid User")) {

                new infoAsync().execute();
            }
        }
    }

    class customerAsync extends AsyncTask<Void, Void, Void> {
        String bResponse;

        @Override
        protected Void doInBackground(Void... voids) {
            //Create Webservice class object
            CustomerWebservice com = new CustomerWebservice();

            //Call Webservice class method and pass values and get response
            bResponse = com.checkValidUsernamePassword(cpCompanyUrl, "wyCheckPartyLogin", cpCompanyID, cpPartyCode, mUserName, mPassword);

            Log.i("AndroidExampleOutput", "----" + bResponse);

            return null;
        }

        protected void onPostExecute(Void result) {
            //Toast.makeText(getApplicationContext(), bResponse, Toast.LENGTH_LONG).show();

            if (bResponse.equals("Successfully Login")) {
                new infoAsync().execute();
            } else {
                Toast.makeText(FormActivity.this, "Do Registration Again", Toast.LENGTH_LONG).show();
            }
        }
    }


    class infoAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            FormWebservice mainWebservice = new FormWebservice();
            aResponse = mainWebservice.getConvertedWeight(cpCompanyUrl, "GetArlnBsFareYqTaxBrnId_JSON1", cpCompanyID, branchName, strStartDate, endDate, airlineCode, checkBsp, checkXo, checkPurchase, checkAll, cpMobileNo);

            Log.i("BBRRResponseForm", aResponse);
            return null;
        }

        protected void onPostExecute(Void result) {
            pbCircular.setVisibility(View.INVISIBLE);

            if (aResponse.equalsIgnoreCase("No Data Found")) {
                Toast.makeText(FormActivity.this, aResponse, Toast.LENGTH_LONG).show();
            } else if (aResponse.trim().equalsIgnoreCase("Something went wrong at server side or wrong input") || aResponse.trim().equals("Something went wrong at server side or wrong input") || aResponse.trim() == "Something went wrong at server side or wrong input") {
                Toast.makeText(FormActivity.this, aResponse, Toast.LENGTH_LONG).show();
            } else {
                if (makeExcelSheet.equals("True") || makeExcelSheet == "True") {
                    String input = "";// = "Harey,Krishnagg"+"\n"+"Harey,Ramgg";
                    String string = aResponse;
                    String[] parts = string.split("%");
//input = parts[0].replace(",","\n")+","+parts[1].replace(",","\n");
                    String[] response0 = parts[0].split(",");
                    String[] response01 = parts[1].split(",");
                    String[] response02 = parts[2].split(",");
                    String[] response03 = parts[3].split(",");
                    String[] response04 = parts[4].split(",");
                    String[] response05 = parts[5].split(",");
                    String[] response06 = parts[6].split(",");
                    String[] response07 = parts[7].split(",");
                    String[] response08 = parts[8].split(",");
                    String[] response09 = parts[9].split(",");
                    String[] response10 = parts[10].split(",");
                    String[] response11 = parts[11].split(",");
                    String[] response12 = parts[12].split(",");
                    String[] response13 = parts[13].split(",");
                    //String[] response14 = parts[14].split(",");
                    // String[] response15 = parts[15].split(",");
                    // String[] response04 = parts[16].split(",");


                    for (int i = 0; i < response0.length; i++) {
                        String Airline = response0[i];
                        String Coupen = response01[i];
                        // String AirlineClass = response02[i];
                        String VoidCxl = response02[i];
                        String BaseFare = response03[i];
                        String YQTax = response04[i];
                        String OtherTaxAmount = response05[i];
                        String ComRec = response06[i];
                        String OrAmount = response07[i];
                        String IncFlow = response08[i];
                        String PlbAmount = response09[i];
                        String AcmAdm = response10[i];
                        //  String Gst = response11[i];
                        String NetAmount = response11[i];
                        String BranchID = response12[i];
                        String AirlineName = response13[i];

                        input = input + "" + Airline + "," + Coupen + "," + VoidCxl + "," + BaseFare + "," + YQTax + "," + OtherTaxAmount + "," + ComRec + "," + OrAmount + "," + IncFlow + "," + PlbAmount + "," + AcmAdm + "," + NetAmount + "," + BranchID + "," + AirlineName + "\n";
                        input = input.replace("[", "");
                        input = input.replace("]", "");

                    }

                    state = Environment.getExternalStorageState();

                    if (Environment.MEDIA_MOUNTED.equals(state)) {//checking sd card or extenal device is available or not
                        File Root = Environment.getExternalStorageDirectory();
                        File Dir = new File(Root.getAbsolutePath() + "/Winyatra");//new folder created
                        if (!Dir.exists()) { //folder exist or not
                            Dir.mkdir();    //folder created
                        }
                        File file = null;
                        if (checkBsp.equalsIgnoreCase("y") || checkBsp.equals("y") || checkBsp == "y") {
                            file = new File(Dir, "AirlineSale Bsp Report.csv");  //text file created
                        } else if (checkXo.equalsIgnoreCase("y") || checkXo.equals("y") || checkXo == "y") {
                            file = new File(Dir, "AirlineSale Xo Report.csv");  //text file created
                        } else if (checkPurchase.equalsIgnoreCase("p") || checkPurchase.equals("p") || checkPurchase == "p") {
                            file = new File(Dir, "AirlineSale Purchase Report.csv");  //text file created
                        } else if (checkAll.equalsIgnoreCase("y") || checkAll.equals("y") || checkAll == "y") {
                            file = new File(Dir, "AirlineSale All Report.csv");  //text file created
                        }


                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file); //write in text file
                            fileOutputStream.write(input.getBytes());
                            fileOutputStream.close();
                            Toast.makeText(getApplicationContext(), "Excel saved ", Toast.LENGTH_LONG).show();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "sd card not found", Toast.LENGTH_LONG).show();
                    }

                } else if (makeExcelSheet.equals("false") || makeExcelSheet == "false") {
                    Intent intent = new Intent(FormActivity.this, RecyclerViewActivity.class);
                    Bundle extra = new Bundle();
                    extra.putString("webserviceresponse", aResponse);
                    intent.putExtras(extra);
                    startActivity(intent);
                }
            }

        }
    }


    public static boolean dateValidation(String startDate, String endDate, String dateFormat) {
        try {

            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);


            if (date1.equals(startingDate)) {
                return true;
            } else if (date1.after(startingDate)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

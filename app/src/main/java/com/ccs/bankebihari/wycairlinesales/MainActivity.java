package com.ccs.bankebihari.wycairlinesales;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemSelectedListener {
    LinearLayout linearLayoutCompantAndBranch;
    Button btnSubmit;
    EditText editTextCompanyID, editTextUserID, editTextUserName, editTextPassword;
    private ProgressDialog progressDialog;
    public static final int progress_bar_type = 0;

    String startDate, cpCompanyName = "", check = "", cpCompanyUrl, cpMobileNo, dropDownAgentBranch = "";
    String cpPartyCode = "", cpUserName = "", cpPassword = "";
    String cpMultipleBranch, cpBranch,cpFinancialYear,strFinancialYear;
    Spinner spinnerFinancialYear;
    String userName, password, res;//   = "Radhey";
    ProgressBar pbCircular;
    SharedPreferences Sharednooftimeapplicationopend;

    CursorLoader cursorLoader;
    ConnectionDetector cd;
    private final String TOPIC = "SendToAirlineSales";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Internet Connection Error");
            alertDialog.setMessage("Please connect to working Internet connection");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            });
            alertDialog.show();

            return;
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Log In");

        onClickDisplayNames();

       // linearLayoutCompantAndBranch = (LinearLayout) findViewById(R.id.ll_companyandbranch);
        editTextCompanyID = (EditText) findViewById(R.id.et_companyid);
        editTextUserName = (EditText) findViewById(R.id.et_username);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        spinnerFinancialYear = (Spinner) findViewById(R.id.spinnerfinancialyear);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        pbCircular = (ProgressBar) findViewById(R.id.pbCirular);
        pbCircular.setVisibility(View.INVISIBLE);

        Sharednooftimeapplicationopend = PreferenceManager.getDefaultSharedPreferences(this);
        String getcount = Sharednooftimeapplicationopend.getString("APP_OPEN_COUNT", "");

        if (getcount.equalsIgnoreCase("1") || getcount == "1") {

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("companyDetail", MODE_PRIVATE);
            String getcompanyID = sharedPreferences.getString("companyID", null);
            String getuserId = sharedPreferences.getString("userID", null);
            String getpassword = sharedPreferences.getString("password", null);

            editTextUserID.setText(getuserId);
            editTextPassword.setText(getpassword);
        }

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(MainActivity.this, "Please connect to working Internet connection", Toast.LENGTH_LONG).show();
            return;
        }


        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("companyDetail", MODE_PRIVATE);
        cpFinancialYear = sharedPreferences2.getString("financialYear", null);

        Calendar calendar = Calendar.getInstance();
        int fyear = calendar.get(Calendar.YEAR);
        spinnerFinancialYear.setOnItemSelectedListener(this);
        List<String> financialYearList = new ArrayList<String>();
        financialYearList.add("Select Financial Year");
        int finyear = Integer.parseInt(cpFinancialYear);
        for (int i = finyear ; i <= fyear; i++) {

            financialYearList.add(String.valueOf(i));
        }

        ArrayAdapter<String> financialYearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, financialYearList);
        financialYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFinancialYear.setAdapter(financialYearAdapter);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///START////Subscribe aaplication for particular topic in FCM//////
                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
                ///END////Subscribe aaplication for particular topic in FCM//////



                if (strFinancialYear.trim().equalsIgnoreCase("Select Financial Year") || strFinancialYear.trim().equals("Select Financial Year") || strFinancialYear == "Select Financial Year"){

                }else {
                    String firstPart = "";
                    String secondPart = "";
                    for (int j = 0; j < strFinancialYear.length(); j++) {
                        if (j < strFinancialYear.length() / 2) {
                            firstPart += strFinancialYear.charAt(j);
                        } else
                            secondPart += strFinancialYear.charAt(j);
                    }

                    Log.d("onItemSelected: ", String.valueOf(secondPart));
                    String nfcpCompanyName = cpCompanyName.substring(0, cpCompanyName.length() - 2);
                    cpCompanyName = nfcpCompanyName + secondPart;
                    Log.d("onItemSelected:2 ", String.valueOf(cpCompanyName));
                }


                userName = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();
                pbCircular.setVisibility(View.VISIBLE);
                new infoAsyncDropDown().execute();
                if (cpPartyCode == "Agent" || cpPartyCode.equalsIgnoreCase("Agent") || cpPartyCode.equals("Agent")) {
                    new agentAsync().execute();
                } else {
                    new customerAsync().execute();
                }
            }
        });

    }


    //////////////Content Provider user///////START///////////

    public void onClickDisplayNames() {
        getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        cursorLoader = new CursorLoader(this, Uri.parse("content://com.wyc.ccs81.myprovider/cte/1"), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        cursor.moveToFirst();

        //StringBuilder res=new StringBuilder();
        while (!cursor.isAfterLast()) {
            //res.append("\n"+cursor.getString(cursor.getColumnIndex("id"))+ "-"+ cursor.getString(cursor.getColumnIndex("name")));
            cpCompanyName = cursor.getString(cursor.getColumnIndex("name"));
            cpCompanyUrl = cursor.getString(cursor.getColumnIndex("companyUrl"));
            cpPartyCode = cursor.getString(cursor.getColumnIndex("partyCode"));
            cpUserName = cursor.getString(cursor.getColumnIndex("userName"));
            cpPassword = cursor.getString(cursor.getColumnIndex("password"));
            cpMobileNo = cursor.getString(cursor.getColumnIndex("mobileNo"));
            cpMultipleBranch = cursor.getString(cursor.getColumnIndex("multipleBranch"));
            cpBranch = cursor.getString(cursor.getColumnIndex("branch"));
            cpFinancialYear = cursor.getString(cursor.getColumnIndex("financialYear"));

            cursor.moveToNext();
        }


        editTextCompanyID.setText(cpCompanyName);
        editTextCompanyID.setClickable(false);
        editTextCompanyID.setFocusable(false);
        editTextCompanyID.setFocusableInTouchMode(false);
        editTextUserName.setText(cpUserName);
        editTextPassword.setText(cpPassword);

        if (cpCompanyName == "" || cpCompanyName.equalsIgnoreCase("") || cpCompanyName.equals("")) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Do Registration First");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
            return;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


//////////////Content Provider user///////END///////////


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinnerfinancialyear) {
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
            aResponse = agentWebservice.getConvertedWeight(cpCompanyUrl, "GetValidUser", cpCompanyName, userName, password);
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
                Toast.makeText(MainActivity.this, "Do Registration Again", Toast.LENGTH_LONG).show();
            } else if (check.equals("Valid User")) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("companyDetail", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("companyID", cpCompanyName);
                editor.putString("userName", cpUserName);
                editor.putString("password", password);
                editor.putString("startDate", startDate);
                editor.putString("companyUrl", cpCompanyUrl);
                editor.putString("partyCode", cpPartyCode);
                editor.putString("mobileNo", cpMobileNo);
                editor.putString("dropDownAgentBranch", dropDownAgentBranch);
                editor.putString("multipleBranch", cpMultipleBranch);
                editor.putString("branch", cpBranch);
                editor.putString("financialYear",cpFinancialYear);
                editor.commit();
                Toast.makeText(getApplicationContext(), check, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
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
            bResponse = com.checkValidUsernamePassword(cpCompanyUrl, "wyCheckPartyLogin", cpCompanyName, cpPartyCode, userName, password);

            Log.i("AndroidExampleOutput", "----" + bResponse);

            return null;
        }

        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), bResponse, Toast.LENGTH_LONG).show();

            if (bResponse.equals("Successfully Login")) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("companyDetail", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("companyID", cpCompanyName);
                editor.putString("userName", cpUserName);
                editor.putString("password", password);
                editor.putString("startDate", "2016-04-01");
                editor.putString("companyUrl", cpCompanyUrl);
                editor.putString("partyCode", cpPartyCode);
                editor.putString("mobileNo", cpMobileNo);
                editor.putString("dropDownAgentBranch", dropDownAgentBranch);
                editor.putString("multipleBranch", cpMultipleBranch);
                editor.putString("branch", cpBranch);
                editor.putString("financialYear",cpFinancialYear);
                editor.commit();
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Do Registration Again", Toast.LENGTH_LONG).show();
            }
        }
    }

    class infoAsyncDropDown extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            DropdownWebservice dropdownWebservice = new DropdownWebservice();
            dropDownAgentBranch = dropdownWebservice.getConvertedWeight(cpCompanyUrl, "GetBranchID", cpCompanyName, "Branch");
            Log.d("RRBBmethod", dropDownAgentBranch);
            return null;
        }
    }
}

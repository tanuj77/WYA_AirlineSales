package com.ccs.bankebihari.wycairlinesales;

import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Banke Bihari on 10/1/2016.
 */
public class FormWebservice {


    /**
     * Variable Decleration................
     */
    String namespace = "http://tempuri.org/";

    //private String url = "http://103.16.142.124/WinYatra_GetArlnBsFareYqTaxBrnId_PUBLISH_FINAL_Json_V4/ArlnBsFareYqTaxBrnId.asmx";
    private String url = null;
    String SOAP_ACTION;
    SoapObject request = null, objMessages = null;
    SoapSerializationEnvelope envelope;
    AndroidHttpTransport androidHttpTransport;

    private List<String> listAirlineName = new ArrayList<>();
    private List<String> listAirline = new ArrayList<>();
    private List<String> listCoupen = new ArrayList<>();
    private List<String> listClass = new ArrayList<>();
    private List<String> listAirlineClass = new ArrayList<>();
    private List<String> listVoidCxl = new ArrayList<>();
    private List<String> listBasefare = new ArrayList<>();
    private List<String> listYqtax = new ArrayList<>();
    private List<String> listOtherTaxAmount = new ArrayList<>();
    private List<String> listComRec = new ArrayList<>();
    private List<String> listOrAmount = new ArrayList<>();
    private List<String> listIncflow = new ArrayList<>();
    private List<String> listPlbAmount = new ArrayList<>();
    private List<String> listAcmAdm = new ArrayList<>();
    private List<String> listGst = new ArrayList<>();
    private List<String> listNetAmount = new ArrayList<>();
    private List<String> listBranchid = new ArrayList<>();


    private CustomAdapter mAdapter;

    FormWebservice() {
    }


    /**
     * Set Envelope
     */
    protected void SetEnvelope(String appUrl) {
        url = appUrl + "WinYatra_GetArlnBsFareYqTaxBrnId.asmx";
        try {

            // Creating SOAP envelope
            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            //You can comment that line if your web service is not .NET one.
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            androidHttpTransport = new AndroidHttpTransport(url);
            androidHttpTransport.debug = true;

        } catch (Exception e) {
            System.out.println("Soap Exception---->>>" + e.toString());
        }
    }

    // MethodName variable is define for which webservice function  will call
    public String getConvertedWeight(String webUrl, String MethodName, String companyID, String BranchName,
                                     String startDate, String endDate, String airlineCode, String checkBsp, String checkXo, String checkPurchase, String checkAll, String mobileNo) {


        try {
            SOAP_ACTION = namespace + MethodName;

            //Adding values to request object
            request = new SoapObject(namespace, MethodName);

            //Adding Double value to request object
//            PropertyInfo weightProp =new PropertyInfo();
//            weightProp.setName("CompId");
//            weightProp.setValue(compID);
//            weightProp.setType(double.class);
//            request.addProperty(weightProp);

            //Adding String value to request object

            request.addProperty("CompId", "" + companyID);
            request.addProperty("BranchName", "" + BranchName.trim());
            request.addProperty("StrStrtDt", "" + startDate);
            request.addProperty("StrEndDt", "" + endDate);
            request.addProperty("ArlnCode", "" + airlineCode);
            request.addProperty("StrAll", "" + checkAll);
            request.addProperty("StrTk_bsp_tkt", "" + checkBsp);
            request.addProperty("StrTK_PAD_REC", "" + checkPurchase);
            request.addProperty("StrTK_XO", "" + checkXo);
            request.addProperty("Mob", "" + mobileNo);

            SetEnvelope(webUrl);

            try {
                //SOAP calling webservice
                androidHttpTransport.call(SOAP_ACTION, envelope);

                //Got Webservice response
                String result = envelope.getResponse().toString();
                if (result.equalsIgnoreCase("No Data Found")) {

                    return result;
                } else {

//http://stackoverflow.com/questions/28133531/how-to-store-and-parse-soap-object-response
//http://stackoverflow.com/questions/32969039/getting-values-from-soap-xml-response
                    //    String result = "{'table': [{'AIRLINE':'098','BASFARE':'90'},{'AIRLINE':'98','BASFARE':'90'}]}";


                    JSONObject jsonRootObject = new JSONObject(result);

                    // table= {{"AIRLINE":"098","BASFARE":"90"},{"AIRLINE":"098","BASFARE":"90"}};
                    //
                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = jsonRootObject.optJSONArray("Table");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        listAirline.add(jsonObject.optString("AIRLINE").toString());
                        listCoupen.add(jsonObject.optString("COUPEN").toString());
                       // listClass.add(jsonObject.optString("CLASS").toString());
                        listVoidCxl.add(jsonObject.optString("VOID_CXL").toString());
                        listBasefare.add(jsonObject.getString("BASFARE"));
                        listYqtax.add(jsonObject.getString("YQTAX"));
                        listOtherTaxAmount.add(jsonObject.getString("OtherTaxAmount").toString());
                        listComRec.add(jsonObject.getString("COM_REC").toString());
                        listOrAmount.add(jsonObject.getString("OR_AMT").toString());
                        listIncflow.add(jsonObject.getString("INC_FLOW").toString());
                        listPlbAmount.add(jsonObject.getString("PLB_AMT").toString());
                        listAcmAdm.add(jsonObject.getString("ACM_ADM").toString());
                        //listGst.add(jsonObject.getString("GST").toString());
                        listNetAmount.add(jsonObject.getString("NET_AMT").toString());
                        listBranchid.add(jsonObject.optString("BRANCHID").toString());
                        listAirlineName.add(jsonObject.optString("AIRLINENAME").toString());
                    }
                    return listAirline + "%" + listCoupen + "%" + listVoidCxl  + "%" + listBasefare + "%" + listYqtax + "%" + listOtherTaxAmount + "%" + listComRec + "%" + listOrAmount + "%" + listIncflow + "%" + listPlbAmount+ "%" + listAcmAdm + "%" + listNetAmount+ "%" + listBranchid+ "%" + listAirlineName;
                }

            } catch (Exception e) {
                // TODO: handle exception
                String error = "Something went wrong at server side or wrong input";
                //return e.toString();
                return error;
            }
        } catch (Exception e) {
            // TODO: handle exception
            String error = "Something went wrong at server side or wrong input";
            //return e.toString();
            return error;
        }

    }
    /************************************/
}

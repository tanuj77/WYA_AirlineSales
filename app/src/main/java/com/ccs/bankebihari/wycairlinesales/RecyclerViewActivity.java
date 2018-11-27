package com.ccs.bankebihari.wycairlinesales;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Banke Bihari on 10/6/2016.
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapter mAdapter;
    String resBaseFare, resYqTax, resBranchId;
    TextView textViewTotalNetYqTax, textViewTotalBaseFare;
    ConnectionDetector cd;
    Boolean minusSignBaseFare = false, minusSignYqTax = false;
    Double TotalYqTax = (double) 0.0, TotalBaseFare = (double) 0.0,TotalVoidCxl = (double)0.0,TotalOtherTaxAmount = (double)0.0,TotalComRec = (double)0.0,TotalOrAmount = (double)0.0,TotalIncFlow = (double)0.0,TotalPlbAmount = (double)0.0,TotalAcmAdm=(double)0.0,TotalGst=(double)0.0,TotalNetAmount=(double)0.0;
    int imgsrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
//        textViewTotalNetYqTax = (TextView) findViewById(R.id.tv_toatalyqtax);
//        textViewTotalBaseFare = (TextView) findViewById(R.id.tv_totalbasefare);


        cd = new ConnectionDetector(getApplicationContext());

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
                    RecyclerViewActivity.this.finish();
                }
            });
            // Showing Alert Message
            alertDialog.show();

            return;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("                       Airline Sale Report");


        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter = new CustomAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {
                view.setBackgroundResource(R.color.LightBlue);
                Movie movie = movieList.get(position);

            }

            public void onLongClick(View view, int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(getApplicationContext(), movie.getAirlineCode() + " is selected!", Toast.LENGTH_SHORT).show();
            }
        }));
        prepareMovieData();

    }

    String check = "check";

    public void prepareMovieData() {
        String getintent = getIntent().getStringExtra("webserviceresponse");
        String string = getintent;
        String[] parts = string.split("%");

        String[] response0 = parts[0].split(",");
        String[] response01 = parts[1].split(",");
        String[] response02 = parts[2].split(",");
        String[] response03 = parts[3].split(",");
        String[] response04 = parts[4].split(",");
        String [] response05 = parts[5].split(",");
        String [] response06 = parts[6].split(",");
        String [] response07 = parts[7].split(",");
        String [] response08 = parts[8].split(",");
        String [] response09 = parts[9].split(",");
        String [] response10 = parts[10].split(",");
        String [] response11 = parts[11].split(",");
        String [] response12 = parts[12].split(",");
        String [] response13 = parts[13].split(",");
        //String [] response14 = parts[14].split(",");
       // String [] response15 = parts[15].split(",");

        for (int i = 0; i < response0.length; i++) {

            if (i == 0) {
                String resAirline = response0[i];
                resAirline = resAirline.replace("[", " ");
                resAirline = resAirline.replace("]", " ");

                String resCoupen = response01[i];
                resCoupen = resCoupen.replace("[","");
                resCoupen = resCoupen.replace("]","");

//                String resClass = response02[i];
//                resClass = resClass.replace("[","");
//                resClass = resClass.replace("[","");

                String resVoidCxl = response02[i];
                resVoidCxl = resVoidCxl.replace("[","");
                resVoidCxl = resVoidCxl.replace("[","");

                resBaseFare = response03[i];
                resBaseFare = resBaseFare.replace("[", "  ");
                resBaseFare = resBaseFare.replace("]", "  ");


                resYqTax = response04[i];
                resYqTax = resYqTax.replace("[", " ");
                resYqTax = resYqTax.replace("]", " ");

                String resOtherTaxAmount = response05[i];
                resOtherTaxAmount = resOtherTaxAmount.replace("[","");
                resOtherTaxAmount = resOtherTaxAmount.replace("]","");

                String resComRec = response06[i];
                resComRec = resComRec.replace("[","");
                resComRec = resComRec.replace("]","");

                String resOrAmount = response07[i];
                resOrAmount=resOrAmount.replace("[","");
                resOrAmount=resOrAmount.replace("]","");

                String resIncFlow = response08[i];
                resIncFlow = resIncFlow.replace("[","");
                resIncFlow=resIncFlow.replace("]","");

                String resPlbAmount = response09[i];
                resPlbAmount = resPlbAmount.replace("[","");
                resPlbAmount = resPlbAmount.replace("]","");

                String resAcmAdm = response10[i];
                resAcmAdm = resAcmAdm.replace("[","");
                resAcmAdm = resAcmAdm.replace("]","");

//                String resGst = response11[i];
//                resGst = resGst.replace("[","");
//                resGst = resGst.replace("]","");

                String resNetAmount = response11[i];
                resNetAmount = resNetAmount.replace("[","");
                resNetAmount = resNetAmount.replace("]","");

                resBranchId = response12[i];
                resBranchId = resBranchId.replace("[", " ");
                resBranchId = resBranchId.replace("]", " ");

                String resAirlineName = response13[i];
                resAirlineName = resAirlineName.replace("[","");
                resAirlineName = resAirlineName.replace("]","");

                Movie movie = new Movie(0,resAirline,resCoupen,resVoidCxl, resBaseFare, resYqTax,resOtherTaxAmount,resComRec,resOrAmount,resIncFlow,resPlbAmount,resAcmAdm,resNetAmount,resBranchId,resAirlineName);
                movieList.add(movie);
            } else if (i > 0) {

                String resAirline = response0[i];
                resAirline = resAirline.replace("]", " ");
              ImageView  airlineLogo = (ImageView) findViewById(R.id.airlinelogo);
                switch(resAirline.trim()) {
                    case "6E":
                        imgsrc =  R.drawable.alindigo;
                        break;
                    case "18Z":
                        imgsrc =  R.drawable.alwizz;
                        break;
                    case "2S":
                        imgsrc =  R.drawable.alaircarnival;
                        break;
                    case "2T":
                        imgsrc =  R.drawable.altrujet;
                        break;
                    case "2V":
                        imgsrc =  R.drawable.alamtrak;
                        break;
                    case "3K":
                        imgsrc =  R.drawable.aljetstar;
                        break;
                    case "9H":
                        imgsrc =  R.drawable.almdlrairlline;
                        break;
                    case "9WB":
                        imgsrc =  R.drawable.aljetairways;
                        break;
                    case "9WD":
                        imgsrc =  R.drawable.aljetairways;
                        break;
                    case "AI5":
                        imgsrc =  R.drawable.alairindia;
                        break;
                    case "AIB":
                        imgsrc =  R.drawable.alairindia;
                        break;
                    case "AID":
                        imgsrc =  R.drawable.alairindia;
                        break;
                    case "AK":
                        imgsrc =  R.drawable.alairasia;
                        break;
                    case "BLU":
                        imgsrc =  R.drawable.alblueair;
                        break;
                    case "DFA":
                        imgsrc =  R.drawable.aldefa;
                        break;
                    case "DFI":
                        imgsrc =  R.drawable.aldefa;
                        break;
//                    case "DLC":
//                        imgsrc =  R.drawable.alairasia;
//                        break;
                    case "DN":
                        imgsrc =  R.drawable.alairdeccan;
                        break;
                    case "EJ":
                        imgsrc =  R.drawable.aleasyjet;
                        break;
                    case "ERL":
                        imgsrc =  R.drawable.aleurorail;
                        break;
                    case "EUW":
                        imgsrc =  R.drawable.aleurowings;
                        break;
                    case "FD":
                        imgsrc =  R.drawable.althaiairasia;
                        break;
                    case "G4":
                        imgsrc =  R.drawable.alallegiant;
                        break;
                    case "G8":
                        imgsrc =  R.drawable.algoair;
                        break;
                    case "G9":
                        imgsrc =  R.drawable.alairarabia;
                        break;
                    case "GA":
                        imgsrc =  R.drawable.algaruda;
                        break;
                    case "I":
                        imgsrc =  R.drawable.alairasia;
                        break;
//                    case "l7":
//                        imgsrc =  R.drawable.alairasia;
//                        break;
                    case "ICB":
                        imgsrc =  R.drawable.alindianairlines;
                        break;
                    case "ICD":
                        imgsrc =  R.drawable.alindianairlines;
                        break;
                    case "ICI":
                        imgsrc =  R.drawable.alindianairlines;
                        break;
//                    case "ILC":
//                        imgsrc =  R.drawable.alindianairlines;
//                        break;
//                    case "INS":
//                        imgsrc =  R.drawable.alindianairlines;
//                        break;
                    case "ITD":
                        imgsrc =  R.drawable.alkingfisher;
                        break;
                    case "IX":
                        imgsrc =  R.drawable.alairindiaexpress;
                        break;
                    case "IXO":
                        imgsrc =  R.drawable.alairindiaexpress;
                        break;
                    case "JA":
                        imgsrc =  R.drawable.aljagsonairline;
                        break;
                    case "JT":
                        imgsrc =  R.drawable.allionairlines;
                        break;
                    case "K2":
                        imgsrc =  R.drawable.aleurolot;
                        break;
                    case "K5":
                        imgsrc =  R.drawable.alseaport;
                        break;
                    case "LB":
                        imgsrc =  R.drawable.allloydaereo;
                        break;
                    case "LE":
                        imgsrc =  R.drawable.alaircosta;
                        break;
                    case "MF":
                        imgsrc =  R.drawable.alxiamen;
                        break;
                    case "OP":
                        imgsrc =  R.drawable.alpegaasus;
                        break;
                    case "RYR":
                        imgsrc =  R.drawable.alryanairlines;
                        break;
                    case "S2B":
                        imgsrc =  R.drawable.alsharaairlines;
                        break;
                    case "S2D":
                        imgsrc =  R.drawable.alsharaairlines;
                        break;
                    case "SG":
                        imgsrc =  R.drawable.alspicejet;
                        break;
                    case "SJ":
                        imgsrc =  R.drawable.alspicejet;
                        break;
                    case "TJK":
                        imgsrc =  R.drawable.altajikairlines;
                        break;
                    case "TR":
                        imgsrc =  R.drawable.altigerair;
                        break;
                    case "VA":
                        imgsrc =  R.drawable.alventura;
                        break;
//                    case "VL":
//                        imgsrc =  R.drawable.;
//                        break;
                    case "XY":
                        imgsrc =  R.drawable.alflynas;
                        break;
                    case "Z2":
                        imgsrc =  R.drawable.alairasia;
                        break;

                    case "001":
                        imgsrc =  R.drawable.alamericanairlines;
                        break;
                    case "002":
                        imgsrc =  R.drawable.alcargoitalia;
                        break;
                    case "005":
                        imgsrc =  R.drawable.alcontinentalairline;
                        break;
                    case "006":
                        imgsrc =  R.drawable.aldeltaairlines;
                        break;
                    case "008":
                        imgsrc =  R.drawable.alvistara;
                        break;
                    case "012":
                        imgsrc =  R.drawable.alnorthwest;
                        break;
                    case "014":
                        imgsrc =  R.drawable.alaircanada;
                        break;
                    case "016":
                        imgsrc =  R.drawable.alunitedairlines;
                        break;
                    case "018":
                        imgsrc =  R.drawable.alamericanairlines;
                        break;
                    case "020":
                        imgsrc =  R.drawable.allufthansacargo;
                        break;
                    case "023":
                        imgsrc =  R.drawable.alfedex;
                        break;
                    case "027":
                        imgsrc =  R.drawable.alalaska;
                        break;
                    case "037":
                        imgsrc =  R.drawable.alusairways;
                        break;
                    case "042":
                        imgsrc =  R.drawable.alvarigbrazilian;
                        break;
                    case "043":
                        imgsrc =  R.drawable.aldragon;
                        break;
                    case "045":
                        imgsrc =  R.drawable.allanchile;
                        break;
                    case "047":
                        imgsrc =  R.drawable.altapportugal;
                        break;
                    case "048":
                        imgsrc =  R.drawable.alcyprus;
                        break;
                    case "050":
                        imgsrc =  R.drawable.alolympic;
                        break;
//                    case "053":
//                        imgsrc =  R.drawable.alaer;
//                        break;
                    case "055":
                        imgsrc =  R.drawable.alalitalia;
                        break;
                    case "057":
                        imgsrc =  R.drawable.alairfrance;
                        break;
                    case "058":
                        imgsrc =  R.drawable.alindianairlines;
                        break;
                    case "061":
                        imgsrc =  R.drawable.alairseylleches;
                        break;
                    case "064":
                        imgsrc =  R.drawable.alczeech;
                        break;
                    case "065":
                        imgsrc =  R.drawable.alsaudiarabian;
                        break;
                    case "070":
                        imgsrc =  R.drawable.alsyrianarab;
                        break;
                    case "071":
                        imgsrc =  R.drawable.alethiopian;
                        break;
                    case "072":
                        imgsrc = R.drawable.algulfair;
                        break;
                    case "074":
                        imgsrc =  R.drawable.alklmcargo;
                        break;
                    case "075":
                        imgsrc =  R.drawable.aliberia;
                        break;
                    case "076":
                        imgsrc =  R.drawable.almiddleeast;
                        break;
                    case "077":
                        imgsrc =  R.drawable.alegyptairline;
                        break;
                    case "079":
                        imgsrc =  R.drawable.alphilippine;
                        break;
                    case "080":
                        imgsrc =  R.drawable.allotpolish;
                        break;
                    case "081":
                        imgsrc =  R.drawable.alqatarairways;
                        break;
                    case "082":
                        imgsrc =  R.drawable.albrussels;
                        break;
                    case "083":
                        imgsrc =  R.drawable.alsouthafrican;
                        break;
                    case "086":
                        imgsrc =  R.drawable.alairnewzeland;
                        break;
                    case "090":
                        imgsrc =  R.drawable.alkingfisher;
                        break;
                    case "093":
                        imgsrc =  R.drawable.alkd;
                        break;
                    case "096":
                        imgsrc =  R.drawable.aliran;
                        break;
                    case "098":
                        imgsrc =  R.drawable.alairindia;
                        break;
                    case "105":
                        imgsrc =  R.drawable.alfinair;
                        break;
                    case "106":
                        imgsrc =  R.drawable.alcaribbean;
                        break;
                    case "108":
                        imgsrc =  R.drawable.aliceland;
                        break;
                    case "112":
                        imgsrc =  R.drawable.alchinacargo;
                        break;
                    case "114":
                        imgsrc =  R.drawable.alelal;
                        break;
                    case "115":
                        imgsrc =  R.drawable.aljatairways;
                        break;
                    case "117":
                        imgsrc =  R.drawable.alsasscandinavian;
                        break;
                    case "118":
                        imgsrc =  R.drawable.altaagangola;
                        break;
                    case "119":
                        imgsrc =  R.drawable.alalm;
                        break;

                    case "124":
                        imgsrc =  R.drawable.alalgerie;
                        break;
                    case "125":
                        imgsrc =  R.drawable.albritishairways;
                        break;
                    case "126":
                        imgsrc =  R.drawable.algaruda;
                        break;
                    case "129":
                        imgsrc =  R.drawable.almartinaircargo;
                        break;
                    case "131":
                        imgsrc = R.drawable.aljapanairlines;
                        break;
                    case "133":
                        imgsrc = R.drawable.allasca;
                        break;
                    case "139":
                        imgsrc = R.drawable.alaeromexicocargo;
                        break;
                    case "140":
                        imgsrc = R.drawable.alliat;
                        break;
                    case "141":
                        imgsrc = R.drawable.alflydubai;
                        break;
                    case "147":
                        imgsrc = R.drawable.alroyalairmarco;
                        break;
                    case "148":
                        imgsrc = R.drawable.allibyan;
                        break;
                    case "157":
                        imgsrc = R.drawable.alqatarairways;
                        break;
                    case "160":
                        imgsrc = R.drawable.alcathaypacific;
                        break;
                    case "163":
                        imgsrc = R.drawable.altntairways;
                        break;
                    case "165":
                        imgsrc = R.drawable.aladria;
                        break;
                    case "169":
                        imgsrc = R.drawable.alhahnn;
                        break;
                    case "172":
                        imgsrc = R.drawable.alcargolux;
                        break;
                    case "176":
                        imgsrc = R.drawable.alemirates;
                        break;
                    case "177":
                        imgsrc = R.drawable.alemirates;
                        break;
                    case "178":
                        imgsrc = R.drawable.alemirates;
                        break;
                    case "180":
                        imgsrc = R.drawable.alkorean;
                        break;
                    case "182":
                        imgsrc = R.drawable.almalvhungarian;
                        break;
                    case "183":
                        imgsrc = R.drawable.alvarigbrazilian;
                        break;
                    case "189":
                        imgsrc = R.drawable.aljadecargo;
                        break;
                    case "201":
                        imgsrc = R.drawable.alairjamaica;
                        break;
                    case "202":
                        imgsrc = R.drawable.altaca;
                        break;
                    case "205":
                        imgsrc = R.drawable.alana;
                        break;
                    case "214":
                        imgsrc = R.drawable.alpakistan;
                        break;
                    case "217":
                        imgsrc = R.drawable.althaiairways;
                        break;
                    case "220":
                       imgsrc =  R.drawable.allufthansa;
                        break;
//                    case "228":
//                        imgsrc =  R.drawable.alsia;
//                        break;
                    case "229":
                        imgsrc =  R.drawable.alkuwaitairways;
                        break;
                    case "230":
                        imgsrc =  R.drawable.alcopaairlines;
                        break;
                    case "231":
                        imgsrc =  R.drawable.allauda;
                        break;
                    case "232":
                        imgsrc = R.drawable.almalaysia;
                        break;
                    case "234":
                        imgsrc = R.drawable.aljapanairsystem;
                        break;
                    case "235":
                        imgsrc = R.drawable.alturkish;
                        break;
                    case "236":
                        imgsrc = R.drawable.albritishmidland;
                        break;
                    case "239":
                        imgsrc = R.drawable.alairmauritius;
                        break;
                    case "250":
                        imgsrc = R.drawable.aluzbekistan;
                        break;
                    case "257":
                        imgsrc = R.drawable.alaustrian;
                        break;
                    case "258":
                        imgsrc = R.drawable.alairmadagascar;
                        break;
                    case "265":
                        imgsrc = R.drawable.alfareaster;
                        break;
                    case "266":
                        imgsrc = R.drawable.alltu;
                        break;
                    case "270":
                        imgsrc = R.drawable.altransmediterra;
                        break;
                    case "272":
                        imgsrc = R.drawable.alkalitta;
                        break;
                    case "285":
                        imgsrc = R.drawable.alroyalnepal;
                        break;
                    case "288":
                        imgsrc = R.drawable.alairhongkong;
                        break;
                    case "297":
                        imgsrc = R.drawable.alchinaairline;
                        break;
                    case "301":
                        imgsrc = R.drawable.alglobalaviation;
                        break;
                    case "302":
                        imgsrc = R.drawable.alskywest;
                        break;
                    case "307":
                        imgsrc = R.drawable.alcenturian;
                        break;
                    case "324":
                        imgsrc = R.drawable.alshandong;
                        break;
                    case "330":
                        imgsrc = R.drawable.alfloridawest;
                        break;
                    case "345":
                        imgsrc = R.drawable.alnorthernaircargo;
                        break;
                    case "356":
                        imgsrc = R.drawable.alcargoluxitalia;
                        break;
                    case "369":
                        imgsrc = R.drawable.alatlasair;
                        break;
                    case "378":
                        imgsrc = R.drawable.alcaymanairways;
                        break;
                    case "390":
                        imgsrc = R.drawable.alaegean;
                        break;

                    case "403":
                        imgsrc = R.drawable.alpolarair;
                        break;
                    case "404":
                        imgsrc = R.drawable.alarrowair;
                        break;
                    case "406":
                        imgsrc = R.drawable.alupsaircargo;
                        break;
                    case "416":
                        imgsrc = R.drawable.alnationalaircargo;
                        break;
                    case "421":
                        imgsrc = R.drawable.alsiberiaairline;
                        break;
                    case "423":
                        imgsrc = R.drawable.aldhlaviation;
                        break;
                    case "465":
                        imgsrc = R.drawable.alairastana;
                        break;
                    case "479":
                        imgsrc = R.drawable.alshenzen;
                        break;
                    case "507":
                        imgsrc = R.drawable.alaeroflot;
                        break;
                    case "512":
                        imgsrc = R.drawable.alroyaljordanian;
                        break;
                    case "526":
                        imgsrc = R.drawable.alsouthwest;
                        break;
                    case "529":
                        imgsrc = R.drawable.alcielos;
                        break;
                    case "549":
                        imgsrc = R.drawable.alabsaaerolinhas;
                        break;
//                    case "552":
//                        imgsrc = R.drawable.almari;
//                        break;
                    case "564":
                        imgsrc = R.drawable.alsunexpress;
                        break;
                    case "566":
                        imgsrc = R.drawable.alukraine;
                        break;
                    case "572":
                        imgsrc = R.drawable.alairmoldova;
                        break;
                    case "575":
                        imgsrc = R.drawable.alcoyne;
                        break;
                    case "580":
                        imgsrc = R.drawable.alairbridge;
                        break;
                    case "586":
                        imgsrc = R.drawable.aljetairways;
                        break;
                    case "587":
                        imgsrc = R.drawable.aljetairways;
                        break;
                    case "588":
                        imgsrc = R.drawable.aljetairways;
                        break;
                    case "589":
                        imgsrc =  R.drawable.aljetairways;
                        break;
                    case "593":
                        imgsrc = R.drawable.alfinair;
                        break;
                    case "603":
                        imgsrc =  R.drawable.alsrilankan;
                        break;
                    case "604":
                        imgsrc =  R.drawable.alcameroonairlines;
                        break;
                    case "607":
                        imgsrc =  R.drawable.alethiadairways;
                        break;
                    case "608":
                        imgsrc =  R.drawable.alethiadairways;
                        break;
                    case "609":
                        imgsrc =  R.drawable.alethiadairways;
                        break;
                    case "614":
                        imgsrc =  R.drawable.allloydaereo;
                        break;
                    case "615":
                        imgsrc =  R.drawable.aldhlaviation;
                        break;
                    case "618":
                        imgsrc =  R.drawable.alsingaporeairline;
                        break;
                    case "623":
                        imgsrc =  R.drawable.albulgaria;
                        break;
                    case "624":
                        imgsrc =  R.drawable.alpegaasus;
                        break;
                    case "629":
                        imgsrc =  R.drawable.alsilkair;
                        break;
                    case "631":
                        imgsrc =  R.drawable.alairgreenland;
                        break;
                    case "635":
                        imgsrc =  R.drawable.alyemenia;
                        break;
                    case "643":
                        imgsrc =  R.drawable.alairmalta;
                        break;
                    case "652":
                        imgsrc =  R.drawable.alregentairways;
                        break;
                    case "656":
                        imgsrc =  R.drawable.alairniugini;
                        break;
                    case "657":
                        imgsrc =  R.drawable.alairbaltic;
                        break;
                    case "672":
                        imgsrc =  R.drawable.alroyalbrunei;
                        break;
                    case "675":
                        imgsrc =  R.drawable.alairmacau;
                        break;
                    case "695":
                        imgsrc =  R.drawable.alevaiairways;
                        break;
//                    case "700":
//                        imgsrc =  R.drawable.al;
//                        break;
                    case "706":
                        imgsrc =  R.drawable.alkenyaairways;
                        break;
                    case "716":
                        imgsrc =  R.drawable.almng;
                        break;
                    case "724":
                        imgsrc =  R.drawable.alswiss;
                        break;
                    case "729":
                        imgsrc =  R.drawable.altampa;
                        break;
                    case "731":
                        imgsrc =  R.drawable.alxiamen;
                        break;
                    case "737":
                        imgsrc =  R.drawable.alsataair;
                        break;
                    case "738":
                        imgsrc =  R.drawable.alvietnam;
                        break;
                    case "745":
                        imgsrc =  R.drawable.alairberlin;
                        break;
                    case "757":
                        imgsrc =  R.drawable.alswiss;
                        break;
                    case "771":
                        imgsrc =  R.drawable.alazerbhaijan;
                        break;
                    case "774":
                        imgsrc =  R.drawable.alshanghai;
                        break;
                    case "779":
                        imgsrc =  R.drawable.alusbangla;
                        break;
                    case "781":
                        imgsrc =  R.drawable.alchinaeastern;
                        break;
                    case "784":
                        imgsrc =  R.drawable.alchinasouthern;
                        break;
                    case "786":
                        imgsrc =  R.drawable.albhutan;
                        break;
                    case "787":
                        imgsrc =  R.drawable.aldrukair;
                        break;
                    case "800":
                        imgsrc =  R.drawable.algrandstarcargo;
                        break;
                    case "803":
                        imgsrc =  R.drawable.almandarin;
                        break;
                    case "816":
                        imgsrc =  R.drawable.almalinto;
                        break;
                    case "817":
                        imgsrc =  R.drawable.almihinlanka;
                        break;
                    case "825":
                        imgsrc =  R.drawable.alshanghai;
                        break;
                    case "831":
                        imgsrc =  R.drawable.albangkokairways;
                        break;
                    case "851":
                        imgsrc =  R.drawable.alhongkong;
                        break;
                    case "858":
                        imgsrc =  R.drawable.alafricawest;
                        break;
                    case "862":
                        imgsrc =  R.drawable.alatlanticsouthe;
                        break;
                    case "865":
                        imgsrc =  R.drawable.almasair;
                        break;
                    case "870":
                        imgsrc =  R.drawable.alaerosvit;
                        break;
                    case "871":
                        imgsrc =  R.drawable.alyangtzeriver;
                        break;
                    case "873":
                        imgsrc =  R.drawable.alaerounion;
                        break;
                    case "876":
                        imgsrc =  R.drawable.alsichuanairline;
                        break;
                    case "880":
                        imgsrc =  R.drawable.alhainan;
                        break;
                    case "881":
                        imgsrc =  R.drawable.alcondor;
                        break;
                    case "886":
                        imgsrc =  R.drawable.alcomair;
                        break;
//                    case "888":
//                        imgsrc =  R.drawable.alswiss;
//                        break;
//                    case "901":
//                        imgsrc =  R.drawable.altabcargo;
//                        break;
                    case "907":
                        imgsrc =  R.drawable.alairarmenia;
                        break;
                    case "909":
                        imgsrc =  R.drawable.althaismile;
                        break;
                    case "910":
                        imgsrc =  R.drawable.alavient;
                        break;
                    case "928":
                        imgsrc =  R.drawable.albouraq;
                        break;
                    case "932":
                        imgsrc =  R.drawable.alverginatlantic;
                        break;
                    case "933":
                        imgsrc =  R.drawable.alnipponcargo;
                        break;
                    case "957":
                        imgsrc =  R.drawable.altambrazilain;
                        break;
                    case "958":
                        imgsrc =  R.drawable.alinselaircargo;
                        break;
                    case "960":
                        imgsrc =  R.drawable.alestonian;
                        break;
                    case "976":
                        imgsrc =  R.drawable.alaeromexpress;
                        break;
                    case "986":
                        imgsrc =  R.drawable.alisland;
                        break;
                    case "988":
                        imgsrc =  R.drawable.alasiana;
                        break;
                    case "989":
                        imgsrc =  R.drawable.algreatwall;
                        break;
                    case "996":
                        imgsrc =  R.drawable.alaireuropacargo;
                        break;
                    case "997":
                        imgsrc =  R.drawable.albimanbangladesh;
                        break;
                    case "999":
                        imgsrc =  R.drawable.alairchina ;
                        break;

                    default:
                        imgsrc = R.color.White;
                }

                String resCoupen = response01[i];
                resCoupen = resCoupen.replace("[","");
                resCoupen = resCoupen.replace("]","");

//                String resClass = response02[i];
//                resClass = resClass.replace("[","");
//                resClass = resClass.replace("]","");

                String resVoidCxl = response02[i];
                resVoidCxl = resVoidCxl.replace("[","");
                resVoidCxl = resVoidCxl.replace("]","");
                double dresVoidCxl = Double.parseDouble(resVoidCxl);
                TotalVoidCxl = TotalVoidCxl +  dresVoidCxl;

                resBaseFare = response03[i];
                resBaseFare = resBaseFare.replace("]", " ");
                Double dresBaseFare = Double.parseDouble(resBaseFare);
//                if (dresBaseFare < 0) { //////////checking minus(-) sign
//                    minusSignBaseFare = true;
//                }
//               // Double removeMinusSignBaseFare = Math.abs(dresBaseFare);////It treats minus(-) sign as a Plus(+) sign
                TotalBaseFare = TotalBaseFare + dresBaseFare;


                resYqTax = response04[i];
                resYqTax = resYqTax.replace("]", " ");
                Double dresNetYqTax = Double.parseDouble(resYqTax);
//                if (dresNetYqTax < 0) { //////////checking minus(-) sign
//                    minusSignYqTax = true;
//                }
               // Double removeMinusSignYqTax = Math.abs(dresNetYqTax);//////It treats minus(-) sign as a Plus(+) sign
                TotalYqTax = TotalYqTax + dresNetYqTax;


                String resOtherTaxAmount = response05[i];
                resOtherTaxAmount = resOtherTaxAmount.replace("[","");
                resOtherTaxAmount = resOtherTaxAmount.replace("]","");
                Double dresOtherTaxAmount = Double.parseDouble(resOtherTaxAmount);
                TotalOtherTaxAmount =TotalOtherTaxAmount + dresOtherTaxAmount;

                String resComRec = response06[i];
                resComRec = resComRec.replace("[","");
                resComRec = resComRec.replace("]","");
                Double dresComRec = Double.parseDouble(resComRec);
                TotalComRec = TotalComRec + dresComRec;

                String resOrAmount = response07[i];
                resOrAmount=resOrAmount.replace("[","");
                resOrAmount=resOrAmount.replace("]","");
                Double dresOrAmount = Double.parseDouble(resOrAmount);
                TotalOrAmount = TotalOrAmount + dresOrAmount;

                String resIncFlow = response08[i];
                resIncFlow = resIncFlow.replace("[","");
                resIncFlow=resIncFlow.replace("]","");
                Double dresIncFlow = Double.parseDouble(resIncFlow);
                TotalIncFlow = TotalIncFlow + dresIncFlow;

                String resPlbAmount = response09[i];
                resPlbAmount = resPlbAmount.replace("[","");
                resPlbAmount = resPlbAmount.replace("]","");
                Double dresPlbAmount = Double.parseDouble(resPlbAmount);
                TotalPlbAmount = TotalPlbAmount + dresPlbAmount;

                String resAcmAdm = response10[i];
                resAcmAdm = resAcmAdm.replace("[","");
                resAcmAdm = resAcmAdm.replace("]","");
                Double dresAcmAdm = Double.parseDouble(resAcmAdm);
                TotalAcmAdm = TotalAcmAdm + dresAcmAdm;

//                String resGst = response11[i];
//                resGst = resGst.replace("[","");
//                resGst = resGst.replace("]","");
//                Double dresGst = Double.parseDouble(resGst);
//                TotalGst = TotalGst + dresGst;

                String resNetAmount = response11[i];
                resNetAmount = resNetAmount.replace("[","");
                resNetAmount = resNetAmount.replace("]","");
                Double dresNetAmount = Double.parseDouble(resNetAmount);
                TotalNetAmount = TotalNetAmount + dresNetAmount;

                resBranchId = response12[i];
                resBranchId = resBranchId.replace("[", " ");
                resBranchId = resBranchId.replace("]", " ");

                String resAirlineName = response13[i];
                resAirlineName = resAirlineName.replace("[","");
                resAirlineName = resAirlineName.replace("]","");

                Movie movie = new Movie(imgsrc,resAirline,resCoupen,resVoidCxl, resBaseFare, resYqTax,resOtherTaxAmount,resComRec,resOrAmount,resIncFlow,resPlbAmount,resAcmAdm,resNetAmount,resBranchId,resAirlineName);
                movieList.add(movie);
            }

        }
        NumberFormat format = new DecimalFormat("###.#####");

        String stringTotalBaseFare = format.format(TotalBaseFare);
        String stringTotalYqTax = format.format(TotalYqTax);
        String stringTotalVoidCxl = format.format(TotalVoidCxl);
        String stringTotalOtherTaxAmount = format.format(TotalOtherTaxAmount);
        String stringTotalComRec = format.format(TotalComRec);
        String stringTotalOrAmount = format.format(TotalOrAmount);
        String stringTotalIncFlow = format.format(TotalIncFlow);
String stringTotalPlbAmount = format.format(TotalPlbAmount);
String stringTotalAcmAdm  =format.format(TotalAcmAdm);
//String stringTotalGst = format.format(TotalGst);
String stringTotalNetAmount = format.format(TotalNetAmount);


//        if (minusSignYqTax) {
//          //  textViewTotalNetYqTax.setText("-" + TotalYqTax + ".00");
//            stringTotalYqTax = "-"+stringTotalYqTax;
//        } else {
//           // textViewTotalNetYqTax.setText("" + TotalYqTax + ".00");
//            stringTotalYqTax = stringTotalYqTax;
//        }
//        if (minusSignBaseFare) {
//           // textViewTotalBaseFare.setText("-" + TotalBaseFare + ".00");
//            stringTotalBaseFare = "-"+stringTotalBaseFare;
//        } else {
//            //textViewTotalBaseFare.setText("" + TotalBaseFare + ".00");
//            stringTotalBaseFare = stringTotalBaseFare;
//        }

        Movie movie = new Movie(0,"Total","",stringTotalVoidCxl, stringTotalBaseFare, stringTotalYqTax,stringTotalOtherTaxAmount,stringTotalComRec,stringTotalOrAmount,stringTotalIncFlow,stringTotalPlbAmount,stringTotalAcmAdm,stringTotalNetAmount,"","");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

}
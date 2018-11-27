package com.ccs.bankebihari.wycairlinesales;

/**
 * Created by Banke Bihari on 10/5/2016.
 */

public class Movie {
    private  int imgsrc;
    private String airlineCode,coupen,airlineClass,voidCxl,baseFare,yqTax,otherTaxAmnt,comRec,orAmount,incFlow,plbAmount,acmAdm,gst,netAmount;
    private String branch;
    private  String airlineName;

    public Movie() {
    }

    public Movie(int imgsrc,String airlineCode,String coupen,String voidCxl, String baseFare, String yqTax,String otherTaxAmnt,String comRec,String orAmount,String incFlow,String plbAmount,String acmAdm,String netAmount, String  branch,String airlineName) {
       this.imgsrc=imgsrc;
        this.airlineCode = airlineCode;
        this.coupen = coupen;
       // this.airlineClass = airlineClass;
        this.voidCxl = voidCxl;
        this.baseFare = baseFare;
        this.yqTax = yqTax;
        this.otherTaxAmnt = otherTaxAmnt;
        this.comRec = comRec;
        this.orAmount = orAmount;
        this.incFlow = incFlow;
        this.plbAmount = plbAmount;
        this.acmAdm = acmAdm;
        //this.gst=gst;
        this.netAmount = netAmount;
        this.branch = branch;
        this.airlineName=airlineName;
    }

    public int getImage() {
        return imgsrc;
    }

    public void setImage(int imgsrc) {
        this.imgsrc = imgsrc;
    }


    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }



    public String getCoupen() {
        return coupen;
    }

    public void setCoupen(String coupen) {
        this.coupen = coupen;
    }

//    public String getAirlineClass() {
//        return airlineClass;
//    }
//
//    public void setAirlineClass(String airlineClass) {
//        this.airlineClass = airlineClass;
//    }


    public String getVoidCxl() {
        return voidCxl;
    }

    public void setVoidCxl(String voidCxl) {
        this.voidCxl = voidCxl;
    }




    public String getBaseFare() {
        String var =String.valueOf(baseFare);
        return var;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }


    public String getYqTax() {
        String var =String.valueOf(yqTax);
        return var;
    }

    public void setYqTax(String yqTax) {
        this.yqTax = yqTax;
    }


    public String getOtherTaxAmnt() {
        return otherTaxAmnt;
    }

    public void setOtherTaxAmnt(String otherTaxAmnt) {
        this.otherTaxAmnt = otherTaxAmnt;
    }


    public String getComRec() {
        return comRec;
    }

    public void setComRec(String comRec) {
        this.comRec = comRec;
    }



    public String getOrAmount() {
        return orAmount;
    }

    public void setOrAmount(String orAmount) {
        this.orAmount = orAmount;
    }


    public String getIncFlow() {
        return incFlow;
    }

    public void setIncFlow(String incFlow) {
        this.incFlow = incFlow;
    }


    public String getPlbAmount() {
        return plbAmount;
    }

    public void setPlbAmount(String plbAmount) {
        this.plbAmount = plbAmount;
    }


//    public String getgst() {
//        return gst;
//    }
//
//    public void setgst(String gst) {
//        this.gst = gst;
//    }


    public String getAcmAdm() {
        return acmAdm;
    }

    public void setAcmAdm(String acmAdm) {
        this.acmAdm = acmAdm;
    }


    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }



    public String getBranch() {
        return branch;
    }

    public void setBranch(String name) {
        this.branch = name;
    }


    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }


}

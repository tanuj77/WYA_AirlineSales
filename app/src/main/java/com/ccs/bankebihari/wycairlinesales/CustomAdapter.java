package com.ccs.bankebihari.wycairlinesales;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Banke Bihari on 10/6/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView branch, baseFare, yqTax, airlineCode,coupen,arlnClass,voidCxl,otherTaxAmnt,comRec,orAmnt,incFlow,plbAmnt,acmAdm,gst,netAmount,total,airlineName;
        public ImageView airlineLogo;

        public MyViewHolder(View view) {
            super(view);
            airlineLogo = (ImageView) view.findViewById(R.id.airlinelogo);
            airlineCode = (TextView) view.findViewById(R.id.tv_airlinecode);
            coupen = (TextView) view.findViewById(R.id.tv_coupen);
           // arlnClass = (TextView) view.findViewById(R.id.tv_class);
            voidCxl = (TextView) view.findViewById(R.id.tv_voidcxl);
            baseFare = (TextView) view.findViewById(R.id.tv_basefare);
            yqTax = (TextView) view.findViewById(R.id.tv_yqtax);
            otherTaxAmnt = (TextView) view.findViewById(R.id.tv_otherTaxAmnt);
            comRec = (TextView) view.findViewById(R.id.tv_comRec);
            orAmnt = (TextView) view.findViewById(R.id.tv_orAmnt);
            incFlow = (TextView) view.findViewById(R.id.tv_incFlow);
            plbAmnt = (TextView) view.findViewById(R.id.tv_plbAmnt);
            acmAdm = (TextView) view.findViewById(R.id.tv_acmAdm);
          //  gst=(TextView) view.findViewById(R.id.tv_gst);
            netAmount = (TextView) view.findViewById(R.id.tv_netAmnt);
            branch = (TextView) view.findViewById(R.id.tv_branch);
            airlineName = (TextView) view.findViewById(R.id.tv_airlineName);
        }
    }


    public CustomAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.LightGrey);
        } else {
            holder.itemView.setBackgroundResource(R.color.White);
        }

        Movie movie = moviesList.get(position);
        holder.airlineLogo.setImageResource(movie.getImage());
        holder.airlineCode.setText(movie.getAirlineCode());
        holder.coupen.setText(movie.getCoupen());
       // holder.arlnClass.setText(movie.getAirlineClass());
        holder.voidCxl.setText(movie.getVoidCxl());
        holder.baseFare.setText(movie.getBaseFare());
        holder.yqTax.setText(movie.getYqTax());
        holder.otherTaxAmnt.setText(movie.getOtherTaxAmnt());
        holder.comRec.setText(movie.getComRec());
        holder.orAmnt.setText(movie.getOrAmount());
        holder.incFlow.setText(movie.getIncFlow());
        holder.plbAmnt.setText(movie.getPlbAmount());
        holder.acmAdm.setText(movie.getAcmAdm());
       // holder.gst.setText(movie.getgst());
        holder.netAmount.setText(movie.getNetAmount());
        holder.branch.setText(movie.getBranch());
        holder.airlineName.setText(movie.getAirlineName());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
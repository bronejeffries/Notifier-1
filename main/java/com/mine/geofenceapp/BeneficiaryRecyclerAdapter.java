package com.mine.geofenceapp;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


public class BeneficiaryRecyclerAdapter extends RecyclerView.Adapter<BeneficiaryRecyclerAdapter.BeneficiaryViewHolder>  {

    private ArrayList<Beneficiary> listBeneficiary;
    public ImageView overflow;
    private Context mContext;


    public BeneficiaryRecyclerAdapter(ArrayList<Beneficiary> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;


    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewAddress;
        public AppCompatTextView textViewCountry;
        public  ImageView overflow;

        public BeneficiaryViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewAddress = (AppCompatTextView) view.findViewById(R.id.textViewAddress);
            textViewCountry = (AppCompatTextView) view.findViewById(R.id.textViewCountry);

        }


    }




    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beneficiary_recycler, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {
        holder.textViewName.setText(listBeneficiary.get(position).getName());
        holder.textViewEmail.setText(listBeneficiary.get(position).getEmail());
        holder.textViewAddress.setText(listBeneficiary.get(position).getAddress());
        holder.textViewCountry.setText(listBeneficiary.get(position).getCountry());


    }


    @Override
    public int getItemCount() {
        return listBeneficiary.size();
    }
    public void setFilter(ArrayList<Beneficiary> newList){
        listBeneficiary= new ArrayList<>();
        listBeneficiary.addAll(newList);
        notifyDataSetChanged();
    }


}
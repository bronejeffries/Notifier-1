package com.mine.geofenceapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mine.geofenceapp.BeneficiaryRecyclerAdapter;
import com.mine.geofenceapp.DatabaseHelper;

import java.util.ArrayList;


public class BeneficiaryListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private AppCompatActivity activity = BeneficiaryListActivity.this;
    Context context = BeneficiaryListActivity.this;
    private RecyclerView recyclerViewBeneficiary;
    private ArrayList<Beneficiary> listBeneficiary;
    private BeneficiaryRecyclerAdapter beneficiaryRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    SearchView searchBox;
    private ArrayList<Beneficiary> filteredList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initObjects();

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("NAME")) {

            //get all needed extras intent

            int id = getIntent().getExtras().getInt("ID");
            String email = getIntent().getExtras().getString("EMAIL");
            String address = getIntent().getExtras().getString("ADDRESS");
            String country = getIntent().getExtras().getString("COUNTRY");



        }else{

            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();

        }

    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewBeneficiary = (RecyclerView) findViewById(R.id.recyclerViewBeneficiary);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listBeneficiary = new ArrayList<>();
        beneficiaryRecyclerAdapter = new BeneficiaryRecyclerAdapter(listBeneficiary, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBeneficiary.setLayoutManager(mLayoutManager);
        recyclerViewBeneficiary.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBeneficiary.setHasFixedSize(true);
        recyclerViewBeneficiary.setAdapter(beneficiaryRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulist,menu);
        MenuItem search=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newCtegory:
                databaseHelper.DeleteAll();
                initObjects();
                Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Beneficiary> newList=new ArrayList<>();
        for(Beneficiary beneficiary:listBeneficiary){
            String name = beneficiary.getName().toLowerCase();
            String email=beneficiary.getEmail().toLowerCase();
            String reason=beneficiary.getAddress().toLowerCase();
            if(name.contains(newText)||email.contains(newText)||reason.contains(newText)){
                newList.add(beneficiary);
            }
        }
        beneficiaryRecyclerAdapter.setFilter(newList);
        return  true;
    }





    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listBeneficiary.clear();
                listBeneficiary.addAll(databaseHelper. getAllBeneficiary());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                beneficiaryRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }



}
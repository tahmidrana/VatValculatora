package com.example.tahmi.androidsmartui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textview_services_list;
    Spinner financial_year;
    Spinner vat_rate;
    Spinner services_list;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview_services_list = (TextView) findViewById(R.id.textview_services_list);

        financial_year = (Spinner) findViewById(R.id.financial_year);
        adapter = ArrayAdapter.createFromResource(this, R.array.financial_year, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        financial_year.setAdapter(adapter);

        vat_rate = (Spinner) findViewById(R.id.vat_rate);
        adapter = ArrayAdapter.createFromResource(this, R.array.vat_rate, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vat_rate.setAdapter(adapter);

        //services_list.setVisibility(View.INVISIBLE);
        //textview_services_list.setVisibility(View.INVISIBLE);
        services_list = (Spinner) findViewById(R.id.services_list);
        adapter = ArrayAdapter.createFromResource(this, R.array.services_list, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        services_list.setAdapter(adapter);

        financial_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), adapterView.getItemIdAtPosition(i) + " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

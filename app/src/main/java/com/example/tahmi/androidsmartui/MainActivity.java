package com.example.tahmi.androidsmartui;

import android.content.DialogInterface;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textview_services_list;
    private Spinner financial_year;
    private Spinner vat_rate;
    private Spinner services_list;
    private Button btn_calculate;
    private EditText editText_price;
    private ArrayAdapter<CharSequence> adapter;
    private int financial_year_pos;
    private int services_list_pos;
    private int vat_rate_pos;
    private boolean vat_inclusive;
    private double vat_rate_val;
    private double vat;
    private double price;
    private double result_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vat_inclusive = false;
        main_function();
    }

    public void main_function() {
        textview_services_list = (TextView) findViewById(R.id.textview_services_list);
        financial_year = (Spinner) findViewById(R.id.financial_year); //financial year spinner
        vat_rate = (Spinner) findViewById(R.id.vat_rate);  //vat rate spinner
        services_list = (Spinner) findViewById(R.id.services_list); //services list spinner
        btn_calculate = (Button) findViewById(R.id.btn_calculate);


        adapter = ArrayAdapter.createFromResource(this, R.array.financial_year, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        financial_year.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.vat_rate, android.R.layout.simple_spinner_dropdown_item);
        vat_rate.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.services_list, R.layout.textview);
        services_list.setAdapter(adapter);


        financial_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), adapterView.getItemIdAtPosition(i) + " is selected", Toast.LENGTH_LONG).show();
                financial_year_pos = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vat_rate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String vatRate = adapterView.getSelectedItem().toString();
                if(vatRate.equals("Truncated")){
                    services_list.setVisibility(View.VISIBLE);
                    textview_services_list.setVisibility(View.VISIBLE);
                    vat_rate_pos = adapterView.getSelectedItemPosition();
                } else {
                    services_list.setVisibility(View.GONE);
                    textview_services_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(vat_rate_pos == 1 || vat_rate_pos == 0){
            services_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    services_list_pos = adapterView.getSelectedItemPosition();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vat_calculator();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.radioButton_vat_inclusive:
                vat_inclusive = true;
                break;
            case R.id.radioButton_vat_exclusive:
                vat_inclusive = false;
                break;
        }
    }

    public void vat_calculator(){
        editText_price = (EditText) findViewById(R.id.editText_price);
        try {
            price = Double.parseDouble(editText_price.getText().toString());
            if (vat_rate_pos == 0) {
                vat_rate_val = 15.0;
            } else if (vat_rate_pos == 1) {
                switch (services_list_pos) {
                    case 0:
                        vat_rate_val = 4.5;
                        break;
                    case 1:
                        vat_rate_val = 7.5;
                        break;
                    case 2:
                        vat_rate_val = 7.5;
                        break;
                    case 3:
                        vat_rate_val = 3.0;
                        break;
                    case 4:
                        vat_rate_val = 7.5;
                        break;
                    case 5:
                        vat_rate_val = 4.0;
                        break;
                    case 6:
                        vat_rate_val = 4.0;
                        break;
                    case 7:
                        vat_rate_val = 2.25;
                        break;
                    case 8:
                        vat_rate_val = 4.0;
                        break;
                    case 9:
                        vat_rate_val = 7.5;
                        break;
                    case 10:
                        vat_rate_val = 1.5;
                        break;
                    case 11:
                        vat_rate_val = 2.5;
                        break;
                    case 12:
                        vat_rate_val = 4.5;
                        break;
                }
            }

            vat = (price * vat_rate_val) / 100;
            result_price = price + vat;

            if (vat_inclusive) {
                vat = price * (vat_rate_val / 115);
                result_price = price - vat;
            }
            DecimalFormat formatter = new DecimalFormat("#.##");
            //String final_result = Double.toString(result_price);
            //String final_vat = Double.toString(vat);
            Double final_result = Double.valueOf(formatter.format(result_price));
            Double final_vat = Double.valueOf(formatter.format(vat));
            //Toast.makeText(getApplicationContext(), "Vat amount: " + final_vat + "\nPrice: " + final_result, Toast.LENGTH_LONG).show();

            AlertDialog.Builder messager = new AlertDialog.Builder(MainActivity.this);
            messager.setTitle("Result");
            messager.setMessage("Vat amount: " + final_vat + "\nValue: " + final_result);
            messager.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            messager.show();

        } catch(Exception e){

        }
    }
}

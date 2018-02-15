package com.example.piano.invoicetotalapp_itc162_classproject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.widget.TextView.*;

public class InvoiceTotalApp_itc162_ClassProject_Activity extends AppCompatActivity
implements OnEditorActionListener{

    // define variables for the widgets - input
    private EditText inputSubtotal;
    private EditText inputDiscountPercent;
    // define variables for the widgets - output
    private TextView showDiscountAmount;
    private TextView totalAmount;
    private Button resetButton;

    // define the SharedPreferences objects
    private SharedPreferences saveSubtotal;
    private SharedPreferences saveDiscountPercent;

    //define instance saved variable
    private String subtotalString ="";
    private String discountPercentString = "";
    //private float discountPercent = .10f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_total_app_itc162__class_project_);

        //get references to the widgets - input
        inputSubtotal = (EditText) findViewById(R.id.inputSubtotal);
        inputDiscountPercent = (EditText) findViewById(R.id.inputDiscountPercent);
        //get references to the widgets - output
        showDiscountAmount = (TextView)findViewById(R.id.showDiscountAmount);
        totalAmount = (TextView)findViewById(R.id.totalAmount);
        resetButton = (Button)findViewById(R.id.resetButton);

        // set listeners for all inputs
        inputSubtotal.setOnEditorActionListener(this);
        inputDiscountPercent.setOnEditorActionListener(this);

        //get SharePreference objects
        saveSubtotal = getSharedPreferences("saveSubtotal", MODE_PRIVATE);
        saveDiscountPercent = getSharedPreferences("saveDiscountPercent", MODE_PRIVATE);
    }
    @Override
    public void onPause(){
        super.onPause();
        //save the instance variable
        SharedPreferences.Editor editSubtotal = saveSubtotal.edit();
        SharedPreferences.Editor editDiscountAmount = saveDiscountPercent.edit();
        editSubtotal.putString("subtotalString", subtotalString);
        editDiscountAmount.putString("discountPercentString", discountPercentString);
        editSubtotal.commit();
        editDiscountAmount.commit();
    }

    // 2/14/2018 displaying the menu
    public boolean onCreateOptionMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    // 2/14/2018 handle the menu event
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;

            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }

    public void computAndDisplay(){
        //get the input Subtotal
        subtotalString = inputSubtotal.getText().toString();
        float mySubtotal;
        //if input subtotalString is empty, mytotal will be zero//else if it has value, pass it over to the formula below
        if (subtotalString.equals("")){
            mySubtotal = 0;
        }else{
            mySubtotal = Float.parseFloat(subtotalString);
        }
        //get the input discount percent
        discountPercentString = inputDiscountPercent.getText().toString();
        float myDiscount;
        //if input discountPercentString is empty, mytotal will be zero//else if it has value, pass it over to the formula below
        if (discountPercentString.equals("")){
            myDiscount = 0;
        }else{
            myDiscount = Float.parseFloat(discountPercentString);
        }

        //computing DiscountAmount and Total
        float myDiscountAmount = mySubtotal * (myDiscount/100);
        float myTotal = mySubtotal - myDiscountAmount;

        //Display results with formatting discountPercent
        NumberFormat dollars =  NumberFormat.getCurrencyInstance();
        showDiscountAmount.setText(dollars.format(myDiscountAmount));
        totalAmount.setText(dollars.format(myTotal));

        //Display input value with percentage formatting
        NumberFormat rate = NumberFormat.getPercentInstance();
        inputDiscountPercent.setText(rate.format(myDiscount/100));
    }
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE ||
                i == EditorInfo.IME_ACTION_UNSPECIFIED){
            computAndDisplay();
        }
        return false;
    }
    // for the clear button
    public void Clear(View clear){
        inputSubtotal.setText("");
        inputDiscountPercent.setText("");
        showDiscountAmount.setText("");
        totalAmount.setText("");
    }
    //just for recommit
}

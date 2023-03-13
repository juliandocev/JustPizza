package com.julidot.justpizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Browser;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.julidot.justpizza.databinding.ActivityMainBinding;
import com.julidot.justpizza.databinding.ActivityRecapitulationBinding;

public class RecapitulationActivity extends AppCompatActivity {

    private ActivityRecapitulationBinding binding;
    TextView tvOrderSumUp;
    EditText etAddress;
    RadioGroup paymentMethod;
    RadioButton rbPaypal;
    RadioButton rbCreditCard;
    LinearLayout llCreditCardForm;

    Boolean isPaypal = true;
    Boolean isCreditCard = false;
    String url = "https://www.paypal.com/";

    String clientName;
    String order;
    String quantity;
    Boolean mushrooms;
    Boolean corn;
    Boolean pickles;
    Boolean justPizza;
    Boolean pizzaWithIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecapitulationBinding.inflate(getLayoutInflater());

        if(savedInstanceState != null){
            isPaypal = savedInstanceState.getBoolean("rbPaypalState");
            isCreditCard = savedInstanceState.getBoolean("rbCreditCardState");
        }

        setContentView(binding.getRoot());

        tvOrderSumUp = binding.tvOrderSumupRecapitulation;
        etAddress = binding.etAddressRecapitulation;
        paymentMethod = binding.rgPaymentMethod;
        rbPaypal = binding.rbPaypal;
        rbCreditCard = binding.rbCreditCard;
        llCreditCardForm = binding.llCreditCardForm;


        Bundle extras = getIntent().getExtras();



        if (extras != null) {
            order = extras.getString("order");
            clientName = extras.getString("clientName");
            quantity = extras.getString("quantity");
            mushrooms = extras.getBoolean("mushrooms");
            corn = extras.getBoolean("corn");
            pickles = extras.getBoolean("pickles");
            justPizza = extras.getBoolean("justPizza");
            pizzaWithIngredients = extras.getBoolean("pizzaWithIngredients");


            // Set the views
            tvOrderSumUp.setText(order);
            etAddress.setText(clientName);

        }

        // Set default view
        initializeViews();


        paymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == rbCreditCard.getId()){
                    llCreditCardForm.setVisibility(View.VISIBLE);
                }
                else {
                    llCreditCardForm.setVisibility(View.GONE);
                }

            }
        });

        // Go back to edit the order
        binding.btnEditOrder.setOnClickListener(view->{
            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("order", order);
            intent.putExtra("clientName", clientName);
            intent.putExtra("quantity",quantity);
            intent.putExtra("mushrooms", mushrooms);
            intent.putExtra("corn", corn);
            intent.putExtra("pickles", pickles);
            intent.putExtra("justPizza", justPizza);
            intent.putExtra("pizzaWithIngredients", pizzaWithIngredients);
            startActivity(intent);


        });

        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);


                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {

        savedInstanceState.putBoolean("rbPaypalState", rbPaypal.isChecked());
        savedInstanceState.putBoolean("rbCreditCardState", rbCreditCard.isChecked());

        super.onSaveInstanceState(savedInstanceState);

    }

    // Initialize the default view
    private void initializeViews() {
        rbPaypal.setChecked(isPaypal);
        rbCreditCard.setChecked(isCreditCard);
        if(rbPaypal.isChecked()){
            llCreditCardForm.setVisibility(View.GONE);
        }
        else{
            llCreditCardForm.setVisibility(View.VISIBLE);
        }
    }
}
package com.julidot.justpizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.julidot.justpizza.databinding.ActivityMainBinding;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // Values
    int pizzaPrice = 4;
    float ingredinetPrice = 0.50f;
    float ingredientsSum =0f;
    float finalPrice =0;
    int quantity = 1;
    String orderSumUp = "";
    String ingredients = "";
    String clientName = "";

    // Views
    RadioGroup rgPizzas;
    TextView tvQuantity;
    RadioButton rbJustPizza;
    RadioButton rbPizzaWithIngredients;
    CheckBox cbMushrooms;
    CheckBox cbCorn;
    CheckBox cbPickles;
    TextView tvSumUpOrder;
    LinearLayout llIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initiate Views
        llIngredients = binding.llIngredients;
        rgPizzas = binding.rgPizzas;
        rbJustPizza = binding.rbJustPizza;
        rbPizzaWithIngredients= binding.rbPizzaWithIngredients;
        cbMushrooms = binding.cbMushrooms;
        cbCorn = binding.cbCorn;
        cbPickles = binding.cbPickles;
        tvSumUpOrder = binding.tvSumUpOrder;
        tvQuantity = binding.tvQuantity;

        // Set the default quantity to the View
        tvQuantity.setText(String.format(Locale.getDefault(), "%d", quantity));

        // RadioGroup Listener
        rgPizzas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Reinitialise values on rb change
                cleanOrderSumUp();
                quantity = 1;
                ingredients ="";
                ingredientsSum=0f;
                // Set the default quantity to the View
                tvQuantity.setText(String.format(Locale.getDefault(), "%d", quantity));


                // checkedId is the RadioButton selected
                if(checkedId == rbJustPizza.getId()){
                    llIngredients.setVisibility(View.GONE);

                    // Erase ingredients
                    cbMushrooms.setChecked(false);
                    cbCorn.setChecked(false);
                    cbPickles.setChecked(false);

                }else{
                    llIngredients.setVisibility(View.VISIBLE);

                }
            }
        });

        // Clean OrderSumUp on CheckBox Click
        initializeCbListeners();



        // Add quantity
        binding.btnMinus.setOnClickListener(view ->{
            cleanOrderSumUp();
            if (quantity >1){
                quantity --;
                tvQuantity.setText(String.format(Locale.getDefault(), "%d", quantity));


            }


        });

        // Take off quantity
        binding.btnPlus.setOnClickListener(view ->{
            cleanOrderSumUp();
            quantity ++;
            tvQuantity.setText(String.format(Locale.getDefault(), "%d", quantity));

        });



        // Order
        binding.btnOrder.setOnClickListener(view ->{
            cleanOrderSumUp();
            ingredients="";
            ingredientsSum=0f;

            // Get the client's name from the View
            clientName = binding.etClientName.getText().toString();


            // Check if the client has entered his name
            if (clientName.matches("")){
                Toast.makeText(MainActivity.this, "Запишете Вашето име.",
                Toast.LENGTH_LONG).show();


            }else{

                boolean isIngredients = !(cbMushrooms.isChecked() && cbCorn.isChecked() && cbPickles.isChecked() && rbJustPizza.isChecked());
                // Check if there are checked ingredients
                if (!isIngredients) {
                    Toast.makeText(MainActivity.this, "Изберете добавки.", Toast.LENGTH_LONG).show();
                }


                if(cbMushrooms.isChecked()){
                    ingredients += ("С гъби\n");
                    ingredientsSum+=ingredinetPrice;

                }
                if(cbCorn.isChecked()){
                    ingredients +=("С царевица\n");
                    ingredientsSum+=ingredinetPrice;
                }
                if(cbPickles.isChecked()){
                    ingredients +=("С кисели краставички\n");
                    ingredientsSum+=ingredinetPrice;
                }

                // Add just the text tvOrder
                getOrderSumUpString();
            }







        });



    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    // Create the sumUpString
    private void getOrderSumUpString(){
        finalPriceCalc();
        orderSumUp = "Име: " + clientName + "\n" +
                ingredients +
                "Количество: " + quantity + "\n"  +
                "Цена: " + finalPrice + "лв" + "\n" +
                "Благодарим Ви!";
        tvSumUpOrder.setText(orderSumUp);
    }

    // Clean orderSumUp string and TextView
    private void cleanOrderSumUp(){
        orderSumUp="";
        tvSumUpOrder.setText(orderSumUp);
    }

    // Final price calculation
    private void finalPriceCalc(){
        finalPrice = (quantity*(pizzaPrice +ingredientsSum));
    }

    // Initialize CheckBox Listeners to clean the OrderSumUp string
    private void initializeCbListeners(){

        cbMushrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cleanOrderSumUp();

            }
        });
        cbPickles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cleanOrderSumUp();

            }
        });
        cbCorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cleanOrderSumUp();

            }
        });

    }

}
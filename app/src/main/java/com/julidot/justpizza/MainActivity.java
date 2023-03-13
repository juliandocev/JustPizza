package com.julidot.justpizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    String clientEmail="";
    Boolean isMushrooms = false;
    Boolean isCorn = false;
    Boolean isPickles =false;
    Boolean isJustPizza = true;
    Boolean isPizzaWithIngredients = false;


    // Views
    RadioGroup rgPizzas;
    TextView tvQuantity;
    RadioButton rbJustPizza;
    RadioButton rbPizzaWithIngredients;
    CheckBox cbMushrooms;
    CheckBox cbCorn;
    CheckBox cbPickles;
    TextView tvSumUpOrder;
    EditText etClientName;
    EditText etClientEmail;
    LinearLayout llIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Check if there is savedInstanceState
        if(savedInstanceState != null){

            quantity = savedInstanceState.getInt("tvQuantityState");
            orderSumUp = savedInstanceState.getString("tvSumUpOrderState");
            clientName = savedInstanceState.getString("etClientNameState");
            clientEmail = savedInstanceState.getString("etClientEmailState");
            isMushrooms = savedInstanceState.getBoolean("cbMushroomsState");
            isCorn = savedInstanceState.getBoolean("cbCornState");
            isPickles = savedInstanceState.getBoolean("cbPicklesState");
            isJustPizza = savedInstanceState.getBoolean("rbJustPizzaState");
            isPizzaWithIngredients = savedInstanceState.getBoolean("rbPizzaWithIngredientsState");

           // Toast.makeText(this, String.format(Locale.getDefault(), "%d", quantity), Toast.LENGTH_LONG).show();

        }

        setContentView(binding.getRoot());

        // Initiate Views
        initiateViews();

        // Set the default quantity to the View
        tvQuantity.setText(String.format(Locale.getDefault(), "%d", quantity));
        tvSumUpOrder.setText(orderSumUp);
        etClientName.setText((clientName));
        etClientEmail.setText((clientEmail));
        cbMushrooms.setChecked(isMushrooms);
        cbCorn.setChecked(isCorn);
        cbPickles.setChecked(isPickles);
        rbJustPizza.setChecked(isJustPizza);
        rbPizzaWithIngredients.setChecked(isPizzaWithIngredients);

        // Check if there are extras in the intent
        Bundle extras = getIntent().getExtras();
        // If there are charge the UI with them
        if (extras != null) {
            tvSumUpOrder.setText(extras.getString("order"));
            etClientName.setText((extras.getString("clientName")));
            etClientEmail.setText((extras.getString("clientEmail")));
            tvQuantity.setText(extras.getString("quantity"));
            cbMushrooms.setChecked(extras.getBoolean("mushrooms"));
            cbCorn.setChecked(extras.getBoolean("corn"));
            cbPickles.setChecked(extras.getBoolean("pickles"));
            rbJustPizza.setChecked(extras.getBoolean("justPizza"));
            rbPizzaWithIngredients.setChecked(extras.getBoolean("pizzaWithIngredients"));

            // Update the llIngredients layout
            if(rbPizzaWithIngredients.isChecked()){
                llIngredients.setVisibility(View.VISIBLE);
            }
            else{
                llIngredients.setVisibility(View.GONE);
            }


        }


        // RadioGroup Listener
        rgPizzas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Reinitialise values on rb change
                cleanOrderSumUp();
                //quantity = 1;
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
        binding.btnConfirmation.setOnClickListener(view ->{
            cleanOrderSumUp();
            ingredients="";
            ingredientsSum=0f;

            // Get the client's name from the View
            clientName = binding.etClientName.getText().toString();
            clientEmail = binding.etEmailClient.getText().toString();


            // Check if the client has entered his name
            if (clientName.matches("") || clientEmail.matches("")){
                Toast.makeText(MainActivity.this, "Запишете Вашето име и email.",
                Toast.LENGTH_LONG).show();


            }else{

                boolean isIngredientsUnchecked = (!cbMushrooms.isChecked() && !cbCorn.isChecked() && !cbPickles.isChecked());
                // Check if there is at least one checked ingredient if pizza with ingredients is chosen
                if (isIngredientsUnchecked && rbPizzaWithIngredients.isChecked()) {
                    Toast.makeText(MainActivity.this, "Изберете добавки.", Toast.LENGTH_LONG).show();
                }else{
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

                    // Intent
                    Intent intent = new Intent(MainActivity.this, RecapitulationActivity.class);
                    intent.putExtra("order", orderSumUp);
                    intent.putExtra("clientName", clientName);
                    intent.putExtra("clientEmail", clientEmail);
                    intent.putExtra("quantity",tvQuantity.getText().toString());
                    intent.putExtra("mushrooms", cbMushrooms.isChecked());
                    intent.putExtra("corn", cbCorn.isChecked());
                    intent.putExtra("pickles", cbPickles.isChecked());
                    intent.putExtra("justPizza", rbJustPizza.isChecked());
                    intent.putExtra("pizzaWithIngredients", rbPizzaWithIngredients.isChecked());
                    startActivity(intent);

                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {


        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("tvQuantityState", Integer.parseInt(tvQuantity.getText().toString()));
        savedInstanceState.putString("tvSumUpOrderState", tvSumUpOrder.getText().toString());
        savedInstanceState.putString("etClientNameState", etClientName.getText().toString());
        savedInstanceState.putString("etClientEmailState", etClientEmail.getText().toString());
        savedInstanceState.putBoolean("cbMushroomsState", cbMushrooms.isChecked());
        savedInstanceState.putBoolean("cbCornState", cbCorn.isChecked());
        savedInstanceState.putBoolean("cbPicklesState", cbPickles.isChecked());
        savedInstanceState.putBoolean("isJustPizzaState", rbJustPizza.isChecked());
        savedInstanceState.putBoolean("rbPizzaWithIngredientsState", rbPizzaWithIngredients.isChecked());

        super.onSaveInstanceState(savedInstanceState);

    }


    // Initiate the views
    private void initiateViews(){
        llIngredients = binding.llIngredients;
        rgPizzas = binding.rgPizzas;
        rbJustPizza = binding.rbJustPizza;
        rbPizzaWithIngredients= binding.rbPizzaWithIngredients;
        cbMushrooms = binding.cbMushrooms;
        cbCorn = binding.cbCorn;
        cbPickles = binding.cbPickles;
        tvSumUpOrder = binding.tvSumUpOrder;
        tvQuantity = binding.tvQuantity;
        etClientName = binding.etClientName;
        etClientEmail = binding.etEmailClient;
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

    // Clean orderSumUp string and TextView //TODO moze i da niama nuzda kato imam preprastane pri aktvity
    private void cleanOrderSumUp(){
        orderSumUp="";
        tvSumUpOrder.setText(orderSumUp);
    }

    // Final price calculation
    private void finalPriceCalc(){
        finalPrice = (quantity*(pizzaPrice +ingredientsSum));
    }


}
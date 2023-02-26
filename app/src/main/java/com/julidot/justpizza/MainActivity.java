package com.julidot.justpizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.julidot.justpizza.databinding.ActivityMainBinding;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int pizzaPrice = 4;
        TextView tvQuantity =binding.tvQuantity;
        AtomicReference<String> orderSumUp = new AtomicReference<>("");
        AtomicInteger quantity = new AtomicInteger();
        quantity.set(0);


        binding.btnMinus.setOnClickListener(view ->{
            if (quantity.get() != 0){
                quantity.getAndDecrement();
                tvQuantity.setText(quantity.toString());
            }


        });

        binding.btnPlus.setOnClickListener(view ->{
            quantity.getAndIncrement();
            tvQuantity.setText(quantity.toString());
        });



        binding.btnOrder.setOnClickListener(view ->{

            String etClientName = binding.etClientName.getText().toString();
            RadioButton rbJustPizza = binding.rbOne;
            CheckBox cbMushrooms = binding.cbMushrooms;
            CheckBox cbCorn = binding.cbCorn;
            CheckBox cbPickles = binding.cbPickles;
            TextView tvSumUpOrder = binding.tvSumUpOrder;


            // Check if the client has entered his name
            if (etClientName.matches("")){
                Toast.makeText(MainActivity.this, "Запишете Вашето име.",
                Toast.LENGTH_LONG).show();
            }else{
                // Check if quantity more than 0
                if (!tvQuantity.getText().toString().equals("0")) {
                    // Check if there are adds to the pizza
                    if (rbJustPizza.isChecked()){
                        // Clean adds
                        cbMushrooms.setChecked(false);
                        cbCorn.setChecked(false);
                        cbPickles.setChecked(false);

                        // Add just the text tvOrder
                        orderSumUp.set("Име: " + etClientName + "\n" +
                                "Количество: " + quantity + "\n"  +
                                "Цена: " + quantity.get() *pizzaPrice+ ",00лв" + "\n" +
                                "Благодарим Ви!");
                        tvSumUpOrder.setText(orderSumUp.toString());

                    }else{
                        String ingredients = "";
                        if(cbMushrooms.isChecked()){
                            ingredients += ("С гъби\n");

                        }
                        if(cbCorn.isChecked()){
                            ingredients +=("С царевица\n");
                        }
                        if(cbPickles.isChecked()){
                            ingredients +=("С кисели краставички\n");
                        }

                        // Add just the text tvOrder
                        orderSumUp.set("Име: " + etClientName + "\n" +
                                ingredients +
                                "Количество: " + quantity + "\n"  +
                                "Цена: " + quantity.get() *pizzaPrice+ ",00лв" + "\n" +
                                "Благодарим Ви!");
                        tvSumUpOrder.setText(orderSumUp.toString());
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Добавете количество.",
                            Toast.LENGTH_LONG).show();
                }



            }


        });



    }
}
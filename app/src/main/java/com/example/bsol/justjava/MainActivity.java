
package com.example.bsol.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.duration;
import static android.R.attr.name;
import static android.R.id.message;
import static com.example.bsol.justjava.R.id.whipped_check_box;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        String summaryMessage = createOrderSummary(5);
//        displayMessage(summaryMessage);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "thedude@justjava.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order");
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(5));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the order button is clicked.
     *
     * @param basePrice is the price per cup of coffee with no toppings
     * @return the summary message
     */

    private String createOrderSummary(int basePrice) {
        TextView nameField = (TextView) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_check_box);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);

        int price = basePrice;
        if (whippedCheckBox.isChecked()) {
            price += 1;
        }
        if (chocolateCheckBox.isChecked()) {
            price += 2;
        }

        String nameReturn = "Name: " + name + "\n";
        if (name.isEmpty()) {
            nameReturn = "";
        }

        return nameReturn +
                "Add whipped cream? " + whippedCheckBox.isChecked() +
                "\nAdd chocolate? " + chocolateCheckBox.isChecked() +
                "\nQuantity: " + quantity +
                "\nTotal: $" + quantity * price + "\nThank you!";
    }

    /**
     * Calculates the price of the order.
     */

    public void increment(View view) {
        if (quantity == 100) {
            Toast toast = Toast.makeText(this, "Can't order more than 100 cups", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity++;
        displayQuantity(quantity);

    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast toast = Toast.makeText(this, "Must order at least one cup", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }

    /**
     * This method displays the given text on the screen.
     */
/*    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}
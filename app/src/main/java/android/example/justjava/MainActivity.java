package android.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
    }
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById ( R.id.name_field );
        String name = nameField.getText ( ).toString ( );


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById ( R.id.whipped_cream_checkbox );
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked ( );
        CheckBox chocolateCheckBox = (CheckBox) findViewById ( R.id.chocolate_checkbox );
        boolean hasChocolate = chocolateCheckBox.isChecked ( );
        display ( quantity );
        int price = CalculatePrice ( hasWhippedCream ,hasChocolate );
        String priceMessage = createOrderSummary ( name ,price ,hasWhippedCream ,hasChocolate );

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage ( priceMessage );
    }

    private int CalculatePrice(boolean addWhippedCream,boolean addChocolate) {
        int basePrice=5;
        if (addWhippedCream) {
            basePrice=basePrice+1;
        }
        if (addChocolate) {
            basePrice=basePrice+2;
        }
        return basePrice*quantity;
    }

    public String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate) {
        String priceMessage=getString ( R.string.order_summary_name,name ) ;
        priceMessage+=  "\n"+ getString ( R.string.order_summary_whipped_cream,addWhippedCream);
        priceMessage += "\n"+ getString ( R.string.order_summary_chocolate,addChocolate);
        priceMessage+="\n"+ getString ( R.string.order_summary_quantity,quantity);
        priceMessage+="\n"+ getString ( R.string.order_summary_price,
        NumberFormat.getCurrencyInstance ().format ( price ) );
        priceMessage+="\n"+ getString ( R.string.thank_you);
        return priceMessage;
    }


    private void display(int i) {
        TextView quantityTextView= (TextView) findViewById ( R.id.quantity_text_view );
        quantityTextView.setText ( "" +i);
    }

    /**
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText( NumberFormat.getCurrencyInstance().format(number));
    }

    public void decrement(View view) {
        if(quantity==1) {
            Toast.makeText ( this,"you cannot have less than 1 coffee" ,Toast.LENGTH_SHORT).show ();
            return;
        }
        quantity=quantity-1;
        display ( quantity );
    }

    public void increment(View view) {
        if(quantity==100) {
            return;
        }
        quantity=quantity+1;
        display ( quantity );
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}
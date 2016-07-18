package com.bags.cse.saks.wifidirect;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
public class MainActivity extends Activity{

    EditText editTextAddress;
    Button buttonConnect;
    String address="";
    LinearLayout ll;
    String tempaddress="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll=(LinearLayout)findViewById(R.id.ll);
        ll.setBackgroundColor(Color.rgb(25,101,123));
        editTextAddress = (EditText) findViewById(R.id.address);
        buttonConnect = (Button) findViewById(R.id.connect);
    }

    public void connecting(View v){
         address=editTextAddress.getText().toString();
        // tempaddress=editTextAddress.getText().toString();
            finish();
            Intent intent = new Intent(MainActivity.this, gettingcoordinates.class);
            intent.putExtra("address", address);
            startActivity(intent);
    }

}

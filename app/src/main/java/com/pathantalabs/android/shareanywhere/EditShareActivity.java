package com.pathantalabs.android.shareanywhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

public class EditShareActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> results;

    EditText editText1,editText2,editText3;
    String APP_SHARE_LINK = "https://play.google.com/store/apps/details?id=com.pathantalabs.android.shareanywhere";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_share);

        NativeExpressAdView nativeExpressAdView = (NativeExpressAdView) findViewById(R.id.nativeAdEdit);
        nativeExpressAdView.loadAd(new AdRequest.Builder().addTestDevice("07CFD0FF9F7F6D57C17CFD87039B720D").build());

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        ImageButton btn1 = (ImageButton) findViewById(R.id.buttonSend1);
        ImageButton btn2 = (ImageButton) findViewById(R.id.buttonSend2);
        ImageButton btn3 = (ImageButton) findViewById(R.id.buttonSend3);

        Bundle bundle = getIntent().getExtras();

        results = bundle.getStringArrayList("KEYWORD");

        if (results != null) {
            editText1.setText(results.get(0));
            editText2.setText(results.get(1));
            editText3.setText(results.get(2));
        }

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSend1:
                String text1 = editText1.getText().toString().trim();
                Intent shareText1 = new Intent(Intent.ACTION_SEND);
                shareText1.putExtra(Intent.EXTRA_TEXT,text1.trim());
                shareText1.setType("text/plain");
                Intent chooser1 = Intent.createChooser(shareText1, "Share Message");
                startActivity(chooser1);
                break;
            case R.id.buttonSend2:
                String text2 = editText2.getText().toString().trim();
                Intent shareText2 = new Intent(Intent.ACTION_SEND);
                shareText2.putExtra(Intent.EXTRA_TEXT,text2.trim());
                shareText2.setType("text/plain");
                Intent chooser2 = Intent.createChooser(shareText2, "Share Message");
                startActivity(chooser2);
                break;
            case R.id.buttonSend3:
                String text3 = editText3.getText().toString().trim();
                Intent shareText3 = new Intent(Intent.ACTION_SEND);
                shareText3.putExtra(Intent.EXTRA_TEXT,text3.trim());
                shareText3.setType("text/plain");
                Intent chooser3= Intent.createChooser(shareText3, "Share Message");
                startActivity(chooser3);
                break;
        }
    }
}

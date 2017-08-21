package com.pathantalabs.android.shareanywhere;

import android.app.AlertDialog;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.plus.PlusOneButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    NativeExpressAdView nativeExpressAdView;

    ArrayList<String> results;
    String APP_SHARE_LINK = "https://play.google.com/store/apps/details?id=com.pathantalabs.android.shareanywhere";
    private static final int REQUEST_SIGN_IN = 0;
    PlusOneButton googleSignIn;
    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    NetworkStatsManager networkStatsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getResources().getString(R.string.appId));

        nativeExpressAdView = (NativeExpressAdView) findViewById(R.id.nativeAd);
        if (isNetworkAvailable()){
            loadAd();
        }
        googleSignIn = (PlusOneButton) findViewById(R.id.googlePlus);

        ImageView micImageView = (ImageView) findViewById(R.id.mic_button);

        ImageView shareButton = (ImageView) findViewById(R.id.shareApp);
        ImageView rateButton = (ImageView) findViewById(R.id.rateUs);
        micImageView.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        rateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mic_button:
                Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tap to Cancel Search");
                startActivityForResult(voiceIntent, 200);
                break;
            case R.id.shareApp:
                Intent chooserShare;
                String shareAppString = "Share Anywhere is Fast, Accurate and User Friendly.\nIt is the Text Sharing App " +
                        "where User can write text using Their Voice.\n For more Details get it From Play Store" + APP_SHARE_LINK;

                Intent shareAppIntent = new Intent(Intent.ACTION_SEND);
                shareAppIntent.putExtra(Intent.EXTRA_TEXT, shareAppString.trim());
                shareAppIntent.setType("text/plain");
                chooserShare = Intent.createChooser(shareAppIntent, "Share the App");
                startActivity(chooserShare);
                break;
            case R.id.rateUs:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_SHARE_LINK));
                startActivity(rateIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                Intent sendData = new Intent(MainActivity.this, EditShareActivity.class);

                if (results == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Error in Speech Recognition.\nTry Again");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    sendData.putStringArrayListExtra("KEYWORD", results);
                }
                startActivity(sendData);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleSignIn.initialize(APP_SHARE_LINK, REQUEST_SIGN_IN);
    }

    public boolean isNetworkAvailable() {
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public void loadAd(){
        nativeExpressAdView.loadAd(new AdRequest.Builder().addTestDevice("07CFD0FF9F7F6D57C17CFD87039B720D").build());
    }
}

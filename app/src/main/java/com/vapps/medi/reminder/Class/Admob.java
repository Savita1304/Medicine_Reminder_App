package com.vapps.medi.reminder.Class;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.vapps.medi.reminder.R;

public class Admob {


    public static InterstitialAd mInterstitialAd;

    public static void adMob1(Context context, final AdView mAdView,
                              boolean isConnected, LinearLayout adlayout,LinearLayout main){

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        AdRequest adRequest = new AdRequest.Builder().build();


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                mAdView.setVisibility(View.VISIBLE);
                adlayout.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.79f
                );
                main.setLayoutParams(param);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                mAdView.setVisibility(View.GONE);
                adlayout.setVisibility(View.GONE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.86f
                );
                main.setLayoutParams(param);
            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {

            }
        });



        mAdView.loadAd(adRequest);


            if (isConnected==true) {

                mAdView.setVisibility(View.VISIBLE);
                adlayout.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.79f
                );
                main.setLayoutParams(param);
            }else {
                mAdView.setVisibility(View.GONE);
                adlayout.setVisibility(View.GONE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.86f
                );
                main.setLayoutParams(param);
            }







    }








    public static void LoadInterestitialAd(Context context){
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,context.getString(R.string.interstitial_full_screen), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        //Log.e("TAG", "onAdLoaded");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Log.e("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

    }

}

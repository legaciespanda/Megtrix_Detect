package com.megtrix.phonedetector;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MegtrixDetect extends AppCompatActivity {
    Context _context;
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    private static final int PERMISSION_REQUEST_CODE = 1;


    //
    private Context context;
    private SubscriptionManager mSubscriptionManager;

    public static boolean isMultiSimEnabled = false;
    public static String defaultSimName;

    public static List<SubscriptionInfo> subInfoList;
    public static ArrayList<String> Numbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {
            getUserPhone();
        }
    }

    public String getUserPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(_context, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return phoneMgr.getLine1Number();
    }

    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            initiateSettings(this);
        }
        ActivityCompat.requestPermissions(this, new String[]{permission},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserPhone();
                } else {
                    initiateSettings(this);
                    //Toast.makeText(activity,"Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }


    public void initiateSettings(Context context) {
        /**
         * Showing Alert Dialog with Settings option
         * Navigates user to app settings
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Permissions Request");
        builder.setMessage("This app needs permissions to be granted to function properly");
        builder.setPositiveButton("Grant  Permissions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }



    //Get both sim numbers in a dual sim android phone
    @SuppressLint({"MissingPermission", "NewApi"})
    private void GetCarriorsInformation() {
        subInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
        if (subInfoList.size() > 1) {
            isMultiSimEnabled = true;
        }
        for (SubscriptionInfo subscriptionInfo : subInfoList) {
            Numbers.add(subscriptionInfo.getNumber());
        }
    }
}

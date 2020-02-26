package com.megtrix.phonedetector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsDialog extends AppCompatActivity {


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

}

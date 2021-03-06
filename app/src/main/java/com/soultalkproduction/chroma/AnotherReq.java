package com.soultalkproduction.chroma;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

public class AnotherReq {
    private static final String PERMISSION = "android.permission.WRITE_SECURE_SETTINGS";
    private static final String COMMAND    = "adb -d shell pm grant " + BuildConfig.APPLICATION_ID + " " + PERMISSION;
    private static final String SU_COMMAND = "su -c pm grant " + BuildConfig.APPLICATION_ID + " " + PERMISSION;

    private static final String DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
    private static final String DISPLAY_DALTONIZER         = "accessibility_display_daltonizer";

    public static boolean hasPermission(Context context) {
        return context.checkCallingOrSelfPermission(PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    public static Dialog WarnDialog(final Context context) {
        return new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
                .setTitle("Permission Required")
                .setMessage(context.getString(R.string.doot, COMMAND))
                .setNegativeButton("Ok", null)
                .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipData clipData = ClipData.newPlainText(COMMAND, COMMAND);
                        ClipboardManager manager = (ClipboardManager) context.getSystemService(Service.CLIPBOARD_SERVICE);
                        manager.setPrimaryClip(clipData);
                        Toast.makeText(context,"Copied Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("root", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toggleGrayscale(context, !isGrayscaleEnable(context));
                        try {
                            Runtime.getRuntime().exec(SU_COMMAND).waitFor();
                            toggleGrayscale(context, !isGrayscaleEnable(context));
                        } catch (Exception e) {
                            Toast.makeText(context,"Developer options failed", Toast.LENGTH_SHORT).show();

                            Log.d("ErrorIs",e.getMessage());

                            e.printStackTrace();
                        }
                    }
                })
                .create();
    }

    public static boolean isGrayscaleEnable(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Secure.getInt(contentResolver, DISPLAY_DALTONIZER_ENABLED, 0) == 1
                && Secure.getInt(contentResolver, DISPLAY_DALTONIZER, 0) == 0;
    }

    public static void toggleGrayscale(Context context, boolean grayscale) {
        ContentResolver contentResolver = context.getContentResolver();
        Secure.putInt(contentResolver, DISPLAY_DALTONIZER_ENABLED, grayscale ? 1 : 0);
        Secure.putInt(contentResolver, DISPLAY_DALTONIZER, grayscale ? 0 : -1);
    }
}

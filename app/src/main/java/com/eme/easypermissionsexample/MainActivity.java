package com.eme.easypermissionsexample;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.BuildConfig;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int RC_CAMERA_AND_LOCATION = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicialización del log
        initLog();

        // Se hace la petición explícita de los permisos
        requiresTwoPermission();
    }

    private void initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void requiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {

            Timber.d("permisos otorgados");
            // Already have permission, do the thing
            // ...
        } else {

            Timber.d("permisos rechazados");
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }
}
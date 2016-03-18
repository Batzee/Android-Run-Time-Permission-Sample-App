package runtimepermission.batzeesappstudio.com.androidruntimepermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button testButton;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton = (Button)findViewById(R.id.buttonTest);
        testButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writePermissionForScreenshot();
            }
        });
    }

    private void shareScreenShot(){
        //Tkae Screenshot method goes here
    }


    private void writePermissionForScreenshot(){

        if(PackageManager.PERMISSION_GRANTED== ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            shareScreenShot();
        }else{
            requestReadPermission(MainActivity.this);
        }
    }

    private static void requestReadPermission(final Context context){
        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setMessage("The app needs permission to write data to the phone so that the Screenshot can be saved")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }).show();

        }else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareScreenShot();
                } else {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                return;
            }
        }
    }
}

package hackupc.skytrips.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hackupc.skytrips.R;
import hackupc.skytrips.helper.AsciiToBinDecoder;

public class LostActivity extends AppCompatActivity {

    private FrameLayout ll;
    private double longitude, latitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        askPermission();
        getLatLong();
        ll = (FrameLayout) findViewById(R.id.rect);
        findViewById(R.id.gps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void askPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void getLatLong(){
        //LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        try {
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            sendSms();
            System.out.println("Long: " + longitude + ", " + "Lat: " + latitude);
//            final LocationListener locationListener = new LocationListener() {
//                public void onLocationChanged(Location location) {
//                    longitude = location.getLongitude();
//                    latitude = location.getLatitude();
//
//                    System.out.println("Long: " + longitude + ", " + "Lat: " + latitude);
//                }
//
//                @Override
//                public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String s) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String s) {
//
//                }
//            };
//
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void sendSms(){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+441922214447", null, latitude + " " + longitude, null, null);
    }

    private void showImage(){
        String somethingElse = "6YDAQ5aACu;6qC622IPuADm;:LAkH76BgAE7;:6u5m:OA561D;yIGPudnmDnHD;uDBp:E4GAAGH;:BQ2PEDumALH;;9qDtpI2DAEn;;zz7;up7AYA2;;;5e2yZuADAF5;;twAGMyAA2GD;;muyHxOYAR78;;6AFPpDcADMQP;mAB5y1pAAviL;:ADX8L3mA4GA5:AH:DdkYAH7AX:AB:9nzaADmmFyAAPeyHyAAuIZlAODwPT:mA4GGLmP6n7sp:AGBBcmDyDm4S:mDBQ3mQ4JmHkXPAmQHEyDK6HxjGYMEAn5A1AB4C7DGAAOdu2AAcBy6N6mDbyVAAv6PYA6QuZMlABOMmaAMCMGIoAApp2DAFpHAF6ADOy:A6Fl76DmEavdPaYDqmYHyP;OZtpEAvEAF1Ptvd52oAEtAX6Tv:Z;GY6DLA5AEH4d5niQAqm2AB7tv;yDMAlJlA4I2;y;ACAIcwA;8HluHuB6C5yA;mmxyB:IABq6B;6EP;65DAA1YA;ABHtuGAmAMbAOAM17:AAA6F1AAAP8C5mAAYDE6AA5I7xyAHcAoYAA;0I:;AHjy3iAA:ApPkAJDddTBB:myTwAPAitj7pyzpE2A4AAruQpq5jv6AsAAGYMLoDpyIDSQA6oCCRm5:CBcAAIHB8APPvAoyA6OAYQMqMsAHuAEFmDMBHuAAH6QA3uA4AJ26ANAAA;AAZ6AQIAPQOA;6A2IDAAA4H67;AAmCA64A4AAD;ANmQmIynyAAD:A46:MCiH:AAHyAY7;7BuHmmAHuAzF;uRuP6EAPuBvA;yHm1AAQ5mBuAPyAm4AAE;mBoAHuAI2AAB;;;sEAuAD2AAB;D5qAAAAA2AAB:AUi3AAAA2AAD:6LJ;6AAAzAAHyI2eOe6ABYYAH:CXuCo6AAaEAL;mzuH:uABeMCLf:aSCNMQHMAM5uD:m8HR2CmAB;6Dy6I1CVAmAA;6D;6GDTmAAAB;ADdmB76xD6AB;6H7yAIAxS1AD:EPAPAoJ;udmD:A:ADu:lQCByHyA4AA:5eojuPHuAyAADawwAYT;uBuAAAy:1;Wi";
        String ascii = AsciiToBinDecoder.decode(somethingElse);

        Paint paintA = new Paint();
        Paint paintB = new Paint();
        Paint paintC = new Paint();
        paintC.setColor(Color.parseColor("#EE5555"));
        paintA.setColor(Color.parseColor("#000000"));
        paintB.setColor(Color.parseColor("#FFFFFF"));
        int n = 80;
        Bitmap bg = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);
        for (int i = 0; i < ascii.length(); i++) {
            int row = i / n;
            int col = i % n;
            if(ascii.charAt(i) == '1')
                canvas.drawRect(col, row, col + 1, row + 1, paintA);
            else
                canvas.drawRect(col, row, col + 1, row + 1, paintB);

            // canvas.drawRect();
        }
        canvas.drawRect(n/2 - 3, n/2 -3, n/2 + 3, n/2 + 3, paintC);

        //canvas.drawRect(0, 0, 50, 50, paintA);

        ll.setBackgroundDrawable(new BitmapDrawable(bg));
    }
}

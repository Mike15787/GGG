package com.example.bustest8;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
/*
條件(){
    return 黃昱菖>陳楷翰;
}
if (條件()) {必要做的事,不做世界會滅亡;}
*/
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleMap.OnMapClickListener {
    private GoogleMap mMap; //宣告 google map 物件
    float zoom;
    private LocationManager locMgr;
    String bestProv;
    TextView textView;
    String BusStation[]={"佳里站","南佳里","臺南火車站","南工宿舍","南工社區"};//5
    String SendBS1[]=new String[5];
    String SendBS2[]=new String[5];
    double StationLatLng[][]={{23.163215848846, 120.17723799726194},{23.158349761293493, 120.17707078536007},{22.997772247236476, 120.21170340173224}
            ,{23.0131979998922, 120.2296134631658},{23.014990269732632, 120.23485604248215}};
    int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // 利用 getSupportFragmentManager() 方法取得管理器
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // 以非同步方式取得 GoogleMap 物件
        mapFragment.getMapAsync(this);

        Button BackBtn = (Button) findViewById(R.id.BackBtn);
        textView = findViewById(R.id.DistanceText);
        BackBtn.setOnClickListener(BListener);

    }

    private Button.OnClickListener BListener = new Button.OnClickListener(){
        public void onClick(View view){
            finish();
        }

    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 取得 GoogleMap 物件
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        LatLng TainanTS = new LatLng(22.997702623090234, 120.21170444036703); // 台北 101
        zoom = 17;
        mMap.addMarker(new MarkerOptions().position(TainanTS).title("台南火車站"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TainanTS, zoom));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);       // 一般地圖

        // 檢查授權
        requestPermission();
    }

    // 檢查授權
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {  // Androis 6.0 以上
            // 判斷是否已取得授權
            int hasPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {  // 未取得授權
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        }
        // 如果裝置版本是 Androis 6.0 以下，
        // 或是裝置版本是6.0（包含）以上，使用者已經授權
        setMyLocation(); //  顯示定位圖層
    }

    // 使用者完成授權的選擇以後，會呼叫 onRequestPermissionsResult 方法
    //     第一個參數：請求授權代碼
    //     第二個參數：請求的授權名稱
    //     第三個參數：使用者選擇授權的結果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { //按 允許 鈕
                setMyLocation(); //  顯示定位圖層
            }else{  //按 拒絕 鈕
                Toast.makeText(this, "未取得授權！", Toast.LENGTH_SHORT).show();
                finish();  // 結束應用程式
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //  顯示定位圖層
    private void setMyLocation() throws SecurityException {
        mMap.setMyLocationEnabled(true); // 顯示定位圖層
    }

    @Override
    public void onLocationChanged(Location location) {
        // 取得地圖座標值:緯度,經度
        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        zoom=17; //設定放大倍率1(地球)-21(街景)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, zoom));
       // location.distanceBetween();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 取得定位服務
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 取得最佳定位
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);

        // 如果GPS或網路定位開啟，更新位置
        if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER) || locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //  確認 ACCESS_FINE_LOCATION 權限是否授權
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locMgr.requestLocationUpdates(bestProv, 30000, 1, this);
            }
        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  確認 ACCESS_FINE_LOCATION 權限是否授權
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locMgr.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
    @Override
    public void onMapClick(LatLng latLng) {
        double SaveDistance1[] = {1,1,1,1,1};
        double RSDistance[] = new double[50];
        String r[] = {"1","1"};
        double b;
        double c;
        int w = 0;
        int o=0;
        String x = Double.toString(latLng.latitude);
        String y = Double.toString(latLng.longitude);
        Marker marker = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(latLng.latitude, latLng.longitude))
                        .anchor(0.5f,  0.5f)
                        .title(x + "," + y)
        );
        GlobalVariable globalVariable = (GlobalVariable)getApplicationContext();

            double lat1 = latLng.latitude;
            double lng1 = latLng.longitude;
            for (int i=0;i<5;i++){

                b = Distance(lat1,lng1,StationLatLng[i][0],StationLatLng[i][1]);
                    if (b>2000){
                        SaveDistance1[i] = 0;
                } /*else {
                    RSDistance[o] = b;
                    o++;
                }*/
            }
            for(int i=0, k=0;i<BusStation.length;i++){
                if(SaveDistance1[i] == 1){
                    SendBS1[k] = BusStation[i];
                    k++;
                }
            }
        if (globalVariable.getQ()==null){
            globalVariable.setBS1(SendBS1);
            globalVariable.setQ(r);
        } else {
            globalVariable.setBS2(SendBS1);
        }
            /*double ReplaceRSD[] = new double[o];
            for (int e=0;e<RSDistance.length;e++){
                ReplaceRSD[e] = RSDistance[e];
            }
            if (ReplaceRSD.length==1){
                if (globalVariable.getQ()==null){
                    globalVariable.setBS1(SendBS1);
                    globalVariable.setQ(r);
                } else {
                    globalVariable.setBS2(SendBS1);
                }
            } else{
                double Yes[] = SortD(ReplaceRSD);
                if (globalVariable.getQ()==null){
                    globalVariable.setBS1(SendBS1);
                    globalVariable.setD1(Yes);
                    globalVariable.setQ(r);
                } else {
                    globalVariable.setBS2(SendBS1);
                    globalVariable.setD2(Yes);
                }
            }*/

    }

    private double Distance(double lat1, double lng1, double lat2, double lng2) {
        double radLatitude1 = lat1 * Math.PI / 180;
        double radLatitude2 = lat2 * Math.PI / 180;
        double l = radLatitude1 - radLatitude2;
        double p = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                * Math.pow(Math.sin(p / 2), 2)));
        distance = distance * 6378137.0;
        distance = Math.round(distance * 10000) / 10000;

        return distance;
    }

    /*private double[] SortD (double[] f){
        double t;
        for (int hh=0;hh<f.length-1;hh++){
            for (int ii=0;ii<f.length-1;ii++){
                if (f[ii]>f[ii+1]){
                    t=f[ii];
                    f[ii] = f[ii+1];
                    f[ii+1] = t;
                }
            }
        }
        return f;
    }*/

}

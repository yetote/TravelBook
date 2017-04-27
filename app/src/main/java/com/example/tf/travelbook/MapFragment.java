//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      佛祖保佑       永无BUG

package com.example.tf.travelbook;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.List;

import utils.MyLocationListener;


/**
 * Created by TF on 2017/1/14.
 */

public class MapFragment extends Fragment {
    private Button button;
    private TextureMapView mapView;
    private LocationManager locationManager;
    private BaiduMap baidumap;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.map_fragment, null, false);
        mapView = (TextureMapView) v.findViewById(R.id.map_fragment_mapview);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.NETWORK_PROVIDER;
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    navigateto(location);
                }
            }
        } catch (Exception e) {
            Log.e("error", e + "");
        }


        List<String> providerlist = locationManager.getProviders(true);
        if (providerlist.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerlist.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(getActivity(), "暂时未找到可用的位置提供器，请检查", Toast.LENGTH_SHORT).show();

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                navigateto(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        PoiSearch mPoiSerch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {


            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获得检索结果
                String name = poiResult.getAllPoi().get(0).name;
                String adress = poiResult.getAllPoi().get(0).address;
                String uid = poiResult.getAllPoi().get(0).uid;
                Log.e("aa", name + adress + uid + poiResult.toString());
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                //获得Place检索页详情
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        mPoiSerch.setOnGetPoiSearchResultListener(poiListener);
        mPoiSerch.searchInCity(new PoiCitySearchOption().city("大连").keyword("美食").pageNum(10));
        mPoiSerch.destroy();

        //定位

        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
        baidumap = mapView.getMap();
        baidumap.setMyLocationEnabled(true);


        return v;
    }

    //    private void showLocation(Location location) {
//        String currentPosition = "latitude is" + location.getLatitude() + "\n" + "longgitude is" + location.getLongitude();
//        Log.e("位置", currentPosition);
//    }
    private void navigateto(Location location) {
        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatusUpdate updata = MapStatusUpdateFactory.newLatLng(ll);
        baidumap.animateMapStatus(updata);
        updata = MapStatusUpdateFactory.zoomTo(16f);
        baidumap.animateMapStatus(updata);
        MyLocationData.Builder locationBuider = new MyLocationData.Builder();
        locationBuider.latitude(location.getLatitude());
        locationBuider.longitude(location.getLongitude());
        MyLocationData locationData = locationBuider.build();
        baidumap.setMyLocationData(locationData);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        Log.e("map", "aaa");
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }
//
}



package com.jjjx.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.jjjx.data.json.JsonMananger;
import com.jjjx.function.entity.CityEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xcoder_xz
 * @date 2017/5/6 0006
 * 如果要使用PickerView的省市区，
 * 要对省市区解析成三个集合,
 */

public class AddressUtil {

    private static final int MSG_LOAD_DATA = 0x0001;
    private Thread thread;

    /**
     * 省
     */
    private ArrayList<String> listProvince = new ArrayList<>();
    /**
     * 市
     */
    private ArrayList<ArrayList<String>> listcity = new ArrayList<>();
    /**
     * 区
     */
    private ArrayList<ArrayList<ArrayList<String>>> listarea = new ArrayList<>();


    private List<CityEntity> listCityEntity = new ArrayList<>();


    private OnProvinceCityAreaLintener mOnProvinceCityAreaLintener;

    /**
     * 设置获取监听
     */
    public void setOnProvinceCityAreaLintener(OnProvinceCityAreaLintener onProvinceCityAreaLintener) {
        this.mOnProvinceCityAreaLintener = onProvinceCityAreaLintener;
    }

    /**
     * 读取assets的省市区文件
     *
     * @param context 上下文
     * @return List集合
     */
    public List<CityEntity> getProvinceToList(Context context) {
        List<CityEntity> cityEntities = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("province.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            stringBuilder = new StringBuilder();
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(stringBuilder.toString())) {
            cityEntities = JsonMananger.jsonToList(stringBuilder.toString(), CityEntity.class);
        }

        return cityEntities;
    }


    /**
     * 得到省市区的集合
     */
    public void getPCA(List<CityEntity> listCity) {
        this.listCityEntity = new ArrayList<>(listCity);
        //省级循环
        Iterator<CityEntity> itP = listCityEntity.iterator();
        while (itP.hasNext()) {
            CityEntity cityPro = itP.next();
            if (cityPro.getParentId() == 0) {
                //如果是省
                int cityProId = cityPro.getId();
                listProvince.add(cityPro.getName());
                itP.remove();

                ArrayList<String> listc = new ArrayList<>();
                ArrayList<ArrayList<String>> listar = new ArrayList<>();
                //市级循环
                Iterator<CityEntity> itC = listCityEntity.iterator();
                while (itC.hasNext()) {

                    CityEntity cityCity = itC.next();
                    int cityCityParId = cityCity.getParentId();
                    if (cityCity.getParentId() == cityProId) {
                        //如果是市
                        int cityCityId = cityCity.getId();
                        listc.add(cityCity.getName());
                        itC.remove();

                        ArrayList<String> lista = new ArrayList<>();
                        //区级循环
                        Iterator<CityEntity> itA = listCityEntity.iterator();
                        while (itA.hasNext()) {
                            CityEntity cityArea = itA.next();
                            int cityAreaId = cityArea.getParentId();
                            if (cityArea.getParentId() == cityCityId) {
                                //如果是省
                                lista.add(cityArea.getName());
                                itA.remove();
                            }
                        }
                        if (lista.size() <= 0) {
                            lista.add("");
                        }
                        listar.add(lista);
                        itC = listCityEntity.iterator();
                    }
                }
                if (listc.size() <= 0) {
                    listc.add("");
                }
                listcity.add(listc);
                listarea.add(listar);
                itP = listCityEntity.iterator();
            }
        }

        if (mOnProvinceCityAreaLintener != null) {
            mOnProvinceCityAreaLintener.listProvinceCityArea(listProvince, listcity, listarea);
        }
    }


    /**
     * 得到省市的集合
     */
    public void getProvinceCity(List<CityEntity> listCity) {
        this.listCityEntity = new ArrayList<>(listCity);
        //省级循环
        Iterator<CityEntity> itP = listCityEntity.iterator();
        while (itP.hasNext()) {
            CityEntity cityPro = itP.next();
            if (cityPro.getParentId() == 0) {
                //如果是省
                int cityProId = cityPro.getId();
                listProvince.add(cityPro.getName());
                itP.remove();

                ArrayList<String> listc = new ArrayList<>();
                //市级循环
                Iterator<CityEntity> itC = listCityEntity.iterator();
                while (itC.hasNext()) {

                    CityEntity cityCity = itC.next();
                    if (cityCity.getParentId() == cityProId) {
                        //如果是市
                        listc.add(cityCity.getName());
                        itC.remove();
                    }
                }
                if (listc.size() <= 0) {
                    listc.add("");
                }
                listcity.add(listc);
                itP = listCityEntity.iterator();
            }
        }
        if (mOnProvinceCityAreaLintener != null) {
            mOnProvinceCityAreaLintener.listProvinceCityArea(listProvince, listcity, new ArrayList<ArrayList<ArrayList<String>>>());
        }
    }


    public String getAddressName(List<CityEntity> listCE, int addressId) {
        String name = null;
        if (listCE != null) {
            for (CityEntity ce : listCE) {
                if (ce.getId() == addressId) {
                    name = ce.getName();
                    break;
                }
            }
        }
        return name;
    }

    /**
     * 根据名字获取地址Id
     *
     * @param listCE     地址集合
     * @param addressStr 地址名字
     */
    public int getAddressId(List<CityEntity> listCE, String addressStr) {
        int id = 0;
        if (listCE != null && !TextUtils.isEmpty(addressStr)) {
            for (CityEntity ce : listCE) {
                if (TextUtils.equals(ce.getName(), addressStr)) {
                    id = ce.getId();
                    break;
                }
            }
        }
        return id;
    }

    /**
     * 移除所有集合的数据
     */
    public void removeAllList(){
        listProvince.clear();
        listcity.clear();
        listarea.clear();
        listCityEntity.clear();
    }


    /**
     * 获取省市区的集合
     */
    public interface OnProvinceCityAreaLintener {
         void listProvinceCityArea(ArrayList<String> listProvince, ArrayList<ArrayList<String>> listcity, ArrayList<ArrayList<ArrayList<String>>> listarea);
    }
}

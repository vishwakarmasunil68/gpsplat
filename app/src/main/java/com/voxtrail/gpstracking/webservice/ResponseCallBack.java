package com.voxtrail.gpstracking.webservice;


import com.voxtrail.gpstracking.pojo.ResponsePOJO;

/**
 * Created by sunil on 29-12-2016.
 */

public interface ResponseCallBack<T> {
    public void onGetMsg(ResponsePOJO<T> responsePOJO);
}

package com.bwie.week3.presenter;


import com.bwie.week3.model.IModel;
import com.bwie.week3.model.IModelImp;
import com.bwie.week3.view.IView;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/15.
 */

public class IPesenterImp implements IPesenter {
    IView iView;
    private final IModelImp iModel;

    public IPesenterImp(IView iView) {
        this.iView=iView;
        iModel = new IModelImp();
    }

    @Override
    public void getData(String urlString) {
        iModel.getRequest(urlString, new IModel.CallBack() {
            @Override
            public void success(String string) {
                iView.showSuccess(string);
            }
        });
    }
}

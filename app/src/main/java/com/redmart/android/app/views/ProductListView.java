package com.redmart.android.app.views;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;

import com.redmart.android.uimodels.ProductItemUIModel;

import java.util.List;
/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public interface ProductListView {

    void showMainProgressBar();

    void showErrorMsg( int stringResourceId);

    void updateRecyclerView(List<ProductItemUIModel> productViewModelList);

    void showFooterLoader();

    void showProductDetailView(Intent intent);

    boolean isLoading();

    GridLayoutManager getLayoutManager();

    int getPageCount();

}

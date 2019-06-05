package com.redmart.android.presenters;

/**
 * Created by ramindu.weeraman on 27/3/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.redmart.android.R;
import com.redmart.android.app.api.RedMartApi;
import com.redmart.android.app.presenters.ProductListPresenter;
import com.redmart.android.app.views.ProductListView;
import com.redmart.android.responsemodels.productList.Product;
import com.redmart.android.responsemodels.productList.ProductListResponse;
import com.redmart.android.uimodels.ProductItemUIModel;
import com.redmart.android.uimodels.UIModelCreator;
import com.redmart.android.utils.disposable.DisposableManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPresenterTest {
    @Mock
    Product product;
    @Mock
    Activity activity;
    @Mock
    RecyclerView recyclerView;
    @Mock
    private ProductListView productListView;
    @Mock
    RedMartApi redMartApi;
    @Mock
    UIModelCreator viewModelCreator;
    @Mock
    DisposableManager disposableManager;


    ProductListPresenter productListPresenter;


    @Before
    public void before() throws Exception {

        productListPresenter = new ProductListPresenter(productListView, redMartApi, recyclerView,
                new MockSchedulerProvider(),viewModelCreator,disposableManager);
    }

    @Test
    public void checkIfProductListObjectCountZero() throws Exception {
        ProductListResponse productListResponse = new ProductListResponse();
        when(redMartApi.getProductList("0", "20"))
                .thenReturn(Observable.just(productListResponse));

        productListPresenter.setPageCounter(0);
        productListPresenter.getProductList();
        InOrder inOrder = Mockito.inOrder(productListView);
        inOrder.verify(productListView, times(1)).showMainProgressBar();
    }


    @Test
    public void checkIfProductListCountNotZero() throws Exception {
        ProductListResponse productListResponse = new ProductListResponse();
        when(redMartApi.getProductList("2", "20"))
                .thenReturn(Observable.just(productListResponse));
        productListPresenter.setPageCounter(2);
        productListPresenter.getProductList();
        InOrder inOrder = Mockito.inOrder(productListView);
        inOrder.verify(productListView, times(1)).showFooterLoader();
    }


    @Test
    public void checkIfProductListError() throws Exception {
        Exception exception = new Exception();
        when(redMartApi.getProductList("0", "20"))
                .thenReturn(Observable.<ProductListResponse>error(exception));
        productListPresenter.getProductList();
        InOrder inOrder = Mockito.inOrder(productListView);
        inOrder.verify(productListView, times(1)).showErrorMsg(R.string.err_productListNotFound);
    }

    @Test
    public void testGetValidProductListNoError() {
        ProductListResponse productListResponse = new ProductListResponse();
        List<ProductItemUIModel> productViewModelList = new ArrayList<>();

        when(redMartApi.getProductList("0", "20"))
                .thenReturn(Observable.just(productListResponse));
        when(viewModelCreator.getProductViewModelList(productListResponse))
                .thenReturn(productViewModelList);

        productListPresenter.getProductList();
        verify(productListView, never()).showErrorMsg(R.string.err_productListNotFound);
    }

    @Test
    public void testGetValidProductListNoSuccess() {
        ProductListResponse productListResponse = new ProductListResponse();
        List<ProductItemUIModel> productViewModelList = new ArrayList<>();

        when(redMartApi.getProductList("0", "20"))
                .thenReturn(Observable.just(productListResponse));
        when(viewModelCreator.getProductViewModelList(productListResponse))
                .thenReturn(productViewModelList);

        productListPresenter.getProductList();
        InOrder inOrder = Mockito.inOrder(productListView);
        inOrder.verify(productListView, times(1)).updateRecyclerView(productViewModelList);
    }


    @Test
    public void testForCurrentLoadingOnCase() throws Exception {
        when(productListView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 3));
        when(productListView.isLoading()).thenReturn(true);
        productListPresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(redMartApi, never()).getProductList("1", "20");
    }

    @Test
    public void testForPageCountCompleteForLoadMoreCase() throws Exception {
        when(productListView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 3));
        when(productListView.isLoading()).thenReturn(true);
        productListPresenter.isLastPage = true;
        productListPresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(redMartApi, never()).getProductList("1", "20");
    }

    @Test
    public void testForProductClickedFlow() throws Exception {
        productListPresenter.onProductClicked(activity, product);
        productListView.showProductDetailView(any(Intent.class));
    }

    @Test
    public void testCreateNullViewModel() throws Exception {
        UIModelCreator viewModelCreator = new UIModelCreator();
        List<ProductItemUIModel> viewModels = viewModelCreator.getProductViewModelList(null);
        assertEquals(null, viewModels);
    }


}

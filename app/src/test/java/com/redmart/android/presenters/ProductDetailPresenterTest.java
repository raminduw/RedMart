package com.redmart.android.presenters;

import com.redmart.android.R;
import com.redmart.android.app.api.RedMartApi;
import com.redmart.android.app.presenters.ProductDetailViewPresenter;
import com.redmart.android.app.views.ProductDetailsView;
import com.redmart.android.responsemodels.productDetails.ProductDetailsResponse;
import com.redmart.android.uimodels.ProductDetailUIModel;
import com.redmart.android.uimodels.UIModelCreator;
import com.redmart.android.utils.disposable.DisposableManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ramindu.weeraman on 28/3/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailPresenterTest {
    @Mock
    RedMartApi redMartApi;
    @Mock
    ProductDetailsView productDetailsView;
    @Mock
    UIModelCreator viewModelCreator;
    @Mock
    DisposableManager disposableManager;

    ProductDetailViewPresenter detailViewPresenter;

    @Before
    public void before() throws Exception {
        detailViewPresenter = new ProductDetailViewPresenter(productDetailsView, redMartApi,
                new MockSchedulerProvider(),viewModelCreator,disposableManager);
    }

    @Test
    public void checkIfProductObjectIsNullCase() throws Exception {
        detailViewPresenter.showDetails(null);
        verify(productDetailsView, only()).showErrorMsg(R.string.err_productDetailsNotFound);
    }

    public void checkIfProductObjectIsNotNullCase() throws Exception {
        detailViewPresenter.showDetails("12345");
        verify(productDetailsView, never()).showErrorMsg(R.string.err_productDetailsNotFound);
    }

    @Test
    public void testGetErrorProductDetails() {
        Exception exception = new Exception();
        when(redMartApi.getProductDetails("12345"))
                .thenReturn(Observable.<ProductDetailsResponse>error(exception));
        detailViewPresenter.showDetails("12345");
        InOrder inOrder = Mockito.inOrder(productDetailsView);
        inOrder.verify(productDetailsView, times(1)).showErrorMsg(R.string.err_productDetailsNotFound);

    }

    @Test
    public void testAddToCart() throws Exception {
        detailViewPresenter.onAddToCartClicked();
        verify(productDetailsView, only()).showErrorMsg(R.string.err_addToCartPending);
    }

    @Test
    public void testCreateNullViewModel() throws Exception {
        UIModelCreator viewModelCreator = new UIModelCreator();
        ProductDetailUIModel viewModel = viewModelCreator.getProductDetailViewModel(null);
        assertEquals(null, viewModel);
    }


    @Test
    public void testGetValidProductDetailsNoError() {
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        when(viewModelCreator.getProductDetailViewModel(productDetailsResponse))
                .thenReturn(new ProductDetailUIModel());
        when(redMartApi.getProductDetails("12345"))
                .thenReturn(Observable.just(productDetailsResponse));
        detailViewPresenter.showDetails("12345");
        verify(productDetailsView, never()).showErrorMsg(R.string.err_productDetailsNotFound);
    }

    @Test
    public void testGetValidProductDetailsSuccess() {
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        ProductDetailUIModel productDetailViewModel = new ProductDetailUIModel();
        when(viewModelCreator.getProductDetailViewModel(productDetailsResponse))
                .thenReturn(productDetailViewModel);
        when(redMartApi.getProductDetails("12345"))
                .thenReturn(Observable.just(productDetailsResponse));
        detailViewPresenter.showDetails("12345");
        InOrder inOrder = Mockito.inOrder(productDetailsView);
        inOrder.verify(productDetailsView, times(1)).showProductDetails(productDetailViewModel);
    }


}
package com.redmart.android.viewmodels;

import com.redmart.android.responseModels.productDetails.ProductDetailsResponse;
import com.redmart.android.responseModels.productList.Primary;
import com.redmart.android.responseModels.productList.Product;
import com.redmart.android.responseModels.productList.ProductListResponse;
import com.redmart.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramindu.weeraman on 30/3/18.
 */

public class ViewModelCreator {


    public ProductDetailViewModel getProductDetailViewModel(ProductDetailsResponse productDetailsResponse) {
        ProductDetailViewModel productDetailViewModel = null;
        if (productDetailsResponse != null) {
            productDetailViewModel = new ProductDetailViewModel();
            Product selectedProduct = productDetailsResponse.getProduct();
            productDetailViewModel.setProductName(selectedProduct.getTitle());
            productDetailViewModel.setProductDetails(selectedProduct != null ? selectedProduct.getMeasure().getWtOrVol() : null);

            double displayAmount = 0;
            double originalAmount = 0;
            if (selectedProduct.getPricing() != null) {
                if (selectedProduct.getPricing().getPromoPrice() != null && selectedProduct.getPricing().getPromoPrice() > 0) {
                    displayAmount = selectedProduct.getPricing().getPromoPrice();
                    originalAmount = selectedProduct.getPricing().getPrice();
                } else {
                    displayAmount = selectedProduct.getPricing().getPrice();
                }

                productDetailViewModel.setSavingMsg(selectedProduct.getPricing().getSavingsText());
                productDetailViewModel.setCurrentAmount(Utils.getInstance().formatAmountToString(displayAmount));
                productDetailViewModel.setOriginalAmount(Utils.getInstance().formatAmountToString(originalAmount));
                productDetailViewModel.setImages(selectedProduct.getImages());

            }

            if (selectedProduct.getDescriptionFields() != null) {
                List<Primary> descList = new ArrayList<>();

                if (selectedProduct.getDescriptionFields().getPrimary() != null) {
                    for (Primary primary : selectedProduct.getDescriptionFields().getPrimary()) {
                        if (!"".equals(primary.getName()) && !"".equals(primary.getContent())) {
                            descList.add(primary);
                        }
                    }
                }

                if (selectedProduct.getDescriptionFields().getSecondary() != null) {
                    for (Primary primary : selectedProduct.getDescriptionFields().getSecondary()) {
                        if (!"".equals(primary.getName()) && !"".equals(primary.getContent())) {
                            descList.add(primary);
                        }
                    }

                }

                if (descList.size() > 0) {
                    productDetailViewModel.setMetaDetailsList(descList);
                }
            }
        }
        return productDetailViewModel;
    }

    public List<ProductViewModel> getProductViewModelList(ProductListResponse productListResponse) {
        List<ProductViewModel> productViewModels = null;
        if (productListResponse != null) {
            productViewModels = new ArrayList<>();
            ProductViewModel productViewModel = null;
            for (Product product : productListResponse.getProducts()) {
                productViewModel = new ProductViewModel(0, product);
                productViewModels.add(productViewModel);
            }
        }

        return productViewModels;

    }

}

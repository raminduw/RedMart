package com.redmart.android.viewmodels;


import com.redmart.android.responseModels.productList.Image;
import com.redmart.android.responseModels.productList.Primary;

import java.util.List;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public class ProductDetailViewModel {

    private String productName;
    private String productDetails;
    private String currentAmount;
    private String originalAmount;
    private String savingMsg;
    private List<Image> images;
    private List<Primary> metaDetailsList;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getSavingMsg() {
        return savingMsg;
    }

    public void setSavingMsg(String savingMsg) {
        this.savingMsg = savingMsg;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Primary> getMetaDetailsList() {
        return metaDetailsList;
    }

    public void setMetaDetailsList(List<Primary> metaDetailsList) {
        this.metaDetailsList = metaDetailsList;
    }


}

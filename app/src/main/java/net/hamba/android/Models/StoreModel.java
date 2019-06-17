package net.hamba.android.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class StoreModel {
    public String storeName;
    public String storeType;
    public String storeCategories;
    public String storeImage;
    public String storeDescription;
    public String storeCountry;
    public String storeAddress;
    public String storePhone;
    public String storeEmail;
    public boolean storeIsInMall;
    public String storeMalls;
    public boolean isSmartPricing;
    public String storeDeliveryRange;
    public boolean isSpecificCountry;
    public String specific;
    public String deliveryTime;
    public boolean isDiscountPercentage;
    public String vatPercent;
    public String paymentsAccepts;
    public String shopGender;
    public String deliverySupports;
    public double address_lat;
    public double address_lng;
    public ArrayList<LatLng> polygon;
    public String storeMinOrder;
    public String currency;
    public String  deliveryCost;
    public ArrayList<WeekdayModel> weekdays;
    public String discountCode;
    public String discountPercent;
    public String licence;
    public String taxNumber;

    public String getStoreMinOrder() {
        return storeMinOrder;
    }

    public void setStoreMinOrder(String storeMinOrder) {
        this.storeMinOrder = storeMinOrder;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public ArrayList<WeekdayModel> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(ArrayList<WeekdayModel> weekdays) {
        this.weekdays = weekdays;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public double getAddress_lat() {
        return address_lat;
    }

    public void setAddress_lat(double address_lat) {
        this.address_lat = address_lat;
    }

    public double getAddress_lng() {
        return address_lng;
    }

    public void setAddress_lng(double address_lng) {
        this.address_lng = address_lng;
    }

    public ArrayList<LatLng> getPolygon() {
        return polygon;
    }

    public void setPolygon(ArrayList<LatLng> polygon) {
        this.polygon = polygon;
    }

    public boolean isSpecificCountry() {
        return isSpecificCountry;
    }

    public void setSpecificCountry(boolean specificCountry) {
        isSpecificCountry = specificCountry;
    }

    public String getSpecific() {
        return specific;
    }

    public void setSpecific(String specific) {
        this.specific = specific;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public boolean isDiscountPercentage() {
        return isDiscountPercentage;
    }

    public void setDiscountPercentage(boolean discountPercentage) {
        isDiscountPercentage = discountPercentage;
    }

    public String getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(String vatPercent) {
        this.vatPercent = vatPercent;
    }

    public String getPaymentsAccepts() {
        return paymentsAccepts;
    }

    public void setPaymentsAccepts(String paymentsAccepts) {
        this.paymentsAccepts = paymentsAccepts;
    }

    public String getShopGender() {
        return shopGender;
    }

    public void setShopGender(String shopGender) {
        this.shopGender = shopGender;
    }

    public String getDeliverySupports() {
        return deliverySupports;
    }

    public void setDeliverySupports(String deliverySupports) {
        this.deliverySupports = deliverySupports;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreCategories() {
        return storeCategories;
    }

    public void setStoreCategories(String storeCategories) {
        this.storeCategories = storeCategories;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public String getStoreCountry() {
        return storeCountry;
    }

    public void setStoreCountry(String storeCountry) {
        this.storeCountry = storeCountry;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public boolean isStoreIsInMall() {
        return storeIsInMall;
    }

    public void setStoreIsInMall(boolean storeIsInMall) {
        this.storeIsInMall = storeIsInMall;
    }

    public String getStoreMalls() {
        return storeMalls;
    }

    public void setStoreMalls(String storeMalls) {
        this.storeMalls = storeMalls;
    }

    public boolean isSmartPricing() {
        return isSmartPricing;
    }

    public void setSmartPricing(boolean smartPricing) {
        isSmartPricing = smartPricing;
    }

    public String getStoreDeliveryRange() {
        return storeDeliveryRange;
    }

    public void setStoreDeliveryRange(String storeDeliveryRange) {
        this.storeDeliveryRange = storeDeliveryRange;
    }

}

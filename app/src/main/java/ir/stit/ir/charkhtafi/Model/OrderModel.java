package ir.stit.ir.charkhtafi.Model;

import java.util.List;

/**
 * Created by sajjadnet on 25/01/2018.
 */

public class OrderModel {

    private String id;
    private String Description;
//    private String NameUser;
    private String TotalPrice;
    private String DeliveryPrice;
    private String Off;
    private String DeliveryFree;
    private float DomainOffPercent; // the amount of percent for domain off
    private int DomainStatus; // 1->dosn't have any off code | 2 -> it has off code & effect in order | 3 -> it has off code & effect to products
    private boolean IsPreOrder;
    private List<OrderFruitModel> fruitModel;
    private List<AllDeliveryModel> allDelivery;
    private boolean Active;
    private double Wallet;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderFruitModel> getFruitModel() {
        return fruitModel;
    }

    public void setFruitModel(List<OrderFruitModel> fruitModel) {
        this.fruitModel = fruitModel;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

//    public String getNameUser() {
//        return NameUser;
//    }

//    public void setNameUser(String nameUser) {
//        NameUser = nameUser;
//    }

    public List<AllDeliveryModel> getAllDelivery() {
        return allDelivery;
    }

    public void setAllDelivery(List<AllDeliveryModel> allDelivery) {
        this.allDelivery = allDelivery;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getDeliveryPrice() {
        return DeliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        DeliveryPrice = deliveryPrice;
    }

    public String getOff() {
        return Off;
    }

    public void setOff(String off) {
        Off = off;
    }

    public String getDeliveryFree() {
        return DeliveryFree;
    }

    public void setDeliveryFree(String deliveryFree) {
        DeliveryFree = deliveryFree;
    }


    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public boolean isPreOrder() {
        return IsPreOrder;
    }

    public void setPreOrder(boolean preOrder) {
        IsPreOrder = preOrder;
    }

    public float getDomainOffPercent() {
        return DomainOffPercent;
    }

    public void setDomainOffPercent(float domainOffPercent) {
        DomainOffPercent = domainOffPercent;
    }

    public int getDomainStatus() {
        return DomainStatus;
    }

    public void setDomainStatus(int domainStatus) {
        DomainStatus = domainStatus;
    }

    public double getWallet() {
        return Wallet;
    }

    public void setWallet(double wallet) {
        Wallet = wallet;
    }
}

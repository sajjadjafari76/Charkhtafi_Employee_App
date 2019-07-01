package ir.stit.ir.charkhtafi.Model;

import java.util.List;



public class DeliveryModel {

    private String Id;
    private String Name;
    private String Debtor;
    private String Address;
    private String BikePrice;
    private String Payment;
    private String PaymentText;
    private String Time;
    private boolean IsPreOrder;
    private String NewTotalOrderPrice;
    private String OldTotalOrderPrice;
    private String Wallet;
    private List<OrderFruitModel> data;

    public List<OrderFruitModel> getData() {
        return data;
    }

    public void setData(List<OrderFruitModel> data) {
        this.data = data;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDebtor() {
        return Debtor;
    }

    public void setDebtor(String debtor) {
        Debtor = debtor;
    }

    public String getNewTotalOrderPrice() {
        return NewTotalOrderPrice;
    }

    public void setNewTotalOrderPrice(String newTotalOrderPrice) {
        NewTotalOrderPrice = newTotalOrderPrice;
    }

    public String getBikePrice() {
        return BikePrice;
    }

    public void setBikePrice(String bikePrice) {
        BikePrice = bikePrice;
    }

    public boolean isPreOrder() {
        return IsPreOrder;
    }

    public void setPreOrder(boolean preOrder) {
        IsPreOrder = preOrder;
    }

    public String getWallet() {
        return Wallet;
    }

    public void setWallet(String wallet) {
        Wallet = wallet;
    }

    public String getOldTotalOrderPrice() {
        return OldTotalOrderPrice;
    }

    public void setOldTotalOrderPrice(String oldTotalOrderPrice) {
        OldTotalOrderPrice = oldTotalOrderPrice;
    }

    public String getPaymentText() {
        return PaymentText;
    }

    public void setPaymentText(String paymentText) {
        PaymentText = paymentText;
    }
}

package ir.stit.ir.charkhtafi.Model;

import java.util.List;



public class DeliveryModel {

    private String Id;
    private String Name;
    private String Debtor;
    private String Address;
    private String BikePrice;
    private String Payment;
    private String Time;
    private boolean IsPreOrder;
    private String TotalOrderPrice;
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

    public String getTotalOrderPrice() {
        return TotalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        TotalOrderPrice = totalOrderPrice;
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
}

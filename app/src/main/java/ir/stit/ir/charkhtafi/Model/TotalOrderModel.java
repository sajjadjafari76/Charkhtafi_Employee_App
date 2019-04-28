package ir.stit.ir.charkhtafi.Model;

import java.util.List;

/**
 * Created by sajjadnet on 1/4/2018.
 */

public class TotalOrderModel {


    private List<AllDeliveryModel> allBuyer;
    private List<TotalProductOrderModel> totalOrder;


    public List<AllDeliveryModel> getAllBuyer() {
        return allBuyer;
    }

    public void setAllBuyer(List<AllDeliveryModel> allBuyer) {
        this.allBuyer = allBuyer;
    }

    public List<TotalProductOrderModel> getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(List<TotalProductOrderModel> totalOrder) {
        this.totalOrder = totalOrder;
    }
}

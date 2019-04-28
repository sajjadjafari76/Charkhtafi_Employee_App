package ir.stit.ir.charkhtafi;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.Model.AllDeliveryModel;
import ir.stit.ir.charkhtafi.Model.DeliveryModel;
import ir.stit.ir.charkhtafi.Model.EditPriceModel;
import ir.stit.ir.charkhtafi.Model.OrderFruitModel;
import ir.stit.ir.charkhtafi.Model.OrderModel;
import ir.stit.ir.charkhtafi.Model.TotalOrderModel;
import ir.stit.ir.charkhtafi.Network.CustomRequest;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.Utils.Tools;

import static ir.stit.ir.charkhtafi.Globals.ApiURLIMAGE;

/**
 * Created by sajjadnet on 1/10/2018.
 */

public class Functions {

    private static Functions functions;
    private Activity context;

    private Functions(Activity context) {
        this.context = context;
    }
    public static Functions getInstance(Activity context) {
        if (functions == null) {
            functions = new Functions(context);
        }
        return functions;
    }


    public void Login(String UserName, String Pass, final GetLoginStatus status) {

//        JSONObject dataSend = null;
//        try {
//            dataSend = new JSONObject();
////            Log.e("sdv", UserName + " | " + Pass);
//            dataSend.put("email", UserName);
//            dataSend.put("password", Pass);
//            dataSend.put("OPR","");
//        } catch (Exception er) {
//            er.printStackTrace();
//        }
//
////        Log.e("sdv", dataSend.toString() + " |");
//        CustomRequest LoginRequest = new CustomRequest(
//                "/login",
//                dataSend,
//                new CustomRequest.ResponseAction() {
//                    @Override
//                    public void onResponseAction(JSONObject jsonObject) {
//
//                        try {
//                            Log.e("LoginResponse", jsonObject.toString() + "|");
//
////                            {"data":{"firstName":"admin","lastName":"admin","email":"admin@email.charkhtafi","level":null,
////                                    "api_token":"vzFkpPLLm4Do9VoQXLonxIZGyzG0xDGXOhGriDL9MGOYndPGRKeTlIOSaZCaSmoBAR1HTk09vquPAQJ48yHYggOoLf0dB8ceYFgQ",
////                                    "status":1}}|
//
//
//                            if (jsonObject.has("data")) {
//                                JSONObject object = jsonObject.getJSONObject("data");
//
//                                Tools.getInstance(context).write("Token", object.getString("api_token"));
//                                Tools.getInstance(context).write("UserId", object.getString("id"));
//                                Tools.getInstance(context).write("Type", Tools.getInstance(context).TypeOfUser(object.getInt("level")));
//                                Tools.getInstance(context).write("FirstName", object.getString("firstName"));
//                                Tools.getInstance(context).write("LastName", object.getString("lastName"));
//                                context.startActivity(new Intent(context, MainActivity.class));
//                                status.onReceived("Ok");
//
//                            }
//                        } catch (Exception er) {
//                            status.onError("Error");
//                            Log.e("LoginError", er.toString());
//                        }
//
//                    }
//
//                    @Override
//                    public void onErrorAction(VolleyError error) {
//                        error.printStackTrace();
//                        Log.e("LoginVolleyError", error.toString());
//                        status.onError("Error");
//                    }
//                }
//                , 0){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("AUTHORIZE", "hpnwE4gXmffbX0hNPOb5qVkLwcJVrFZh3cpDQC8W69uPVeIMS2rooh7RRdJnSRs2n4SH9ypAEClRa471lrBnsCxjgtQhxsTMoaVjMD9M9KmPWjUleeV7wqrPbAi1PCyNZmAGpUU3t0A0wlB59RTXv1KCG064P7zTzqdYHftEOL68jDgfWnC0iouBeEdWMtOa8iMoRI3vTpzNJhTol0Kf4qZiesbxshDsgDrGzY3ntzlZQqzcxcWlYx4TJZFHaI0PUmawLS4Lav2RwQbqIz1pRInAcwxasH8UdcFMoCPs9vxJ3bH8JSFVHaWg6mUYls5fg4du3Y5spMRQItwsMUnk14p5tsDzp6vpvmctMGwDct4Xb2MconyQ6f3JDvXkQfiqDzTUKSSsN5uAchh0joWgnKStD9mb36SZcFwZ3oo6LduB5vG0CECn3fRDlh9P9MIDl6nL2uDOEeNDx2G85e21Eu0MsfbIyQpjqI52OLp5BWLdDgq9Mjzto4IoJwAyTHGuj2kq4Rani0bfEGXuCEz3OTslbQEwiA2OAi4T1dhHfwvOYDOWMTEMPstrQKJD158Ls0dGmAX3CY03Wq23vidMiJV343ugQeNsXXfSqIZBopFwSIvco9pTDqFffz132N5g");
//                return map;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(LoginRequest);

//        Tools.getInstance(context).write("Token", "vzFkpPLLm4Do9VoQXLonxIZGyzG0xDGXOhGriDL9MGOYndPGRKeTlIOSaZCaSmoBAR1HTk09vquPAQJ48yHYggOoLf0dB8ceYFgQ");
//        Tools.getInstance(context).write("Type", Tools.getInstance(context).TypeOfUser(1));
//        Tools.getInstance(context).write("FirstName", "سجاد");
//        Tools.getInstance(context).write("LastName", "جعفری");
//        context.startActivity(new Intent(context, MainActivity.class));

        Map<String, String> params = new HashMap<>();
        params.put("OPR", "");
        params.put("tel", "");
        params.put("pwd", "");


        StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
            @Override
            public void onResponseAction(String jsonObject) {

            }

            @Override
            public void onErrorAction(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(loginRequest);

    }


    //****  Edit_Price   ****//
    public void ProductList(final GetProductList productList) {

//        JSONObject dataSend = null;
//        try {
//            dataSend = new JSONObject();
//        }catch (Exception er) {
//            er.printStackTrace();
//        }

        CustomRequest ProductListRequest = new CustomRequest(
                "/productList?api_token=" + Tools.getInstance(context).read("Token", ""),
                null,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("ProductListResponse", jsonObject.toString() + "|");



                            if (jsonObject.has("data")) {
                                List<EditPriceModel> data = new ArrayList<>();
                                JSONArray array = jsonObject.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    if (object.getJSONArray("prices").length() != 0) {
                                        for (int j = 0; j < object.getJSONArray("prices").length(); j++) {

                                            EditPriceModel model = new EditPriceModel();
//                                            model.setId(object.getString("id"));
                                            model.setId(object.getJSONArray("prices").getJSONObject(j).getString("id"));
//                                            model.setProduct_Id(object.getJSONArray("prices").getJSONObject(j).getString("product_id"));
                                            model.setProduct_Id(object.getJSONArray("prices").getJSONObject(j).getString("product_id"));
                                            model.setName(object.getString("name"));
                                            model.setImage(object.getString("image"));
                                            model.setType(object.getJSONArray("prices").getJSONObject(j).getString("title"));
                                            model.setPrice(object.getJSONArray("prices").getJSONObject(j).getString("price"));

                                            data.add(model);

                                        }
                                    }
//                                    Log.e("sajjadjafari", object.getJSONArray("prices").getJSONObject(0).getString("title") + " |");
                                }

                                productList.OnListReceived(data);
                            }
                        } catch (Exception er) {
                            productList.OnListError("Error1");
                            Log.e("ProductListError", er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("ProductListErrorVolley", error.toString());
                        productList.OnListError("Error2");
                    }
                }
                , 1);

        AppController.getInstance().addToRequestQueue(ProductListRequest);
    }
    public void UpdatePrice(String Id, String Price, String ProductId, int position, AllForUpdatePrice all) {

//        id
//                price
//        product_id
//        Log.e("changePrice", Id + " | " + Price + " | " + ProductId);

        JSONObject dataSend = null;
        try {
            dataSend = new JSONObject();
            dataSend.put("id", Id);
            dataSend.put("price", Price);
            dataSend.put("product_id", ProductId);
            dataSend.put("api_token", Tools.getInstance(context).read("Token", ""));
        } catch (Exception er) {
            er.printStackTrace();
        }

        CustomRequest UpdatePrice = new CustomRequest(
                "/priceUpdate",
                dataSend,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("UpdatePriceResponse", jsonObject.toString() + "|");


                            all.Success(jsonObject.getJSONObject("data").getString("price"), position);


                        } catch (Exception er) {
                            all.Failed("Error");
                            Log.e("UpdatePriceError", er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("UpdatePriceErrorVolley", error.toString());
                        all.Failed("");
                    }
                }
                , 0);

        AppController.getInstance().addToRequestQueue(UpdatePrice);
    }


    //****  Total_Order   ****//
    public void TotalOrder(final GetTotalOrder totalOrder) {

        CustomRequest TotalOrder = new CustomRequest(
                "/orderList/"+ Tools.getInstance(context).read("UserId","") +"?api_token=" + Tools.getInstance(context).read("Token", ""),
                null,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("TotalOrderResponse", jsonObject.toString() + "|");

                            JSONArray myArray = new JSONArray();
                            try {
                                if (jsonObject.getJSONArray("orders").length() != 0) {
                                JSONArray array = jsonObject.getJSONArray("orders");

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);

                                        JSONArray array1 = object.getJSONArray("order_items");

                                        List<TotalOrderModel> model = new ArrayList<>();


                                        for (int j = 0; j < array1.length(); j++) {
                                            JSONObject object1 = array1.getJSONObject(j);
//                                            Log.e("object1", object1.toString() + " | ");

                                            JSONObject myobject = new JSONObject();
                                            myobject.put("IdFruit", object1.getJSONObject("product").getInt("id"));
                                            myobject.put("IdPrice", object1.getJSONObject("price").getInt("id"));
                                            myobject.put("name", object1.getJSONObject("product").getString("name"));
                                            myobject.put("weight", object1.getString("quantity"));
                                            myobject.put("image", object1.getJSONObject("product").getString("image"));
                                            myobject.put("degree", object1.getJSONObject("price").getString("title"));
                                            myobject.put("unit", object1.getJSONObject("unit").getString("title"));
                                            myobject.put("price", object1.getString("sellPrice"));
                                            myArray.put(myobject);
//                                            Log.e("123456", myArray.length() + "");

//                                        Log.e("123456", myArray.toString());      /* "09100410875"*/

                                        }
                                    }
                                    JSONArray jsonArray = new JSONArray();
//                                    Log.e("object1", myArray.toString() + " | " + myArray.length() + " | " + myArray.getJSONObject(0).getInt("IdFruit") + " | ");

                                    for (int i = 0; i < myArray.length(); i++) {

                                        if (jsonArray.length() == 0) {
                                            JSONObject jsonObject12 = new JSONObject();
                                            jsonObject12.put("IdFruit", myArray.getJSONObject(i).getInt("IdFruit"));
                                            jsonObject12.put("IdPrice", myArray.getJSONObject(i).getInt("IdPrice"));
                                            jsonObject12.put("name", myArray.getJSONObject(i).getString("name"));
                                            jsonObject12.put("weight", myArray.getJSONObject(i).getString("weight"));
                                            jsonObject12.put("image", myArray.getJSONObject(i).getString("image"));
                                            jsonObject12.put("price", myArray.getJSONObject(i).getString("price"));
                                            jsonObject12.put("degree", myArray.getJSONObject(i).getString("degree"));
                                            jsonObject12.put("unit", myArray.getJSONObject(i).getString("unit"));
                                            jsonArray.put(jsonObject12);
//                                            Log.e("js0", jsonArray.toString() + " | " + jsonArray.length());
                                        } else {
                                            int lenght = jsonArray.length();
                                            int c = 0;
                                            for (int j = 0; j < lenght; j++) {

                                                int idfj = jsonArray.getJSONObject(j).getInt("IdFruit");
                                                int idfm = myArray.getJSONObject(i).getInt("IdFruit");

                                                int idpj = jsonArray.getJSONObject(j).getInt("IdPrice");
                                                int idpm = myArray.getJSONObject(i).getInt("IdPrice");

                                                if (idfj == idfm && idpj == idpm) {
                                                    float a1 = Float.parseFloat(myArray.getJSONObject(i).getString("weight"));
                                                    float a2 = Float.parseFloat(jsonArray.getJSONObject(j).getString("weight"));
                                                    jsonArray.getJSONObject(j).put("weight", String.valueOf((a1 + a2)));
//                                                    Log.e("js2", jsonArray.toString() + " | " + jsonArray.length());
                                                } else {
                                                    ++c;
                                                    if (lenght == c) {
//                                                        Log.e("js1", jsonArray.toString() + " | " + jsonArray.length());
                                                        JSONObject jsonObject1 = new JSONObject();
                                                        jsonObject1.put("IdFruit", myArray.getJSONObject(i).getInt("IdFruit"));
                                                        jsonObject1.put("IdPrice", myArray.getJSONObject(i).getInt("IdPrice"));
                                                        jsonObject1.put("name", myArray.getJSONObject(i).getString("name"));
                                                        jsonObject1.put("weight", myArray.getJSONObject(i).getString("weight"));
                                                        jsonObject1.put("image", myArray.getJSONObject(i).getString("image"));
                                                        jsonObject1.put("price", myArray.getJSONObject(i).getString("price"));
                                                        jsonObject1.put("degree", myArray.getJSONObject(i).getString("degree"));
                                                        jsonObject1.put("unit", myArray.getJSONObject(i).getString("unit"));
                                                        jsonArray.put(jsonObject1);
                                                    }
                                                }
                                            }
                                        }

                                    }
//                                    Log.e("js", jsonArray.toString() + " | " + jsonArray.length());

//                                    List<TotalOrderModel> models = new ArrayList<>();
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        TotalOrderModel totalOrderModel = new TotalOrderModel();
//                                        totalOrderModel.setName(jsonArray.getJSONObject(i).getString("name"));
//                                        totalOrderModel.setImage(jsonArray.getJSONObject(i).getString("image"));
//                                        totalOrderModel.setId(jsonArray.getJSONObject(i).getString("IdFruit"));
//                                        totalOrderModel.setPrice(jsonArray.getJSONObject(i).getString("price"));
//                                        totalOrderModel.setWight(jsonArray.getJSONObject(i).getString("weight"));
//                                        totalOrderModel.setDegree(jsonArray.getJSONObject(i).getString("degree"));
//                                        totalOrderModel.setUnit(jsonArray.getJSONObject(i).getString("unit"));
//                                        models.add(totalOrderModel);
//                                    }

//                                    totalOrder.OnListReceived(models);
                                }else {
                                    totalOrder.OnListNull("null");
                                }

                            } catch (Exception er) {
                                Log.e("TotalOrderError1", er.toString());
                            }

                        } catch (Exception er) {
                            totalOrder.OnListError("Error");
                            Log.e("TotalOrderError2", er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("TotalOrderErrorVolley", error.toString());
                        totalOrder.OnListError("Error");
                    }
                }
                , 1);

        AppController.getInstance().addToRequestQueue(TotalOrder);
    }
    public void SendTotalData(List<HashMap<String, Object>> data, All all) {

        JSONObject dataSend = null;
        try {
            dataSend = new JSONObject();
            dataSend.put("data", data);
        } catch (Exception er) {
            er.printStackTrace();
        }

//        Log.e("myjson", dataSend.toString() + " |");

        CustomRequest SendTotalOrder = new CustomRequest(
                "",
                dataSend,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("SendTotalOrderResponse", jsonObject.toString() + "|");

                            all.Success("");

                        }catch (Exception er) {
                            all.Failed("Error");
                            Log.e("SendTotalOrderError" , er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("SendTotalOrderErrorVoll" , error.toString());
                        all.Failed("Error");
                    }
                }
        ,0);

        AppController.getInstance().addToRequestQueue(SendTotalOrder);
    }


    //****   Order   ****//
    public void Order(final GetOrder getOrder) {

        final CustomRequest Order = new CustomRequest(
                "/orderList/"+ Tools.getInstance(context).read("UserId","") +"?api_token=" + Tools.getInstance(context).read("Token", "") /*+ "vzFkpPLLm4Do9VoQXLonxIZGyzG0xDGXOhGriDL9MGOYndPGRKeTlIOSaZCaSmoBAR1HTk09vquPAQJ48yHYggOoLf0dB8ceYFgQ"*/,
                null,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("OrderResponse", jsonObject.toString() + "|");

                            JSONArray array = jsonObject.getJSONArray("orders");

                            if (array.length() != 0) {
                                List<OrderModel> orderModels = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);

                                    OrderModel model = new OrderModel();
                                    model.setId(object.getString("id"));
                                    model.setDescription(object.getString("description"));
                                    model.setTotalPrice(object.getString("total"));
//                                    model.setNameUser("نام : " + object.getJSONObject("user").getString("name"));
                                    model.setDeliveryFree(object.getJSONObject("shipping").getString("free"));
                                    if (Integer.parseInt(object.getString("total")) >= Integer.parseInt(object.getJSONObject("shipping").getString("free"))) {
                                        model.setDeliveryPrice("0");
                                    }else {
                                        model.setDeliveryPrice(object.getJSONObject("shipping").getString("price"));
                                    }
                                    model.setOff((object.isNull("off")) ? "0" : object.getJSONObject("off").getString("percent"));
                                    List<OrderFruitModel> data ;
                                    JSONArray array1 = object.getJSONArray("order_items");

                                    data = new ArrayList<>();
                                    for (int j = 0; j < array1.length(); j++) {
                                        JSONObject object1 = array1.getJSONObject(j);

                                        OrderFruitModel orderFruitModel = new OrderFruitModel();
                                        orderFruitModel.setFruitId(object1.getString("id"));
                                        orderFruitModel.setOff(object1.getJSONObject("product").getString("off"));
                                        orderFruitModel.setFruitProductId(object1.getJSONObject("price").getString("product_id"));
                                        orderFruitModel.setName(object1.getJSONObject("product").getString("name"));
                                        orderFruitModel.setImage(object1.getJSONObject("product").getString("image"));
                                        orderFruitModel.setWeight(object1.getJSONObject("price").getString("title"));
//                                        Log.e("unit", object1.getJSONObject("unit").getString("title") + " }");
                                        orderFruitModel.setTotalWeight(object1.getString("quantity"));
                                        orderFruitModel.setUnit(object1.getJSONObject("unit").getString("title"));
                                        orderFruitModel.setPrice(object1.getString("sellPrice"));

                                        data.add(orderFruitModel);

                                    }
                                    model.setFruitModel(data);
                                    ///////     Delivery     ////////
                                    JSONArray deliverysArray = jsonObject.getJSONArray("deliverys");

                                    List<AllDeliveryModel> allDeliveryModels = new ArrayList<>();
                                    for (int j = 0; j < deliverysArray.length(); j++) {
                                        JSONObject object1 = deliverysArray.getJSONObject(j);
                                        AllDeliveryModel deliveryModel = new AllDeliveryModel();
                                        deliveryModel.setId(object1.getString("id"));
                                        deliveryModel.setName(object1.getString("lastName"));
                                        allDeliveryModels.add(deliveryModel);
                                    }
                                    model.setAllDelivery(allDeliveryModels);

                                    orderModels.add(model);
                                }
                                getOrder.OnListReceived(orderModels);
                            } else {
                                getOrder.OnListNull("null");
                            }
                        } catch (Exception er) {
                            getOrder.OnListError("Error");
                            Log.e("OrderError", er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("OrderErrorVolley", error.toString());
                        getOrder.OnListError("Error");
                    }
                }
                , 1);

        AppController.getInstance().addToRequestQueue(Order);
    }
    public void SendToDelivery(String DeliveryId, final All all, JSONArray orderItems, String OrderId, String total) {

//        Log.e("ReportSendToDelivery",total + " | " + orderItems);

        JSONObject dataSend = null;
        try {
            dataSend = new JSONObject();
            dataSend.put("api_token", Tools.getInstance(context).read("Token", ""));
            dataSend.put("orderItems", /*new JSONObject().put("orderItems", orderItems)*/ orderItems);
            dataSend.put("deliveryid", DeliveryId);
            dataSend.put("status", "3");
            dataSend.put("newTotal", total);
//            Log.e("mydata",  dataSend.toString() + " | " + new JSONObject().put("orderItems", orderItems) + " |" + OrderId + " | " + DeliveryId + " | " + total + " | " + Tools.getInstance(context).read("Token", ""));
        } catch (Exception er) {
            er.printStackTrace();
        }


        CustomRequest SendToDelivery = new CustomRequest(
                "/orderUpdate/" + OrderId,
                dataSend,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("SendToDeliveryResponse", jsonObject.toString() + "|");

                            if (jsonObject.has("data")) {
                                JSONObject object = jsonObject.getJSONObject("data");
                                if (object.getString("status").equals("success")) {
                                    all.Success("success");
                                }
                            }

                        } catch (Exception er) {
                            all.Failed("Error");
                            Log.e("SendToDeliveryListError", er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("SendToDeliveryErrorVoll", error.toString());
                        all.Failed("Error");
                    }
                }
                , 0);

        AppController.getInstance().addToRequestQueue(SendToDelivery);
    }


    //****   Delivery   ****//
    public void Delivery(String Id, final GetDelivery delivery) {

        final CustomRequest Delivery = new CustomRequest(
                "/orderList/" + Tools.getInstance(context).read("UserId", "") + "?api_token=" + Tools.getInstance(context).read("Token", ""),
                null,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("DeliveryResponse", jsonObject.toString() + "|");

                            JSONArray array = jsonObject.getJSONArray("orders");

                            if (array.length() != 0) {
                                List<DeliveryModel> DeliveryModels = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);

                                    DeliveryModel model = new DeliveryModel();
                                    model.setId(object.getString("id"));
                                    model.setAddress(object.getJSONObject("address").getString("country") + " , " +
                                            object.getJSONObject("address").getString("city") + " , " +
                                            object.getJSONObject("address").getString("address"));
                                    model.setName("نام : " + object.getJSONObject("user").getString("name"));
                                    model.setPayment(object.getJSONObject("payment").getString("title"));
                                    model.setTime(object.getString("orderDate"));
                                    model.setDebtor( Tools.getInstance(context).FormattedPrice2(
                                            String.valueOf
                                                    (
                                                        (Integer.parseInt(
                                                                (object.get("newTotal") == null) ? object.getString("total") : object.getString("newTotal")
                                                    )
                                                        - Integer.parseInt(object.getString("total"))
                                                        )
                                                    )
                                            )
                                    );
                                    model.setTotalOrderPrice("قیمت کل : " + Tools.getInstance(context).FormattedPrice2(object.getString("newTotal")));
                                    List<HashMap<String, Object>> data = null;
                                    JSONArray array1 = object.getJSONArray("order_items");

                                    data = new ArrayList<>();
                                    for (int j = 0; j < array1.length(); j++) {
                                        JSONObject object1 = array1.getJSONObject(j);

                                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                        hashMap.put("id", object1.getString("id"));
                                        hashMap.put("name", object1.getJSONObject("product").getString("name"));
                                        hashMap.put("image", ApiURLIMAGE + object1.getJSONObject("product").getString("image"));
                                        hashMap.put("weight", object1.getString("quantity") + " " +
                                                object1.getJSONObject("unit").getString("title"));
                                        data.add(hashMap);

                                    }
//                                    model.setData(data);
                                    DeliveryModels.add(model);
                                }
                                delivery.OnListReceived(DeliveryModels);
                            } else {
                                delivery.OnListNull("null");
                            }

                        } catch (Exception er) {
                            delivery.OnListError("Error");
                            Log.e("DeliveryError", er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("DeliveryErrorVolley", error.toString());
//                        delivery.OnListError("Error");
                    }
                }
                , 1);

        AppController.getInstance().addToRequestQueue(Delivery);
    }

    public void SendDelivery(String Id, String credit, All all) {


        JSONObject dataSend = null;
        try {
            dataSend = new JSONObject();
            dataSend.put("status", "4");
            dataSend.put("credit", Tools.getInstance(context).UnFormattedPrice(credit));
            dataSend.put("api_token", Tools.getInstance(context).read("Token", ""));
        } catch (Exception er) {
            er.printStackTrace();
        }Log.e("credit", dataSend + "");

        final CustomRequest Delivery = new CustomRequest(
                "/deliveryOrderUpdate/" + Id,
                dataSend,
                new CustomRequest.ResponseAction() {
                    @Override
                    public void onResponseAction(JSONObject jsonObject) {

                        try {
                            Log.e("DeliveryResponse", jsonObject.toString() + "|");

                            if (jsonObject.has("data")) {
                                if (jsonObject.getJSONObject("data").getString("status").equals("success")) {
                                    all.Success("Ok");
                                }else {
                                    all.Failed("Error");
                                }
                            }else {
                                all.Failed("Error");
                            }

                        } catch (Exception er) {
                            all.Failed("Error");
                            Log.e("DeliveryError", er.toString());
                        }

                    }

                    @Override
                    public void onErrorAction(VolleyError error) {
                        Log.e("DeliveryErrorVolley", error.toString());
                        all.Failed("Error");
                    }
                }
                , 0);

        AppController.getInstance().addToRequestQueue(Delivery);
    }


    //****   Interface   ****//
    public interface GetLoginStatus {
        void onReceived(String Receiveddata);

        void onError(String Errordata);
    }

    public interface GetProductList {
        void OnListReceived(List<EditPriceModel> models);

        void OnListError(String str);
    }
    public interface GetTotalOrder {
        void OnListReceived(List<TotalOrderModel> models);

        void OnListNull(String isnull);

        void OnListError(String str);
    }
    public interface GetOrder {
        void OnListReceived(List<OrderModel> models);

        void OnListError(String str);

        void OnListNull(String isnull);
    }
    public interface GetDelivery {
        void OnListReceived(List<DeliveryModel> models);

        void OnListError(String str);

        void OnListNull(String isnull);
    }
    public interface All {
        void Success(String text);

        void Failed(String error);
    }
    public interface AllForUpdatePrice {
        void Success(String text, int position);

        void Failed(String error);
    }

}

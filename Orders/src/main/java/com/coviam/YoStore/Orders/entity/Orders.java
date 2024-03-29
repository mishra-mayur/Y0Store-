package com.coviam.YoStore.Orders.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = Orders.COLLECTION_NAME)
public class Orders {

    public static final String COLLECTION_NAME="Orders";

    @Id
    private String orderId;
    @Field
    private BigDecimal orderPrice;
    private String userId;
    private List<OrderDetails> orderDetails;
    private Date date;

    public Orders() {this.orderDetails = new ArrayList<>();}

    public Orders(String orderId, BigDecimal orderPrice, String userId, Date date, List<OrderDetails> orderDetails) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.userId = userId;
        this.date = date;
        this.orderDetails = orderDetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

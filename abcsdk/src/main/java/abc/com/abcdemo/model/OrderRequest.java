package abc.com.abcdemo.model;

import java.io.Serializable;

/**
 * Created by LiYang on 2018/3/15.
 */

public class OrderRequest implements Serializable{
    private String cardNo;
    private String openId;
    private String money;
    private String busType;

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

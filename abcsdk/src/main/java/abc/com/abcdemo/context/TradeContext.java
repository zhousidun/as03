package abc.com.abcdemo.context;

/**
 * Created by LiYang on 2018/3/15.
 */

public class TradeContext {
    private static TradeContext instance;
    public static void init(){
        instance = new TradeContext();
    }

    public static TradeContext getInstance(){
        if(instance == null){
            instance = new TradeContext();
        }
        return instance;
    }

    private String phoneNo;

    private String uuid;

    private String transType;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}

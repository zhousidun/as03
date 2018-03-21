package abc.com.abcdemo.model;

/**
 * Created by LiYang on 2018/3/19.
 */

public class ScanResult {
    private String UUID;
    private String DeviceID;
    private String BusType;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String busType) {
        BusType = busType;
    }
}

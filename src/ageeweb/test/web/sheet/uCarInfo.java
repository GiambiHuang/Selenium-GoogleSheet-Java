package ageeweb.test.web.sheet;

public class uCarInfo {

  private Customer custInfo;
  
  private CarInfo carInfo;
  
  private CustReturn custRetInfo;

  private String carPrice;
  
  private String hsrPrice;
  
  private String totalPrice;
  
  public String getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(String totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getCarPrice() {
    return carPrice;
  }

  public void setCarPrice(String carPrice) {
    this.carPrice = carPrice;
  }

  public String getHsrPrice() {
    return hsrPrice;
  }

  public void setHsrPrice(String hsrPrice) {
    this.hsrPrice = hsrPrice;
  }

  public Customer getCustInfo() {
    return custInfo;
  }

  public void setCustInfo(Customer custInfo) {
    this.custInfo = custInfo;
  }

  public CarInfo getCarInfo() {
    return carInfo;
  }

  public void setCarInfo(CarInfo carInfo) {
    this.carInfo = carInfo;
  }

  public CustReturn getCustRetInfo() {
    return custRetInfo;
  }

  public void setCustRetInfo(CustReturn custRetInfo) {
    this.custRetInfo = custRetInfo;
  } 
}

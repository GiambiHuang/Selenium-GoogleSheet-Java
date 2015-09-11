package ageeweb.test.web.sheet;

public class CustReturn {
  
  /** 高鐵回程車次. */
  private String hsrNo;
  
  /** 回程車廂類型. */
  private String classType;
  
  /** 回程出發日期. */
  private String hsrDepDate;
  
  /** 回程出發站. */
  private String from_Station;
  
  /** 抵達站. */
  private String arrStation;
  
  /** 回程出發時間. */
  private String hsrDepTime;
  
  /** 抵達時間. */
  private String hsrDepArrTime;
  

  public String getHsrNo() {
    return hsrNo;
  }

  public void setHsrNo(String hsrNo) {
    this.hsrNo = hsrNo;
  }

  public String getClassType() {
    return classType;
  }

  public void setClassType(String classType) {
    this.classType = classType;
  }

  public String getHsrDepDate() {
    return hsrDepDate;
  }

  public void setHsrDepDate(String hsrDepDate) {
    this.hsrDepDate = hsrDepDate;
  }

  public String getFrom_Station() {
    return from_Station;
  }

  public void setFrom_Station(String from_Station) {
    this.from_Station = from_Station;
  }

  public String getArrStation() {
    return arrStation;
  }

  public void setArrStation(String arrStation) {
    this.arrStation = arrStation;
  }

  public String getHsrDepTime() {
    return hsrDepTime;
  }

  public void setHsrDepTime(String hsrDepTime) {
    this.hsrDepTime = hsrDepTime;
  }

  public String getHsrDepArrTime() {
    return hsrDepArrTime;
  }

  public void setHsrDepArrTime(String hsrDepArrTime) {
    this.hsrDepArrTime = hsrDepArrTime;
  }
  
}

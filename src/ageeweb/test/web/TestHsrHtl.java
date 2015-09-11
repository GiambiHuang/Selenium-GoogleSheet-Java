package ageeweb.test.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import ageeweb.test.web.sheet.GoogleSheetTest;
import ageeweb.test.web.sheet.TestPassagerInfo;
import ageeweb.test.web.sheet.uCarInfo;

public class TestHsrHtl {
  
  private WebDriver driver;
  private void init(){
    System.setProperty("webdriver.chrome.driver",
        "-------------------");
    driver = new ChromeDriver();
    driver.get("-------------------");
  }
  public void ucarTestRun() throws Exception{
    GoogleSheetTest googl = new GoogleSheetTest();
    List<uCarInfo> listCust = googl.getSpreadsheetSize();
    List<TestPassagerInfo> listTest = googl.getSpreadsheetTest();
    init();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait waitComp = new WebDriverWait(driver, 10);
    /* 選擇出發地  */
    ListElement(By.className("dropdown-toggle"),10).get(0).click();
    List<WebElement> dropDownList = ListElement(By.xpath("//ul[@class='dropdown-menu inner selectpicker']/li"),10);
    for(WebElement item : dropDownList){
      if(item.getText().equals(listCust.get(0).getCustInfo().getFrom_Station()))item.click();
    }
    
    /* 選擇日期  */
    String[] AfterSplit = listCust.get(0).getCustInfo().getHsrDepDate().split("/");
    selectDate(By.name("hsrDepDate"),AfterSplit,10);
    
    /* 選擇車廂  */
    clickElement(By.id( "hsrtime"),10).click();
    if("標準車廂".equals(listCust.get(0).getCustInfo().getClassType())){
      waitComp.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[class='train-no']")));
      clickElement(By.cssSelector("label[class='btn btn-default']"),10).click();
      clickElement(By.cssSelector("label[class='btn btn-default']"),10).click();
      waitComp.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[class='train-no']")));
         while(true){
             WebElement carData =(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("timetable-list-start")));
                 try{
                   List<WebElement> carNoList = carData.findElements(By.className("train-number"));
                   if(("N").equals(carNoList.get(0).getText().substring(carNoList.get(0).getText().length()-1)))break;
                 }catch(StaleElementReferenceException e){
                 }catch(StringIndexOutOfBoundsException e){
                 }
         }
    }else{
      waitComp.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[class='train-no']")));
      clickElement(By.cssSelector("label[class='btn btn-default']"),10).click();
      waitComp.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[class='train-no']")));
      while(true){
        WebElement carData =(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("timetable-list-start")));
            try{
              List<WebElement> carNoList = carData.findElements(By.className("train-number"));
              if(("C").equals(carNoList.get(0).getText().substring(carNoList.get(0).getText().length()-1)))break;
            }catch(StaleElementReferenceException e){
            }catch(StringIndexOutOfBoundsException e){
            }
      }
    }
    /* 選擇時刻區間 */
    String[] timeSplit = listCust.get(0).getCustInfo().getHsrDepArrTime().split(":");
    Integer result =  Integer.parseInt(timeSplit[0]);
    if(9 >= result && result >= 6 ){
      clickElement(By.cssSelector("a[href='.time-morning']"),10).click();
    }else if(13 >= result && result >= 10 ){
      clickElement(By.cssSelector("a[href='.time-noon']"),10).click();
    }else if(17 >= result && result >= 14 ){
      clickElement(By.cssSelector("a[href='.time-afternoon']"),10).click();
    }else if(20 >= result && result >= 18 ){
      clickElement(By.cssSelector("a[href='.time-night']"),10).click();
    }else if(23 >= result && result >= 21 ){
      clickElement(By.cssSelector("a[href='.time-midnight']"),10).click();
    }
    
    /* 選擇車次 */
    waitComp.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='"+listCust.get(0).getCustInfo().getHsrNo()+"']")));
    clickElement(By.cssSelector("input[value='"+listCust.get(0).getCustInfo().getHsrNo()+"']"),10).click();
    clickElement(By.cssSelector("button[class='btn-checkOK btn-green']"),10).click();
    waitComp.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.cssSelector("div[id='submitButton']"))));
    while(true){
        try{
          clickElement(By. id( "submitButton"),5).click();
          waitComp.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'type-adult')]/button[@type='button']")));
          break;
        }catch(WebDriverException e){
      }
    }
    /*選成人票數----------*/
    selectTicket("type-adult",listCust.get(0).getCustInfo().getAdultTick(),10);
    /*選小孩票數----------*/
    selectTicket("type-child",listCust.get(0).getCustInfo().getChildTick(),10);
    /*選愛心票數----------*/
    selectTicket("type-special",listCust.get(0).getCustInfo().getLoveTick(),10);
    /*選資深公民票數----------*/
    selectTicket("type-old",listCust.get(0).getCustInfo().getOldManTick(),10);
    /*選車輛數----------*/
    selectTicket("f2-amount",listCust.get(0).getCarInfo().getCarAmount(),10);
    
    /*選擇取車日期----------*/
    String[] carDaySplit = listCust.get(0).getCarInfo().getCarDate().split("/");
    selectDate(By.name("carDepDate"),carDaySplit,10);
    
    /*取車時間----------*/
    selectTicket("start-time",listCust.get(0).getCarInfo().getCarTime(),10);
    
    /*選擇商品----------*/
    waitComp.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'rentcar-promote-detail')]/div[@class='promote-info']")));
    List<WebElement> carProd = ListElement(By.xpath("//div[contains(@id,'rentcar-detail')]/div[@class='rentcar-promote-detail']/div"),10);
    for(WebElement item : carProd){
      if(item.getAttribute("data-usehours").equals(listCust.get(0).getCarInfo().getCarHrs())&&item.getAttribute("data-overnight").equals("false")){
        item.click();
        break;
        }
      //----選擇一般方案
    }
    /*選擇回程時區----------*/
    waitComp.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.tabs-end ul.page li.active a")));
    String[] reTimeSplit = listCust.get(0).getCustRetInfo().getHsrDepTime().split(":");
    Integer reResult =  Integer.parseInt(reTimeSplit[0]);
    if(9 >= reResult && reResult >= 6 ){
      js.executeScript("arguments[0].click();", clickElement(By.cssSelector("a[href='.time-morning']"),10));
    }else if(13 >= reResult && reResult >= 10 ){
      js.executeScript("arguments[0].click();", clickElement(By.cssSelector("a[href='.time-noon']"),10));
    }else if(17 >= reResult && reResult >= 14 ){
      js.executeScript("arguments[0].click();", clickElement(By.cssSelector("a[href='.time-afternoon']"),10));
    }else if(20 >= reResult && reResult >= 18 ){
      js.executeScript("arguments[0].click();", clickElement(By.cssSelector("a[href='.time-night']"),10));
    }else if(23 >= reResult && reResult >= 21 ){
      js.executeScript("arguments[0].click();", clickElement(By.cssSelector("a[href='.time-midnight']"),10));
    }
    
    waitComp.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='"+listCust.get(0).getCustRetInfo().getHsrNo()+"']")));
    driver.findElement(By.cssSelector("input[value='"+listCust.get(0).getCustRetInfo().getHsrNo()+"']")).click();
    driver.findElement(By.cssSelector("button[class='btn-end-order btn-order btn-orange']")).click();
      
    // 閱讀確認
    waitComp.until(ExpectedConditions.elementToBeClickable(By.className("css-label")));
    clickElement(By.className("css-label"),10).click();
    // 帳號
    clickElement(By.id("loginId"),10).sendKeys("-------------------");
    // 密碼
    clickElement(By.id("loginPwd"),10).sendKeys("-------------------");
    // 填寫訂購頁面
    clickElement(By.id("loginButton"),10).click();
    waitComp.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.customer-title")));
    List<WebElement> tableList = ListElement(By.cssSelector("div.customer-info"),10);
    /* 填寫乘客資料  */
    int childCount = 4;
    int loveCount = 8;
    int oldCount = 12;
    for(int s = 0;s<tableList.size();s++){
      if(s==0){
        clickElement(By.cssSelector("button.btn.dropdown-toggle.selectpicker.btn-default"),10).click();
        List<WebElement> memberDrop = ListElement(By.xpath("//div[contains(@class,'select-record')]//ul[@class='dropdown-menu inner selectpicker']/li"),10);
        for(WebElement item : memberDrop){
          if(("會員").equals(item.getText())){
            item.click();
            break;
            }
        }
      }else{
        List<WebElement> listTitle = ListElement(By.cssSelector("div.customer-title"),10);
        String kindOfPassger = listTitle.get(s).findElement(By.id("personType"+s)).getText();
        int testIndex = 0;
        if(("孩童票").equals(kindOfPassger)){
          testIndex = childCount;
          childCount++;
        }else if(("愛心票").equals(kindOfPassger)){
          testIndex = loveCount;
          loveCount++;
        }else if(("資深公民票").equals(kindOfPassger)){
          testIndex = oldCount;
          oldCount++;
        }else{
          testIndex = s;
        }
        tableList.get(s).findElement(By.id("orderCustsNameChnFirst"+s)).sendKeys(listTest.get(testIndex).getFirstName());
        tableList.get(s).findElement(By.id("orderCustsNameChnLast"+s)).sendKeys(listTest.get(testIndex).getLastName());
        tableList.get(s).findElement(By.id("selectBox_orderCustsSex"+s)).click();
        List<WebElement> sexDrop = ListElement(By.xpath("//div[contains(@id,'selectBox_orderCustsSex"+s+"')]//ul[@class='dropdown-menu inner selectpicker']/li"),10);
          for(WebElement item : sexDrop){
            if((item.getText()).equals(listTest.get(testIndex).getSex())){
              item.click();
              break;
              }
          }
        String[] bitthString = listTest.get(testIndex).getBornInfo().split("/");
        tableList.get(s).findElement(By.id("orderCustsBirthdayYYYY"+s)).sendKeys(bitthString[0]);
        tableList.get(s).findElement(By.id("orderCustsBirthdayMM"+s)).sendKeys(bitthString[1]);
        tableList.get(s).findElement(By.id("orderCustsBirthdayDD"+s)).sendKeys(bitthString[2]);
        tableList.get(s).findElement(By.id("orderCustsPersonId"+s)).sendKeys(listTest.get(testIndex).getPassagerId());
          if(s<Integer.parseInt(listCust.get(0).getCarInfo().getCarAmount())){
            tableList.get(s).findElement(By.id("orderContactMobile1"+s)).sendKeys(listTest.get(testIndex).getMobile());
          }
      }
    }
    
    clickElement(By.xpath("//div[contains(@id,'selectBox_cCity')]/button[@data-id='cState']"),10).click();
    List<WebElement> cityDrop = ListElement(By.xpath("//div[contains(@id,'selectBox_cCity')]//ul[@class='dropdown-menu inner selectpicker']/li"),10);
    for(WebElement item : cityDrop){
      if(("高雄市").equals(item.getText())){
        item.click();
        break;
        }
    }
    clickElement(By.xpath("//div[contains(@id,'selectBox_cCity')]/button[@data-id='cCity']"),10).click();
    List<WebElement> statDrop = ListElement(By.xpath("//div[contains(@id,'selectBox_cCity')]//ul[@class='dropdown-menu inner selectpicker']/li"),10);
    for(WebElement item : statDrop){
      if(("南沙群島").equals(item.getText())){
        item.click();
        break;
        }
    }
  }
  private String checkAlert(int wait) {
    try {
        waitComp(wait).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        String resultReport = alert.getText();
        alert.accept();
        return resultReport;
    } catch (Exception e) {
      return "ok";
    }
  }
  public WebDriverWait waitComp(int wait){
    WebDriverWait waitTab = new WebDriverWait(driver, wait);
    return waitTab;
  }
  private List<WebElement> ListElement(By element,int wait) throws Exception{
    waitComp(wait).until(ExpectedConditions.elementToBeClickable(element));
    List<WebElement> ListElement = driver.findElements(element);
    return ListElement;
  }
  private WebElement clickElement(By element,int wait) throws Exception{
    waitComp(wait).until(ExpectedConditions.elementToBeClickable(element));
    WebElement ListElement = driver.findElement(element);
    return ListElement;
  }
  private void selectTicket(String element,String tickets,int wait) throws Exception{
    waitComp(wait).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'"+element+"')]/button[@type='button']")));
    clickElement(By.xpath("//div[contains(@class,'"+element+"')]/button[@type='button']"),wait).click();
    List<WebElement> listTick = ListElement(By.xpath("//div[contains(@class,'"+element+"')]//ul[@class='dropdown-menu inner selectpicker']/li"),wait);
    for(WebElement item : listTick){
      if(item.getText().equals(tickets)){
        item.click();
        break;
        }
    }
  }

  private void selectDate(By element,String[] Day,int wait) throws Exception{
    clickElement(element,wait).click();
    //-----Day[1]月份
    WebElement cardate= clickElement(By.xpath("// table[@class='ui-datepicker-calendar']"),wait);
    List<WebElement> carColumns=cardate.findElements(By.tagName("td"));
    for(WebElement cell : carColumns) {
      if(cell.getText().equals(Day[2])){
        cell.findElement(By.linkText(Day[2])).click();
        break;
    }}
  }
}

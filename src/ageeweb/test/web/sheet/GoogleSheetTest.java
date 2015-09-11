package ageeweb.test.web.sheet;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;


public class GoogleSheetTest {
  private static List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds", "https://spreadsheets.google.com/feeds/spreadsheets/private/full", "https://docs.google.com/feeds");
  
  private static final String SPREADSHEET_ID = "-------------------@developer.gserviceaccount.com";
  
  private static final String DSHEETKEY_P12 = "-------------------.p12";
  
  private static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/worksheets/-------------------/private/full";
  
  public GoogleCredential initServiceCredential() throws AuthenticationException, MalformedURLException, IOException, ServiceException, GeneralSecurityException {
    /* Build service account credential. */
    GoogleCredential credential = new GoogleCredential.Builder()
          .setTransport(GoogleNetHttpTransport.newTrustedTransport())
          .setJsonFactory(JacksonFactory.getDefaultInstance())
          .setServiceAccountId(SPREADSHEET_ID)
          .setServiceAccountPrivateKeyFromP12File(getP12(DSHEETKEY_P12))
          .setServiceAccountScopes(SCOPES)
          .build();
    credential.refreshToken();
    return credential;
  }

  public List<uCarInfo> getSpreadsheetSize() throws AuthenticationException, MalformedURLException, IOException, ServiceException, GeneralSecurityException, URISyntaxException {
    /* 註冊OAuth2憑證  */
    SpreadsheetService service = new SpreadsheetService("-------------------");
    service.setOAuth2Credentials(initServiceCredential());
    
    /* Define the list of Spreadsheet(or Single Spreadsheet) URL to request.*/
    URL WORKSHEET_FEED_URL = new URL(SPREADSHEET_URL);
    WorksheetFeed feedWk = service.getFeed(WORKSHEET_FEED_URL, WorksheetFeed.class);
    int docsSize = feedWk.getEntries().size()-2;
    WorksheetEntry worksheets = feedWk.getEntries().get(docsSize); // 選擇工作表
    String urlString = worksheets.getListFeedUrl() + "?start-index=3";
    URL listFeedUrl = new URL(urlString);
    ListFeed feed2 = service.getFeed(listFeedUrl, ListFeed.class);
    /**
     * List<ListEntry>:每一列Row的集合
     * 按照指定Column從Row:1開始寫入欄位值
     */
    List<uCarInfo> uCarList = new ArrayList<uCarInfo>();
    List<ListEntry> listEntry = feed2.getEntries();
    for(ListEntry listEn:listEntry){
      CustomElementCollection elements = listEn.getCustomElements();
        CarInfo carIn = new CarInfo();
        Customer cust = new Customer();
        CustReturn custRe = new CustReturn();
        uCarInfo uClist = new uCarInfo();
        cust.setFrom_Station(elements.getValue("選擇內容"));
        cust.setArrStation(elements.getValue("_clrrx"));
        cust.setHsrDepDate(elements.getValue("_cyevm"));
        cust.setClassType(elements.getValue("_cztg3"));
        cust.setHsrNo(elements.getValue("_d180g"));
        cust.setHsrDepTime(elements.getValue("_d2mkx"));
        cust.setHsrDepArrTime(elements.getValue("_cssly"));
        cust.setAdultTick(elements.getValue("_cu76f"));
        cust.setChildTick(elements.getValue("_cvlqs"));
        cust.setLoveTick(elements.getValue("_cx0b9"));
        cust.setOldManTick(elements.getValue("_c9ney"));
        carIn.setCarAmount(elements.getValue("_db1zf"));
        carIn.setCarDate(elements.getValue("_dcgjs"));
        carIn.setCarTime(elements.getValue("_ddv49"));
        carIn.setCarStation(elements.getValue("_d415a"));
        carIn.setCarHrs(elements.getValue("_d5fpr"));
        carIn.setCarBackDate(elements.getValue("_d6ua4"));
        carIn.setCarBackTime(elements.getValue("_d88ul"));
        custRe.setFrom_Station(elements.getValue("_dkvya"));
        custRe.setArrStation(elements.getValue("_dmair"));
        custRe.setHsrDepDate(elements.getValue("_dnp34"));
        custRe.setClassType(elements.getValue("_dp3nl"));
        custRe.setHsrNo(elements.getValue("_df9om"));
        custRe.setHsrDepTime(elements.getValue("_dgo93"));
        custRe.setHsrDepArrTime(elements.getValue("_di2tg"));
        uClist.setCarInfo(carIn);
        uClist.setCustInfo(cust);
        uClist.setCustRetInfo(custRe);
        uClist.setCarPrice(elements.getValue("_dw4je"));
        uClist.setHsrPrice(elements.getValue("_dxj3v"));
        uClist.setTotalPrice(elements.getValue("_dyxo8"));
        uCarList.add(uClist);   
    }
    return uCarList;
}
  
  public List<TestPassagerInfo> getSpreadsheetTest() throws AuthenticationException, MalformedURLException, IOException, ServiceException, GeneralSecurityException, URISyntaxException {
    /* 註冊OAuth2憑證  */
    SpreadsheetService service = new SpreadsheetService("-------------------");
    service.setOAuth2Credentials(initServiceCredential());
    
    /* Define the list of Spreadsheet(or Single Spreadsheet) URL to request.*/
    URL WORKSHEET_FEED_URL = new URL(SPREADSHEET_URL);
    WorksheetFeed feedWk = service.getFeed(WORKSHEET_FEED_URL, WorksheetFeed.class);
    int docsSize = feedWk.getEntries().size()-1;
    WorksheetEntry worksheets = feedWk.getEntries().get(docsSize); // 選擇工作表
    String urlString = worksheets.getListFeedUrl() + "?start-index=1";
    URL listFeedUrl = new URL(urlString);
    ListFeed feed2 = service.getFeed(listFeedUrl, ListFeed.class);
    /**
     * List<ListEntry>:每一列Row的集合
     * 按照指定Column從Row:1開始寫入欄位值
     */
    List<TestPassagerInfo> uTestList = new ArrayList<TestPassagerInfo>();
    List<ListEntry> listEntry = feed2.getEntries();
    for(ListEntry listEn:listEntry){
      CustomElementCollection elements = listEn.getCustomElements();
      TestPassagerInfo testList = new TestPassagerInfo();
      testList.setFirstName(elements.getValue("旅客姓名").substring(0, 1));
      testList.setLastName(elements.getValue("旅客姓名").substring(1));
      testList.setSex(elements.getValue("性別"));
      testList.setPassagerId(elements.getValue("身分證字號"));
      testList.setBornInfo(elements.getValue("出生年月日"));
      testList.setMobile(elements.getValue("聯絡電話"));
      uTestList.add(testList);
    }
    return uTestList;
}
  /**
   * 取得授權p12金鑰
   */
  public File getP12(String KeyP12){
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    URL url = classloader.getResource(KeyP12);
    final File file = new File(url.getFile());
    System.out.println(url.getFile());
    return file;
  }
  
}

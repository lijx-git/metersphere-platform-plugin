
import io.metersphere.platform.client.BaseZentaoJsonClient;
import io.metersphere.platform.client.ZentaoGetClient;
import io.metersphere.platform.client.ZentaoRestClient;
import io.metersphere.platform.client.ZentaoFactory;
import io.metersphere.platform.domain.*;
import io.metersphere.platform.domain.response.rest.ZentaoRestBugDetailResponse;
import io.metersphere.platform.domain.response.rest.ZentaoRestDemandResponse;
import io.metersphere.platform.domain.response.rest.ZentaoRestUserResponse;
import io.metersphere.platform.impl.ZentaoPlatform;
import io.metersphere.platform.utils.DateUtils;
import io.metersphere.plugin.utils.JSON;
import io.metersphere.plugin.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.metersphere.platform.api.AbstractPlatform.PROXY_PATH;

/**
 * @program: metersphere-platform-plugin
 * @ClassName: Test
 * @description:
 */

public class Test {

  protected ZentaoRestClient zentaorestClient;
  protected BaseZentaoJsonClient zentaoJsonClient;
  protected ZentaoPlatform zentaoPlatform;
  protected ZentaoConfig zentaoConfig=new ZentaoConfig();




  @BeforeClass
    public void createClient(){

    zentaoConfig.setAccount("admin");
    zentaoConfig.setPassword("Calong@2015");
    zentaoConfig.setUrl("http://10.1.13.22:8085/");
    zentaoConfig.setRequestType("PATH_INFO");
      //zentaoConfig.setRequestType("GET");


    zentaorestClient = new ZentaoRestClient(zentaoConfig.getUrl());
    zentaoJsonClient =ZentaoFactory.getInstance(zentaoConfig.getUrl(),zentaoConfig.getRequestType());
    zentaorestClient.initConfig(zentaoConfig);
    zentaoJsonClient.initConfig(zentaoConfig);


      PlatformRequest request=new PlatformRequest();
      request.setIntegrationConfig(JSON.toJSONString(zentaoConfig));
      zentaoPlatform=new ZentaoPlatform(request);
      //zentaoPlatform=new ZentaoPlatform();


  }
   @org.testng.annotations.Test
  public void validateProjectConfig() {
   // zentaorestClient.checkProjectExist("1","products");
       zentaorestClient.checkProjectExist("1","projects");
  }
//  @org.testng.annotations.Test
//  public void testGetBugs(){
//
//    List<PlatformBugDTO> needSyncBugs = new ArrayList<>();
//    Map<String, Object> bugResponseMap = zentaoClient.getBugsByProjectId(0, 0, "1");
//    List<?> zentaoBugs = (List<?>) bugResponseMap.get("bugs");
//    int currentSize = zentaoBugs.size();
//    System.out.println("禅道缺陷数据："+currentSize);
//
//  }
   @org.testng.annotations.Test
  public void addissue(){
     Map<String, Object> paramMap = new LinkedHashMap<>();
     paramMap.put("project","1");
     paramMap.put("title","测试图片");
     paramMap.put("product","1");
     paramMap.put("severity","1");
     paramMap.put("pri","1");
     paramMap.put("type","codeerror");
       List<String> list=new ArrayList<>();
       list.add("trunk");
       paramMap.put("openedBuild",list);
       paramMap.put("steps","<img src=\"/file-read-?fileName=28e82891.png\" alt=\"file-read-?fileName=28e82891.png\" /><br /><img src=\"/file-read-?fileName=d3887245.jpg\" alt=\"file-read-?fileName=d3887245.jpg\" />");

       System.out.println("addIssue请求参数："+ JSON.toJSONString(paramMap));
       AddIssueResponse.Issue issue = zentaorestClient.addIssue(paramMap);
       System.out.println("issue id："+issue.getId());

  }

@org.testng.annotations.Test
public void testms2ZentaoDescription(){
      String description="![产品定位_副本.png](/resource/md/get?fileName=28e82891.png)![weicha.jpg](/resource/md/get?fileName=d3887245.jpg)";
      String projectId="1";
      zentaoPlatform.ms2ZentaoDescription(description,projectId);

}
/**
 * @Description 上传单个文件
 * @param
 */

@org.testng.annotations.Test
public void uploadImgJson() {
    File file = null;
    try {
        file = new File("/opt/metersphere/data/image/markdown" + "/" + URLDecoder.decode("28e82891.png", StandardCharsets.UTF_8.name()));
    } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
    }
   String url= zentaoJsonClient.uploadImgFile(file);
    System.out.println(url);
}
    @org.testng.annotations.Test
    public void uploadFile() {
        File file = null;
        try {
            file = new File("/opt/metersphere/data/image/markdown" + "/" + URLDecoder.decode("28e82891.png", StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String url= zentaoJsonClient.uploadFile(file,"1");
        System.out.println(url);
    }


  @org.testng.annotations.Test
    public void updateIssue(){
      Map<String, Object> paramMap1 = new LinkedHashMap<>();

      paramMap1.put("project","4");


      paramMap1.put("title","MSv2.10.10缺陷");
      paramMap1.put("severity","2");
      paramMap1.put("pri","2");
      paramMap1.put("type","codeerror");
      List<String> list=new ArrayList<>();
      list.add("主干");
      paramMap1.put("openedBuild",list);
     // paramMap1.put("status","resolved");
      zentaorestClient.updateIssue("46",paramMap1);

  }
  @org.testng.annotations.Test
    public void deleteissue(){
      zentaorestClient.deleteIssue("30");
  }
  @org.testng.annotations.Test
  public void getbug(){
      ZentaoRestBugDetailResponse isuue=zentaorestClient.get("2");
      System.out.println(isuue);
  }
  @org.testng.annotations.Test
  public void getCreateMetaData(){
    zentaoJsonClient.getCreateMetaData("1",zentaorestClient);
  }
  @org.testng.annotations.Test
  public void servicein(){
    zentaorestClient.auth();
  }

  @org.testng.annotations.Test
  public void getBugByProject(){
    Map<String, Object> response = zentaoJsonClient.getBugsByProductId( 1, 200,"1",zentaorestClient);
//      Map<String, Object> response = zentaorestClient.getProductBugs(1);
      List<Map>  zentaoIssues = (ArrayList<Map>) response.get("bugs");

    //List<Map>   issues=new ArrayList<Map>(zentaoIssues);
      System.out.println("数量："+zentaoIssues.size());
      zentaoIssues = zentaoIssues.stream().filter(map -> ( map.get("project").toString().equals("4"))).collect(Collectors.toList());


      //zentaoIssues.forEach((key, value) -> System.out.println("Key = " + key + ", Value = " + value));
   List<String> allIds = zentaoIssues.stream().map(i -> i.get("id").toString()).collect(Collectors.toList());

      System.out.println("筛选后的数量:"+zentaoIssues.size());




      System.out.println("筛选后的数量:"+zentaoIssues.size());
  }
  @org.testng.annotations.Test
   public void getsession(){
    System.out.println(zentaoJsonClient.auth());
   }

   @org.testng.annotations.Test
    public void testZenPlatform(){
       PlatformRequest request=new PlatformRequest();
       ZentaoPlatform zentaoPlatform=new ZentaoPlatform(request);

   }
   @org.testng.annotations.Test()
    public void testMethod(){
//       MultiValueMap<String, Object> paramMap1 = new LinkedMultiValueMap<>();
//       paramMap1.add("project","1");
//       paramMap1.add("title","MSv2.10.10缺陷");
//       paramMap1.add("severity","2");
//       paramMap1.add("pri","2");
//       paramMap1.add("type","codeerror");
//     Map<String,Object> map=paramMap1.toSingleValueMap();
//     map.forEach((key, value) -> System.out.println("Key = " + key + ", Value = " + value));


       //System.out.println(zentaoJsonClient.requestUrl.getReplaceImgUrl());

//       String id="1111-";
//       String[] arr=id.split("-");
//       for (int i = 0; i < arr.length; i++) {
//           System.out.println(i+"===="+arr[i]);
//       }
//
//       System.out.println("对对对对对："+(StringUtils.isNotBlank(id.split("-")[1])?id.split("-")[1]:id.split("-")[0]));
//

//  int[] arr=new int[]{1,2,3,4};
//       for (int i = 0; i < arr.length; i++) {
//           if (i==2){
//               System.out.println("2222");
//           //    break;
//               continue;
//           }
//           System.out.println(i);
//       }
       MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
       paramMap.add("account", "admin");
       paramMap.add("password", "Calong@2015");
       System.out.println(JSON.toJSONString(paramMap));


  }

   @org.testng.annotations.Test
    public void getDemand(){
       List<ZentaoRestDemandResponse.Story> stories=zentaorestClient.getDemands("2","products").getStories();
       System.out.println("产品1："+stories);
       List<ZentaoRestDemandResponse.Story> stories1= zentaorestClient.getDemands("1","projects").getStories();
       System.out.println("项目1"+stories1);

   }
   @org.testng.annotations.Test
    public void getUser(){
       ZentaoRestUserResponse users = zentaorestClient.getUsers(1, Integer.MAX_VALUE);
       List<SelectOption> userOptions = users.getUsers().stream().map(user -> new SelectOption(user.getRealname(), user.getAccount())).collect(Collectors.toList());
       System.out.println("user:"+users);
   }
    @org.testng.annotations.Test
    public void testImg(){

        ResponseEntity<byte[]> response=zentaorestClient.proxyForGet("/file-read-10.jpg&", byte[].class);
        System.out.println(response);
    }
    @org.testng.annotations.Test
    public void testattach(){
        File file=new File("/Users/test/Desktop/test.html");
        if(file.exists()) {
            zentaoJsonClient.uploadAttachment("bug", "53", file);
        }
    }

    @org.testng.annotations.Test
    public void closeBug(){
      zentaorestClient.closeBug("46");
    }
    @org.testng.annotations.Test
    public void activeBug(){
        ResponseEntity response= zentaorestClient.activeBug("45","lijx");
        System.out.println(response);
    }

    @org.testng.annotations.Test
    public void  resolveBug(){
      //zentaorestClient.resolveBug("56","lijx");
    }

    @org.testng.annotations.Test
    public void testTime() throws Exception {
        // 用于解析的SimpleDateFormat对象，设置UTC时区
//        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
//            // 解析ISO 8601格式的日期字符串
//        Date date = iso8601Format.parse("2025-03-27T10:34:45Z");
//        System.out.println(date.getTime());
//
//        System.out.println(DateUtils.getTime("2025-03-27 10:34:45").getTime());
String openedDate="2025-03-27T10:34:45Z";
        if (StringUtils.isNotBlank(openedDate) && !openedDate.startsWith("0000-00-00"))
            System.out.println(DateUtils.getZoneTime(openedDate).getTime());
        else
            System.out.println("没进啦");
    }


}

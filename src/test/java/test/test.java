package test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class test
{
    public static void main(String[] args) throws IOException
    {
        HttpClient client = HttpClients.createDefault();
        String urlStr = "http://183.208.120.208:9093/wms/services/restful/json/GisDataService/getRealData?mn=003" +
                "&typeCode=";
        HttpGet get = new HttpGet(urlStr);

        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String result = "";
        result = EntityUtils.toString(entity, "UTF-8");
        //CitySystem.out.println(result);
        /*JSONObject json = JSONObject.fromObject(result);    //String to Json

        Map<String, Class<StationConfig>> map = new HashMap<>();
        map.put("contentFields", StationConfig.class);
        JsonConfig jsonConfig1 = (JsonConfig) JSONObject.toBean(json,JsonConfig.class);
        JsonConfig jsonConfig2 = (JsonConfig) JSONObject.toBean(json,JsonConfig.class,map);

        CitySystem.out.println(jsonConfig1);
        //jsonConfig2.getContentFields().get(0).setName("lucifer");
        CitySystem.out.println(jsonConfig2.getContentFields().get(0).getName());*/
    }

}

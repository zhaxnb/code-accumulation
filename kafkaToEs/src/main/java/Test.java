import com.alibaba.fastjson.JSON;
import entity.FbPost;

public class Test {
    public static void main(String[] args) {
        String jsonStr="{\"name\":\"JSON\",\"age\":\"24\",\"id_str\": \"123456\",\"address\":\"北京市西城区\",\"created_at_stamp\":\"120345\",\"user\": {\"id_str\": \"123456789\"}}";
        FbPost jsonObject = JSON.parseObject(jsonStr, FbPost.class);
        System.out.println("jsonObject = " + jsonObject);
        System.out.println(jsonObject.toString());
        byte b2[] = {0x01};
        String str2 = new String(b2);
        String str3 = "\u0001";
        System.out.println("str3 = " + str3);
    }
}

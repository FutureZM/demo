package com.zhou.demo.com.zhou.demo.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.zhou.demo.util.JsonUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/10/18 下午7:22
 */
public class Demo {
    public static void main(String[] args) {
//        String data = "{\"custom\":{\"code\":\"0000\",\"certinfo\":[{\"expiredateto\":\"\",\"certownername\":\"侯广林\",\"awarddate\":\"2021-12-06 19:17:44\",\"certlevel\":\"A\",\"certname\":\"中华人民共和国不动产权证书\",\"copycertattachurl\":\"\",\"certareaguid\":\"30cd313b-f780-4fd9-a3b3-8a84b463a0bd\",\"sltattachurl\":\"\",\"version\":\"1\",\"rowguid\":\"df98ed92-29a7-4ea1-8377-37ce00862489\",\"certcliengguid\":\"9e63894d-a4f9-402c-9dcc-027d36eef4a8\",\"certno\":\"豫（2021）林州市不动产权第0036728号\",\"certawarddept\":\"林州市自然资源和规划局\",\"copycertcliengguid\":\"7723d3ed-88bc-4331-aa82-436ca8b5bc1e\",\"certattachurl\":\"http://59.227.104.18/ayzwfw/filedownload/73EBBA46BABAAE1F36A5C27DD0776211680A8C4CA50588A18039A663851FA02451F851055B0667090F114841B80A4E2BBACD546E6CD38544E4DEBC3D6D240340\",\"certownercerttype\":\"99\",\"certcatalogid\":\"d0b657f5-7826-454f-8474-d5e5c76da88a\",\"tycertcatcode\":\"GT_0003\",\"isnewsltimage\":\"1\",\"certownerno\":\"410521196401075517\",\"sltimagecliengguid\":\"f600c208-b8fc-48a2-bde3-38a2bba058d2\",\"expiredatefrom\":\"\"}],\"text\":\"获取证照信息成功！\"},\"status\":{\"code\":\"0000\",\"text\":\"\"}}";
//        System.out.println(data);
//        final Map map = JsonUtils.parse(data, Map.class);
//        System.out.println(map);
//        final List<Map> list = (List<Map>) ((Map) map.get("custom")).get("certinfo");
//        System.out.println(list);
//        final Optional<Map> optional = list.stream().filter(m -> String.valueOf(m.get("certcliengguid")).equals("9e63894d-a4f9-402c-9dcc-027d36eef4a8")).findFirst();
//        System.out.println(optional.isPresent());
//        optional.ifPresent(System.out::println);
//        System.out.println(StringUtils.isEmpty(optional.get().get("sltattachurl")));
        String text = "我是一段测试aaaa";

        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        System.out.println(encryptStr);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println(decryptStr);
    }
}

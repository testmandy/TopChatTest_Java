package com.top.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt加密解密工具
 *
 * @Author mandy
 * @Create 2018-04-11 16:16
 * @Version: V1.0
 */
@Test
public class JwtToken {


    /**
     * 公共密钥
     */
    public static final String SECRET = "b4e6c0848d672c020aadb9cccfca6fde";


    /**
     * 创建token
     * @return
     * @throws UnsupportedEncodingException
     */

    public static String createToken() throws UnsupportedEncodingException {

        //过期时间- 1分钟过期
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,2);
        Date expiresDate = nowTime.getTime();

        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        String myJson = "{\"tre_type\":7,\"tre_number\":1,\"tre_token\":0,\"tre_createTime\":1554970926936," +
                "\"tre_event_name\":\"T-X2x8Vggo76fYrF5bMnSMPZjtt8NsCqwng\",\"tre_desc\":\"joined group\"}";
        String token = JWT.create()
                .withIssuer("TOP CHAT")
                //设置过期时间
                .withExpiresAt(expiresDate)
                .withClaim("extra",myJson)
                .sign(Algorithm.HMAC256(SECRET));
        System.out.println(token);
        return token;
    }


    /**
     * 解密
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String,Claim> verifyToken(String token) throws UnsupportedEncodingException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .build();

        DecodedJWT jwt = null;

        try{

            //解密
            jwt = verifier.verify(token);

        }catch (Exception e){
            throw new RuntimeException("token已经失效");
        }

        return jwt.getClaims();

    }

}

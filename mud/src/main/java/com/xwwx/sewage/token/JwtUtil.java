package com.xwwx.sewage.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @see 签名
 * @author 可乐罐
 *
 */
public class JwtUtil {

	/**
	 *	过期时间15分钟
	 */
	private static final long EXPIRE_TIME = 15 *  60 * 1000;
	
	/**
	 *  token私钥
	 */
	private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";
	
	/**
	 *	生成签名,15分钟后过期
	 * 	@param userName
	 * 	@param userId
	 * 	@return
	 */
	public static String sign(String userId,String userName) {
		try {
			//过期时间
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			//私钥加密算法
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			//设置头部信息
			Map<String,Object> header = new HashMap<String,Object>(2);
			header.put("typ", "JWT");
			header.put("alg", "HS256");
			//附带userName,userId生成签名
			return JWT.create()
					.withHeader(header)
					.withClaim("userId", userId)
					.withClaim("userName", userName)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (Exception e) {
			
			return null;
		}
	}
	
	public static boolean verify(String token) {
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getUserName(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userName").asString();
		}catch(JWTDecodeException e) {
			return null;
		}
	}
}

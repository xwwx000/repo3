package sewage;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {

	@Test
	public void lesson1() {
		Jedis jedis = null;
		try {
			jedis = new Jedis("127.0.0.1", 6379);
			jedis.auth("8888");
			System.out.println(jedis.ping());
			System.out.println(jedis.select(1));
			jedis.set("key1", "Íõ±¦Ç¿");
			System.out.println(jedis.get("key1"));
		} catch (Exception e) {

		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
}

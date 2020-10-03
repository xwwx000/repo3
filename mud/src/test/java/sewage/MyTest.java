package sewage;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwwx.sewage.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath*:spring-*.xml"})
public class MyTest {
	 private static final Logger logger = LoggerFactory.getLogger(MyTest.class); 
	@Resource
	private UserService userService;

	
	@Test
	public void menu() {
		
		userService.getMenuByUserIdAndModuleId("11", "admin");
	}
}

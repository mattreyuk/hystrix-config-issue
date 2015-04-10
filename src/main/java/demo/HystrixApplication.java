package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.netflix.hystrix.HystrixCommandProperties;
//import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.HystrixPlugins;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableCircuitBreaker

@RestController
public class HystrixApplication {

    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Autowired
    private MyService myService;

    @RequestMapping("/")
    public String ok() {
        return myService.ok();
    }

    @RequestMapping("/fail")
    public String fail() {
        return myService.fail();
    }

	public static void main(String[] args) {
		HystrixPlugins.getInstance().registerConcurrencyStrategy(new HystrixMDCConcurrencyStrategy());
		//ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.requestLog.enabled", false);
		//ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.requestCache.enabled", false);
		HystrixCommandProperties.Setter().withRequestCacheEnabled(false);
		HystrixCommandProperties.Setter().withRequestLogEnabled(false);
        SpringApplication.run(HystrixApplication.class, args);
    }
}

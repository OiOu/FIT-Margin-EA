package smartBot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.TimeZone;

@ComponentScan({"smartBot"})
@SpringBootApplication
@EnableScheduling
public class SmartBotApplication {

	private final static Logger logger = LogManager.getLogger(SmartBotApplication.class);

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(SmartBotApplication.class, args);

		ConfigurableEnvironment env = context.getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		logger.info("\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"),
				env.getProperty("server.contextPath"), protocol,
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
				env.getProperty("server.contextPath"));
		String configServerStatus = env.getProperty("configserver.status");
		logger.info("\n----------------------------------------------------------\n\t"
						+ "Config Server: \t{}\n----------------------------------------------------------",
				configServerStatus == null ?
						"Not found or not setup for this application" :
						configServerStatus);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		DateTimeZone.setDefault(DateTimeZone.UTC);
	}

	private static void printArg(String strArgumentName, Environment env){
		String strArgumentValue = env.getProperty(strArgumentName);
		if (strArgumentValue != null)
			logger.info(strArgumentName + "=" + strArgumentValue);
	}

	private static String checkHttp(Environment env) {
		String http = "http";
		String strRequireSsl = env.getProperty("security.require-ssl");
		String strSslKeystore = env.getProperty("server.sss.key-store");
		boolean requireSsl = Boolean.valueOf(strRequireSsl);
		if (requireSsl)
			http = "https";
		if(strSslKeystore!=null)
			http = "https";
		return http;
	}
}

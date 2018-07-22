package smartBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@ComponentScan({"smartBot"})
@SpringBootApplication
public class SmartBotApplication {
	private static final Logger log = LoggerFactory.getLogger(SmartBotApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext context = SpringApplication.run(SmartBotApplication.class, args);

		ConfigurableEnvironment env = context.getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		log.info("\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"),
				env.getProperty("server.contextPath"), protocol,
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
				env.getProperty("server.contextPath"));
		String configServerStatus = env.getProperty("configserver.status");
		log.info("\n----------------------------------------------------------\n\t"
						+ "Config Server: \t{}\n----------------------------------------------------------",
				configServerStatus == null ?
						"Not found or not setup for this application" :
						configServerStatus);
	}

	private static void printArg(String strArgumentName, Environment env){
		String strArgumentValue = env.getProperty(strArgumentName);
		if(strArgumentValue!=null)
			log.info(strArgumentName+"="+strArgumentValue);
	}

	private static String checkHttp(Environment env) {
		String http = "http";
		String strRequireSsl = env.getProperty("security.require-ssl");
		String strSslKeystore = env.getProperty("server.sss.key-store");
		boolean requireSsl = Boolean.valueOf(strRequireSsl);
		if(requireSsl)
			http = "https";
		if(strSslKeystore!=null)
			http = "https";
		return http;
	}
}

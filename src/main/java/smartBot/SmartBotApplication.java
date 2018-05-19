package smartBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class SmartBotApplication {
	private static final Logger log = LoggerFactory.getLogger(SmartBotApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(SmartBotApplication.class);

		ConfigurableApplicationContext context = app.run(args);
		Environment env = context.getEnvironment();
		String http = checkHttp(env);

		printArg("com.sun.management.jmxremote.port", env);
		printArg("com.sun.management.jmxremote.rmi.port", env);
		printArg("com.sun.management.jmxremote.local.only", env);
		printArg("java.rmi.server.hostname", env);


		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\t{}://localhost:{}{}\n\t" +
						"External: \t{}://{}:{}{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				http,
				env.getProperty("server.port"),
				env.getProperty("server.contextPath"),
				http,
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				env.getProperty("server.contextPath"));

		String configServerStatus = env.getProperty("configserver.status");
		log.info("\n----------------------------------------------------------\n\t" +
						"Config Server: \t{}\n----------------------------------------------------------",
				configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);

		SpringApplication.run(SmartBotApplication.class, args);
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

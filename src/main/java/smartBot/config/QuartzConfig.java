package smartBot.config;

import org.quartz.SimpleTrigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import smartBot.config.quartz.AutowiringSpringBeanJobFactory;
import smartBot.planner.MarginRatesJSONPlanner;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("config/quartz/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SimpleTriggerFactoryBean marginRatesPlannerTriggerFactory() {
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(marginRatesPlannerFactory().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setRepeatInterval(14400000); // each hour
        stFactory.setMisfireInstruction(
            SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return stFactory;
    }

    @Bean
    public JobDetailFactoryBean marginRatesPlannerFactory() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(MarginRatesJSONPlanner.class);
        factory.setDurability(true);
        factory.setGroup("MarginRatesJSONPlannerGroup");
        factory.setName("MarginRatesJSONPlanner");
        return factory;
    }
}

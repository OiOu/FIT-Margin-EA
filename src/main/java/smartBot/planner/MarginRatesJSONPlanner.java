package smartBot.planner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import smartBot.bean.MarginRates;
import smartBot.bussines.service.MarginRatesService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.utils.HttpDownloader;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarginRatesJSONPlanner implements Job {

    private static final Log logger = LogFactory.getLog(MarginRatesJSONPlanner.class);

    @Resource
    private HttpDownloader httpDownloader;

    @Resource
    private ServerCache serverCache;

    @Resource
    private MarginRatesService marginRatesService;

    @Scheduled(cron = "0 0 * ? * MON-FRI")
    public void execute() throws JobExecutionException {
        execute(null);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        List<MarginRates> marginRatesResultList = new ArrayList<>();

        logInstanceIp();
        try {
            List<MarginRates> marginRatesList = httpDownloader.getMarginRatesFXJSONFile().getMarginRates();
            marginRatesList.forEach(marginRates -> marginRates.setStartDate(new DateTime()));
            marginRatesResultList.addAll(marginRatesService.createAll(marginRatesList));

            marginRatesList = httpDownloader.getMarginRatesMetalJSONFile().getMarginRates();
            marginRatesList.forEach(marginRates -> marginRates.setStartDate(new DateTime()));
            marginRatesResultList.addAll(marginRatesService.createAll(marginRatesList));
        } catch (Exception ex) {
            logger.info("Internet connection failed");
        }
        logInstanceStop(startTime);
    }

    private void logInstanceIp() {
        logger.info("MarginRatesJSONPlanner...");
        try {
            logger.debug("Started on ip" + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            logger.debug("Can't fetch ip.", e);
        }

    }

    private void logInstanceStop(long startTime) {
        if (logger.isDebugEnabled()) {
            logger.debug("Margin rates JSON planer execution finished:  " + (System.currentTimeMillis() - startTime));
        }
    }

}

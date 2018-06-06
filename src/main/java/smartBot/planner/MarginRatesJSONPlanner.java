package smartBot.planner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartBot.bean.MarginRates;
import smartBot.bussines.service.impl.MarginRatesServiceImpl;
import smartBot.utils.HttpDownloader;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class MarginRatesJSONPlanner implements Job {

    private static final Log logger = LogFactory.getLog(MarginRatesJSONPlanner.class);

    @Autowired
    private HttpDownloader httpDownloader;

    @Resource
    private MarginRatesServiceImpl marginRatesService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        logInstanceIp();

        List<MarginRates> marginRatesList = httpDownloader.getMarginRatesFXJSONFile().getMarginRates();
        marginRatesService.createAll(marginRatesList);

        marginRatesList = httpDownloader.getMarginRatesMetalJSONFile().getMarginRates();
        marginRatesService.createAll(marginRatesList);

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

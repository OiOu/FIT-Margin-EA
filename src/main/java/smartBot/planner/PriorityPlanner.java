package smartBot.planner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartBot.utils.HttpDownloader;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class PriorityPlanner implements Job {

    private static final Log logger = LogFactory.getLog(PriorityPlanner.class);

    @Autowired
    private HttpDownloader httpDownloader;

//    @Resource
//    private PriorityService priorityService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        logInstanceIp();

//        priorityService.determine();

        logInstanceStop(startTime);
    }

    private void logInstanceIp() {
        logger.info("PriorityPlanner...");
        try {
            logger.debug("Started on ip" + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            logger.debug("Can't fetch ip.", e);
        }

    }

    private void logInstanceStop(long startTime) {
        if (logger.isDebugEnabled()) {
            logger.debug("PriorityPlanner execution finished:  " + (System.currentTimeMillis() - startTime));
        }
    }

}

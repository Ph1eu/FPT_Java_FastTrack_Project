package org.example.reportservice.SpringBatch.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class JobLauncherConfig.
 *  <p>
 * Class description explaining the usage.
 * </p>
 *
 */
@Configuration
//@EnableBatchProcessing
@EnableScheduling
public class JobLauncherConfig {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherConfig.class);

    /** The job launcher. */
    @Autowired
    JobLauncher jobLauncher;

    /** The import empoyee job. */
    @Autowired
    @Qualifier("testResultReportJob")
    Job testReportJob;



    /** The file path. */
    @Value("${job.enabled-for}")
    public String jobEnabledFor;

    /**
     * Lanch jobs.
     */
    @Scheduled(cron = "${cron.expression-value}")
    public void setJobLauncherJobs() {

//        Map<String, JobParameter> maps = new HashMap<>();
//        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters();

        try {
            LOGGER.info("Launch job Test report job Import Job ....");

            JobExecution testReportJobExec   = jobLauncher.run(testReportJob, parameters);


            LOGGER.info("All job execution completed, status : {} ",
                    "All Jobs executed !!!...");

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException exception) {

            LOGGER.error("Exception message : {} " ,  exception.getMessage());
        }

    }


}

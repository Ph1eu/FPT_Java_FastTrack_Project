package org.example.reportservice.SpringBatch.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class TestReportJobListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(TestReportJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Before job execution...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("After job execution...");
        if (jobExecution.getStatus().toString().equals(ExitStatus.COMPLETED.toString())) {
            logger.info("Test report job completed successfully.");
        } else if (jobExecution.getStatus().toString().equals( ExitStatus.FAILED.toString())) {
            logger.error("Test report job failed.");
        }
    }
}

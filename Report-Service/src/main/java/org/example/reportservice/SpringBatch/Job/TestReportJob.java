package org.example.reportservice.SpringBatch.Job;

import org.example.reportservice.Model.dto.TestReportDto;
import org.example.reportservice.Model.entity.TestReport;
import org.example.reportservice.SpringBatch.Listener.TestReportJobListener;
import org.example.reportservice.SpringBatch.Processor.TestReportProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

@Configuration
//@EnableBatchProcessing
public class TestReportJob {
    private static final Logger logger = LoggerFactory.getLogger(TestReportJob.class);

    /** The job builder factory. */
    public JobBuilder jobBuilder;

    /** The step builder factory. */
    public StepBuilder stepBuilder;
    @Autowired
    public DataSource dataSource;
//    @Value("./src/main/resources/output")
//    public String filePath;
    @Bean("testReportReader")
    public JdbcCursorItemReader<TestReport> reader() throws SQLException {
        logger.info("Initializing the reader...");
        JdbcCursorItemReader<TestReport> itemReader = new JdbcCursorItemReader<>();
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        String sql = "SELECT * FROM test_report" ;//+ "WHERE test_date BETWEEN '" + sevenDaysAgo + "' AND '" + today + "'";
        itemReader.setSql(sql);
        itemReader.setDataSource(dataSource);
        itemReader.setRowMapper(new BeanPropertyRowMapper<>(TestReport.class));
        return itemReader;
    }
    @Bean("testResultReportWriter")
    public FlatFileItemWriter<TestReportDto> writer() {
        logger.info("Initializing the writer...");
        FlatFileItemWriter<TestReportDto> writer = new FlatFileItemWriter<>();

        // print the current
        writer.setResource(new FileSystemResource("test_report.csv"));
        DelimitedLineAggregator<TestReportDto> lineAggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<TestReportDto> fieldExtractor = new BeanWrapperFieldExtractor<TestReportDto>();
        fieldExtractor.setNames(new String[] {"id", "patientId", "testType", "testResult", "testDate", "testStatus"});
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(lineAggregator);
        writer.setForceSync(true);
        return writer;
    }
    @Bean("testResultReportProcessor")
    public TestReportProcessor processor()
    {
        logger.info("Initializing the processor...");
        return new TestReportProcessor();
    }
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean("testResultReportJob")
    public Job testReportJob(JobRepository jobRepository) throws SQLException {
        return new JobBuilder("testResultReportJob",jobRepository )
                .incrementer(new RunIdIncrementer())
                .start(importData(jobRepository))
                .listener(new TestReportJobListener())
                .build();
    }
    @Bean("testResultReportStep")
    public Step importData(JobRepository jobRepository) throws SQLException {
        return new  StepBuilder("testResultReportStep",jobRepository).<TestReport, TestReportDto>chunk(5,transactionManager(dataSource))
                .reader(reader())
                .processor(processor())
                .writer(writer()).build();

    }
}

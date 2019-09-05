package com.springbatch.demo.config;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobListener.class);

    @Value("${input.folder}")
    private String inputResource;

    @Value("${processed.dir}")
    private String processedResource;


    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("============ JOB FINISHED ============ \n");

            // Destination directory
            File dir = new File(processedResource);

            // File (or directory) to be moved
            File dir1 = new File(inputResource);
            if (dir1.isDirectory()) {

                File[] content = dir1.listFiles();
                for (int i = 0; i < content.length; i++) {
                    //move content[i]
                    log.info("============ FILE ============ \n" + dir + File.separator + content[i].getName());

                    try {
                        FileUtils.moveFile(FileUtils.getFile(dir1 + File.separator + content[i].getName()),
                                FileUtils.getFile(dir + File.separator + content[i].getName()));
                    } catch (IOException e) {
                        //log.info("Error: ", e);
                    }

                }
            }

        }
    }

}

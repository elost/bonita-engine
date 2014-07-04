package org.bonitasoft.engine.scheduler.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import org.bonitasoft.engine.log.technical.TechnicalLogSeverity;
import org.bonitasoft.engine.log.technical.TechnicalLoggerService;
import org.bonitasoft.engine.scheduler.JobIdentifier;
import org.bonitasoft.engine.scheduler.StatelessJob;
import org.bonitasoft.engine.scheduler.exception.SSchedulerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.spi.TriggerFiredBundle;


@RunWith(MockitoJUnitRunner.class)
public class TransactionalSimpleJobFactoryTest {

    @Mock
    private SchedulerServiceImpl schedulerService;

    @Mock
    private TechnicalLoggerService logger;

    @Mock
    private TriggerFiredBundle bundle;

    @Mock
    private Scheduler scheduler;

    @InjectMocks
    TransactionalSimpleJobFactory factory;

    @Mock
    private JobDataMap jobDataMap;

    @Mock
    private StatelessJob job;

    @Before
    public void setUp() throws Exception {
        final JobDetailImpl jobDetailImpl = new JobDetailImpl();
        jobDetailImpl.setJobClass(ConcurrentQuartzJob.class);
        jobDetailImpl.setJobDataMap(jobDataMap);
        given(bundle.getJobDetail()).willReturn(jobDetailImpl);
        given(jobDataMap.get("tenantId")).willReturn("1");
        given(jobDataMap.get("jobId")).willReturn("2");

        given(logger.isLoggable(any(Class.class), any(TechnicalLogSeverity.class))).willReturn(true);
    }

    @Test
    public void newJob_should_return_a_QuartzJob_wrapping_the_job_returned_by_scheduler_service() throws Exception {
        //given
        final String jobName = "aJob";
        given(jobDataMap.get("jobName")).willReturn(jobName);
        given(schedulerService.getPersistedJob(new JobIdentifier(2, 1, jobName))).willReturn(job);

        //when
        final Job quartzJob = factory.newJob(bundle, scheduler);

        //then
        assertThat(quartzJob).isInstanceOf(QuartzJob.class);
        assertThat(((QuartzJob) quartzJob).getBosJob()).isEqualTo(job);
    }

    @Test(expected = SchedulerException.class)
    public void newJob_should_throw_SchedulerException_if_getJogDescriptor_throws_Exception_and_it_is_not_a_bonita_job() throws Exception {
        //given
        final String jobName = "aJob";
        given(jobDataMap.get("jobName")).willReturn(jobName);
        given(schedulerService.getPersistedJob(new JobIdentifier(2, 1, jobName))).willThrow(new SSchedulerException(""));

        //when
        factory.newJob(bundle, scheduler);
    }

    @Test
    public void newJob_should_return_quatzJob_with_null_job_if_getJogDescriptor_throws_Exception_and_it_is_a_bonita_job() throws Exception {
        //given
        final String jobName = "BPMEventHandling";
        given(jobDataMap.get("jobName")).willReturn(jobName);
        given(schedulerService.getPersistedJob(new JobIdentifier(2, 1, jobName))).willThrow(new SSchedulerException(""));

        //when
        final Job newJob = factory.newJob(bundle, scheduler);
        assertThat(newJob).isInstanceOf(QuartzJob.class);
        assertThat(((QuartzJob) newJob).getBosJob()).isNull();
    }

}
package santeclair.lunar.framework.web.quartz;

import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class SchedulerFactoryBeanWithShutdownDelay extends SchedulerFactoryBean {

    @Override
    public void destroy() throws SchedulerException {
        super.destroy();
        // TODO: Ugly workaround for https://jira.terracotta.org/jira/browse/QTZ-192
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

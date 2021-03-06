/**
 * Copyright (C) 2011 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301, USA.
 **/
package org.bonitasoft.engine.scheduler.impl;

import java.util.Properties;

import org.bonitasoft.engine.log.technical.TechnicalLoggerService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Baptiste Mesta
 */
public class BonitaSchedulerFactory extends StdSchedulerFactory {

    private SchedulerServiceImpl schedulerService;
    private final TechnicalLoggerService logger;

    public BonitaSchedulerFactory(final Properties props, final TechnicalLoggerService logger) throws SchedulerException {
        super(props);
        this.logger = logger;
    }

    @Override
    public Scheduler getScheduler() throws SchedulerException {
        final Scheduler scheduler = super.getScheduler();
        scheduler.setJobFactory(new TransactionalSimpleJobFactory(schedulerService, logger));
        return scheduler;
    }

    public void setBOSSchedulerService(final SchedulerServiceImpl schedulerService) {
        this.schedulerService = schedulerService;
    }

}

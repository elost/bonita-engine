/*******************************************************************************
 * Copyright (C) 2015 BonitaSoft S.A.
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
 ******************************************************************************/
package org.bonitasoft.engine.api.impl.transaction;

import org.bonitasoft.engine.commons.LifecycleService;
import org.bonitasoft.engine.commons.exceptions.SBonitaException;

/**
 * @author Matthieu Chaffotte
 */
public class ResumeServiceStrategy implements ServiceStrategy {

    private static final long serialVersionUID = -7207853911827859069L;

    @Override
    public String getStateName() {
        return "resume";
    }

    @Override
    public void changeState(final LifecycleService service) throws SBonitaException {
        service.resume();
    }

    @Override
    public boolean shouldRefreshClassLoaders() {
        return true;
    }

}
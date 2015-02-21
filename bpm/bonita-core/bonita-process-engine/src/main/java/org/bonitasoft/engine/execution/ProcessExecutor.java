/**
 * Copyright (C) 2011-2013 BonitaSoft S.A.
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
package org.bonitasoft.engine.execution;

import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.bpm.connector.ConnectorDefinitionWithInputValues;
import org.bonitasoft.engine.bpm.connector.ConnectorEvent;
import org.bonitasoft.engine.commons.exceptions.SBonitaException;
import org.bonitasoft.engine.core.expression.control.model.SExpressionContext;
import org.bonitasoft.engine.core.operation.model.SOperation;
import org.bonitasoft.engine.core.process.definition.model.SProcessDefinition;
import org.bonitasoft.engine.core.process.instance.api.exceptions.SFlowNodeExecutionException;
import org.bonitasoft.engine.core.process.instance.api.exceptions.SFlowNodeReadException;
import org.bonitasoft.engine.core.process.instance.api.exceptions.SProcessInstanceCreationException;
import org.bonitasoft.engine.core.process.instance.model.SProcessInstance;
import org.bonitasoft.engine.core.process.instance.model.SConnectorInstance;
import org.bonitasoft.engine.core.connector.exception.SConnectorInstanceModificationException;

/**
 * @author Baptiste Mesta
 * @author Yanyan Liu
 * @author Elias Ricken de Medeiros
 * @author Hongwen Zang
 * @author Celine Souchet
 * @author Matthieu Chaffotte
 */
public interface ProcessExecutor extends ContainerExecutor {

    SProcessInstance start(long processDefinitionId, long targetSFlowNodeDefinitionId, long starterId, long starterSubstituteId,
            SExpressionContext expressionContext, List<SOperation> operations, Map<String, Object> context,
            List<ConnectorDefinitionWithInputValues> connectors, long callerId, long subProcessDefinitionId) throws SProcessInstanceCreationException;

    SProcessInstance start(long starterId, long starterSubstituteId, List<SOperation> operations,
            Map<String, Object> context, List<ConnectorDefinitionWithInputValues> connectorsWithInput, FlowNodeSelector selector)
            throws SProcessInstanceCreationException;

    SProcessInstance start(long starterId, long starterSubstituteId,
            SExpressionContext expressionContext, List<SOperation> operations, Map<String, Object> context,
            List<ConnectorDefinitionWithInputValues> connectors, long callerId, FlowNodeSelector selector) throws SProcessInstanceCreationException;

    boolean executeConnectors(SProcessDefinition processDefinition, SProcessInstance sInstance, ConnectorEvent activationEvent) throws SBonitaException;

    SProcessInstance startElements(final SProcessInstance sProcessInstance, FlowNodeSelector selector) throws SProcessInstanceCreationException,
            SFlowNodeExecutionException, SFlowNodeReadException;

    void handleProcessCompletion(final SProcessDefinition sProcessDefinition, final SProcessInstance sProcessInstance, final boolean hasActionsToExecute)
            throws SBonitaException;

    void setConnectorState(final SConnectorInstance sConnectorInstance, final String state) throws SConnectorInstanceModificationException;
}

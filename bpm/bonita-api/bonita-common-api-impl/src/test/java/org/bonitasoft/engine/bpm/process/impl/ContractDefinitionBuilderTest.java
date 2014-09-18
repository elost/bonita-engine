package org.bonitasoft.engine.bpm.process.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.bpm.contract.RuleDefinition;
import org.bonitasoft.engine.bpm.contract.SimpleInputDefinition;
import org.bonitasoft.engine.bpm.contract.Type;
import org.bonitasoft.engine.bpm.contract.impl.ContractDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.FlowElementContainerDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.UserTaskDefinitionImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mvel2.MVEL;

@RunWith(MockitoJUnitRunner.class)
public class ContractDefinitionBuilderTest {

    private static final String name = "name";

    private static final Type type = Type.TEXT;

    private static final String description = "description";

    private static final String actorName = "actor name";

    @Mock
    ContractDefinitionImpl contractDefinitionImpl;

    @Mock
    private ProcessDefinitionBuilder processDefinitionBuilder;

    @Mock
    private FlowElementContainerDefinitionImpl container;

    private List<SimpleInputDefinition> inputs;

    private UserTaskDefinitionImpl activity;

    private ContractDefinitionBuilder contractDefinitionBuilder;

    @Before
    public void before() throws Exception {
        activity = new UserTaskDefinitionImpl(name, actorName);
        contractDefinitionBuilder = new ContractDefinitionBuilder(processDefinitionBuilder, container, activity);

    }

    @Test
    public void addInputTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addSimpleInput(name, type, description);

        //then
        assertThat(activity.getContract().getSimpleInputs()).hasSize(1);
        checkBuilder(builder);

    }

    @Test
    public void addComplexInputTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addComplexInput(name, description, new ArrayList<SimpleInputDefinition>(), null);

        //then
        assertThat(activity.getContract().getComplexInputs()).hasSize(1);
        checkBuilder(builder);

    }

    @Test
    public void addRuleTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addRule(name, "expression", "explanation", "inputName");

        //then
        assertThat(activity.getContract().getRules()).hasSize(1);
        checkBuilder(builder);

    }

    @Test
    public void addMandatoryRuleTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addMandatoryRule("inputName");

        //then
        assertThat(activity.getContract().getRules()).hasSize(1);
        RuleDefinition rule = activity.getContract().getRules().get(0);
        assertThat(rule.getInputNames()).containsExactly("inputName");
        assertThat(rule.getExplanation()).as("bad explanation").isEqualTo("input inputName is mandatory");
        checkBuilder(builder);
    }

    @Test
    public void addMandatoryRule_should_fail() throws Exception {
        //given
        List<Object> failingValues = new ArrayList<Object>();
        failingValues.add(null);
        failingValues.add("");

        //when then
        checkRuleShouldGiveExpectedResult(failingValues, false);
    }

    @Test
    public void addMandatoryRule_should_success() throws Exception {
        //given
        List<Object> successValues = new ArrayList<Object>();
        successValues.add("not null value");
        successValues.add(0);
        successValues.add(new Date());

        //when then
        checkRuleShouldGiveExpectedResult(successValues, true);
    }

    private void checkRuleShouldGiveExpectedResult(List<Object> values, Boolean expectedResult) {
        for (Object failingValue : values) {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("inputName", failingValue);

            //when
            contractDefinitionBuilder.addMandatoryRule("inputName");

            //then
            checkRuleGivesExpectedResult(variables, expectedResult);
        }
    }

    private void checkRuleGivesExpectedResult(Map<String, Object> variables, Boolean expectedResult) {
        final Boolean ruleEvaluation = MVEL.evalToBoolean(activity.getContract().getRules().get(0).getExpression(), variables);
        assertThat(ruleEvaluation).as("mandatory rule failure with value:" + variables.toString()).isEqualTo(expectedResult);
    }

    private void checkBuilder(final ContractDefinitionBuilder builder) {
        assertThat(builder).as("should return a builder").isNotNull().isEqualTo(contractDefinitionBuilder);
    }

}
package org.bonitasoft.engine.identity.xml;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bonitasoft.engine.identity.ExportedCustomUserInfoValue;
import org.bonitasoft.engine.xml.XMLNode;
import org.junit.Test;

public class CustomUserInfoValueNodeBuilderTest {

    private static final String USER_INFO_VALUES_TAG = "customUserInfoValues";

    private static final String USER_INFO_VALUE_TAG = "customUserInfoValue";

    private static final String NAME_TAG = "name";

    private static final String VALUE_TAG = "value";

    private static final String LOCATION_VALUE = "Engineering";

    private static final String LOCATION_NAME = "Location";

    private static final String SKILLS_VALUE = "Java";

    private static final String SKILLS_NAME = "Skills";

    @Test
    public void getNode_should_return_root_node_without_child_if_there_are_no_values() {
        // when
        XMLNode node = CustomUserInfoValueNodeBuilder.buildNode(Collections.<ExportedCustomUserInfoValue> emptyList());

        // then
        assertThat(node.getName()).isEqualTo(USER_INFO_VALUES_TAG);
        assertThat(node.getChildNodes()).isEmpty();
    }

    @Test
    public void getNode_should_return_node_containing_all_children() {
        // given
        List<ExportedCustomUserInfoValue> infoValues = new ArrayList<ExportedCustomUserInfoValue>(2);
        infoValues.add(new ExportedCustomUserInfoValue(SKILLS_NAME, SKILLS_VALUE));
        infoValues.add(new ExportedCustomUserInfoValue(LOCATION_NAME, LOCATION_VALUE));

        // when
        XMLNode node = CustomUserInfoValueNodeBuilder.buildNode(infoValues);

        // then
        assertThat(node.getName()).isEqualTo(USER_INFO_VALUES_TAG);
        assertThat(node.getChildNodes().size()).isEqualTo(2);

        checkCustomUserInfoValueNode(SKILLS_NAME, SKILLS_VALUE, node.getChildNodes().get(0));
        checkCustomUserInfoValueNode(LOCATION_NAME, LOCATION_VALUE, node.getChildNodes().get(1));
    }

    private void checkCustomUserInfoValueNode(String nameContent, String valueContent, XMLNode customUserInfoNode) {
        assertThat(customUserInfoNode.getName()).isEqualTo(USER_INFO_VALUE_TAG);
        assertThat(customUserInfoNode.getChildNodes().size()).isEqualTo(2);

        XMLNode nameNode = customUserInfoNode.getChildNodes().get(0);
        assertThat(nameNode.getName()).isEqualTo(NAME_TAG);
        assertThat(nameNode.getContent()).isEqualTo(nameContent);

        XMLNode valueNode = customUserInfoNode.getChildNodes().get(1);
        assertThat(valueNode.getName()).isEqualTo(VALUE_TAG);
        assertThat(valueNode.getContent()).isEqualTo(valueContent);
    }

}

DELETE FROM multi_biz_data WHERE tenantid = ${tenantid};
DELETE FROM ref_biz_data_inst WHERE tenantid = ${tenantid};
DELETE FROM connector_instance WHERE tenantid = ${tenantid};
DELETE FROM hidden_activity WHERE tenantid = ${tenantid};
DELETE FROM message_instance WHERE tenantid = ${tenantid};
DELETE FROM pending_mapping WHERE tenantid = ${tenantid};
DELETE FROM event_trigger_instance WHERE tenantid = ${tenantid};
DELETE FROM waiting_event WHERE tenantid = ${tenantid};
DELETE FROM process_instance WHERE tenantid = ${tenantid};
DELETE FROM flownode_instance WHERE tenantid = ${tenantid};
DELETE FROM token WHERE tenantid = ${tenantid};
DELETE FROM breakpoint WHERE tenantid = ${tenantid};
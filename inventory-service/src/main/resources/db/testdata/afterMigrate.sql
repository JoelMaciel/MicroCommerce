SET foreign_key_checks = 0;

DELETE FROM inventory;

SET foreign_key_checks = 1;


INSERT INTO inventory (id, code_sku, quantity) VALUES
 ('d497c7fa-d086-4468-bf2e-ff20f26aa89f', 'SKU001', 10),
 ('cd59bd09-0feb-49a2-a6cc-145d808aa477', 'SKU002', 0),
 ('967635cc-767b-4812-9fa1-b80b997faa5a', 'SKU003', 20);


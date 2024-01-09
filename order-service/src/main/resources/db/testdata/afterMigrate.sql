set foreign_key_checks = 0;

delete FROM orders;
delete from order_line_items;

set foreign_key_checks = 1;


INSERT INTO `orders` (order_id, order_number) VALUES
('57b1b1b6-c328-48d8-923f-6bd63170716c', 'ORDER001'),
('2b8941ce-00e7-40e3-8883-4f906513887f', 'ORDER002'),
('6075dc19-b85d-4b10-b6bd-aa7f92001f9d', 'ORDER003');

INSERT INTO order_line_items (id, code_sku, price, quantity, order_id) VALUES
('ec132d17-b373-43f4-9480-8559a8e56854', 'SKU001', 10.99, 2, '57b1b1b6-c328-48d8-923f-6bd63170716c'),
('48284425-0d0d-4f9c-bed7-a08d779b0e24', 'SKU002', 15.50, 3, '57b1b1b6-c328-48d8-923f-6bd63170716c'),
('1b7e90ab-525d-4567-b68f-f1edfe3a0cd6', 'SKU003', 8.75, 4, '2b8941ce-00e7-40e3-8883-4f906513887f'),
('ef33f9b1-ef12-4ce4-ad85-fa12606b03c9', 'SKU004', 12.25, 1, '2b8941ce-00e7-40e3-8883-4f906513887f'),
('6994cc33-ff3c-4f55-8da7-ee648b3beefd', 'SKU005', 5.99, 6, '6075dc19-b85d-4b10-b6bd-aa7f92001f9d'),
('4a03c8e0-c718-45fc-b182-1814fb87a4ee', 'SKU006', 9.00, 2, '6075dc19-b85d-4b10-b6bd-aa7f92001f9d');




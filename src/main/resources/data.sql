-- Estados
INSERT INTO estado (id, nome) VALUES ('11111111-1111-1111-1111-000000000001', 'Pernambuco');
INSERT INTO estado (id, nome) VALUES ('11111111-1111-1111-1111-000000000002', 'Ceará');
INSERT INTO estado (id, nome) VALUES ('11111111-1111-1111-1111-000000000003', 'Paraíba');

-- Cidades
INSERT INTO cidade (id, nome, estado_id) VALUES ('22222222-2222-2222-2222-000000000001', 'Recife', '11111111-1111-1111-1111-000000000001');
INSERT INTO cidade (id, nome, estado_id) VALUES ('22222222-2222-2222-2222-000000000002', 'Olinda', '11111111-1111-1111-1111-000000000001');
INSERT INTO cidade (id, nome, estado_id) VALUES ('22222222-2222-2222-2222-000000000003', 'Fortaleza', '11111111-1111-1111-1111-000000000002');
INSERT INTO cidade (id, nome, estado_id) VALUES ('22222222-2222-2222-2222-000000000004', 'Sobral', '11111111-1111-1111-1111-000000000002');
INSERT INTO cidade (id, nome, estado_id) VALUES ('22222222-2222-2222-2222-000000000005', 'João Pessoa', '11111111-1111-1111-1111-000000000003');

-- Categorias
INSERT INTO categoria (id, nome) VALUES ('33333333-3333-3333-3333-000000000001', 'Smartphones');
INSERT INTO categoria (id, nome) VALUES ('33333333-3333-3333-3333-000000000002', 'Notebooks');
INSERT INTO categoria (id, nome) VALUES ('33333333-3333-3333-3333-000000000003', 'Acessórios');
INSERT INTO categoria (id, nome) VALUES ('33333333-3333-3333-3333-000000000004', 'Áudio');
INSERT INTO categoria (id, nome) VALUES ('33333333-3333-3333-3333-000000000005', 'Gaming');
INSERT INTO categoria (id, nome) VALUES ('33333333-3333-3333-3333-000000000006', 'Wearables');

-- Produtos
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000001', 'iPhone 15 Pro', 9499.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000002', 'Galaxy S24 Ultra', 8999.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000003', 'MacBook Air M3', 12990.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000004', 'Dell XPS 13', 9899.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000005', 'AirPods Pro 2', 2199.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000006', 'Sony WH-1000XM5', 2899.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000007', 'PlayStation 5 Slim', 4499.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000008', 'Xbox Series X', 4399.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000009', 'Apple Watch Series 9', 4299.00);
INSERT INTO produto (id, nome, preco) VALUES ('44444444-4444-4444-4444-000000000010', 'Carregador USB-C 65W', 199.00);

-- Produto x Categoria
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000001', '33333333-3333-3333-3333-000000000001');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000002', '33333333-3333-3333-3333-000000000001');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000003', '33333333-3333-3333-3333-000000000002');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000004', '33333333-3333-3333-3333-000000000002');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000005', '33333333-3333-3333-3333-000000000004');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000005', '33333333-3333-3333-3333-000000000003');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000006', '33333333-3333-3333-3333-000000000004');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000007', '33333333-3333-3333-3333-000000000005');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000008', '33333333-3333-3333-3333-000000000005');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000009', '33333333-3333-3333-3333-000000000006');
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES ('44444444-4444-4444-4444-000000000010', '33333333-3333-3333-3333-000000000003');

-- Clientes (apenas Pessoa Física com CPF)
INSERT INTO cliente (id, nome, email, cpf) VALUES ('55555555-5555-5555-5555-000000000001', 'Ana Beatriz Cardoso', 'ana.cardoso@exemplo.com', '11144477735');
INSERT INTO cliente (id, nome, email, cpf) VALUES ('55555555-5555-5555-5555-000000000002', 'Bruno Henrique Lima', 'bruno.lima@exemplo.com', '52998224725');
INSERT INTO cliente (id, nome, email, cpf) VALUES ('55555555-5555-5555-5555-000000000003', 'Carla Souza Andrade', 'carla.andrade@exemplo.com', '39053344705');

-- Telefones (ElementCollection -> tabela `telefone`)
INSERT INTO telefone (cliente_id, telefones) VALUES ('55555555-5555-5555-5555-000000000001', '81 99999-0001');
INSERT INTO telefone (cliente_id, telefones) VALUES ('55555555-5555-5555-5555-000000000001', '81 3344-0001');
INSERT INTO telefone (cliente_id, telefones) VALUES ('55555555-5555-5555-5555-000000000002', '85 98888-0002');
INSERT INTO telefone (cliente_id, telefones) VALUES ('55555555-5555-5555-5555-000000000003', '83 3777-0003');

-- Enderecos
INSERT INTO endereco (id, logradouro, numero, complemento, bairro, cep, cliente_id, cidade_id) VALUES ('66666666-6666-6666-6666-000000000001', 'Rua das Flores', 120, 'Apto 502', 'Boa Viagem', '51020-010', '55555555-5555-5555-5555-000000000001', '22222222-2222-2222-2222-000000000001');
INSERT INTO endereco (id, logradouro, numero, complemento, bairro, cep, cliente_id, cidade_id) VALUES ('66666666-6666-6666-6666-000000000002', 'Avenida Beira Mar', 1500, NULL, 'Meireles', '60165-121', '55555555-5555-5555-5555-000000000002', '22222222-2222-2222-2222-000000000003');
INSERT INTO endereco (id, logradouro, numero, complemento, bairro, cep, cliente_id, cidade_id) VALUES ('66666666-6666-6666-6666-000000000003', 'Rua Epitácio Pessoa', 350, 'Sala 12', 'Tambaú', '58039-000', '55555555-5555-5555-5555-000000000003', '22222222-2222-2222-2222-000000000005');

-- Pedidos
INSERT INTO pedido (id, instante, cliente_id, endereco_entrega_id) VALUES ('77777777-7777-7777-7777-000000000001', '2025-01-15 10:32:00', '55555555-5555-5555-5555-000000000001', '66666666-6666-6666-6666-000000000001');
INSERT INTO pedido (id, instante, cliente_id, endereco_entrega_id) VALUES ('77777777-7777-7777-7777-000000000002', '2025-02-20 14:10:00', '55555555-5555-5555-5555-000000000002', '66666666-6666-6666-6666-000000000002');
INSERT INTO pedido (id, instante, cliente_id, endereco_entrega_id) VALUES ('77777777-7777-7777-7777-000000000003', '2025-03-05 09:55:00', '55555555-5555-5555-5555-000000000003', '66666666-6666-6666-6666-000000000003');

-- Pagamentos (PK = pedido_id por causa do @MapsId)
INSERT INTO pagamento (pedido_id, estado_pagamento) VALUES ('77777777-7777-7777-7777-000000000001', 201);
INSERT INTO pagamento_com_cartao (pedido_id, numero_de_parcelas) VALUES ('77777777-7777-7777-7777-000000000001', 6);

INSERT INTO pagamento (pedido_id, estado_pagamento) VALUES ('77777777-7777-7777-7777-000000000002', 101);
INSERT INTO pagamento_com_boleto (pedido_id, data_vencimento, data_pagamento) VALUES ('77777777-7777-7777-7777-000000000002', '2025-02-27', NULL);

INSERT INTO pagamento (pedido_id, estado_pagamento) VALUES ('77777777-7777-7777-7777-000000000003', 201);
INSERT INTO pagamento_com_cartao (pedido_id, numero_de_parcelas) VALUES ('77777777-7777-7777-7777-000000000003', 10);

-- Itens do pedido (preco é snapshot, desconto pode ser zero)
INSERT INTO item_pedido (pedido_id, produto_id, desconto, quantidade, preco) VALUES ('77777777-7777-7777-7777-000000000001', '44444444-4444-4444-4444-000000000001', 0.00, 1, 9499.00);
INSERT INTO item_pedido (pedido_id, produto_id, desconto, quantidade, preco) VALUES ('77777777-7777-7777-7777-000000000001', '44444444-4444-4444-4444-000000000005', 0.00, 1, 2199.00);

INSERT INTO item_pedido (pedido_id, produto_id, desconto, quantidade, preco) VALUES ('77777777-7777-7777-7777-000000000002', '44444444-4444-4444-4444-000000000003', 500.00, 1, 12990.00);
INSERT INTO item_pedido (pedido_id, produto_id, desconto, quantidade, preco) VALUES ('77777777-7777-7777-7777-000000000002', '44444444-4444-4444-4444-000000000010', 0.00, 2, 199.00);

INSERT INTO item_pedido (pedido_id, produto_id, desconto, quantidade, preco) VALUES ('77777777-7777-7777-7777-000000000003', '44444444-4444-4444-4444-000000000007', 0.00, 2, 4499.00);
INSERT INTO item_pedido (pedido_id, produto_id, desconto, quantidade, preco) VALUES ('77777777-7777-7777-7777-000000000003', '44444444-4444-4444-4444-000000000009', 100.00, 1, 4299.00);

INSERT INTO `clientes` (id, nome, sobrenome, situacao) VALUES (1,'Ana','Silva',1);
INSERT INTO `clientes` (id, nome, sobrenome, situacao) VALUES (2,'Rafael','Dias',1);
INSERT INTO `clientes` (id, nome, sobrenome, situacao) VALUES (3,'Aline Marisa','Vaz',0);
INSERT INTO `clientes` (id, nome, sobrenome, situacao) VALUES (4,'Aline','Dias',0);

INSERT INTO `produtos` (id, nome, valor, descricao, situacao, estoque) VALUES (1,'Café',12.80,'Café em pó tradicional Igaçu lata 400g',1,75);
INSERT INTO `produtos` (id, nome, valor, descricao, situacao, estoque) VALUES (2,'Erva Mate',9.80,'Erva Mate Pura Folha 1kg',1,70);
INSERT INTO `produtos` (id, nome, valor, descricao, situacao, estoque) VALUES (3,'Chá Preto',3.20,'Prenda',1,70);
INSERT INTO `produtos` (id, nome, valor, descricao, situacao, estoque) VALUES (4,'Arroz',10.90,'Arroz Camil branco polido tipo 1 pacote 5kg',1,100);
INSERT INTO `produtos` (id, nome, valor, descricao, situacao, estoque) VALUES (5,'Feijão',6.80,'Feijão Tordilho pacote 1kg',0,100);

INSERT INTO `pedidos` VALUES (1,'2023-05-24','2023-05-24',0,'visa débito','',226,1);

INSERT INTO `itens` VALUES (1,10,'',128,1,1),(2,10,'',98,1,2);
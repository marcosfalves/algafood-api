create table pedido (
    id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,
    data_cricacao datetime not null,
    data_confirmacao datetime,
    data_cancelamento datetime,
    data_entrega datetime,
    status varchar(10) not null,
    forma_pagamento_id bigint not null,
    restaurante_id bigint not null,
    usuario_id bigint not null,
	endereco_cidade_id bigint not null,
	endereco_cep varchar(9) not null,
	endereco_logradouro varchar(100) not null,
	endereco_numero varchar(20) not null,
	endereco_complemento varchar(60),
	endereco_bairro varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8mb4;


create table item_pedido (
    id bigint not null auto_increment,
    pedido_id bigint not null,
    produto_id bigint not null,
    quantidade int not null,
    preco_unitario decimal(10,2) not null,
    preco_total decimal(10,2) not null,
    observacao varchar(200),

    primary key (id)
) engine=InnoDB default charset=utf8mb4;


alter table pedido add constraint fk_forma_pagamento_pedido
foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table pedido add constraint fk_restaurante_pedido
foreign key (restaurante_id) references restaurante (id);

alter table pedido add constraint fk_usuario_pedido
foreign key (usuario_id) references usuario (id);

alter table pedido add constraint fk_pedido_cidade
foreign key (endereco_cidade_id) references cidade (id);

alter table item_pedido add constraint fk_pedido_item_pedido
foreign key (pedido_id) references pedido (id);

alter table item_pedido add constraint fk_produto_item_pedido
foreign key (produto_id) references produto (id);
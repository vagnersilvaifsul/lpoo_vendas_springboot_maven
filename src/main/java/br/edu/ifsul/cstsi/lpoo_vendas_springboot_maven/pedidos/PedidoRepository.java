package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.pedidos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    //List<Pedido> findByIdCliente(Long id);
    @Query(value="select p from Cliente c inner join Pedido p on c.id = p.cliente.id where c.id = ?1")
    List<Pedido> findByIdCliente(Long id);

}

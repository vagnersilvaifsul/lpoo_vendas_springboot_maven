package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.pedidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository rep;

    public Pedido insert(Pedido pedido) {
        Assert.isNull(pedido.getId(),"Não foi possível inserir o registro");
        return rep.save(pedido);
    }

    public List<Pedido> getPedidosByIdCliente(Long id){
        Assert.notNull(id,"Não foi possível buscar o registro");
        return rep.findByIdCliente(id);
    }

    public Pedido getPedidoById(Long id) {
        Optional<Pedido> optional = rep.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public Pedido update(Pedido pedido) {
        Assert.notNull(pedido.getId(),"Não foi possível atualizar o registro");

        // Busca o produto no banco de dados
        Optional<Pedido> optional = rep.findById(pedido.getId());
        if(optional.isPresent()) {
            Pedido db = optional.get();
            // Copiar as propriedades
            db.setFormaPagamento(pedido.getFormaPagamento());
            db.setEstado(pedido.getEstado());
            db.setDataCriacao(pedido.getDataCriacao());
            db.setDataModificacao(LocalDate.now());
            db.setTotalPedido(pedido.getTotalPedido());
            db.setSituacao(pedido.getSituacao());
            // Atualiza o produto
            rep.save(db);

            return db;
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }
}

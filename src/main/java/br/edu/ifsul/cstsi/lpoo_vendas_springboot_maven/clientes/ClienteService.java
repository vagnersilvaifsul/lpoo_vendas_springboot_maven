package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository rep;

    public List<Cliente> getClientes() {
        return rep.findAll();
    }

    public Cliente getClienteById(Long id) {
        Optional<Cliente> optional = rep.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public Cliente getClienteByIdSituacaoTrue(Long id){
        Optional<Cliente> optional = rep.finByIdSituacaoTrue(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public List<Cliente> getClientesByNome(String nome) {
        return new ArrayList<>(rep.findByNome(nome + "%"));
    }

    public List<Cliente> getClientesBySituacao(Boolean situacao){
        return new ArrayList<>(rep.findBySituacao(situacao));
    }

    public Cliente insert(Cliente cliente) {
        Assert.isNull(cliente.getId(),"Não foi possível inserir o registro");
        return rep.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        Assert.notNull(cliente.getId(),"Não foi possível atualizar o registro");

        // Busca o produto no banco de dados
        Optional<Cliente> optional = rep.findById(cliente.getId());
        if(optional.isPresent()) {
            Cliente db = optional.get();
            // Copiar as propriedades
            db.setNome(cliente.getNome());
            db.setSobrenome(cliente.getSobrenome());
            db.setSituacao(cliente.getSituacao());
            // Atualiza o produto
            rep.save(db);

            return db;
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}

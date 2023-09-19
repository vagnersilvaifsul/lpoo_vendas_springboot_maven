package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.itens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ItemService {
    @Autowired
    private ItemRepository rep;

    public Item insert(Item item) {
        Assert.isNull(item.getId(),"Não foi possível inserir o registro");
        return rep.save(item);
    }
}

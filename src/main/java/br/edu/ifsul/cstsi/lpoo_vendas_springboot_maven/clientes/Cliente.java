package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes;

import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.pedidos.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sobrenome;
	private Boolean situacao;
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	List<Pedido> pedidos;


	@Override
	public String toString() {
		return "\nCliente{" +
			"id=" + id +
			", nome='" + nome + '\'' +
			", sobrenome='" + sobrenome + '\'' +
			", situacao=" + situacao +
			'}';
	}
}

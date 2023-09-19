package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.pedidos;

import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes.Cliente;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.itens.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String formaPagamento;
	private Estado estado;
	private LocalDate dataCriacao;
	private LocalDate dataModificacao;
	private Double totalPedido;
	private Boolean situacao;
	@ManyToOne
	private Cliente cliente;
	@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
	List<Item> itens;

	@Override
	public String toString() {
		return "\n\nPedido{" +
			"id=" + id +
			", formaPagamento='" + formaPagamento + '\'' +
			", estado='" + estado + '\'' +
			", dataCriacao=" + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataCriacao) +
			", dataModificacao=" + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataModificacao) +
			", totalPedido=" + NumberFormat.getCurrencyInstance().format(totalPedido) +
			", situacao=" + situacao +
			", cliente=" + cliente +
			", itens=" + itens +
            "}";
	}
}

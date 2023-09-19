package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produtos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Double valor;
	private Integer estoque;
	private Boolean situacao;

	@Override
	public String toString() {
		return "\nProduto{" +
			"id=" + id +
			", nome='" + nome + '\'' +
			", descricao='" + descricao + '\'' +
			", valor=" + valor +
			", estoque=" + estoque +
			", situacao=" + situacao +
			'}';
	}
}

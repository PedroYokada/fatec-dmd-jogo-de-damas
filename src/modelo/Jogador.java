package modelo;

public class Jogador {

	// Atributos encapsulados do jogador.
	private final String nome;
	private final String cor;

	public Jogador(String nome, String cor) {

		// Impede a criação de jogador sem nome.
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome inválido.");
		}

		// Aceita apenas as cores BRANCAS ou PRETAS.
		if (!cor.equals("BRANCAS") && !cor.equals("PRETAS")) {
			throw new IllegalArgumentException("Cor inválida.");
		}

		// Armazena o nome e a cor recebidos.
		this.nome = nome.trim();
		this.cor = cor;
	}

	// Retorna o nome do jogador.
	public String getNome() {
		return nome;
	}

	// Retorna a cor do jogador.
	public String getCor() {
		return cor;
	}

	// Retorna nome e cor em formato de texto.
	@Override
	public String toString() {
		return nome + " (" + cor + ")";
	}
}
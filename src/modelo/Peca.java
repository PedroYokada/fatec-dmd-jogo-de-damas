package modelo;

// Classe abstrata que serve como base para os diferentes tipos de peça.
// Define atributos e métodos comuns que serão herdados pelas subclasses.
public abstract class Peca {

	// Atributo encapsulado que armazena a cor da peça.
	private final String cor;

	// Construtor da classe Peca.
	// Valida e armazena a cor recebida.
	public Peca(String cor) {

		// Aceita apenas as cores BRANCAS ou PRETAS.
		if (cor == null || (!cor.equals("BRANCAS") && !cor.equals("PRETAS"))) {
			throw new IllegalArgumentException("Cor inválida.");
		}

		this.cor = cor;
	}

	// Getter que retorna a cor da peça.
	public String getCor() {
		return cor;
	}

	// Método abstrato que obriga as subclasses
	// a definirem seu próprio símbolo.
	public abstract char getSimbolo();

	// Método abstrato que obriga as subclasses
	// a implementarem suas próprias regras de movimentação.
	public abstract boolean movimentoValido(Posicao origem, Posicao destino);

	// Sobrescreve toString() e retorna o símbolo da peça
	// em formato de texto.
	@Override
	public String toString() {
		return String.valueOf(getSimbolo());
	}
}
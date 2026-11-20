package modelo;

// PecaComum é uma subclasse de Peca.
// Por herança, recebe atributos e métodos da classe pai,
// como a cor e o método getCor().
public class PecaComum extends Peca {

	// Recebe a cor e a envia para o construtor da classe pai.
	public PecaComum(String cor) {
		super(cor);
	}

	// Sobrescreve o método abstrato getSimbolo().
	// Retorna 'b' para peças brancas e 'p' para peças pretas.
	@Override
	public char getSimbolo() {

		if (getCor().equals("BRANCAS")) {
			return 'b';
		}

		return 'p';
	}

	// Sobrescreve o método abstrato movimentoValido()
	// utilizando as regras específicas da PecaComum.
	@Override
	public boolean movimentoValido(Posicao origem, Posicao destino) {

		// Calcula a diferença entre origem e destino
		// nas linhas e colunas da matriz.
		int diferencaLinha = destino.getLinha() - origem.getLinha();
		int diferencaColuna = destino.getColuna() - origem.getColuna();

		// Verifica o movimento normal de uma casa na diagonal.
		if (Math.abs(diferencaColuna) == 1) {

			// Peças brancas avançam uma linha.
			if (getCor().equals("BRANCAS")) {
				return diferencaLinha == 1;
			}

			// Peças pretas avançam uma linha no sentido contrário.
			return diferencaLinha == -1;
		}

		// Verifica o formato geométrico de uma captura:
		// duas linhas e duas colunas na diagonal.
		if (Math.abs(diferencaLinha) == 2 && Math.abs(diferencaColuna) == 2) {

			return true;
		}

		// Qualquer outro deslocamento é considerado inválido.
		return false;
	}
}
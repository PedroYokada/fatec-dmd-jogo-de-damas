package modelo;

// Dama é uma subclasse de Peca.
// Por herança, recebe atributos e métodos da classe pai,
// como a cor, getCor() e toString().
//
// Possui regras próprias de movimentação:
// pode andar várias casas na diagonal,
// tanto para frente quanto para trás.
public class Dama extends Peca {

	// Recebe a cor e a envia para o construtor da classe pai.
	public Dama(String cor) {
		super(cor);
	}

	// Sobrescreve o método abstrato getSimbolo().
	// Retorna 'B' para dama branca e 'P' para dama preta.
	@Override
	public char getSimbolo() {

		if (getCor().equals("BRANCAS")) {
			return 'B';
		}

		return 'P';
	}

	// Sobrescreve o método abstrato movimentoValido()
	// utilizando as regras específicas da Dama.
	@Override
	public boolean movimentoValido(Posicao origem, Posicao destino) {

		// Calcula o deslocamento entre origem e destino
		// nas linhas e colunas da matriz.
		int diferencaLinha = destino.getLinha() - origem.getLinha();
		int diferencaColuna = destino.getColuna() - origem.getColuna();

		// Math.abs() desconsidera o sentido do movimento
		// e compara apenas a distância percorrida.
		//
		// Se a diferença de linha for igual à diferença de coluna,
		// o movimento está ocorrendo na diagonal.
		//
		// diferencaLinha != 0 impede que origem e destino
		// sejam a mesma posição.
		//
		// Neste método é validado apenas o formato diagonal.
		// Bloqueios, destino ocupado e capturas serão
		// verificados posteriormente por Tabuleiro e Partida.
		return Math.abs(diferencaLinha) == Math.abs(diferencaColuna) && diferencaLinha != 0;
	}
}
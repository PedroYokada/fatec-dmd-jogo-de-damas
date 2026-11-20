package jogo;

import modelo.Peca;
import modelo.PecaComum;
import modelo.Posicao;

public class Tabuleiro {

	// Matriz 8x8 que representa as casas do tabuleiro.
	private final Peca[][] casas;

	// Construtor responsável por criar o tabuleiro.
	public Tabuleiro() {

		casas = new Peca[8][8];

		inicializarPecas();
	}

	// Posiciona as peças nas posições iniciais.
	private void inicializarPecas() {

		// Peças brancas nas linhas 1, 2 e 3.
		for (int linha = 0; linha < 3; linha++) {

			for (int coluna = 0; coluna < 8; coluna++) {

				// As peças ocupam somente as casas escuras.
				if ((linha + coluna) % 2 == 0) {

					casas[linha][coluna] = new PecaComum("BRANCAS");
				}
			}
		}

		// Peças pretas nas linhas 6, 7 e 8.
		for (int linha = 5; linha < 8; linha++) {

			for (int coluna = 0; coluna < 8; coluna++) {

				if ((linha + coluna) % 2 == 0) {

					casas[linha][coluna] = new PecaComum("PRETAS");
				}
			}
		}
	}

	// Retorna a peça existente em uma posição.
	public Peca getPeca(Posicao posicao) {

		return casas[posicao.getLinha()][posicao.getColuna()];
	}

	// Verifica se uma posição está vazia.
	public boolean estaVazia(Posicao posicao) {

		return getPeca(posicao) == null;
	}

	// Coloca uma peça em determinada posição.
	public void colocarPeca(Posicao posicao, Peca peca) {

		casas[posicao.getLinha()][posicao.getColuna()] = peca;
	}

	// Remove uma peça do tabuleiro.
	public void removerPeca(Posicao posicao) {

		casas[posicao.getLinha()][posicao.getColuna()] = null;
	}

	// Move uma peça da origem para o destino.
	public void moverPeca(Posicao origem, Posicao destino) {

		Peca peca = getPeca(origem);

		colocarPeca(destino, peca);

		removerPeca(origem);
	}

	// Remove todas as peças do tabuleiro.
	// Utilizado principalmente ao carregar partidas salvas.
	public void limpar() {

		for (int linha = 0; linha < 8; linha++) {

			for (int coluna = 0; coluna < 8; coluna++) {

				casas[linha][coluna] = null;
			}
		}
	}

	// Retorna uma representação visual do tabuleiro.
	@Override
	public String toString() {

		StringBuilder texto = new StringBuilder();

		texto.append("\n");
		texto.append("    A B C D E F G H\n");
		texto.append("\n");

		// Percorre da linha 8 até a linha 1.
		for (int linha = 7; linha >= 0; linha--) {

			texto.append(linha + 1).append("   ");

			for (int coluna = 0; coluna < 8; coluna++) {

				Peca peca = casas[linha][coluna];

				if (peca == null) {

					texto.append(". ");

				} else {

					texto.append(peca.getSimbolo()).append(" ");
				}
			}

			texto.append("  ").append(linha + 1);
			texto.append("\n");
		}

		texto.append("\n");
		texto.append("    A B C D E F G H\n");

		return texto.toString();
	}
}
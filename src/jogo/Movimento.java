package jogo;

import modelo.Posicao;

// Representa um movimento realizado no tabuleiro.
// Armazena a posição de origem e a posição de destino.
public class Movimento {

	// Posições encapsuladas e imutáveis do movimento.
	private final Posicao origem;
	private final Posicao destino;

	// Construtor que recebe a origem e o destino do movimento.
	public Movimento(Posicao origem, Posicao destino) {

		if (origem == null || destino == null) {
			throw new IllegalArgumentException("Origem e destino são obrigatórios.");
		}

		this.origem = origem;
		this.destino = destino;
	}

	// Retorna a posição de origem.
	public Posicao getOrigem() {
		return origem;
	}

	// Retorna a posição de destino.
	public Posicao getDestino() {
		return destino;
	}

	// Retorna o movimento em formato de texto.
	@Override
	public String toString() {
		return origem + " -> " + destino;
	}
}
package jogo;

import java.util.ArrayList;
import java.util.List;

import modelo.Dama;
import modelo.Jogador;
import modelo.Peca;
import modelo.PecaComum;
import modelo.Posicao;

// Classe responsável por controlar o estado e as regras da partida.
public class Partida {

	// Jogadores da partida.
	private final Jogador jogadorBrancas;
	private final Jogador jogadorPretas;

	// Tabuleiro utilizado durante a partida.
	private final Tabuleiro tabuleiro;

	// Guarda a cor que possui o turno atual.
	private String turno;

	// Guarda o estado atual da partida.
	private String status;

	// Guarda a posição da peça que precisa continuar
	// uma sequência de capturas.
	private Posicao pecaEmCaptura;

	// Guarda as peças capturadas durante uma sequência.
	// Elas permanecem bloqueando o tabuleiro até o final.
	private final List<Posicao> capturasPendentes;

	// Construtor responsável por iniciar uma nova partida.
	public Partida(String nomeBrancas, String nomePretas) {

		jogadorBrancas = new Jogador(nomeBrancas, "BRANCAS");
		jogadorPretas = new Jogador(nomePretas, "PRETAS");

		tabuleiro = new Tabuleiro();

		turno = "BRANCAS";
		status = "EM_ANDAMENTO";

		pecaEmCaptura = null;
		capturasPendentes = new ArrayList<>();
	}

	// Retorna o jogador das peças brancas.
	public Jogador getJogadorBrancas() {

		return jogadorBrancas;
	}

	// Retorna o jogador das peças pretas.
	public Jogador getJogadorPretas() {

		return jogadorPretas;
	}

	// Retorna o tabuleiro da partida.
	public Tabuleiro getTabuleiro() {

		return tabuleiro;
	}

	// Retorna a cor que possui o turno atual.
	public String getTurno() {

		return turno;
	}

	// Retorna o status atual da partida.
	public String getStatus() {

		return status;
	}

	// Recebe um movimento e aplica as regras da partida.
	public void mover(Movimento movimento) {

		if (movimento == null) {

			throw new IllegalArgumentException("Movimento inválido.");
		}

		// Impede novos movimentos se a partida já terminou.
		if (!status.equals("EM_ANDAMENTO")) {

			throw new IllegalStateException("A partida já foi encerrada.");
		}

		// Durante uma captura múltipla,
		// somente a mesma peça pode continuar jogando.
		if (pecaEmCaptura != null && !movimento.getOrigem().equals(pecaEmCaptura)) {

			throw new IllegalArgumentException("A mesma peça deve continuar a sequência de capturas.");
		}

		Peca peca = tabuleiro.getPeca(movimento.getOrigem());

		// Verifica se existe uma peça na origem.
		if (peca == null) {

			throw new IllegalArgumentException("Não existe peça na posição de origem.");
		}

		// Verifica se a peça pertence ao jogador atual.
		if (!peca.getCor().equals(turno)) {

			throw new IllegalArgumentException("Essa peça não pertence ao jogador da vez.");
		}

		// O destino deve estar vazio.
		if (!tabuleiro.estaVazia(movimento.getDestino())) {

			throw new IllegalArgumentException("A posição de destino está ocupada.");
		}

		// Verifica a regra geométrica definida pela própria peça.
		if (!peca.movimentoValido(movimento.getOrigem(), movimento.getDestino())) {

			throw new IllegalArgumentException("Movimento inválido.");
		}

		// Descobre se o movimento representa uma captura.
		Posicao posicaoCapturada = obterCapturaDoMovimento(peca, movimento);

		// Descobre o maior número de capturas
		// disponível no estado atual.
		int maiorCaptura = obterMaiorNumeroCapturasObrigatorias();

		if (maiorCaptura > 0) {

			// Se existe captura,
			// movimento normal é proibido.
			if (posicaoCapturada == null) {

				throw new IllegalArgumentException("Existe uma captura obrigatória.");
			}

			// Simula a sequência escolhida.
			List<Posicao> simulacaoCapturas = new ArrayList<>(capturasPendentes);

			simulacaoCapturas.add(posicaoCapturada);

			int capturasDaJogada = 1
					+ calcularMaximoCapturas(movimento.getDestino(), peca, simulacaoCapturas, movimento.getOrigem());

			// Obriga a escolher uma sequência
			// com o maior número possível de capturas.
			if (capturasDaJogada < maiorCaptura) {

				throw new IllegalArgumentException(
						"É obrigatório escolher a sequência " + "com o maior número de capturas.");
			}
		}

		// Se ocorreu uma captura,
		// guarda a posição.
		// A peça capturada ainda não é removida.
		if (posicaoCapturada != null) {

			if (capturasPendentes.contains(posicaoCapturada)) {

				throw new IllegalArgumentException("Essa peça já foi capturada nesta sequência.");
			}

			capturasPendentes.add(posicaoCapturada);
		}

		// Executa fisicamente o movimento.
		tabuleiro.moverPeca(movimento.getOrigem(), movimento.getDestino());

		// Se ocorreu captura,
		// verifica se a mesma peça ainda possui
		// outra captura disponível.
		if (posicaoCapturada != null) {

			int proximasCapturas = calcularMaximoCapturas(movimento.getDestino(), peca,
					new ArrayList<>(capturasPendentes), movimento.getDestino());

			if (proximasCapturas > 0) {

				pecaEmCaptura = movimento.getDestino();

				// O turno não muda.
				return;
			}
		}

		// A sequência terminou.
		finalizarCapturas();

		// A promoção acontece somente depois que
		// toda a jogada foi concluída.
		promoverSeNecessario(movimento.getDestino(), peca);

		// Verifica se o adversário perdeu.
		verificarVitoria();

		// Só troca o turno se a partida continuar.
		if (status.equals("EM_ANDAMENTO")) {

			trocarTurno();
		}
	}

	// Descobre se o movimento representa uma captura.
	private Posicao obterCapturaDoMovimento(Peca peca, Movimento movimento) {

		if (peca instanceof PecaComum) {

			return obterCapturaComum(peca, movimento);
		}

		if (peca instanceof Dama) {

			return obterCapturaDama(peca, movimento);
		}

		return null;
	}

	// Verifica uma possível captura
	// realizada por PecaComum.
	private Posicao obterCapturaComum(Peca peca, Movimento movimento) {

		int diferencaLinha = movimento.getDestino().getLinha() - movimento.getOrigem().getLinha();

		int diferencaColuna = movimento.getDestino().getColuna() - movimento.getOrigem().getColuna();

		// Movimento de uma casa não é captura.
		if (Math.abs(diferencaLinha) != 2 || Math.abs(diferencaColuna) != 2) {

			return null;
		}

		Posicao posicaoCapturada = obterPosicaoMeio(movimento);

		// Uma peça já capturada
		// não pode ser capturada novamente.
		if (capturasPendentes.contains(posicaoCapturada)) {

			throw new IllegalArgumentException("Essa peça já foi capturada nesta sequência.");
		}

		Peca pecaCapturada = tabuleiro.getPeca(posicaoCapturada);

		if (pecaCapturada == null) {

			throw new IllegalArgumentException("Não existe peça para capturar.");
		}

		if (pecaCapturada.getCor().equals(peca.getCor())) {

			throw new IllegalArgumentException("Não é possível capturar uma peça da mesma cor.");
		}

		return posicaoCapturada;
	}

	// Analisa o caminho percorrido por uma Dama.
	// Uma captura válida pode atravessar apenas
	// uma peça adversária.
	private Posicao obterCapturaDama(Peca peca, Movimento movimento) {

		int diferencaLinha = movimento.getDestino().getLinha() - movimento.getOrigem().getLinha();

		int diferencaColuna = movimento.getDestino().getColuna() - movimento.getOrigem().getColuna();

		int passoLinha = diferencaLinha > 0 ? 1 : -1;

		int passoColuna = diferencaColuna > 0 ? 1 : -1;

		int linha = movimento.getOrigem().getLinha() + passoLinha;

		int coluna = movimento.getOrigem().getColuna() + passoColuna;

		Posicao capturada = null;

		while (linha != movimento.getDestino().getLinha() && coluna != movimento.getDestino().getColuna()) {

			Posicao atual = new Posicao(linha, coluna);

			Peca encontrada = tabuleiro.getPeca(atual);

			if (encontrada != null) {

				// Peças já capturadas
				// continuam bloqueando.
				if (capturasPendentes.contains(atual)) {

					throw new IllegalArgumentException("O caminho da Dama está bloqueado.");
				}

				// A Dama não pode atravessar
				// peça da mesma cor.
				if (encontrada.getCor().equals(peca.getCor())) {

					throw new IllegalArgumentException("O caminho da Dama está bloqueado.");
				}

				// Não pode saltar duas peças
				// em uma única captura.
				if (capturada != null) {

					throw new IllegalArgumentException("A Dama não pode atravessar mais de uma peça.");
				}

				capturada = atual;
			}

			linha += passoLinha;
			coluna += passoColuna;
		}

		return capturada;
	}

	// Retorna a posição localizada no meio
	// de uma captura de PecaComum.
	private Posicao obterPosicaoMeio(Movimento movimento) {

		int linha = (movimento.getOrigem().getLinha() + movimento.getDestino().getLinha()) / 2;

		int coluna = (movimento.getOrigem().getColuna() + movimento.getDestino().getColuna()) / 2;

		return new Posicao(linha, coluna);
	}

	// Retorna o maior número de capturas
	// disponível para o jogador atual.
	private int obterMaiorNumeroCapturasObrigatorias() {

		// Durante uma sequência,
		// somente a mesma peça pode continuar capturando.
		if (pecaEmCaptura != null) {

			Peca peca = tabuleiro.getPeca(pecaEmCaptura);

			if (peca == null) {

				return 0;
			}

			return calcularMaximoCapturas(pecaEmCaptura, peca, new ArrayList<>(capturasPendentes), pecaEmCaptura);
		}

		return maiorCapturaDaCor(turno, new ArrayList<>(capturasPendentes));
	}

	// Percorre todas as peças da cor informada
	// e encontra a maior sequência possível.
	private int maiorCapturaDaCor(String cor, List<Posicao> jaCapturadas) {

		int maior = 0;

		for (int linha = 0; linha < 8; linha++) {

			for (int coluna = 0; coluna < 8; coluna++) {

				Posicao posicao = new Posicao(linha, coluna);

				Peca peca = tabuleiro.getPeca(posicao);

				if (peca == null || !peca.getCor().equals(cor)) {

					continue;
				}

				int quantidade = calcularMaximoCapturas(posicao, peca, new ArrayList<>(jaCapturadas), posicao);

				if (quantidade > maior) {

					maior = quantidade;
				}
			}
		}

		return maior;
	}

	// Calcula recursivamente
	// o maior número de capturas possível
	// para uma determinada peça.
	private int calcularMaximoCapturas(Posicao origem, Peca peca, List<Posicao> jaCapturadas, Posicao posicaoOriginal) {

		if (peca instanceof PecaComum) {

			return calcularCapturasComum(origem, peca, jaCapturadas, posicaoOriginal);
		}

		if (peca instanceof Dama) {

			return calcularCapturasDama(origem, peca, jaCapturadas, posicaoOriginal);
		}

		return 0;
	}

	// Calcula recursivamente
	// as capturas possíveis para uma PecaComum.
	private int calcularCapturasComum(Posicao origem, Peca peca, List<Posicao> jaCapturadas, Posicao posicaoOriginal) {

		int maior = 0;

		int[][] direcoes = { { 2, 2 }, { 2, -2 }, { -2, 2 }, { -2, -2 } };

		for (int[] direcao : direcoes) {

			int destinoLinha = origem.getLinha() + direcao[0];

			int destinoColuna = origem.getColuna() + direcao[1];

			if (!estaDentro(destinoLinha, destinoColuna)) {

				continue;
			}

			int meioLinha = origem.getLinha() + direcao[0] / 2;

			int meioColuna = origem.getColuna() + direcao[1] / 2;

			Posicao meio = new Posicao(meioLinha, meioColuna);

			Posicao destino = new Posicao(destinoLinha, destinoColuna);

			Peca adversaria = getPecaSimulada(meio, posicaoOriginal);

			if (adversaria == null) {

				continue;
			}

			if (adversaria.getCor().equals(peca.getCor())) {

				continue;
			}

			if (jaCapturadas.contains(meio)) {

				continue;
			}

			if (!estaVaziaSimulada(destino, posicaoOriginal)) {

				continue;
			}

			List<Posicao> novasCapturas = new ArrayList<>(jaCapturadas);

			novasCapturas.add(meio);

			int quantidade = 1 + calcularMaximoCapturas(destino, peca, novasCapturas, posicaoOriginal);

			if (quantidade > maior) {

				maior = quantidade;
			}
		}

		return maior;
	}

	// Calcula recursivamente
	// as capturas possíveis para uma Dama.
	private int calcularCapturasDama(Posicao origem, Peca peca, List<Posicao> jaCapturadas, Posicao posicaoOriginal) {

		int maior = 0;

		int[][] direcoes = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

		for (int[] direcao : direcoes) {

			int linha = origem.getLinha() + direcao[0];

			int coluna = origem.getColuna() + direcao[1];

			Posicao adversaria = null;

			while (estaDentro(linha, coluna)) {

				Posicao atual = new Posicao(linha, coluna);

				Peca encontrada = getPecaSimulada(atual, posicaoOriginal);

				if (encontrada == null) {

					// Depois de encontrar
					// uma adversária,
					// cada casa vazia seguinte
					// é um possível destino.
					if (adversaria != null) {

						List<Posicao> novasCapturas = new ArrayList<>(jaCapturadas);

						novasCapturas.add(adversaria);

						int quantidade = 1 + calcularMaximoCapturas(atual, peca, novasCapturas, posicaoOriginal);

						if (quantidade > maior) {

							maior = quantidade;
						}
					}

					linha += direcao[0];
					coluna += direcao[1];

					continue;
				}

				// Uma peça já capturada
				// permanece bloqueando.
				if (jaCapturadas.contains(atual)) {

					break;
				}

				// Peça própria bloqueia
				// o caminho.
				if (encontrada.getCor().equals(peca.getCor())) {

					break;
				}

				// Uma segunda peça
				// também bloqueia.
				if (adversaria != null) {

					break;
				}

				adversaria = atual;

				linha += direcao[0];
				coluna += direcao[1];
			}
		}

		return maior;
	}

	// Durante a simulação,
	// a posição inicial da peça deve
	// ser considerada vazia depois que ela se move.
	private Peca getPecaSimulada(Posicao posicao, Posicao posicaoOriginal) {

		if (posicao.equals(posicaoOriginal)) {

			return null;
		}

		return tabuleiro.getPeca(posicao);
	}

	// Verifica se uma posição está vazia
	// durante uma simulação de captura.
	private boolean estaVaziaSimulada(Posicao posicao, Posicao posicaoOriginal) {

		return getPecaSimulada(posicao, posicaoOriginal) == null;
	}

	// Remove definitivamente
	// todas as peças capturadas
	// depois que a sequência termina.
	private void finalizarCapturas() {

		for (Posicao posicao : capturasPendentes) {

			tabuleiro.removerPeca(posicao);
		}

		capturasPendentes.clear();

		pecaEmCaptura = null;
	}

	// Promove uma PecaComum para Dama
	// quando ela termina a jogada
	// na última linha.
	private void promoverSeNecessario(Posicao posicao, Peca peca) {

		if (!(peca instanceof PecaComum)) {

			return;
		}

		boolean promoverBranca = peca.getCor().equals("BRANCAS") && posicao.getLinha() == 7;

		boolean promoverPreta = peca.getCor().equals("PRETAS") && posicao.getLinha() == 0;

		if (promoverBranca || promoverPreta) {

			tabuleiro.colocarPeca(posicao, new Dama(peca.getCor()));
		}
	}

	/*
	 * CORREÇÃO DO TM19
	 *
	 * Verifica se o jogador responsável pelo turno atual ainda possui peças e algum
	 * movimento legal disponível.
	 *
	 * Este método é chamado depois de carregar uma partida.
	 */
	public void verificarSituacaoDoTurnoAtual() {

		// Se a partida já terminou,
		// não é necessário alterar nada.
		if (!status.equals("EM_ANDAMENTO")) {

			return;
		}

		/*
		 * Se o jogador da vez não possui peças ou não possui qualquer movimento legal,
		 * o adversário vence.
		 */
		if (!possuiPecas(turno) || !possuiMovimentoLegal(turno)) {

			if (turno.equals("BRANCAS")) {

				status = "VITORIA_PRETAS";

			} else {

				status = "VITORIA_BRANCAS";
			}
		}
	}

	// Verifica se o adversário ficou sem peças
	// ou sem movimentos legais.
	private void verificarVitoria() {

		String adversario;

		if (turno.equals("BRANCAS")) {

			adversario = "PRETAS";

		} else {

			adversario = "BRANCAS";
		}

		if (!possuiPecas(adversario) || !possuiMovimentoLegal(adversario)) {

			if (turno.equals("BRANCAS")) {

				status = "VITORIA_BRANCAS";

			} else {

				status = "VITORIA_PRETAS";
			}
		}
	}

	// Verifica se determinada cor
	// ainda possui peças.
	private boolean possuiPecas(String cor) {

		for (int linha = 0; linha < 8; linha++) {

			for (int coluna = 0; coluna < 8; coluna++) {

				Posicao posicao = new Posicao(linha, coluna);

				Peca peca = tabuleiro.getPeca(posicao);

				if (peca != null && peca.getCor().equals(cor)) {

					return true;
				}
			}
		}

		return false;
	}

	// Verifica se a cor ainda possui
	// algum movimento legal.
	private boolean possuiMovimentoLegal(String cor) {

		// Se existe captura,
		// existe movimento legal.
		if (maiorCapturaDaCor(cor, new ArrayList<>()) > 0) {

			return true;
		}

		for (int linha = 0; linha < 8; linha++) {

			for (int coluna = 0; coluna < 8; coluna++) {

				Posicao posicao = new Posicao(linha, coluna);

				Peca peca = tabuleiro.getPeca(posicao);

				if (peca == null || !peca.getCor().equals(cor)) {

					continue;
				}

				if (possuiMovimentoNormal(posicao, peca)) {

					return true;
				}
			}
		}

		return false;
	}

	// Verifica se uma peça possui
	// algum movimento normal.
	private boolean possuiMovimentoNormal(Posicao origem, Peca peca) {

		if (peca instanceof PecaComum) {

			int direcao;

			if (peca.getCor().equals("BRANCAS")) {

				direcao = 1;

			} else {

				direcao = -1;
			}

			int novaLinha = origem.getLinha() + direcao;

			int[] colunas = { -1, 1 };

			for (int diferencaColuna : colunas) {

				int novaColuna = origem.getColuna() + diferencaColuna;

				if (estaDentro(novaLinha, novaColuna)) {

					Posicao destino = new Posicao(novaLinha, novaColuna);

					if (tabuleiro.estaVazia(destino)) {

						return true;
					}
				}
			}

			return false;
		}

		if (peca instanceof Dama) {

			int[][] direcoes = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

			// Se a primeira casa
			// de uma diagonal estiver vazia,
			// a Dama possui pelo menos
			// um movimento normal.
			for (int[] direcao : direcoes) {

				int linha = origem.getLinha() + direcao[0];

				int coluna = origem.getColuna() + direcao[1];

				if (estaDentro(linha, coluna)) {

					Posicao destino = new Posicao(linha, coluna);

					if (tabuleiro.estaVazia(destino)) {

						return true;
					}
				}
			}
		}

		return false;
	}

	// Verifica se linha e coluna
	// estão dentro dos limites
	// do tabuleiro 8x8.
	private boolean estaDentro(int linha, int coluna) {

		return linha >= 0 && linha < 8 && coluna >= 0 && coluna < 8;
	}

	// Alterna o turno entre
	// BRANCAS e PRETAS.
	private void trocarTurno() {

		if (turno.equals("BRANCAS")) {

			turno = "PRETAS";

		} else {

			turno = "BRANCAS";
		}
	}

	// Informa se a partida pode ser salva.
	// Durante uma captura múltipla o salvamento é bloqueado.
	public boolean podeSalvar() {

		return pecaEmCaptura == null;
	}

	// Restaura turno e status ao carregar uma partida salva.
	public void restaurarEstado(String turnoSalvo, String statusSalvo) {

		if (turnoSalvo == null || (!turnoSalvo.equals("BRANCAS") && !turnoSalvo.equals("PRETAS"))) {

			throw new IllegalArgumentException("Turno inválido.");
		}

		if (statusSalvo == null || (!statusSalvo.equals("EM_ANDAMENTO") && !statusSalvo.equals("VITORIA_BRANCAS")
				&& !statusSalvo.equals("VITORIA_PRETAS"))) {

			throw new IllegalArgumentException("Status inválido.");
		}

		turno = turnoSalvo;
		status = statusSalvo;

		// Uma partida salva nunca deve voltar
		// no meio de uma captura múltipla.
		pecaEmCaptura = null;
		capturasPendentes.clear();
	}
}
package aplicacao;

import java.io.IOException;
import java.util.Scanner;

import jogo.Movimento;
import jogo.Partida;
import modelo.Posicao;
import persistencia.PersistenciaArquivo;
import persistencia.PersistenciaPartida;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		PersistenciaPartida persistencia = new PersistenciaArquivo();
		Partida partida = null;

		mostrarCabecalho();

		boolean executando = true;

		while (executando) {

			System.out.print("> ");
			String entrada = scanner.nextLine().trim();

			if (entrada.isEmpty()) {
				continue;
			}

			String[] partes = entrada.split("\\s+");
			String comando = partes[0].toUpperCase();

			try {

				if (comando.equals("NOVO")) {

					if (partes.length != 3) {
						System.out.println("Uso: NOVO <jogador1> <jogador2>");
						continue;
					}

					partida = new Partida(partes[1], partes[2]);

					System.out.println();
					System.out.println("Partida iniciada!");
					System.out.println();
					System.out.println("Jogador atual: " + partida.getJogadorBrancas().getNome() + " (Brancas)");

					mostrarTabuleiro(partida);

				} else if (comando.equals("MOVER")) {

					garantirPartida(partida);

					if (partes.length != 3) {
						System.out.println("Uso: MOVER <origem> <destino>");
						continue;
					}

					Posicao origem = Posicao.deCoordenada(partes[1]);
					Posicao destino = Posicao.deCoordenada(partes[2]);

					Movimento movimento = new Movimento(origem, destino);

					partida.mover(movimento);

					System.out.println();
					System.out.println("Movimento realizado!");

					mostrarTabuleiro(partida);

					if (partida.getStatus().equals("EM_ANDAMENTO")) {
						mostrarTurno(partida);
					} else {
						System.out.println("STATUS=" + partida.getStatus());
					}

				} else if (comando.equals("ESTADO")) {

					garantirPartida(partida);

					mostrarTabuleiro(partida);

					System.out.println("TURNO=" + partida.getTurno());
					System.out.println();
					System.out.println("STATUS=" + partida.getStatus());

				} else if (comando.equals("SALVAR")) {

					garantirPartida(partida);

					if (partes.length != 2) {
						System.out.println("Uso: SALVAR <arquivo>");
						continue;
					}

					persistencia.salvar(partida, partes[1]);

					System.out.println();
					System.out.println("Partida salva com sucesso!");

				} else if (comando.equals("CARREGAR")) {

					if (partes.length != 2) {
						System.out.println("Uso: CARREGAR <arquivo>");
						continue;
					}

					/*
					 * A partida é carregada primeiro para uma variável temporária. Assim, se o
					 * arquivo apresentar algum problema, a partida atual não é perdida.
					 */
					Partida carregada = persistencia.carregar(partes[1]);

					/*
					 * CORREÇÃO DO TM19
					 *
					 * Ao carregar uma partida, verifica imediatamente se o jogador do turno atual
					 * ainda possui algum movimento legal.
					 */
					carregada.verificarSituacaoDoTurnoAtual();

					/*
					 * Somente depois de carregar e validar corretamente a nova partida substitui a
					 * partida anterior.
					 */
					partida = carregada;

					System.out.println();
					System.out.println("Partida carregada com sucesso!");

					mostrarTabuleiro(partida);

					if (partida.getStatus().equals("EM_ANDAMENTO")) {

						mostrarTurno(partida);

					} else {

						System.out.println("TURNO=" + partida.getTurno());
						System.out.println("STATUS=" + partida.getStatus());
					}

				} else if (comando.equals("SAIR")) {

					executando = false;

					System.out.println();
					System.out.println("Programa encerrado.");

				} else {

					System.out.println();
					System.out.println("Comando desconhecido.");
				}

			} catch (IllegalArgumentException e) {

				System.out.println();
				System.out.println("Erro: " + e.getMessage());

			} catch (IllegalStateException e) {

				System.out.println();
				System.out.println("Erro: " + e.getMessage());

			} catch (IOException e) {

				System.out.println();
				System.out.println("Erro de arquivo: " + e.getMessage());
			}
		}

		scanner.close();
	}

	// Garante que exista uma partida antes de utilizar
	// comandos como MOVER, ESTADO e SALVAR.
	private static void garantirPartida(Partida partida) {

		if (partida == null) {

			throw new IllegalStateException("Nenhuma partida em andamento.");
		}
	}

	// Exibe o cabeçalho inicial do programa.
	private static void mostrarCabecalho() {

		System.out.println("=================================");
		System.out.println();
		System.out.println("          JOGO DE DAMAS");
		System.out.println();
		System.out.println("=================================");
		System.out.println();

		System.out.println("Comandos disponíveis:");
		System.out.println();

		System.out.println("NOVO <jogador1> <jogador2>");
		System.out.println();

		System.out.println("MOVER <origem> <destino>");
		System.out.println();

		System.out.println("ESTADO");
		System.out.println();

		System.out.println("SALVAR <arquivo>");
		System.out.println();

		System.out.println("CARREGAR <arquivo>");
		System.out.println();

		System.out.println("SAIR");
		System.out.println();
	}

	// Exibe o tabuleiro utilizando o método toString()
	// implementado na classe Tabuleiro.
	private static void mostrarTabuleiro(Partida partida) {

		System.out.println(partida.getTabuleiro());
	}

	// Exibe o jogador responsável pelo turno atual.
	private static void mostrarTurno(Partida partida) {

		if (partida.getTurno().equals("BRANCAS")) {

			System.out.println("Turno: BRANCAS - " + partida.getJogadorBrancas().getNome());

		} else {

			System.out.println("Turno: PRETAS - " + partida.getJogadorPretas().getNome());
		}

		System.out.println();
	}
}
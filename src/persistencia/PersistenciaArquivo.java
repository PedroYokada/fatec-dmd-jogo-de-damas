package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import jogo.Partida;
import jogo.Tabuleiro;

import modelo.Dama;
import modelo.Peca;
import modelo.PecaComum;
import modelo.Posicao;

// Responsável por salvar e carregar partidas em arquivos de texto.
public class PersistenciaArquivo implements PersistenciaPartida {

	@Override
	public void salvar(Partida partida, String caminho) throws IOException {

		if (partida == null) {
			throw new IOException("Não existe partida para salvar.");
		}

		// Não permite salvar no meio de uma captura múltipla.
		if (!partida.podeSalvar()) {
			throw new IOException("Termine a sequência de capturas antes de salvar.");
		}

		try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminho))) {

			// Salva os nomes dos jogadores.
			arquivo.write(partida.getJogadorBrancas().getNome());
			arquivo.newLine();

			arquivo.write(partida.getJogadorPretas().getNome());
			arquivo.newLine();

			// Salva turno e status.
			arquivo.write(partida.getTurno());
			arquivo.newLine();

			arquivo.write(partida.getStatus());
			arquivo.newLine();

			Tabuleiro tabuleiro = partida.getTabuleiro();

			// Salva as 8 linhas do tabuleiro.
			for (int linha = 0; linha < 8; linha++) {

				for (int coluna = 0; coluna < 8; coluna++) {

					Posicao posicao = new Posicao(linha, coluna);

					Peca peca = tabuleiro.getPeca(posicao);

					if (peca == null) {

						arquivo.write(".");

					} else {

						boolean branca = peca.getCor().equals("BRANCAS");

						if (peca instanceof Dama) {

							arquivo.write(branca ? "B" : "P");

						} else {

							arquivo.write(branca ? "b" : "p");
						}
					}
				}

				arquivo.newLine();
			}
		}
	}

	@Override
	public Partida carregar(String caminho) throws IOException {

		try (BufferedReader arquivo = new BufferedReader(new FileReader(caminho))) {

			// Recupera os dados principais.
			String nomeBrancas = arquivo.readLine();

			String nomePretas = arquivo.readLine();

			String turno = arquivo.readLine();

			String status = arquivo.readLine();

			// Verifica se o cabeçalho do arquivo existe.
			if (nomeBrancas == null || nomePretas == null || turno == null || status == null) {

				throw new IOException("Arquivo de partida incompleto.");
			}

			// Cria uma nova partida temporária.
			Partida partida = new Partida(nomeBrancas, nomePretas);

			Tabuleiro tabuleiro = partida.getTabuleiro();

			// Remove as peças iniciais.
			tabuleiro.limpar();

			// Lê as oito linhas do tabuleiro.
			for (int linha = 0; linha < 8; linha++) {

				String linhaSalva = arquivo.readLine();

				if (linhaSalva == null || linhaSalva.length() != 8) {

					throw new IOException("Tabuleiro salvo inválido.");
				}

				for (int coluna = 0; coluna < 8; coluna++) {

					char simbolo = linhaSalva.charAt(coluna);

					Posicao posicao = new Posicao(linha, coluna);

					switch (simbolo) {

					case 'b':

						tabuleiro.colocarPeca(posicao, new PecaComum("BRANCAS"));
						break;

					case 'p':

						tabuleiro.colocarPeca(posicao, new PecaComum("PRETAS"));
						break;

					case 'B':

						tabuleiro.colocarPeca(posicao, new Dama("BRANCAS"));
						break;

					case 'P':

						tabuleiro.colocarPeca(posicao, new Dama("PRETAS"));
						break;

					case '.':

						// Casa vazia.
						break;

					default:

						throw new IOException("Símbolo inválido no arquivo.");
					}
				}
			}

			// Restaura turno e status.
			try {

				partida.restaurarEstado(turno, status);

			} catch (IllegalArgumentException e) {

				throw new IOException("Estado salvo inválido: " + e.getMessage(), e);
			}

			return partida;
		}
	}
}
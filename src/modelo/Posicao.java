package modelo;

// Importa Objects, utilizado na geração do hashCode.
import java.util.Objects;

public class Posicao {

	// Atributos encapsulados e imutáveis que representam
	// a linha e a coluna da posição.
	private final int linha;
	private final int coluna;

	public Posicao(int linha, int coluna) {

		// Valida se os índices estão dentro dos limites da matriz 8x8.
		if (linha < 0 || linha > 7 || coluna < 0 || coluna > 7) {
			throw new IllegalArgumentException("Posição inválida.");
		}

		// Inicializa os atributos da posição.
		this.linha = linha;
		this.coluna = coluna;
	}

	// Getter que retorna o índice da linha.
	public int getLinha() {
		return linha;
	}

	// Getter que retorna o índice da coluna.
	public int getColuna() {
		return coluna;
	}

	// Converte uma coordenada textual, como "A3",
	// para um objeto Posicao com índices de matriz.
	public static Posicao deCoordenada(String coordenada) {

		// Impede o processamento de uma referência nula.
		if (coordenada == null) {
			throw new IllegalArgumentException("Coordenada inválida.");
		}

		// Remove espaços nas extremidades e padroniza em letras maiúsculas.
		coordenada = coordenada.trim().toUpperCase();

		// A coordenada deve possuir exatamente dois caracteres.
		if (coordenada.length() != 2) {
			throw new IllegalArgumentException("Coordenada inválida.");
		}

		// Separa a coluna e a linha utilizando charAt().
		char letraColuna = coordenada.charAt(0);
		char numeroLinha = coordenada.charAt(1);

		// Valida os limites permitidos: colunas A-H e linhas 1-8.
		if (letraColuna < 'A' || letraColuna > 'H' || numeroLinha < '1' || numeroLinha > '8') {

			throw new IllegalArgumentException("Coordenada inválida.");
		}

		// Converte os caracteres da coordenada em índices de 0 a 7.
		int coluna = letraColuna - 'A';
		int linha = numeroLinha - '1';

		return new Posicao(linha, coluna);
	}

	// Converte os índices internos da matriz novamente
	// para uma coordenada textual, como "A3".
	public String paraCoordenada() {

		char letraColuna = (char) ('A' + coluna);
		char numeroLinha = (char) ('1' + linha);

		return "" + letraColuna + numeroLinha;
	}

	// Sobrescreve toString() para representar a posição
	// utilizando a coordenada do tabuleiro.
	@Override
	public String toString() {
		return paraCoordenada();
	}

	// Sobrescreve equals() para realizar comparação lógica
	// entre dois objetos Posicao.
	@Override
	public boolean equals(Object obj) {

		// Verifica se as duas referências apontam para o mesmo objeto.
		if (this == obj) {
			return true;
		}

		// Verifica se o objeto recebido é uma instância de Posicao.
		if (!(obj instanceof Posicao)) {
			return false;
		}

		// Realiza o casting para permitir acesso aos atributos de Posicao.
		Posicao outra = (Posicao) obj;

		// Duas posições são iguais quando possuem
		// a mesma linha e a mesma coluna.
		return linha == outra.linha && coluna == outra.coluna;
	}

	// Sobrescreve hashCode() utilizando linha e coluna.
	// Objetos considerados iguais por equals() devem possuir
	// o mesmo valor de hashCode.
	@Override
	public int hashCode() {
		return Objects.hash(linha, coluna);
	}
}
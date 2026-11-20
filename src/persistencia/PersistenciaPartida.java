package persistencia;

// Importa IOException para declarar erros de entrada e saída.
import java.io.IOException;

// Importa a classe Partida.
import jogo.Partida;

// Define o contrato de persistência.
public interface PersistenciaPartida {

	void salvar(Partida partida, String caminho) throws IOException;

	Partida carregar(String caminho) throws IOException;

}
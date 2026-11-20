# Relatório de Testes Manuais

| Teste | Resultado obtido | Situação |
|---|---|---|
| TM01 — Inicialização do tabuleiro | Ao executar `NOVO Ana Pedro` e `ESTADO`, o tabuleiro foi inicializado corretamente, com 12 peças de cada cor, turno das BRANCAS e status `EM_ANDAMENTO`. | APROVADO |
| TM02 — Movimentação válida | O comando `MOVER A3 B4` moveu corretamente a peça branca para B4, deixou A3 vazia e passou o turno para as PRETAS. | APROVADO |
| TM03 — Movimento horizontal inválido | O comando `MOVER A3 C3` foi rejeitado. O tabuleiro permaneceu inalterado e o turno continuou com as BRANCAS. | APROVADO |
| TM04 — Movimento para posição ocupada | Ao executar `MOVER A3 C1`, o sistema rejeitou corretamente o movimento para uma posição ocupada. Nenhuma peça foi movimentada e o turno permaneceu inalterado. | APROVADO |
| TM05 — Movimentação de peça adversária | A tentativa de movimentar uma peça preta durante o turno das BRANCAS foi rejeitada, sem alterações no tabuleiro. | APROVADO |
| TM06 — Coordenada inválida | O sistema rejeitou corretamente uma movimentação utilizando a coordenada inválida `Z9` e permaneceu funcionando normalmente. | APROVADO |
| TM07 — Alternância dos turnos | Após `MOVER A3 B4`, o turno passou para as PRETAS. Depois de `MOVER B6 A5`, retornou corretamente para as BRANCAS. | APROVADO |
| TM08 — Duas jogadas consecutivas do mesmo jogador | Após `MOVER A3 B4`, o turno passou para as PRETAS. A tentativa seguinte com `MOVER C3 D4` foi rejeitada, mantendo o turno e o tabuleiro inalterados. | APROVADO |
| TM09 — Captura simples | Foi preparado o cenário com uma peça branca em C3 e uma preta em D4. Ao executar `MOVER C3 E5`, a peça preta foi capturada e a branca terminou em E5. | APROVADO |
| TM10 — Captura obrigatória | Com uma captura disponível em C3 sobre D4, a tentativa `MOVER C3 B4` foi rejeitada e o sistema informou a existência de captura obrigatória. | APROVADO |
| TM11 — Captura múltipla | No cenário preparado, foram executados `MOVER C3 E5` e `MOVER E5 G7`. A mesma peça realizou as duas capturas antes da troca de turno. | APROVADO |
| TM12 — Captura para trás | No cenário preparado, a peça branca em E5 executou `MOVER E5 C3`, capturando corretamente a peça preta em D4. | APROVADO |
| TM13 — Prioridade da maior captura | O sistema rejeitou a sequência com apenas uma captura e aceitou corretamente a sequência que realizava duas capturas consecutivas. | APROVADO |
| TM14 — Promoção a dama | Ao executar `MOVER A7 B8`, a peça branca alcançou a última linha e foi promovida corretamente a dama, passando a ser representada por `B`. | APROVADO |
| TM15 — Movimentação longa da dama | A dama branca em C3 executou `MOVER C3 F6`, percorrendo corretamente várias casas na diagonal e terminando em F6. | APROVADO |
| TM16 — Movimentação da dama para trás | A dama branca em F6 executou `MOVER F6 C3`, realizando corretamente o movimento longo para trás. | APROVADO |
| TM17 — Dama bloqueada por peça própria | Ao executar `MOVER C3 F6` com uma peça branca em E5, o sistema rejeitou corretamente o movimento, impedindo a dama de atravessar uma peça da própria cor. | APROVADO |
| TM18 — Vitória por eliminação | Foi carregado um cenário com a última peça preta em D4. Após `MOVER C3 E5`, a peça foi capturada e o status passou para `VITORIA_BRANCAS`. | APROVADO |
| TM19 — Vitória por ausência de movimentos | Foi carregado um cenário em que as PRETAS ainda possuíam uma peça, mas não tinham movimentos legais. O sistema encerrou a partida e declarou vitória das BRANCAS. | APROVADO |
| TM20 — Salvar uma partida | Após `NOVO Ana Pedro` e `MOVER A3 B4`, o comando `SALVAR partida.txt` armazenou corretamente jogadores, tabuleiro, turno das PRETAS e status da partida. | APROVADO |
| TM21 — Carregar uma partida | Após reiniciar o programa e executar `CARREGAR partida.txt`, a partida do TM20 foi restaurada corretamente, mantendo B4 ocupada, A3 vazia e turno das PRETAS. | APROVADO |
| TM22 — Arquivo inexistente | O comando `CARREGAR inexistente.txt` apresentou uma mensagem de erro, sem encerrar a aplicação ou carregar uma partida inválida. | APROVADO |
| TM23 — Arquivo inválido | Ao executar `CARREGAR invalido.txt`, o sistema identificou o conteúdo inválido, exibiu uma mensagem de erro e preservou o estado válido da partida anterior. | APROVADO |

# Testes Adicionais de Robustez

| Teste | Resultado obtido | Situação |
|---|---|---|
| TA01 — Comando desconhecido | Ao inserir um comando desconhecido, o sistema apresentou uma mensagem de erro e permaneceu em execução normalmente. | APROVADO |
| TA02 — Comando MOVER sem coordenadas | Ao executar `MOVER` sem informar origem e destino, o sistema rejeitou a entrada e continuou funcionando normalmente. | APROVADO |
| TA03 — Coordenada de casa clara | Ao executar `MOVER A3 A4`, o sistema rejeitou corretamente o movimento, mantendo o tabuleiro e o turno inalterados. | APROVADO |
| TA04 — Origem sem peça | Ao executar `MOVER A4 B5`, o sistema identificou que não havia peça na posição de origem e rejeitou o movimento. | APROVADO |
| TA05 — Movimento para trás com peça comum | Ao executar `MOVER C3 B2`, o sistema rejeitou corretamente o movimento para trás de uma peça comum sem captura. | APROVADO |
| TA06 — Movimento após encerramento da partida | Após a partida atingir o status `VITORIA_BRANCAS`, uma nova tentativa de movimentação foi rejeitada corretamente. | APROVADO |
| TA07 — Arquivo vazio | Ao executar `CARREGAR testes/invalidos/vazio.txt`, o sistema informou que o arquivo estava incompleto, rejeitou o carregamento e preservou o estado anterior da partida. | APROVADO |
| TA08 — Comandos em letras minúsculas | Os comandos `novo`, `mover` e `estado` escritos em letras minúsculas foram reconhecidos e executados corretamente. | APROVADO |
| TA09 — Salvamento durante captura múltipla | Durante uma sequência de captura múltipla, a tentativa de salvar a partida foi rejeitada. O estado permaneceu preservado e a sequência pôde ser concluída normalmente. | APROVADO |
| TA10 — Nova partida após encerramento | Após o encerramento de uma partida, o comando `NOVO Ana Pedro` iniciou corretamente uma nova partida, restaurando o tabuleiro inicial, o turno das BRANCAS e o status `EM_ANDAMENTO`. | APROVADO |

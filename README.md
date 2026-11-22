# Jogo de Damas em Java

Projeto final desenvolvido para a disciplina de **Programação Orientada a Objetos**, utilizando Java e execução pelo console do Eclipse IDE.

O sistema implementa um jogo de damas para dois jogadores, incluindo movimentação de peças comuns e damas, capturas, captura obrigatória, captura múltipla, prioridade da maior captura, promoção, alternância de turnos, identificação do vencedor e persistência de partidas em arquivos de texto.

---

# Integrantes

- Isabella Felix Souza
- Lucas Ferreira da Silva
- Maria Júlia Ribeiro de Carvalho
- Mateus Nunes do Nascimento
- Pedro Henrique Leite Yokada
- Walisson da Cruz Silva

---

# Tecnologias utilizadas

- **Java:** 26.0.2
- **IDE:** Eclipse IDE
- **Persistência:** arquivos de texto (`.txt`)
- **Interface:** Console
- **Bibliotecas externas:** nenhuma

O projeto utiliza apenas recursos da biblioteca padrão do Java.

---

# Estrutura do projeto

```text
JogoDeDama/
│
├── src/
│   ├── aplicacao/
│   │   └── Main.java
│   │
│   ├── jogo/
│   │   ├── Movimento.java
│   │   ├── Partida.java
│   │   └── Tabuleiro.java
│   │
│   ├── modelo/
│   │   ├── Dama.java
│   │   ├── Jogador.java
│   │   ├── Peca.java
│   │   ├── PecaComum.java
│   │   └── Posicao.java
│   │
│   └── persistencia/
│       ├── PersistenciaArquivo.java
│       └── PersistenciaPartida.java
│
├── partidas/
│   └── partida.txt
│
├── testes/
│   ├── cenarios/
│   │   ├── tm09.txt
│   │   ├── tm10.txt
│   │   ├── tm11.txt
│   │   ├── tm12.txt
│   │   ├── tm13.txt
│   │   ├── tm14.txt
│   │   ├── tm15.txt
│   │   ├── tm16.txt
│   │   ├── tm17.txt
│   │   ├── tm18.txt
│   │   └── tm19.txt
│   │
│   ├── invalidos/
│   │   ├── invalido.txt
│   │   └── vazio.txt
│   │
│   └── relatorio-testes.md
│
└── README.md
```

---

# Como importar o projeto no Eclipse

1. Abra o **Eclipse IDE**.
2. Acesse `File > Import`.
3. Selecione `General > Existing Projects into Workspace`.
4. Clique em `Next`.
5. Selecione a pasta onde o projeto foi extraído.
6. Marque o projeto `JogoDeDama`.
7. Clique em `Finish`.

Caso necessário, configure o Java em:

```text
Window > Preferences > Java > Installed JREs
```

O projeto foi desenvolvido utilizando:

```text
Java 26.0.2
```

---

# Como executar

No Eclipse:

1. Abra `src`.
2. Abra o pacote `aplicacao`.
3. Localize `Main.java`.
4. Clique com o botão direito.
5. Selecione:

```text
Run As > Java Application
```

Toda a interação acontece pelo **Console do Eclipse**.

---

# Comandos disponíveis

## Nova partida

```text
NOVO Ana Pedro
```

---

## Movimentar uma peça

```text
MOVER A3 B4
```

---

## Consultar estado

```text
ESTADO
```

---

## Salvar partida

```text
SALVAR partidas/partida.txt
```

---

## Carregar partida

```text
CARREGAR partidas/partida.txt
```

---

## Encerrar

```text
SAIR
```

Os comandos também podem ser escritos em letras minúsculas:

```text
novo Ana Pedro
mover A3 B4
estado
```

---

# Representação do tabuleiro

| Símbolo | Significado |
|---|---|
| `b` | Peça branca comum |
| `p` | Peça preta comum |
| `B` | Dama branca |
| `P` | Dama preta |
| `.` | Posição vazia |

As coordenadas utilizam o padrão:

```text
A1 até H8
```

---

# Regras implementadas

O sistema implementa:

- inicialização com 24 peças;
- movimentação diagonal;
- validação de movimentos;
- alternância de turnos;
- impedimento de movimentação de peças adversárias;
- captura simples;
- captura para frente e para trás;
- captura obrigatória;
- captura múltipla;
- prioridade da maior captura;
- promoção para dama;
- movimentação longa da dama;
- bloqueio da dama por outras peças;
- vitória por eliminação;
- vitória por ausência de movimentos legais;
- salvamento de partidas;
- carregamento de partidas;
- tratamento de arquivos inexistentes e inválidos;
- tratamento de entradas incorretas.

---

# Principais classes

## Main

Responsável pela inicialização da aplicação, leitura dos comandos e interação com o usuário pelo console.

---

## Partida

Controla:

- jogadores;
- turno;
- status;
- movimentações;
- capturas;
- captura obrigatória;
- captura múltipla;
- maior sequência de capturas;
- promoção;
- condições de vitória.

---

## Tabuleiro

Representa o tabuleiro 8x8 e gerencia a posição das peças.

---

## Movimento

Representa uma tentativa de movimentação entre uma posição de origem e uma posição de destino.

---

## Posicao

Representa as coordenadas utilizadas no tabuleiro.

Exemplos:

```text
A3
B4
E5
```

---

## Jogador

Representa um jogador e armazena seu nome e cor.

---

## Peca

Representa o conceito geral de uma peça do jogo.

---

## PecaComum

Representa uma peça comum antes da promoção.

---

## Dama

Representa uma peça promovida, capaz de realizar movimentos longos pelas diagonais.

---

## PersistenciaPartida

Define as operações necessárias para salvar e carregar partidas.

---

## PersistenciaArquivo

Implementa a persistência utilizando arquivos de texto.

---

# Programação Orientada a Objetos

## Encapsulamento

Os atributos internos são protegidos e manipulados por métodos responsáveis pelas regras do sistema.

Por exemplo, o turno e o estado da partida não são modificados diretamente pelo usuário, mas pelos métodos da classe `Partida`.

---

## Herança

Os diferentes tipos de peças compartilham características comuns através da estrutura baseada em `Peca`.

`PecaComum` e `Dama` representam especializações de uma peça.

---

## Polimorfismo

Objetos de diferentes tipos de peça podem ser manipulados através de uma referência comum de `Peca`, permitindo que o sistema aplique regras específicas de acordo com o tipo concreto do objeto.

---

## Abstração

Cada classe representa uma responsabilidade do domínio do jogo.

Exemplos:

- `Jogador`: participante;
- `Posicao`: coordenada;
- `Movimento`: jogada;
- `Peca`: peça;
- `Tabuleiro`: tabuleiro;
- `Partida`: regras do jogo;
- `PersistenciaPartida`: persistência.

---

# Persistência

O formato dos arquivos é:

```text
Nome do jogador das brancas
Nome do jogador das pretas
Turno
Status
Linha 1 do tabuleiro
Linha 2 do tabuleiro
Linha 3 do tabuleiro
Linha 4 do tabuleiro
Linha 5 do tabuleiro
Linha 6 do tabuleiro
Linha 7 do tabuleiro
Linha 8 do tabuleiro
```

Exemplo:

```text
Ana
Pedro
PRETAS
EM_ANDAMENTO
b.b.b.b.
.b.b.b.b
..b.b.b.
.b......
........
.p.p.p.p
p.p.p.p.
.p.p.p.p
```

---

# Diretórios importantes

## Partidas

Copiar:

```text
partidas/
```

Arquivo principal:

```text
partidas/partida.txt
```

---

## Cenários de testes

Copiar:

```text
testes/cenarios/
```

Arquivos:

```text
testes/cenarios/tm09.txt
testes/cenarios/tm10.txt
testes/cenarios/tm11.txt
testes/cenarios/tm12.txt
testes/cenarios/tm13.txt
testes/cenarios/tm14.txt
testes/cenarios/tm15.txt
testes/cenarios/tm16.txt
testes/cenarios/tm17.txt
testes/cenarios/tm18.txt
testes/cenarios/tm19.txt
```

---

## Arquivos inválidos

Copiar:

```text
testes/invalidos/
```

Arquivos:

```text
testes/invalidos/invalido.txt
testes/invalidos/vazio.txt
```

---

# Comandos para testar todos os arquivos

## TM09 — Captura simples

Carregar:

```text
CARREGAR testes/cenarios/tm09.txt
```

Executar:

```text
MOVER C3 E5
ESTADO
```

Resultado esperado:

- peça branca termina em E5;
- peça preta em D4 é capturada.

---

## TM10 — Captura obrigatória

Carregar:

```text
CARREGAR testes/cenarios/tm10.txt
```

Executar:

```text
MOVER C3 B4
ESTADO
```

Resultado esperado:

- movimento rejeitado;
- sistema informa que existe captura obrigatória;
- peça permanece em C3.

---

## TM11 — Captura múltipla

Carregar:

```text
CARREGAR testes/cenarios/tm11.txt
```

Executar:

```text
MOVER C3 E5
MOVER E5 G7
ESTADO
```

Resultado esperado:

- primeira captura em D4;
- segunda captura em F6;
- peça termina em G7.

---

## TM12 — Captura para trás

Carregar:

```text
CARREGAR testes/cenarios/tm12.txt
```

Executar:

```text
MOVER E5 C3
ESTADO
```

Resultado esperado:

- peça preta em D4 capturada;
- peça branca termina em C3.

---

## TM13 — Prioridade da maior captura

Carregar:

```text
CARREGAR testes/cenarios/tm13.txt
```

Primeiro tente a sequência menor:

```text
MOVER C3 A5
```

Ela deve ser rejeitada.

Depois execute a maior sequência:

```text
MOVER C3 E5
MOVER E5 G7
ESTADO
```

Resultado esperado:

- sequência de uma captura rejeitada;
- sequência de duas capturas aceita.

---

## TM14 — Promoção para dama

Carregar:

```text
CARREGAR testes/cenarios/tm14.txt
```

Executar:

```text
MOVER A7 B8
ESTADO
```

Resultado esperado:

- peça branca chega em B8;
- peça é promovida;
- passa a ser representada por `B`.

---

## TM15 — Movimento longo da dama

Carregar:

```text
CARREGAR testes/cenarios/tm15.txt
```

Executar:

```text
MOVER C3 F6
ESTADO
```

Resultado esperado:

- dama termina em F6;
- C3 fica vazia.

---

## TM16 — Movimento da dama para trás

Carregar:

```text
CARREGAR testes/cenarios/tm16.txt
```

Executar:

```text
MOVER F6 C3
ESTADO
```

Resultado esperado:

- dama realiza corretamente o movimento para trás;
- termina em C3.

---

## TM17 — Dama bloqueada

Carregar:

```text
CARREGAR testes/cenarios/tm17.txt
```

Executar:

```text
MOVER C3 F6
ESTADO
```

Resultado esperado:

- movimento rejeitado;
- a dama não atravessa a peça branca em E5.

---

## TM18 — Vitória por eliminação

Carregar:

```text
CARREGAR testes/cenarios/tm18.txt
```

Executar:

```text
MOVER C3 E5
ESTADO
```

Resultado esperado:

```text
STATUS=VITORIA_BRANCAS
```

Depois teste:

```text
MOVER E5 F6
```

O movimento deve ser rejeitado porque a partida terminou.

---

## TM19 — Vitória por ausência de movimentos

Carregar:

```text
CARREGAR testes/cenarios/tm19.txt
```

Executar:

```text
ESTADO
```

Resultado esperado:

```text
STATUS=VITORIA_BRANCAS
```

Depois tente:

```text
MOVER A1 B2
```

O movimento deve ser rejeitado porque a partida foi encerrada.

---

# Teste de salvamento

Criar uma nova partida:

```text
NOVO Ana Pedro
MOVER A3 B4
```

Salvar no diretório organizado:

```text
SALVAR partidas/partida.txt
```

O arquivo será:

```text
partidas/partida.txt
```

---

# Teste de carregamento

Após salvar:

```text
CARREGAR partidas/partida.txt
ESTADO
```

O esperado é:

- A3 vazia;
- B4 ocupada por peça branca;
- turno das PRETAS;
- status `EM_ANDAMENTO`.

---

# Teste de arquivo inválido

Arquivo:

```text
testes/invalidos/invalido.txt
```

Comando:

```text
CARREGAR testes/invalidos/invalido.txt
```

Resultado esperado:

- mensagem de erro;
- programa continua funcionando;
- estado anterior não é perdido.

Depois:

```text
ESTADO
```

---

# Teste de arquivo vazio

Arquivo:

```text
testes/invalidos/vazio.txt
```

Comando:

```text
CARREGAR testes/invalidos/vazio.txt
```

Resultado esperado:

```text
Erro de arquivo: Arquivo de partida incompleto.
```

O programa deve continuar funcionando.

---

# Teste de arquivo inexistente

Não deve existir nenhum arquivo chamado:

```text
inexistente.txt
```

Executar:

```text
CARREGAR inexistente.txt
```

Resultado esperado:

- erro informado;
- aplicação continua em execução;
- estado anterior preservado.

---

# Testes de robustez

## TA01 — Comando desconhecido

```text
TESTE
```

Resultado esperado:

```text
Comando desconhecido.
```

---

## TA02 — MOVER sem parâmetros

```text
NOVO Ana Pedro
MOVER
ESTADO
```

O comando deve ser rejeitado.

---

## TA03 — Casa clara

```text
NOVO Ana Pedro
MOVER A3 A4
ESTADO
```

O movimento deve ser rejeitado.

---

## TA04 — Origem vazia

```text
NOVO Ana Pedro
MOVER A4 B5
ESTADO
```

O movimento deve ser rejeitado.

---

## TA05 — Movimento para trás de peça comum

```text
NOVO Ana Pedro
MOVER C3 B2
ESTADO
```

O movimento deve ser rejeitado.

---

## TA06 — Movimento após encerramento

```text
CARREGAR testes/cenarios/tm18.txt
MOVER C3 E5
ESTADO
MOVER E5 F6
```

O último movimento deve ser rejeitado.

---

## TA07 — Arquivo vazio

```text
CARREGAR testes/invalidos/vazio.txt
ESTADO
```

O carregamento deve ser rejeitado.

---

## TA08 — Comandos em letras minúsculas

```text
novo Ana Pedro
mover A3 B4
estado
```

Os comandos devem ser reconhecidos normalmente.

---

## TA09 — Salvar durante captura múltipla

```text
CARREGAR testes/cenarios/tm11.txt
MOVER C3 E5
SALVAR partidas/ta09.txt
```

O salvamento deve ser rejeitado.

Depois:

```text
MOVER E5 G7
ESTADO
```

A captura múltipla deve continuar normalmente.

---

## TA10 — Nova partida após encerramento

```text
CARREGAR testes/cenarios/tm18.txt
MOVER C3 E5
ESTADO
```

Confirme:

```text
STATUS=VITORIA_BRANCAS
```

Depois:

```text
NOVO Ana Pedro
ESTADO
```

O esperado é:

```text
TURNO=BRANCAS
STATUS=EM_ANDAMENTO
```

e o tabuleiro deve retornar à configuração inicial.

---

# Relatório de testes

O relatório completo está localizado em:

```text
testes/relatorio-testes.md
```

### 📋 Acesso aos testes manuais

O relatório com os resultados dos testes **TM01 até TM23** e dos testes adicionais de robustez **TA01 até TA10** pode ser acessado diretamente pelo GitHub:

[📋 Visualizar relatório de testes — TM01 a TM23](https://github.com/PedroYokada/fatec-dmd-jogo-de-damas/blob/main/testes/relatorio-testes.md)

Ele contém:

```text
TM01 até TM23
TA01 até TA10
```

---

# Sequência rápida para apresentação

Para demonstrar uma nova partida:

```text
NOVO Ana Pedro
ESTADO
MOVER A3 B4
MOVER B6 A5
ESTADO
```

Para demonstrar captura:

```text
CARREGAR testes/cenarios/tm09.txt
MOVER C3 E5
ESTADO
```

Para captura múltipla:

```text
CARREGAR testes/cenarios/tm11.txt
MOVER C3 E5
MOVER E5 G7
ESTADO
```

Para promoção:

```text
CARREGAR testes/cenarios/tm14.txt
MOVER A7 B8
ESTADO
```

Para movimentação da dama:

```text
CARREGAR testes/cenarios/tm15.txt
MOVER C3 F6
ESTADO
```

Para vitória:

```text
CARREGAR testes/cenarios/tm18.txt
MOVER C3 E5
ESTADO
```

Para salvar:

```text
NOVO Ana Pedro
MOVER A3 B4
SALVAR partidas/partida.txt
```

Para carregar:

```text
CARREGAR partidas/partida.txt
ESTADO
```

---

# Limitações conhecidas

Atualmente:

- a interface funciona exclusivamente pelo console;
- o jogo é destinado a dois jogadores locais;
- não há inteligência artificial;
- não há multiplayer em rede;
- não há interface gráfica;
- não são implementadas todas as condições oficiais de empate.

Esses recursos podem ser adicionados futuramente como extensões.

---

# Conclusão

O projeto aplica conceitos de Programação Orientada a Objetos no desenvolvimento de um jogo de damas funcional em Java.

A aplicação implementa movimentações, capturas, captura obrigatória, captura múltipla, damas, controle de turnos, identificação de vitória, persistência e tratamento de erros.

Também foram preparados cenários específicos de teste, arquivos para validação de erros e um relatório contendo os resultados dos testes funcionais e de robustez.

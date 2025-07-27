# 🤖 Bot Assistente para Discord (A.A.N.G.)

Um **bot modular para Discord** escrito em **Java** utilizando a biblioteca **JDA**.  
Ele atua como um **assistente multifuncional**, capaz de gerenciar mensagens, organizar arquivos, controlar processos do servidor (shutdown/restart), enviar relatórios do sistema e até executar outros bots.

---

## 🗂️ Estrutura do Projeto

```
📦 Projeto-Bot-Discord
 ├── 📂 org/example
 │   ├── Main.java
 │   │
 │   ├── 📂 connection
 │   │   └── Connection.java         # Conexão com o Discord (JDA)
 │   │
 │   ├── 📂 controller
 │   │   ├── ComandoController.java  # Centraliza e despacha comandos
 │   │   └── PathController.java     # Gerencia criação de pastas para arquivos
 │   │
 │   ├── 📂 comandos
 │   │   ├── Awaken.java             # Despertar/encerrar bots externos
 │   │   ├── Clear.java              # Limpeza de mensagens do Discord
 │   │   ├── Download.java           # Envio de arquivos para o canal
 │   │   ├── Rest.java               # Encerrar processos via PID
 │   │   ├── Restart.java            # Reiniciar o computador/servidor
 │   │   ├── Search.java             # Busca processos (jar/exe)
 │   │   ├── Shutdown.java           # Desligar o computador/servidor
 │   │   ├── Status.java             # Relatório do sistema/arquivos
 │   │   └── Upload.java             # Receber e salvar arquivos
 │   │
 │   ├── 📂 security
 │   │   └── Criptografia.java       # Criptografia e descriptografia AES
 │   │
 │   └── 📂 utilitarios
 │       └── FileUtils.java          # Classificação e download de arquivos
 │
 ├── 📂 Arquivos
 │   ├── BOTs/                      # Armazena bots .jar, .exe etc.
 │   ├── Documentos/                # PDFs, DOCs, TXT
 │   ├── Imagens/                   # PNG, JPG etc.
 │   ├── Musicas/                   # MP3, WAV
 │   ├── Planilhas/                 # XLSX, CSV
 │   └── Videos/                    # MP4, WMV
 │
 └── README.md (este arquivo)
````

---

## ⚡ Lista Completa de Comandos

Todos os comandos devem ser **prefixados com `!`**.

### 🧹 **Comandos de Limpeza**

* **`!limpar <número>`**
  Apaga uma quantidade específica de mensagens no canal atual (máximo 100).
  *Exemplo:*

  ```
  !limpar 10
  ```

  → Apaga as últimas 10 mensagens.

* **`!limpar today`**
  Apaga apenas as mensagens enviadas **no dia atual**.

* **`!limpar ALL`**
  Apaga **todo o histórico do canal**, processando em blocos de 100 mensagens.

---

### 📊 **Relatórios do Sistema**

* **`!relatar`**
  Mostra um **relatório completo do sistema**, incluindo:

  * Sistema operacional e arquitetura
  * Versão da JVM
  * Diretório de execução e usuário atual
  * Memória RAM usada/livre
  * Espaço em disco usado/livre
  * Contagem de arquivos em cada pasta (`Planilhas`, `Documentos`, etc.)

* **`!relatar <pasta>`**
  Lista **todos os arquivos** dentro de uma pasta específica.
  *Exemplo:*

  ```
  !relatar Imagens
  ```

  → Mostra todos os arquivos na pasta `Arquivos/Imagens`.

---

### 🔌 **Controle do Servidor**

* **`!desligar`**
  Envia uma mensagem de aviso e **desliga o servidor/computador** após 10 segundos.

* **`!reiniciar`**
  Envia um aviso e **reinicia o servidor/computador** após uma contagem regressiva.

* **`!buscar jar`**
  Lista todos os processos **Java (.jar)** em execução.

* **`!buscar exe`**
  Lista todos os processos **executáveis (.exe)** em execução (Windows) ou programas similares em Linux/Mac.

* **`!descansar <PID>`**
  Finaliza um processo específico usando o **PID**.
  *Exemplo:*

  ```
  !descansar 12345
  ```

  → Encerra o processo com PID `12345`.

---

### 🤖 **Gerenciamento de Bots**

* **`!despertar <botName>`**
  Executa outro **bot local** armazenado em `Arquivos/BOTs`.
  *Exemplo:*

  ```
  !despertar MeuOutroBot
  ```

  → Executa `MeuOutroBot.jar` da pasta BOTs.

* **`!descansar <botName>`**
  Encerra um bot específico em execução.

---

### 📂 **Gerenciamento de Arquivos**

* **`!upload <caminho>`**
  Faz upload de um arquivo **local do servidor** para o diretório correto (organiza automaticamente por tipo).
  *Exemplo:*

  ```
  !upload /home/user/relatorio.xlsx
  ```

  → Salva em `Arquivos/Planilhas`.

* **`!download <arquivo>`**
  Envia um arquivo do servidor para o canal do Discord.
  *Exemplo:*

  ```
  !download relatorio.xlsx
  ```

  → O bot envia o arquivo do diretório correto.

---

## 🚀 Como Executar

1. **Clonar o repositório**

   ```bash
   git clone <seu-repo>
   cd Projeto-Bot-Discord
   ```

2. **Configurar o Token do Discord**

   * O token está criptografado em `Connection.java`.
   * Para alterar, use o método `Criptografia.criptografar()` para gerar um novo valor e substituir.

3. **Compilar e Executar**
   Certifique-se de ter **Java 17+** instalado:

   ```bash
   javac -cp "libs/*" org/example/Main.java
   java -cp ".:libs/*" org.example.Main
   ```

4. **Rodar em Produção**
   Gere um `.jar` e execute:

   ```bash
   mvn package
   java -jar target/bot-discord.jar
   ```

---

## 🔐 Segurança

* Token do bot criptografado com **AES-256**.
* Uploads organizados por tipo em diretórios específicos.
* Pode ser estendido com **controle de permissões por roles do Discord** para restringir comandos críticos.

---

## 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **JDA (Java Discord API)**
* **AES-256** para criptografia do token
* **ProcessBuilder** para controle do sistema
* **Streams & Lambdas** para processamento funcional

---

## 📌 Próximos Passos

* [ ] Adicionar sistema de permissões baseado em roles.
* [ ] Criar um comando `!help` com lista dinâmica de comandos.
* [ ] Melhorar logging (armazenar logs em arquivos).
* [ ] Criar interface web para monitorar remotamente os processos.

---

## 👨‍💻 Autor

**Enrico**
Desenvolvedor Back-End e criador do **A.A.N.G. Bot**, nome em homenagem ao Avatar Aang.

---

```


package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.io.File;

public class Status {

    public static void relatorio(MessageReceivedEvent event) {
        // Extrai o comando e os parâmetros da mensagem
        String[] args = event.getMessage().getContentRaw().split(" ");

        // Se o comando for sem parâmetro, exibe o relatório completo
        if (args.length == 1) {
            exibirRelatorioCompleto(event);
        } else if (args.length == 2) {
            // Se o comando for com parâmetro (nome da pasta), exibe a lista de arquivos
            String pastaNome = args[1]; // O nome da pasta será o segundo argumento do comando
            listarArquivosDaPasta(event, pastaNome);
        } else {
            // Caso o comando seja mal formatado, avisa o usuário
            event.getChannel().sendMessage("⚠️ **Comando mal formatado. Utilize `!relatar` ou `!relatar <nome_da_pasta>`**").queue();
        }
    }

    // Método para exibir as informações detalhadas do sistema (sem parâmetro)
    private static void exibirRelatorioCompleto(MessageReceivedEvent event) {
        StringBuilder sb = new StringBuilder();

        // Cabeçalho
        sb.append("🔧 **Relatório do Sistema** 🔧\n")
                .append("-".repeat(30)).append("\n\n");

        // Sistema Operacional
        sb.append("🖥️ **SO**: ").append(System.getProperty("os.name")).append(" ").append(System.getProperty("os.version")).append("\n")
                .append("🏛️ **Arquitetura**: ").append(System.getProperty("os.arch")).append("\n\n");

        // JVM
        sb.append("☕ **JVM**: ").append(System.getProperty("java.version")).append("\n")
                .append("🏢 **Fornecedor**: ").append(System.getProperty("java.vendor")).append("\n")
                .append("📁 **Caminho**: ").append(System.getProperty("java.home")).append("\n\n");

        // Ambiente
        sb.append("📂 **Diretório**: ").append(System.getProperty("user.dir")).append("\n")
                .append("👤 **Usuário**: ").append(System.getProperty("user.name")).append("\n")
                .append("🌍 **Idioma**: ").append(System.getProperty("user.language")).append("\n\n");

        // Memória RAM
        long totalMemory = Runtime.getRuntime().totalMemory() / (1024 * 1024); // Total em MB
        long freeMemory = Runtime.getRuntime().freeMemory() / (1024 * 1024);   // Livre em MB
        long usedMemory = totalMemory - freeMemory; // Usada em MB

        // Armazenamento
        File file = new File("/");
        long totalSpace = file.getTotalSpace() / (1024 * 1024 * 1024); // Total em GB
        long freeSpace = file.getFreeSpace() / (1024 * 1024 * 1024);   // Livre em GB

        sb.append("-".repeat(30)).append("\n")
                .append("🧠 **Memória RAM**: ")
                .append(usedMemory).append(" MB / ")
                .append(totalMemory).append(" MB\n")
                .append("💾 **Armazenamento**: ")
                .append(freeSpace).append(" GB livre / ")
                .append(totalSpace).append(" GB total\n")
                .append("-".repeat(30)).append("\n");

        // Pastas e número de arquivos
        String[] pastaNomes = {"Planilhas", "Documentos", "Imagens", "Videos", "Musicas", "BOTs"};
        for (String pastaNome : pastaNomes) {
            // Caminho absoluto para cada pasta
            File pasta = new File(System.getProperty("user.dir") + File.separator + "Arquivos" + File.separator + pastaNome);

            // Verifica se a pasta existe
            if (!pasta.exists()) {
                System.out.println("Pasta não encontrada: " + pasta.getAbsolutePath());
            } else {
                // Contar o número de arquivos na pasta
                int arquivosCount = pasta.listFiles() != null ? pasta.listFiles().length : 0;

                sb.append("📂 **").append(pastaNome).append("**: ").append(arquivosCount).append(" arquivos\n");
            }
        }

        // Exibe a mensagem no canal
        event.getChannel().sendMessage(sb.toString()).queue();
    }

    // Método para listar os arquivos de uma pasta específica (com parâmetro)
    private static void listarArquivosDaPasta(MessageReceivedEvent event, String pastaNome) {
        StringBuilder sb = new StringBuilder();

        // Caminho da pasta
        File pasta = new File(System.getProperty("user.dir") + File.separator + "Arquivos" + File.separator + pastaNome);

        // Verifica se a pasta existe
        if (!pasta.exists()) {
            event.getChannel().sendMessage("⚠️ **Pasta não encontrada: " + pastaNome + "**").queue();
            return;
        }

        // Verifica se é um diretório
        if (!pasta.isDirectory()) {
            event.getChannel().sendMessage("⚠️ **O caminho fornecido não é uma pasta: " + pastaNome + "**").queue();
            return;
        }

        // Lista os arquivos da pasta
        File[] arquivos = pasta.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            sb.append("🚫 **A pasta está vazia.**");
        } else {
            sb.append("📂 **Arquivos na pasta** `").append(pastaNome).append("`:\n");
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    sb.append("- ").append(arquivo.getName()).append("\n");
                }
            }
        }

        // Envia a lista de arquivos para o canal
        event.getChannel().sendMessage(sb.toString()).queue();
    }
}

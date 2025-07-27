package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Search {
    public static void busca(String code, MessageReceivedEvent event) {
        String comando = "";
        String osName = System.getProperty("os.name").toLowerCase();
        String[] ext = {"exe", "jar"};
        code = code.trim(); // Remove espaços em branco

        try {
            if (osName.contains("win")) {
                // Comandos para Windows
                if (code.equals(ext[0])) {
                    comando = "tasklist /v | findstr .exe";
                } else if (code.equals(ext[1])) {
                    comando = "tasklist /v | findstr java.exe";
                } else {
                    event.getChannel().sendMessage("""
                            Extensão errada!
                            Apresente a extensão correta de acordo com o exemplo a seguir.
                            Exemplo: `!buscar jar Nome_do_Programa`
                            """).queue();
                    return;
                }
                comando = "cmd.exe /c " + comando;
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
                // Comandos para Unix/Linux/Mac
                if (code.equals(ext[0])) {
                    comando = "ps aux | grep .exe";
                } else if (code.equals(ext[1])) {
                    comando = "ps aux | grep java";
                } else {
                    event.getChannel().sendMessage("""
                            Extensão errada!
                            Apresente a extensão correta de acordo com o exemplo a seguir.
                            Exemplo: `!buscar jar Nome_do_Programa`
                            """).queue();
                    return;
                }
            } else {
                event.getChannel().sendMessage("Sistema operacional não suportado.").queue();
                return;
            }

            // Cria o processo para executar o comando
            ProcessBuilder builder = new ProcessBuilder(comando.split(" "));
            Process process = builder.start();

            // Lê a saída do comando
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String resultado = strFrag(reader);

            if (resultado.isEmpty()) {
                event.getChannel().sendMessage("Nenhum processo correspondente encontrado.").queue();
            } else {
                sendInChunks(resultado, event);
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Erro ao executar o comando: " + e.getMessage());
            event.getChannel().sendMessage("ERRO:\n" + e.getMessage()).queue();
        }
    }

    private static String strFrag(BufferedReader reader) throws IOException {
        StringBuilder l = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] partes = line.split("\\s+");
            if (partes.length >= 2) {
                String lastPart = partes[partes.length - 1];
                String pid = partes[1];
                l.append(lastPart).append("[").append(pid).append("]\n");
            }
        }
        return l.toString();
    }

    private static void sendInChunks(String message, MessageReceivedEvent event) {
        String[] linhas = message.split("\n");
        StringBuilder chunk = new StringBuilder();
        for (int i = 0; i < linhas.length; i++) {
            chunk.append(linhas[i]).append("\n");
            if ((i + 1) % 10 == 0 || i == linhas.length - 1) {
                event.getChannel().sendMessage(chunk.toString()).queue();
                chunk.setLength(0);
            }
        }
    }
}

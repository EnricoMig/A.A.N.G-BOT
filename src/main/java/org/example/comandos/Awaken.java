package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Awaken {

    public static void despertar(String botName, MessageReceivedEvent event) throws IOException {
        String basePath = System.getProperty("user.dir");
        String jarPath = basePath + File.separator + "Arquivos" + File.separator + "BOTs" + File.separator + botName + ".jar";

        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            String msg = "Arquivo Jar não encontrado: " + jarPath;
            System.err.println(msg);
            event.getChannel().sendMessage(msg).queue();
            return;
        }

        String comando;
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            comando = "java -jar \"" + jarFile.getAbsolutePath() + "\"";
        } else {
            comando = "java -jar '" + jarFile.getAbsolutePath() + "'";
        }

        try {
            ProcessBuilder builder;
            if (osName.contains("win")) {
                builder = new ProcessBuilder("cmd.exe", "/c", comando);
            } else {
                builder = new ProcessBuilder("bash", "-c", comando);
            }
            builder.inheritIO(); // Herda a saída para o console
            builder.start();

            event.getChannel().sendMessage("Estou pronto para te ajudar, Mestre!").queue();
        } catch (IOException e) {
            String erro = "Erro ao iniciar o bot: " + e.getMessage();
            System.err.println(erro);
            event.getChannel().sendMessage(erro).queue();
        }
    }

    public static void descansar(String botName) throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String comando;

        if (osName.contains("win")) {
            comando = "taskkill /F /IM \"" + botName + ".jar\"";
        } else {
            comando = "pkill -f '" + botName + ".jar'";
        }

        try {
            ProcessBuilder builder;
            if (osName.contains("win")) {
                builder = new ProcessBuilder("cmd.exe", "/c", comando);
            } else {
                builder = new ProcessBuilder("bash", "-c", comando);
            }

            Process process = builder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Agora o " + botName + " já está descansando!");
            } else {
                System.out.println("Falha ao encerrar o bot: " + botName);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao tentar encerrar o bot: " + e.getMessage());
        }
    }

    private static List<String> windowsLista() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("tasklist");
        Process process = builder.start();
        return new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines()
                .collect(Collectors.toList());
    }

    private static List<String> unixLista() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("ps", "-e");
        Process process = builder.start();
        return new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines()
                .collect(Collectors.toList());
    }
}
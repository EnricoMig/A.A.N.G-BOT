package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Rest {
    public static void desligar(String PID, MessageReceivedEvent event) {
        String comando;
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            // Comando para encerrar processos no Windows
            comando = "taskkill /PID " + PID + " /F";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            // Comando para encerrar processos no Unix/Linux/Mac
            comando = "kill -9 " + PID;
        } else {
            String msg = "Sistema operacional não suportado.";
            System.err.println(msg);
            event.getChannel().sendMessage(msg).queue();
            return;
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
                String msg = "Processo com PID " + PID + " encerrado com sucesso!";
                System.out.println(msg);
                event.getChannel().sendMessage(msg).queue();
            } else {
                String errorOutput = getErrorOutput(process);
                String msg = "Falha ao encerrar o processo com PID " + PID + ".\n" + errorOutput;
                System.err.println(msg);
                event.getChannel().sendMessage(msg).queue();
            }
        } catch (Exception e) {
            String erro = "Erro ao executar o comando: " + e.getMessage();
            System.err.println(erro);
            event.getChannel().sendMessage(erro).queue();
        }
    }

    private static String getErrorOutput(Process process) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errorOutput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            errorOutput.append(line).append("\n");
        }
        return errorOutput.toString();
    }
}

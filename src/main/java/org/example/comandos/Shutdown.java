package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class Shutdown {
    public static void desligar(MessageReceivedEvent event){
        // Envia a mensagem de aviso
        event.getChannel().sendMessage("⚠️ **Atenção!** O sistema será desligado em 10 segundos...").queue();
        try {
            // Espera 30 segundos antes de desligar
            for(int x = 10;x >= 0; x--){
                event.getChannel().sendMessage("Contagem: "+x).queue();
                Thread.sleep(1000);
            }

            // Executa o comando de desligamento
            shutdownComputer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Método para desligar o computador
    private static void shutdownComputer() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Para Windows
                Runtime.getRuntime().exec("shutdown -s -t 0");
            } else if (os.contains("nix") || os.contains("nux")) {
                // Para Linux ou macOS
                Runtime.getRuntime().exec("shutdown -h now");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

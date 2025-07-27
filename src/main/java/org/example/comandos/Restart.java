package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class Restart {
    public static void reiniciar(MessageReceivedEvent event){
        // Envia a mensagem de aviso
        event.getChannel().sendMessage("⚠️ **Atenção!** O sistema será reiniciado em 30 segundos...").queue();
        try {
            // Espera 30 segundos antes de reiniciar
            for(int x = 10;x >= 0; x--){
                event.getChannel().sendMessage("Contagem: "+x).queue();
                Thread.sleep(1000);
            }
            // Executa o comando de reinício
            restartComputer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Método para reiniciar o computador
    private static void restartComputer() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Para Windows
                Runtime.getRuntime().exec("shutdown -r -t 0");
            } else if (os.contains("nix") || os.contains("nux")) {
                // Para Linux ou macOS
                Runtime.getRuntime().exec("shutdown -r now");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

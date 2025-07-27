package org.example.controller;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.comandos.*;

import java.io.IOException;

public class ComandoController {
    public static void escolherComandos(String[] comando, MessageReceivedEvent event) throws IOException {
        switch (comando[0].toLowerCase()) {
            case "limpar":
                Clear.clear(comando[1].trim(), event);
                break;

            case "relatar":
                Status.relatorio(event);
                break;

            case "desligar":
                Shutdown.desligar(event);
                break;

            case "reiniciar":
                Restart.reiniciar(event);
                break;

            case "despertar":
                Awaken.despertar(comando[1].trim(), event);
                break;

            case "buscar":
                Search.busca(comando[1].trim(), event);
                break;

            case "descansar":
                Rest.desligar(comando[1].trim(), event);
                break;

            case "upload":
                Upload.receber(comando[1].trim(), event);
                break;

            case "download":
                Download.enviar(comando,event);
                break;

            default:
                event.getChannel().sendMessage("Comando não reconhecido!").queue();
                break;
        }
    }
}

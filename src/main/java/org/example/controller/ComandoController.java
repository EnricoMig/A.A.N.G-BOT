<<<<<<< HEAD:AssistenteBOT/src/main/java/org/example/controller/ComandoController.java
package org.example.controller;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.comandos.*;

import java.io.IOException;

public class ComandoController {
    public static void escolherComandos(String[] comando, MessageReceivedEvent event) throws IOException {
        if (comando.length == 0) {
            event.getChannel().sendMessage("Nenhum comando fornecido.").queue();
            return;
        }

        String cmd = comando[0].toLowerCase();

        // Reconstrói todo o resto do array como parâmetro, preservando espaços e parênteses
        String param = comando.length > 1
            ? String.join(" ", java.util.Arrays.copyOfRange(comando, 1, comando.length))
            : "";

        switch (cmd) {
            case "limpar":
                Clear.clear(param.trim(), event);
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
                Awaken.despertar(param.trim(), event);
                break;
            case "buscar":
                Search.busca(param.trim(), event);
                break;
            case "descansar":
                Rest.desligar(param.trim(), event);
                break;
            case "upload":
                Upload.receber(param, event);  // envia o path completo com espaços/parênteses
                break;
            case "download":
                Download.enviar(comando, event);
                break;
            case "help":
                Help.exibirAjuda(event);
                break;
            default:
                event.getChannel().sendMessage("Comando não reconhecido!").queue();
                break;
        }
    }
}
=======
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
>>>>>>> origin/main:src/main/java/org/example/controller/ComandoController.java

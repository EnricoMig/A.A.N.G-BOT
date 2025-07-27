package org.example;

import org.example.connection.Connection;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static org.example.controller.ComandoController.escolherComandos;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        Connection.conectar();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event){
        TextChannel channel = event.getJDA().getTextChannelById("1314588138395402260");
        if(channel != null){
            channel.sendMessage("Olá! Estou online e pronto para ajudar!").queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        if(event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();
        System.out.println(message);

        if(message.startsWith("!")){

            String[] comando = message.substring(1).split(" ");

            try {
                escolherComandos(comando,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
package org.example.comandos;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import java.util.concurrent.atomic.AtomicReference;

public class Clear {

    public static void clear(String message,MessageReceivedEvent event ){
        switch(message){
            case "ALL":
                all(event);
                break;

            case "today":
                today(event);
                break;

            default:
                quantidade(Integer.parseInt(message),event);

        }


    }

    private static void quantidade(int qtd,MessageReceivedEvent event){
        if(qtd >1){
            try {

                    if(qtd>0 && qtd<=100){
                    event.getChannel().purgeMessages(event.getChannel().getHistory().retrievePast(qtd).complete());
                    event.getChannel().sendMessage("Limpei "+qtd+" mensagens!").queue();
                } else{
                    event.getChannel().sendMessage("Por favor forneça um número válido de mensagens (1 a 100).").queue();
                }

            }catch (NumberFormatException e){
                event.getChannel().sendMessage("Por favor, forneça um número válido de mensagens.").queue();
            }

        } else {
            event.getChannel().sendMessage("Por favor, forneça o número de mensagens a ser removido (exemplo: !clear 10).").queue();
        }

    } //Check

    private static void all(MessageReceivedEvent event) {
        // Certifique-se de que o canal é um TextChannel
        if (event.getChannel() instanceof TextChannel) {
            TextChannel textChannel = (TextChannel) event.getChannel();

            // Chama a função para limpar todas as mensagens
            messages(textChannel, 1000);
        } else {
            event.getChannel().sendMessage("Este comando só pode ser usado em um canal de texto!").queue();
        }
    }

    private static void messages(TextChannel textChannel, int limit) {
        // Limita a quantidade de mensagens a 100 por vez
        textChannel.getHistoryFromBeginning(limit > 100 ? 100 : limit).queue(messages -> {
            // Converte o MessageHistory para uma Collection<Message>
            Collection<Message> messageCollection = messages.getRetrievedHistory();

            // Exclui as mensagens
            textChannel.deleteMessages(messageCollection).queue();

            // Se ainda houver mais mensagens, chama novamente para apagar mais
            if (messages.getRetrievedHistory().size() == 100) {
                messages(textChannel, limit - 100);  // Chama novamente com o número restante
            } else {
                textChannel.sendMessage("Apaguei todas as mensagens no canal").queue();
            }
        });
    }

    public static void today(MessageReceivedEvent event){
        // Cast do canal para TextChannel
        TextChannel textChannel = (TextChannel) event.getChannel();
        LocalDate today = LocalDate.now();

        textChannel.getHistoryFromBeginning(100).queue(messages -> {
            List<Message> messageList = messages.getRetrievedHistory();
            AtomicReference<LocalDate> messageDate = new AtomicReference<>(null);
            messageList.stream().filter(message -> {
                messageDate.set(message.getTimeCreated().toLocalDate());
                return messageDate.get().equals(today);
            }).forEach(message -> {
                message.delete().queue();
            });
            textChannel.sendMessage("Apaguei todas as mensagens do dia " + messageDate.get());
        });
    }

}

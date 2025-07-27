package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help {

    public static void exibirAjuda(MessageReceivedEvent event) {
        String helpMessage = """
                🤖 **A.A.N.G. - Guia Rápido de Comandos**
                
                🧹 **Limpeza**
                • !limpar <número> → Apaga X mensagens (máx. 100)
                • !limpar today → Apaga mensagens do dia atual
                • !limpar ALL → Apaga todo o histórico do canal

                📊 **Relatórios**
                • !relatar → Mostra status do sistema (SO, memória, espaço)
                • !relatar <pasta> → Lista arquivos da pasta (Planilhas, Documentos, etc.)

                🔌 **Controle do Servidor**
                • !desligar → Desliga o servidor/PC
                • !reiniciar → Reinicia o servidor/PC
                • !buscar jar → Lista processos Java (.jar)
                • !buscar exe → Lista programas (.exe)
                • !descansar <PID> → Encerra processo pelo PID

                🤖 **Gerenciar Bots**
                • !despertar <botName> → Executa outro bot da pasta BOTs
                • !descansar <botName> → Encerra bot em execução

                📂 **Arquivos**
                • !upload <caminho> → Envia arquivo local do servidor para a pasta correta
                • !download <arquivo> → Envia arquivo do servidor para o canal

                💡 *Exemplo:*
                `!limpar 20` → Apaga as últimas 20 mensagens
                `!relatar Imagens` → Lista arquivos da pasta Imagens
                `!despertar MeuBot` → Executa MeuBot.jar da pasta BOTs
                """;

        event.getChannel().sendMessage(helpMessage).queue();
    }
}

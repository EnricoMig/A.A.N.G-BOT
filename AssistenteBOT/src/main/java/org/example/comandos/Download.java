package org.example.comandos;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.util.Arrays;

public class Download {
    private final String baseDirectory;

    public static void enviar(String[] comando, MessageReceivedEvent event) {
        // Verifica se o comando contém o nome do arquivo
        if (comando.length > 1) {
            String fileName = comando[1].trim(); // Pega o nome do arquivo
            File file = new File(new Download().baseDirectory + determineSubfolder(fileName) + File.separator + fileName);

            // Verifica se o arquivo existe
            if (file.exists()) {
                FileUpload fileUpload = FileUpload.fromData(file);

                // Envia o arquivo para o canal de texto
                TextChannel channel = event.getChannel().asTextChannel(); // Obtém o canal de texto do evento
                channel.sendFiles(fileUpload).queue(
                        success -> System.out.println("Arquivo enviado com sucesso."),
                        failure -> System.err.println("Erro ao enviar o arquivo: " + failure.getMessage())
                );
            } else {
                event.getChannel().sendMessage("Arquivo não encontrado. Verifique o nome e a extensão.").queue();
            }
        } else {
            event.getChannel().sendMessage("Por favor, forneça o nome do arquivo a ser baixado.").queue();
        }
    }

    public Download() {
        // Diretório base onde os arquivos estão localizados
        this.baseDirectory = System.getProperty("user.dir") + File.separator + "Arquivos" + File.separator;
    }

    // Método para determinar a subpasta baseada na extensão do arquivo
    private static String determineSubfolder(String fileName) {
        String fileExtension = getFileExtension(fileName).toLowerCase();

        // Verifica a extensão e retorna a subpasta correspondente
        switch (fileExtension) {
            case "doc":
            case "docx":
            case "txt":
            case "pdf":
                return "Documentos";  // Arquivos de documentos
            case "xlsx":
            case "xls":
            case "csv":
                return "Planilhas";  // Arquivos de planilhas
            case "png":
            case "jpg":
            case "jpeg":
            case "img":
                return "Imagens";  // Arquivos de imagens
            case "mp4":
            case "wmv":
                return "Videos";  // Arquivos de vídeo
            case "wav":
            case "mp3":
                return "Musicas";  // Arquivos de música
            case "exe":
            case "jar":
            case "py":
                return "BOTs";  // Arquivos de BOTs
            default:
                return "";  // Caso não haja correspondência, retornará vazio
        }
    }

    // Método para extrair a extensão de um arquivo
    private static String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {  // Se não encontrar ponto, não tem extensão
            return "";
        }
        return fileName.substring(lastIndexOfDot + 1);
    }
}

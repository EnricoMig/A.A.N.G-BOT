package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.Message.Attachment;
import org.example.utilitarios.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Upload {
    private final Path baseDir;

    public Upload() {
        this.baseDir = Paths.get(System.getProperty("user.dir"), "Arquivos").toAbsolutePath();
    }

    public static void receber(String _param, MessageReceivedEvent event) {
        var attachments = event.getMessage().getAttachments();
        if (attachments.isEmpty()) {
            event.getChannel().sendMessage("Nenhum arquivo anexado. Use `!upload` com o arquivo anexo.").queue();
            return;
        }

        Attachment attachment = attachments.get(0);
        String filename = attachment.getFileName();
        String ext = attachment.getFileExtension() != null ? attachment.getFileExtension().toLowerCase() : "";
        String folderName = FileUtils.getFolderByExtension(ext);

        Upload uploader = new Upload();
        Path destFolder = uploader.baseDir.resolve(folderName);
        try {
            Files.createDirectories(destFolder);
        } catch (IOException e) {
            event.getChannel().sendMessage("Erro ao criar pasta `" + folderName + "`.").queue();
            e.printStackTrace();
            return;
        }

        File destFile = destFolder.resolve(filename).toFile();

        attachment.getProxy()
            .downloadToFile(destFile)
            .thenAccept(file -> {
                event.getChannel().sendMessage(
                    "Arquivo **" + file.getName() + "** salvo em `" + folderName + "`."
                ).queue();
            })
            .exceptionally(err -> {
                event.getChannel().sendMessage("Falha ao salvar o arquivo `" + filename + "`").queue();
                err.printStackTrace();
                return null;
            });
    }
}

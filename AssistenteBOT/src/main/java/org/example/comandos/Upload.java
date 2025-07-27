package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.utilitarios.FileUtils;

import java.io.File;
import java.io.IOException;

public class Upload {
    private final String baseDirectory;

    public static void receber(String comando, MessageReceivedEvent event) {
        // Verifica se o comando contém o caminho de arquivo
        if (comando != null && !comando.isEmpty()) {
            File file = new File(comando);

            // Verifica se o arquivo existe e não é um diretório
            if (file.exists() && file.isFile()) {
                // Processa o arquivo fornecido
                Upload upload = new Upload();
                upload.saveAttachment(file);
                event.getChannel().sendMessage("Arquivo '" + file.getName() + "' salvo com sucesso!").queue();
            } else {
                event.getChannel().sendMessage("Caminho de arquivo inválido. Verifique o caminho e tente novamente.").queue();
            }
        } else {
            event.getChannel().sendMessage("Nenhum caminho de arquivo fornecido.").queue();
        }
    }

    public Upload() {
        this.baseDirectory = System.getProperty("user.dir") + File.separator + "Arquivos" + File.separator;
    }

    // Método para salvar um arquivo que foi passado diretamente (caminho de arquivo)
    private void saveAttachment(File file) {
        // Obter a pasta com base na extensão do arquivo
        String folderName = FileUtils.getFolderByExtension(getFileExtension(file));
        File folder = new File(baseDirectory + folderName);

        // Garantir que o diretório exista, se não, cria
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Falha ao criar diretório: " + folder.getAbsolutePath());
            return;
        }

        // Copiar o arquivo para o diretório adequado
        try {
            // Usando Files.copy para copiar o arquivo para o destino
            File destination = new File(folder, file.getName());
            java.nio.file.Files.copy(file.toPath(), destination.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Arquivo '" + file.getName() + "' salvo em: " + folder.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obter a extensão do arquivo
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        return index > 0 ? fileName.substring(index + 1).toLowerCase() : "";
    }
}

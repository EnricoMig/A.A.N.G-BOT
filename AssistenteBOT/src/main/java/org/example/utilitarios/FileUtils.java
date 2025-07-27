package org.example.utilitarios;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

    // Mapeamento de extensões para pastas
    private static final Map<String, String> folderMap = new HashMap<>();

    static {
        folderMap.put("xls", "Planilhas");
        folderMap.put("xlsx", "Planilhas");
        folderMap.put("csv", "Planilhas");
        folderMap.put("doc", "Documentos");
        folderMap.put("docx", "Documentos");
        folderMap.put("pdf", "Documentos");
        folderMap.put("png", "Imagem");
        folderMap.put("img", "Imagem");
        folderMap.put("wmv", "Videos");
        folderMap.put("mp4", "Videos");
        folderMap.put("mpeg", "Videos");
        folderMap.put("mp3", "Musicas");
        folderMap.put("wav", "Musicas");
        folderMap.put("jar", "BOTs");
        folderMap.put("exe", "BOTs");
        folderMap.put("deb", "BOTs");
    }

    // Retorna o nome da pasta com base na extensão do arquivo
    public static String getFolderByExtension(String extension) {
        return folderMap.getOrDefault(extension, "Outros");
    }

    // Faz o download de um arquivo de uma URL para o destino
    public static void downloadFileFromUrl(String fileUrl, File destination) throws IOException {
        try (InputStream in = new URL(fileUrl).openStream()) {
            Files.copy(in, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}

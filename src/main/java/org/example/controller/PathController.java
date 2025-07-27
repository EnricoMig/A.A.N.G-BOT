package org.example.controller;

import java.io.File;

public class PathController {
    public static void criarPastas(){
        // Caminho direto para a pasta "Arquivos"
        String path = "Arquivos";  // Nome da pasta 'Arquivos' direto
        File arquivos = new File(path);

        if (!arquivos.exists()) {
            arquivos.mkdirs();  // Cria a pasta 'Arquivos' diretamente
        }

        // Criando as subpastas dentro de 'Arquivos'
        new File(arquivos, "Planilhas").mkdirs();
        new File(arquivos, "Documentos").mkdirs();
        new File(arquivos, "Imagens").mkdirs();
        new File(arquivos, "Videos").mkdirs();
        new File(arquivos, "Musicas").mkdirs();
        new File(arquivos, "BOTs").mkdirs();


    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir")+File.separator+"Arquivos"+File.separator);
    }
}

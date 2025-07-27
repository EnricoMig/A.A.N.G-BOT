package org.example.connection;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.Main;
import org.example.controller.PathController;
import org.example.security.Criptografia;

import java.io.File;

public class Connection extends ListenerAdapter {
    public static void conectar() throws Exception {
        PathController.criarPastas();
        String Token =  Criptografia.descriptografar("Q/oEJhrGx7t/P/IXEkWaqVvhkBga3obFMM7yTzwtaNF23nWcUcgYY6uPd90voNUy+7ZDJRuQuI4xQgUFginyqq1GD0bUrX0Yn9obe758UsU=",
                "4gw9QFf15f447SEbEpvT4WmyYguo0eLv1xYXyTGDnnI=");
        JDABuilder.createDefault(Token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Main())
                .build();
    }


}

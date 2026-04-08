package br.edu.victorsantosconsumoapi;

import br.edu.victorsantosconsumoapi.service.ItemService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class VictorsantosConsumoapiApplication {

    private static ItemService itemService;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(VictorsantosConsumoapiApplication.class, args);
        // Crie um cliente Java que envie uma requisição POST para o endpoint de criação, com um JSON representando um novo item do seu sistema (de acordo com o caso de uso definido pelo professor).
        criarCarro(1, "Toyota", "Corolla", "Branco");
        criarCarro(2, "Honda", "Civic", "Prata");
        // Crie um cliente Java que realize uma requisição GET para o endpoint de listagem e imprima os dados retornados no console.
        obterLista();
        // Crie um cliente Java que envie uma requisição GET com path param, buscando um item pelo identificador definido. Imprima a resposta e o código HTTP.
        obterCarroEspecifico(1);
        // Crie um cliente Java que envie uma requisição GET para o endpoint /status e imprima o JSON com o status e timestamp.
        obterStatus();

    }

    private static void criarCarro(int id,String marca, String modelo, String cor) throws IOException {
        itemService = new ItemService();

        String payload = String.format("{\"id\": %d, \"marca\": \"%s\", \"modelo\": \"%s\", \"cor\": \"%s\"}", id, marca, modelo, cor);

        ItemResponse response = itemService.httpRequest("/itens", "POST", payload, null);

        System.out.println(response);
    }

    private static void obterLista() throws IOException {
        itemService = new ItemService();

        ItemResponse response = itemService.httpRequest("/itens", "GET", null, null);

        System.out.println(response);
    }

    private static void obterCarroEspecifico(int id) throws IOException {
        itemService = new ItemService();

        ItemResponse response = itemService.httpRequest("/itens/"+id, "GET", null, null);

        System.out.println(response);
    }

    private static void obterStatus() throws IOException {
        itemService = new ItemService();

        ItemResponse response = itemService.httpRequest("/status", "GET", null, null);

        System.out.println(response);
    }

}

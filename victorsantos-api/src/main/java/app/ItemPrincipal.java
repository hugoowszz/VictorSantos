package app;

import io.javalin.Javalin;

import java.time.LocalDateTime;
import java.util.*;

public class ItemPrincipal {

    private static List<Carro> lista = new ArrayList<Carro>();

    public static Javalin createApp(){
        Javalin app = Javalin.create();

        //GET /hello
        app.get("/hello", ctx -> ctx.result("Hello, Javalin!"));

        //GET /status
        app.get("/status", ctx -> {
            Map<String, String> response = new HashMap<>();
            response.put("status", "ok");
            response.put("timestamp", LocalDateTime.now().toString());

            ctx.json(response);
        });

        //POST /echo
        app.post("/echo", ctx -> {
            Map<String, String> body = ctx.bodyAsClass(Map.class);

            ctx.json(body);
        });

        //GET /saudacao/{nome}
        app.get("/saudacao/{nome}", ctx -> {
            Map<String, String> response = new HashMap<>();

            String nome = ctx.pathParam("nome");

            response.put("mensagem", "Olá, " +nome + "!");

            ctx.json(response);
        });

        //POST - Incluir um item - /itens
        app.post("/itens", ctx ->{
            Carro carro = ctx.bodyAsClass(Carro.class);
            lista.add(carro);
            ctx.status(201).json(carro);
        });

        //GET - Recuperar os itens - /itens
        app.get("/itens", ctx -> {
            ctx.json(lista);
        });

        // GET - Recuperar item por id - /itens/{id}
        app.get("/itens/{id}", ctx -> {

            try{
                int id = Integer.valueOf(ctx.pathParam("id"));
                Optional<Carro> carroEncontrado = lista.stream().filter(i -> i.getId() == id).findFirst();
                if(carroEncontrado.isPresent()){
                    ctx.json(carroEncontrado.get());
                } else {
                    ctx.status(404).json(Map.of("erro", "O item de id " + id + " não foi encontrado"));

                }
            } catch (NumberFormatException e){
                ctx.status(400).json(Map.of("erro", "O id deve ser um numero inteiro"));
            }


        });
        return app;
    }

    public static void main(String[] args) {
        createApp().start(7000);
    }

    public static void limpar(){
        lista.clear();
    }
}

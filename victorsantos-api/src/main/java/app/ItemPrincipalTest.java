package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemPrincipalTest {

    @BeforeEach
    public void setup(){
        ItemPrincipal.limpar();
    }

    // Escreva um teste para o endpoint /hello, validando status 200 e a resposta "Hello, Javalin!".
    @Test
    public void deveRetornarHello(){

        Javalin app = ItemPrincipal.createApp();

        JavalinTest.test(app, (server, client) ->{
            Response response = client.get("/hello");

            int codigo = response.code();
            String mensagem = response.body().string();

            assertEquals(200, codigo);
            assertEquals("Hello, Javalin!", mensagem);
        });
    }

    // Escreva um teste para o endpoint de criação (POST), simulando o envio de um novo item e verificando se o status retornado é 201.
    @Test
    public void deveCriarCarro(){
        Javalin app = ItemPrincipal.createApp();

        JavalinTest.test(app, (server, client) ->{

            String json = """
                    {
                    "id":1,
                    "marca":"Hyundai",
                    "modelo":"Tucson",
                    "cor":"Prata"
                    }
                    """;

            Response response = client.post("/itens", json);

            String mensagem = response.body().string();
            int codigo = response.code();

            ObjectMapper mapper = new ObjectMapper();

            Carro carro = mapper.readValue(mensagem, Carro.class);

            assertEquals(201, codigo);
            assertEquals(1, carro.getId());
            assertEquals("Hyundai", carro.getMarca());
            assertEquals("Tucson", carro.getModelo());
            assertEquals("Prata", carro.getCor());

        });
    }

    // Escreva um teste para o endpoint de busca (GET com path param), verificando se um item recém-cadastrado pode ser recuperado corretamente.
    @Test
    public void deveBuscarCarro(){
        Javalin app = ItemPrincipal.createApp();

        JavalinTest.test(app, (server, client) ->{
            String json = """
                    {
                    "id":2,
                    "marca":"Audi",
                    "modelo":"Q3",
                    "cor":"Preto"
                    }
                    """;

            client.post("/itens", json);
            Response response = client.get("/itens/2");

            String mensagem = response.body().string();
            int codigo = response.code();

            ObjectMapper mapper = new ObjectMapper();

            Carro carro = mapper.readValue(mensagem, Carro.class);

            assertEquals(200, codigo);
            assertEquals(2, carro.getId());
            assertEquals("Audi", carro.getMarca());
            assertEquals("Q3", carro.getModelo());
            assertEquals("Preto", carro.getCor());
        });
    }
    // Escreva um teste para o endpoint de listagem (GET), garantindo que ele retorne um array JSON não vazio após a criação de pelo menos um item.
    @Test
    public void deveRetornarListaCarros(){
        Javalin app = ItemPrincipal.createApp();

        JavalinTest.test(app, (server, client) ->{
            String json = """
                    {
                    "id":3,
                    "marca":"Chevrolet",
                    "modelo":"Chevette",
                    "cor":"Roxo"
                    }
                    """;

            client.post("/itens", json);
            Response response = client.get("/itens");

            String mensagem = response.body().string();
            int codigo = response.code();

            ObjectMapper mapper = new ObjectMapper();

            Carro[] carros = mapper.readValue(mensagem, Carro[].class);

            assertEquals(200, codigo);
            assertEquals(3, carros[0].getId());
            assertEquals("Chevrolet", carros[0].getMarca());
            assertEquals("Chevette", carros[0].getModelo());
            assertEquals("Roxo", carros[0].getCor());

            assertEquals(1, carros.length);

            assertTrue(carros.length > 0);

        });
    }
}


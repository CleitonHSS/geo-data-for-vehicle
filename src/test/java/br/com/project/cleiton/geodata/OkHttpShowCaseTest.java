package br.com.project.cleiton.geodata;

import br.com.project.cleiton.geodata.domain.Data;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

//
// Os testes a seguir têm como objetivo mostrar o uso da biblioteca OkHttp no acesso a endpoints REST.
//
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OkHttpShowCaseTest extends BaseTestAbstract {

    //
    // Fazendo um POST
    //
    @Test
    public void okHttpPost() throws IOException {

        String url = "https://jsonplaceholder.typicode.com/posts";

        Data data = new Data(123, "Yep", "Mais yep");
        String dataAsString = objectMapper.writeValueAsString(data);

        RequestBody requestBody = RequestBody.create(dataAsString, mediaTypeJson);

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
            String ans = response.body().string();
            LOGGER.info("okHttpPost() - {}", ans);
        }
    }

    //
    // Fazendo um GET de um item
    //
    @Test
    public void okHttpGetUmItem() throws IOException {

        String url = "https://jsonplaceholder.typicode.com/todos/123";

        Request request = new Request.Builder().url(url).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
            String ans = response.body().string();
            LOGGER.info("okHttpGetUmItem() - {}", ans);
        }
    }

    //
    // Fazendo um GET de uma lista de items
    //
    @Test
    public void okHttpGetTodosOsItems() throws IOException {

        String url = "https://jsonplaceholder.typicode.com/todos";

        Request request = new Request.Builder().url(url).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
            List<Data> list = jsonAdapter.fromJson(response.body().source());
            for (Data data : list) {
                LOGGER.info("okHttpGetTodosOsItems() - {}", data);
            }
        }
    }

    //
    // Fazendo um PUT
    //
    @Test
    public void okHttpPut() throws IOException {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        Data data = new Data(123, "Yep", "Mais yep");
        String dataAsString = objectMapper.writeValueAsString(data);

        RequestBody requestBody = RequestBody.create(dataAsString, mediaTypeJson);

        Request request = new Request.Builder().url(url).put(requestBody).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
            String ans = response.body().string();
            LOGGER.info("okHttpPut() - {}", ans);
        }
    }

    //
    // Fazendo um PATCH
    //
    @Test
    public void okHttpPatch() throws IOException {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        Data data = new Data(123, "Yep", "Mais yep");
        String dataAsString = objectMapper.writeValueAsString(data);

        RequestBody requestBody = RequestBody.create(dataAsString, mediaTypeJson);

        Request request = new Request.Builder().url(url).patch(requestBody).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
            String ans = response.body().string();
            LOGGER.info("okHttpPatch() - {}", ans);
        }
    }

    //
    // Fazendo um DELETE
    //
    @Test
    public void okHttpDelete() throws IOException {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        Request request = new Request.Builder().url(url).delete().build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
            String ans = response.body().string();
            LOGGER.info("okHttpDelete() - {}", ans);
        }
    }

}

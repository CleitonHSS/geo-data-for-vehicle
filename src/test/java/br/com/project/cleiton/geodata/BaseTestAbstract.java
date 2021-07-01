package br.com.project.cleiton.geodata;

import br.com.project.cleiton.geodata.domain.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BaseTestAbstract {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTestAbstract.class);

    // OkHttp
    // Ref:
    // https://square.github.io/okhttp/
    protected OkHttpClient okHttpClient = new OkHttpClient();
    protected String mediaType = "application/json; charset=utf-8";
    protected MediaType mediaTypeJson = MediaType.get(mediaType);
    protected MockWebServer mockWebServer = new MockWebServer();

    // Jackson Json
    // Ref:
    // https://github.com/FasterXML/jackson
    protected ObjectMapper objectMapper = new ObjectMapper();

    // Moshi
    // Ref:
    // https://github.com/square/moshi
    protected Moshi moshi = new Moshi.Builder().build();
    protected JsonAdapter<List<Data>> jsonAdapter = moshi.adapter(Types.newParameterizedType(List.class, Data.class));

    @BeforeAll
    public void beforeAll() {
        LOGGER.info("beforeAll() - enter");
    }

    @BeforeEach
    public void BeforeEach() {
        LOGGER.info("BeforeEach() - enter");
    }

}

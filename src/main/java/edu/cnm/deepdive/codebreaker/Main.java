package edu.cnm.deepdive.codebreaker;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.cnm.deepdive.codebreaker.model.Game;
import java.io.IOException;
import java.time.OffsetDateTime;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;
import edu.cnm.deepdive.codebreaker.model;


public class Main {
  static void main() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(OffsetDateTime.class, new TypeAdapter<OffsetDateTime>() {

          @Override
          public void write(JsonWriter jsonWriter, OffsetDateTime offsetDateTime)
              throws IOException {
            jsonWriter.jsonValue(offsetDateTime != null?offsetDateTime.toString():null);
          }

          @Override
          public OffsetDateTime read(JsonReader jsonReader) throws IOException {
            return OffsetDateTime.parse(jsonReader.nextString());
          }
        })
        .create();

    Interceptor interceptor = new HttpLoggingInterceptor()
        .setLevel(Level.BODY);

    okhttp3.OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build();

    edu.cnm.deepdive.codebreaker.service.DefaultApi api = new Retrofit.Builder()
        .baseUrl("https://ddc-java.services/codebreaker-solitaire/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(edu.cnm.deepdive.codebreaker.service.DefaultApi.class);

    edu.cnm.deepdive.codebreaker.model.Game game = new edu.cnm.deepdive.codebreaker.model.Game()
        .pool("ABCDE")
        .length(2);

    api.startGame(game).enqueue(new Callback<>() {
      @Override
      public void onResponse(Call<Game> call, Response<Game> response) {
        System.out.println("Created this game\n" + response.body());
      }

      @Override
      public void onFailure(Call<Game> call, Throwable throwable) {
        System.out.println("Failure");
      }
    });
  }
}

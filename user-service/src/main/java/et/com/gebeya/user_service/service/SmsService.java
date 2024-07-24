package et.com.gebeya.user_service.service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
@Service
public class SmsService {
    @Value("${afroMessage.token}")
    private String afroMessageToken;

    @Value("${afroMessage.baseUrl}")
    private String afroMessageBaseUrl;

    public void sendSms(String to, String from, String senderName, String message) throws IOException {
        OkHttpClient client=new OkHttpClient();

        HttpUrl.Builder urlBuilder= Objects.requireNonNull(HttpUrl.parse(afroMessageBaseUrl)).newBuilder();
        urlBuilder.addQueryParameter("to",to);
        urlBuilder.addQueryParameter("message", message);
        urlBuilder.addQueryParameter("from", from);
        urlBuilder.addQueryParameter("sender", senderName);

        String url=urlBuilder.build().toString();
        Request request=new Request.Builder()
                .header("Authorization","Bearer "+afroMessageToken)
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                e.printStackTrace();

                // Handle failure
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {

                System.out.println("Server response: " + response.body().string());

            }
        });


    }

}

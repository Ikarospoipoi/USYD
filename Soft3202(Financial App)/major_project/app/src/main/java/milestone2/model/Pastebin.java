package milestone2.model;


import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Pastebin {
    private String PASTEBIN_API_KEY=System.getenv("PASTEBIN_API_KEY");
    //Pastebin POST request

    /**
     *
     * @param result
     * @param argumrnt
     * @return
     * @throws IOException
     * @throws InterruptedException
     * This method is used to post the String to the pastebin
     */
    public String post(String result, String argumrnt) throws IOException, InterruptedException {
        if(argumrnt.equals("offline")){
            return "Dummy Link: https://pastebin.com/raw/dummy";
        }
        try {
            String url = "https://pastebin.com/api/api_post.php";
            String api_option = "paste";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "api_dev_key=" + PASTEBIN_API_KEY +
                                    "&api_option=" + api_option +
                                    "&api_paste_code=" + result))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response.body();



        } catch (IOException urlException) {
            urlException.printStackTrace();
        }

//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .POST(HttpRequest.BodyPublishers.ofString(data))
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
        return null;
    }





    public static void main(String[] args) throws IOException, InterruptedException {

        //System.out.println(pb.Again("test"));
    }

}

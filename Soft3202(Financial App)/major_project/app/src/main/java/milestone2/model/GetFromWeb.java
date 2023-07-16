package milestone2.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetFromWeb {
    private String INPUT_API_KEY=System.getenv("INPUT_API_KEY");

    /**
     * This method is used to search specific company by name
     * @param company_name
     * @param argument
     * @return String
     */
    public String SearchCompany(String company_name, String argument) {
        if(argument.equals("offline")){
            String text="Symbol: BA" + "\n";
            text += "------------------------------\n";
            text +="Symbol: BAA" + "\n";
            text += "------------------------------\n";
            return text;
        }
        try {
            String URL = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + company_name +"&apikey=" + INPUT_API_KEY;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            System.out.println(jsonObject);
            JsonArray lists = jsonObject.getAsJsonArray("bestMatches");
            String text = "";
            if(lists.size()==0){
                return null;
            }
            for (JsonElement element : lists) {
                text += element.getAsJsonObject().get("1. symbol").getAsString() + "\n";
                text += "------------------------------\n";
            }
            return text;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!  I am SearchCompany");
            System.out.println(e.getMessage());
            return "LoginWithToken: IOException";
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException!I am SearchCompany");
            return "LoginWithToken: URISyntaxException";
        }
    }

    /**
     * This method is used to get the company data
     * @param company_name
     * @param argument
     * @return String
     */
    public String CompanyDetail(String company_name, String argument) {
        if(argument.equals("offline")){
            return"Symbol: BA\n" +
                    "Name: Boeing Company\n" +
                    "Type: Equity\n" +
                    "Region: United States\n" +
                    "Market Open: 09:30\n" +
                    "Market Close: 16:00\n" +
                    "Time zone: UTC-04\n" +
                    "Currency: USD\n" +
                    "Match Score: 1.0000\n";
        }
        try {
            String URL = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + company_name +"&apikey=" + INPUT_API_KEY;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            System.out.println(jsonObject);
            JsonArray lists = jsonObject.getAsJsonArray("bestMatches");
            String text = "";
            if(lists.size()==0){
                return "Error";
            }
            for (JsonElement element : lists) {
                String symbol = element.getAsJsonObject().get("1. symbol").getAsString();
                if(symbol.equals(company_name)) {
                    text += "Company Symbol: "+element.getAsJsonObject().get("1. symbol").getAsString() + "\n";
                    text += "Company Name: "+element.getAsJsonObject().get("2. name").getAsString() + "\n";
                    text += "Company Type: "+element.getAsJsonObject().get("3. type").getAsString() + "\n";
                    text += "Company Region: "+element.getAsJsonObject().get("4. region").getAsString() + "\n";
                    text += "Company Market Open: "+element.getAsJsonObject().get("5. marketOpen").getAsString() + "\n";
                    text += "Company Market Close: "+element.getAsJsonObject().get("6. marketClose").getAsString() + "\n";
                    text += "Company TimeZone: "+element.getAsJsonObject().get("7. timezone").getAsString() + "\n";
                    text += "Company Currency: "+element.getAsJsonObject().get("8. currency").getAsString() + "\n";
                    text += "Company Match Score: "+element.getAsJsonObject().get("9. matchScore").getAsString();
                }
//                if(element.getAsJsonObject().get("1. Symbol").getAsString().equals(company_name)) {
            }
            System.out.println(text);
            return text;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request! I am CompanyDetail");
            System.out.println(e.getMessage());
            return "LoginWithToken: IOException";
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException! I am CompanyDetail");
            return "LoginWithToken: URISyntaxException";
        }
    }

    /**
     * Get the company's financial data
     * @param company_name
     * @param argument
     * @return
     */
    public JsonArray CompanyFinancialDetail(String company_name, String argument){
        if(argument.equals("offline")){
            JsonArray jsonArray = new JsonArray();
            return jsonArray;
        }
        try {
            String URL = "https://www.alphavantage.co/query?function=CASH_FLOW&symbol="+ company_name + "&apikey=" + INPUT_API_KEY;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
//            System.out.println(jsonObject);
            JsonArray lists = jsonObject.getAsJsonArray("quarterlyReports");
            System.out.println(lists);
            return lists;
        }catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!I am CompanyFinancialDetail");
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException! I am CompanyFinancialDetail");
            return null;
        }
    }



    public static void main(String[] args) {
        GetFromWeb getFromWeb = new GetFromWeb();
        getFromWeb.CompanyFinancialDetail("H","online");
    }

}
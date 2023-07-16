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

    public String Register(String username, String argument) {
        if(argument.equals("offline")){
            return "This is a test token, you can use it to try this game, but nothing will be recorded.\n" +
                    "Your Dummy token is: 9950eccd-5b82-44ea-9aea-6cd1a6f9013b";
        }
        try {
            if(checkvalid()) {
//                Post post = new Post(10, "My title", "My body text\nOf this post");
//                Gson gson = new Gson();
//                String postJSON = gson.toJson(post);
                HttpRequest request = HttpRequest.newBuilder(new
                                URI("https://api.spacetraders.io/users/"+username+"/claim"))
                        .POST(HttpRequest.BodyPublishers.ofString("htdgds"))
                        .build();

                HttpClient client = HttpClient.newBuilder().build();
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                String response1 = response.body();
                System.out.println(response1);
                String[] temp = response1.split("\"");
                String token = temp[3];
                if(temp[1].equals("error")){
                    System.out.println("Name already taken");
                    return "Name Has been used!";
                }
                System.out.println(token);
                return token;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because of the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }
        return null;
    }

    public boolean checkvalid(){
        try {
            HttpRequest request = HttpRequest.newBuilder(new
                    URI("https://api.spacetraders.io/game/status"))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 200 && response.statusCode() < 300) {
                return true;
            }
            return false;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return false;
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return false;
        }
    }

    public String GetLoan(String token, String argument) {
        if(argument.equals("offline")){
            return "This is Dummy Loan you can get\n" +
                    "  loans: \n" +
                    "      amount: 200000,\n" +
                    "      collateralRequired: false,\n" +
                    "      rate: 40,\n" +
                    "      termInDays: 2,\n" +
                    "      type: STARTUP";
        }
        try {
            String URL = "https://api.spacetraders.io/types/loans?token="+token;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String response1 = response.body();
            String[] temp = response1.split("\\{");
            String a = temp[2];
            System.out.println(a);
            String m = "";
            String[] temp2 = a.split(",");
            for (String b : temp2) {
                m += b + "\n";
            }
            m = m.substring(0, m.length()-3);
            System.out.println(m);
            return m;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "LoginWithToken: IOException";
        }
      catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
          System.out.println("Something went wrong with our request! URISyntaxException");
            return "LoginWithToken: URISyntaxException";
        }
    }

    public String GetShips(String token, String argument) {
        if(argument.equals("offline")) {
            return "Here is The dummy Ships in the Dummy market + \n" +
                    "class: MK-I\n" +
                    "manufacturer: Jackshaw\n" +
                    "maxCargo: 50\n" +
                    "plating: 5\n" +
                    "purchaseLocations: [{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":21125}]\n" +
                    "speed: 1\n" +
                    "type: JW-MK-I\n" +
                    "weapons: 5\n" +
                    "------------------------------\n" +
                    "class: MK-I\n" +
                    "manufacturer: Gravager\n" +
                    "maxCargo: 100\n" +
                    "plating: 10\n" +
                    "purchaseLocations: [{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":42650}]\n" +
                    "speed: 1\n" +
                    "type: GR-MK-I\n" +
                    "weapons: 5\n" +
                    "------------------------------\n" +
                    "class: MK-I\n" +
                    "manufacturer: Electrum\n" +
                    "maxCargo: 50\n" +
                    "plating: 5\n" +
                    "purchaseLocations: [{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":37750}]\n" +
                    "speed: 2\n" +
                    "type: EM-MK-I\n" +
                    "weapons: 10\n" +
                    "------------------------------\n"
                    ;
        }

        try {
            String URL = "https://api.spacetraders.io/systems/OE/ship-listings?token="+token+"&class=MK-I";
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
            System.out.println(jsonObject.toString());
            JsonArray lists = jsonObject.getAsJsonArray("shipListings");
            String text = "";
            for(JsonElement element : lists) {
                text += "class: " + element.getAsJsonObject().get("class").getAsString() + "\n";
                text += "manufacturer: " +element.getAsJsonObject().get("manufacturer").getAsString() + "\n";
                text += "maxCargo: "+ element.getAsJsonObject().get("maxCargo").getAsString() + "\n";
                text += "plating: "+ element.getAsJsonObject().get("plating").getAsString() + "\n";

                text += "purchaseLocations: "+ element.getAsJsonObject().get("purchaseLocations").toString() + "\n";
                text += "speed: "+ element.getAsJsonObject().get("speed").getAsString() + "\n";
                text += "type: "+ element.getAsJsonObject().get("type").getAsString() + "\n";
                text += "weapons: "+ element.getAsJsonObject().get("weapons").getAsString() + "\n";
                text += "------------------------------\n";
            }
            System.out.println(text);
//            String[] temp = response1.split("\\{");
//            String a = temp[2];
//            System.out.println(a);
//            String m = "";
//            String[] temp2 = a.split(",");
//            for (String b : temp2) {
//                m += b + "\n";
//            }
//            m = m.substring(0, m.length()-3);
//            System.out.println(m);
            return text;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "LoginWithToken: IOException";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "LoginWithToken: URISyntaxException";
        }
    }

    public String LoginWithToken(String token, String argument) {
        if (argument.equals("offline")) {
            return "This is a test token, you can use it to try this game, but nothing will be recorded.\n" +
                    "Here is your dummy user information. \n" +
                    "  user: \n" +
                    "    credits: 0,\n" +
                    "    joinedAt: 2021-05-13T02:29:41.741Z,\n" +
                    "    shipCount: 0,\n" +
                    "    structureCount: 0,\n" +
                    "    username: space-trader";
        }
        try {
            String URL = "https://api.spacetraders.io/my/account?token="+token;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String response1 = response.body();
            String[] temp = response1.split("\\{");
            String a = temp[2];
            System.out.println(a);
            String m = "";
            String[] temp2 = a.split(",");
            if(temp2[0].equals("\"message\":\"Token was invalid or missing from the request. Did you confirm sending the token as a query parameter or authorization header?\"")){
                return "message: Token was invalid or missing from the request. Did you confirm sending the token as a query parameter or authorization header?";
            }
            for (String b : temp2) {
                m += b + "\n";
            }
            m = m.substring(0, m.length()-4);
            System.out.println(m);


            return m;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "LoginWithToken: IOException";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "LoginWithToken: URISyntaxException";
        }
    }

    public int GetExactAmountUserHas(String token, String argument) {
        if(argument.equals("offline")){
            return 10000000;
        }
        try {
            String URL = "https://api.spacetraders.io/types/loans?token="+token;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String response1 = response.body();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response1, JsonObject.class);
            JsonArray loan = jsonObject.getAsJsonArray("loans");
            JsonElement element = loan.get(0);
            int amount = element.getAsJsonObject().get("amount").getAsInt();
            return amount;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return -1;
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return -2;
        }
    }

    public int getShipPrice(String token, String shipName, String shiplocation, String argument) {
        if(argument.equals("offline")){
            return -2;
        }
        try {
            String URL = "https://api.spacetraders.io/systems/OE/ship-listings?token="+token+"&class=MK-I";
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
            JsonArray lists = jsonObject.getAsJsonArray("shipListings");
            for (JsonElement element : lists) {
                if(element.getAsJsonObject().get("type").getAsString().equals(shipName)) {
                    JsonObject ship = element.getAsJsonObject();
                    JsonArray prices = ship.getAsJsonArray("purchaseLocations");
                    JsonElement information = prices.get(0);
                    String location = information.getAsJsonObject().get("location").getAsString();
                    if(location.equals(shiplocation)) {
                        int price = information.getAsJsonObject().get("price").getAsInt();
                        return price;
                    }
                    return 0;
                }
            }
            System.out.println("No such ship");
            return -1;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return -2;
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return -2;
        }
    }

    public String PurchaseShip(String token, String shipName, String shiplocation, int amount, String argument) {
        if(argument.equals("offline")) {
            return " user: \n" +
                    "    credits: 178875\n" +
                    "\n" +
                    " ship: \n" +
                    "    cargo: [],\n" +
                    "    class: MK-I,\n" +
                    "    id: ckno9324k0079iiop71j5nrob,\n" +
                    "    location: OE-PM-TR,\n" +
                    "    manufacturer: \"Jackshaw\",\n" +
                    "    maxCargo: 50,\n" +
                    "    plating: 5,\n" +
                    "    spaceAvailable: 50,\n" +
                    "    speed: 1,\n" +
                    "    type: JW-MK-I,\n" +
                    "    weapons: 5,\n" +
                    "    x: 21,\n" +
                    "    y: -24\n"
                    ;
        }
        try {
            String URL = "https://api.spacetraders.io/my/ships?token=" + token +"&location=" + shiplocation + "&type=" + shipName;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .POST(HttpRequest.BodyPublishers.ofString("New Ship"))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            System.out.println(jsonObject.toString());
            String text = "";
            try {
                text+="user: \n";
                text+="   credits: " + jsonObject.get("credits").getAsString() + "\n";
                text += "Ship: \n";
                text += "    cargo: " + jsonObject.getAsJsonObject("ship").get("cargo") + "\n";
                text += "    class: " + jsonObject.getAsJsonObject("ship").get("class") + "\n";
                text += "    id: " + jsonObject.getAsJsonObject("ship").get("id") + "\n";
                text += "    location: " + jsonObject.getAsJsonObject("ship").get("location") + "\n";
                text += "    manufacturer: " + jsonObject.getAsJsonObject("ship").get("manufacturer") + "\n";
                text += "    maxCargo: " + jsonObject.getAsJsonObject("ship").get("maxCargo")+ "\n";
                text += "    plating: " + jsonObject.getAsJsonObject("ship").get("plating") + "\n";
                text += "    specialAvailable: " + jsonObject.getAsJsonObject("ship").get("specialAvailable") + "\n";
                text += "    speed: " + jsonObject.getAsJsonObject("ship").get("speed") + "\n";
                text += "    type: " + jsonObject.getAsJsonObject("ship").get("type") + "\n";
                text += "    weapons: " + jsonObject.getAsJsonObject("ship").get("weapons") + "\n";
                text += "    x: " + jsonObject.getAsJsonObject("ship").get("x") + "\n";
                text += "    y: " + jsonObject.getAsJsonObject("ship").get("y") + "\n";
                return text;
            } catch (Exception e) {
                return "Not enough credits";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request!";
        }
    }

    public String TakeoutLoan(String token, String argument) {
        if(argument.equals("offline")){
            return "  credits: 200000,\n" +
                    "  loan: \n" +
                    "    due: 2021-05-15T02:32:43.269Z,\n" +
                    "    id: ckoma153c0060zbnzquw2xa29,\n" +
                    "    repaymentAmount: 280000,\n" +
                    "    status: CURRENT,\n" +
                    "    type: STARTUP";
        }
        try {
            String URL = "https://api.spacetraders.io/my/loans?token=" + token +"&type=STARTUP";
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .POST(HttpRequest.BodyPublishers.ofString("New Ship"))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
//            System.out.println(jsonObject.toString());
            try {
                String text = "";
                text += "credits:" + jsonObject.get("credits").getAsString() + "\n";
                text += "loan: \n";
                text += "     due: " + jsonObject.getAsJsonObject("loan").get("due")+ "\n";
                text += "     id: " + jsonObject.getAsJsonObject("loan").get("id")+ "\n";
                text += "     repaymentAmount: " + jsonObject.getAsJsonObject("loan").get("repaymentAmount")+  "\n";
                text += "     status: " + jsonObject.getAsJsonObject("loan").get("status")+ "\n";
                text += "     type: " + jsonObject.getAsJsonObject("loan").get("type")+ "\n";
                System.out.println(text);
                return text;
            } catch (Exception e) {

                return "Only one loan allowed at a time.";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request! URISyntaxException";
        }
    }

    public String PurchaseShipFuel(String token, String shipID, String quantity, String good, String argument) {
        if(argument.equals("offline")){
            return  "  user: {\n" +
                    "    credits: 178815\n" +
                    "  order: {\n" +
                    "    good: FUEL,\n" +
                    "    pricePerUnit: 3,\n" +
                    "    quantity: 20,\n" +
                    "    total: 60\n" +
                    "  ship: {\n" +
                    "    cargo: \n" +
                    "        good: FUEL,\n" +
                    "        quantity: 20,\n" +
                    "        totalVolume: 20\n" +
                    "    class: MK-I,\n" +
                    "    id: ckm07ezq50354ti0j1drcey9v,\n" +
                    "    location: OE-PM-TR,\n" +
                    "    manufacturer: Jackshaw,\n" +
                    "    maxCargo: 50,\n" +
                    "    plating: 5,\n" +
                    "    spaceAvailable: 30,\n" +
                    "    speed: 1,\n" +
                    "    type: JW-MK-I,\n" +
                    "    weapons: 5,\n" +
                    "    x: 21,\n" +
                    "    y: -24\n";
        }
        try {
            String URL = "https://api.spacetraders.io/my/purchase-orders?token=" + token +
                    "&shipId=" +shipID +
                    "&good=" + good +
                    "&quantity=" + quantity;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .POST(HttpRequest.BodyPublishers.ofString("New Ship"))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            System.out.println(jsonObject.toString());
            try {
                String credits = jsonObject.get("credits").getAsString();
                JsonObject order = jsonObject.get("order").getAsJsonObject();
                String goods = order.get("good").getAsString();
                String quantity1 = order.get("quantity").getAsString();
                String priceperunit = order.get("pricePerUnit").getAsString();
                String totalprice = order.get("total").getAsString();
                String text = "";
                text += "credits:" + credits + "\n";
                text += "order: \n";
                text += "     good: " + goods + "\n";
                text += "     quantity: " + quantity1 + "\n";
                text += "     pricePerUnit: " + priceperunit + "\n";
                text += "     total: " + totalprice + "\n";
                text += "Ship: \n";
                text += "     cargo: " + jsonObject.getAsJsonObject("ship").get("cargo") + "\n";
                text += "     class: " + jsonObject.getAsJsonObject("ship").get("class") + "\n";
                text += "     id: " + jsonObject.getAsJsonObject("ship").get("id") + "\n";
                text += "     location: " + jsonObject.getAsJsonObject("ship").get("location") + "\n";
                text += "     manufacturer: " + jsonObject.getAsJsonObject("ship").get("manufacturer") + "\n";
                text += "     maxCargo: " + jsonObject.getAsJsonObject("ship").get("maxCargo") + "\n";
                text += "     plating: " + jsonObject.getAsJsonObject("ship").get("plating") + "\n";
                text += "     specialAvailable: " + jsonObject.getAsJsonObject("ship").get("specialAvailable") + "\n";
                text += "     speed: " + jsonObject.getAsJsonObject("ship").get("speed") + "\n";
                text += "     type: " + jsonObject.getAsJsonObject("ship").get("type") + "\n";
                text += "     weapons: " + jsonObject.getAsJsonObject("ship").get("weapons") + "\n";
                text += "     x: " + jsonObject.getAsJsonObject("ship").get("x") + "\n";
                text += "     y: " + jsonObject.getAsJsonObject("ship").get("y") + "\n";
                return text;
            } catch (Exception e) {
                JsonObject error = jsonObject.get("error").getAsJsonObject();
                try {
                    JsonObject errordata = error.get("data").getAsJsonObject();
                    String ShipIdError = errordata.get("shipId").getAsString();
                    System.out.println("Your ship id is not valid");
                    return "Your ship id is not valid";
                } catch (Exception e1) {

                }

                try {
                    JsonObject errordata = error.get("data").getAsJsonObject();
                    String QUantityError = errordata.get("quantity").getAsString();
                    System.out.println("The quantity must be at least 1.");
                    return "The quantity must be at least 1.";
                } catch (Exception e1) {

                }


                return "You may not have enough credits or the good dose not exist";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request! URISyntaxException";
        }
    }

    public String ViewMarket(String token, String location, String argument) {
        if(argument.equals("offline")) {
            return "marketplace: \n" +
                    "        pricePerUnit: 4,\n" +
                    "        quantityAvailable: 406586,\n" +
                    "        symbol: METALS,\n" +
                    "        volumePerUnit: 1\n" +
                    "----------------------------------------------"+
                    "        pricePerUnit: 231,\n" +
                    "        quantityAvailable: 5407,\n" +
                    "        symbol: MACHINERY,\n" +
                    "        volumePerUnit: 4\n" +
                    "----------------------------------------------"+
                    "        pricePerUnit: 8,\n" +
                    "        quantityAvailable: 406586,\n" +
                    "        symbol: CHEMICALS,\n" +
                    "        volumePerUnit: 1\n" +
                    "----------------------------------------------"+
                    "        pricePerUnit: 1,\n" +
                    "        quantityAvailable: 462806,\n" +
                    "        symbol: FUEL,\n" +
                    "        volumePerUnit: 1\n" +
                    "----------------------------------------------";
        }
        try {
            String URL = "https://api.spacetraders.io/locations/"+ location+"/marketplace?token=" + token;
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
            System.out.println(jsonObject.toString());
            JsonArray jsonArray = jsonObject.get("marketplace").getAsJsonArray();
            String text = "";

            try {
                text += "marketplace: \n";
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject goods = jsonElement.getAsJsonObject();
                    text += "    symbol: " + goods.get("symbol") + "\n";
                    text += "    volumePerUnit: " + goods.get("volumePerUnit") + "\n";
                    text += "    pricePerUnit: " + goods.get("pricePerUnit") + "\n";
                    text += "    spread: " + goods.get("spread") + "\n";
                    text += "    purchasePricePerUnit: " + goods.get("purchasePricePerUnit") + "\n";
                    text += "    sellPricePerUnit: " + goods.get("sellPricePerUnit") + "\n";
                    text += "    quantityAvailable: " + goods.get("quantityAvailable") + "\n";
                    text += "----------------------------------\n";
                }
                System.out.println(text);
                return text;
            } catch (Exception e) {
                return "Not enough credits";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request!";
        }
    }

    public String GetShipsOwned(String token, String argument) {
        if(argument.equals("offline")) {
            return "ships: \n" +
                    "            cargo: [],\n" +
                    "            class: MK-I,\n" +
                    "            flightPlanId: ckon8lk9m005354nz12qjbucx,\n" +
                    "            id: ckon84fo20196vinzktdlvdlv,\n" +
                    "            location: OE-PM-TR,\n" +
                    "            manufacturer: Jackshaw,\n" +
                    "            maxCargo: 50,\n" +
                    "            plating: 5,\n" +
                    "            spaceAvailable: 32,\n" +
                    "            speed: 1,\n" +
                    "            type: JW-MK-I\",\n" +
                    "            weapons: 5,\n" +
                    "            x: 21,\n" +
                    "            y: -24\n" +
                    "----------------------------------------------" +
                    "            cargo: [],\n" +
                    "            class: MK-I,\n" +
                    "            id: ckon8tmxu00239unzl5embmcu,\n" +
                    "            location: OE-PM-TR,\n" +
                    "            manufacturer: Jackshaw,\n" +
                    "            maxCargo: 50,\n" +
                    "            plating: 5,\n" +
                    "            spaceAvailable: 50,\n" +
                    "            speed: 1,\n" +
                    "            type: JW-MK-I\",\n" +
                    "            weapons: 5,\n" +
                    "            x: 21,\n" +
                    "            y: -24";

        }
        try {
            String URL = "https://api.spacetraders.io/my/ships?token=" + token;
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
            System.out.println(jsonObject.toString());
            JsonArray ships = jsonObject.getAsJsonArray("ships");
            String text = "Ships: \n";
            try {
                for (JsonElement jsonElement : ships) {
                    JsonObject ship = jsonElement.getAsJsonObject();
                    text += "     cargo: " + ship.get("cargo") + "\n";
                    text += "     class: " + ship.get("class") + "\n";
                    text += "     id: " + ship.get("id") + "\n";
                    text += "     location: " + ship.get("location") + "\n";
                    text += "     manufacturer: " + ship.get("manufacturer") + "\n";
                    text += "     maxCargo: " + ship.get("maxCargo") + "\n";
                    text += "     plating: " + ship.get("plating") + "\n";
                    text += "     specialAvailable: " + ship.get("specialAvailable") + "\n";
                    text += "     speed: " + ship.get("speed") + "\n";
                    text += "     type: " + ship.get("type") + "\n";
                    text += "     weapons: " + ship.get("weapons") + "\n";
                    text += "     x: " + ship.get("x") + "\n";
                    text += "     y: " + ship.get("y") + "\n";
                    text += "-------------------------------------------------\n";
                }
                System.out.println(text);
                return text;
            } catch (Exception e) {
                return "Something went wrong with our request!";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request!";
        }
    }

    public String FindNearbylocation(String token, String type, String argument) {
        if(argument.equals("offline")){
            return "locations: \n" +
                    "      name: Carth,\n" +
                    "      symbol: OE-CR,\n" +
                    "      type: PLANET,\n" +
                    "      x: 16,\n" +
                    "      y: 17\n" +
                    "      name: Koria,\n" +
                    "      symbol: \"OE-KO\",\n" +
                    "      type: PLANET,\n" +
                    "      x: -48,\n" +
                    "      y: -7\n";
        }
        try {
            String URL = "https://api.spacetraders.io/systems/OE/locations?token=" + token +"&type="+ type;
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
            JsonArray locations = jsonObject.getAsJsonArray("locations");
            try {
                String text = "Locations: \n";
                for (JsonElement jsonElement : locations) {
                    JsonObject jobj = jsonElement.getAsJsonObject();
                    text += "     name: " + jobj.get("name") + "\n";
                    text += "     type: " + jobj.get("type") + "\n";
                    text += "     symbol: " + jobj.get("symbol") + "\n";
                    text += "     x: " + jobj.get("x") + "\n";
                    text += "     y: " + jobj.get("y") + "\n";
                    text += "     allowsConstruction: " + jobj.get("allowsConstruction") + "\n";
                    text += "     traits: " + jobj.get("traits") + "\n";
                    text += "----------------------------------------\n";
                }
                System.out.println(text);
                return text;
            } catch (Exception e) {

                return "Invalid Type.";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request! URISyntaxException";
        }
    }

    public String CreateFlightPlan(String token, String ShipId, String destination,String argument) {
        if(argument.equals("offline")){
            return "  flightPlan: \n" +
                    "    arrivesAt: 2021-03-08T06:41:19.658Z,\n" +
                    "    departure: OE-PM-TR,\n" +
                    "    destination: OE-PM,\n" +
                    "    distance: 1,\n" +
                    "    fuelConsumed: 1,\n" +
                    "    fuelRemaining: 19,\n" +
                    "    id: ckm07t6ki0038060jv7b2x5gk,\n" +
                    "    shipId: ckm07ezq50354ti0j1drcey9v,\n" +
                    "    terminatedAt: 2021-03-08T06:41:18.752Z,\n" +
                    "    timeRemainingInSeconds: 0";
        }
        try {
            String URL = "https://api.spacetraders.io/my/flight-plans?token=" + token +"&shipId="+ ShipId + "&destination=" + destination;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .POST(HttpRequest.BodyPublishers.ofString("New Ship"))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            System.out.println(jsonObject);
            try {
                String text = "Flight Plan: \n";
                JsonObject flightPlan = jsonObject.getAsJsonObject("flightPlan");
                text += "     arrivesAt: " + flightPlan.get("arrivesAt") + "\n";
                text += "     departure: " + flightPlan.get("departure") + "\n";
                text += "     destination: " + flightPlan.get("destination") + "\n";
                text += "     distance: " + flightPlan.get("distance") + "\n";
                text += "     fuelConsumed: " + flightPlan.get("fuelConsumed") + "\n";
                text += "     fuelRemaining: " + flightPlan.get("fuelRemaining") + "\n";
                text += "     id: " + flightPlan.get("id") + "\n";
                text += "     shipId: " + flightPlan.get("shipId") + "\n";
                text += "     terminatedAt: " + flightPlan.get("terminatedAt") + "\n";
                text += "     timeRemainingInSeconds: " + flightPlan.get("timeRemainingInSeconds") + "\n";
                System.out.println(text);
                return text;
            } catch (Exception e) {
                try{
                    String text = jsonObject.get("error").getAsJsonObject().get("data").getAsJsonObject().get("destination").getAsString();
                }catch (Exception e1){
                    return "Invalid destination.";
                }
                try{
                    String text = jsonObject.get("error").getAsJsonObject().get("data").getAsJsonObject().get("shipId").getAsString();
                }catch (Exception e1){
                    return "Invalid shipId.";
                }
                return "Start location cannot be same as the destination or you do not have enough fuel.";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request! URISyntaxException";
        }
    }

    public String ListFlightPlan(String token, String FlightPlanId, String argument) {
        if(argument.equals("offline")){
            return "  flightPlan: \n" +
                    "    arrivesAt: 2021-03-08T06:41:19.658Z,\n" +
                    "    departure: OE-PM-TR,\n" +
                    "    destination: OE-PM,\n" +
                    "    distance: 1,\n" +
                    "    fuelConsumed: 1,\n" +
                    "    fuelRemaining: 19,\n" +
                    "    id: ckm07t6ki0038060jv7b2x5gk,\n" +
                    "    shipId: ckm07ezq50354ti0j1drcey9v,\n" +
                    "    terminatedAt: 2021-03-08T06:41:18.752Z,\n" +
                    "    timeRemainingInSeconds: 0";
        }
        try {
            String URL = "https://api.spacetraders.io/my/flight-plans/" + FlightPlanId +"?token="+ token;
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
            try {
                String text = "Flight Plan: \n";
                JsonObject flightPlan = jsonObject.getAsJsonObject("flightPlan");
                text += "     arrivesAt: " + flightPlan.get("arrivesAt") + "\n";
                text += "     departure: " + flightPlan.get("departure") + "\n";
                text += "     destination: " + flightPlan.get("destination") + "\n";
                text += "     distance: " + flightPlan.get("distance") + "\n";
                text += "     fuelConsumed: " + flightPlan.get("fuelConsumed") + "\n";
                text += "     fuelRemaining: " + flightPlan.get("fuelRemaining") + "\n";
                text += "     id: " + flightPlan.get("id") + "\n";
                text += "     shipId: " + flightPlan.get("shipId") + "\n";
                text += "     terminatedAt: " + flightPlan.get("terminatedAt") + "\n";
                text += "     timeRemainingInSeconds: " + flightPlan.get("timeRemainingInSeconds") + "\n";
                System.out.println(text);
                return text;
            } catch (Exception e) {
                try{
                    String text = jsonObject.get("error").getAsJsonObject().get("data").getAsJsonObject().get("destination").getAsString();
                }catch (Exception e1){
                    return "Invalid destination.";
                }
                try{
                    String text = jsonObject.get("error").getAsJsonObject().get("data").getAsJsonObject().get("shipId").getAsString();
                }catch (Exception e1){
                    return "Invalid shipId.";
                }
                return "Start location cannot be same as the destination or you do not have enough fuel.";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request! URISyntaxException";
        }
    }

    public String SellGoods(String token, String shipID, String quantity, String good, String argument) {
        if(argument.equals("offline")){
            return  "credits: 178755,\n" +
                    "  order: \n" +
                    "    good: METALS,\n" +
                    "    pricePerUnit: 21,\n" +
                    "    quantity: 25,\n" +
                    "    total: 630\n" +
                    "  ship: \n" +
                    "    cargo: \n" +
                    "        good: FUEL,\n" +
                    "        quantity: 18,\n" +
                    "        totalVolume: 18\n" +
                    "    class: MK-I,\n" +
                    "    id: ckne4w8me01141ds62dnui0c8,\n" +
                    "    location: OE-PM,\n" +
                    "    manufacturer: Jackshaw,\n" +
                    "    maxCargo: 50,\n" +
                    "    plating: 5,\n" +
                    "    spaceAvailable: 32,\n" +
                    "    speed: 1,\n" +
                    "    type: JW-MK-I,\n" +
                    "    weapons: 5,\n" +
                    "    x: 20,\n" +
                    "    y: -25";
        }
        try {
            String URL = "https://api.spacetraders.io/my/sell-orders?token=" + token +
                    "&shipId=" +shipID +
                    "&good=" + good +
                    "&quantity=" + quantity;
//            System.out.println(URL);
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(URL))
                    .POST(HttpRequest.BodyPublishers.ofString("New Ship"))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            System.out.println(jsonObject.toString());
            try {
                String text = "";
                String credits = jsonObject.get("credits").getAsString();
                JsonObject order = jsonObject.get("order").getAsJsonObject();
                String goods = order.get("good").getAsString();
                String quantity1 = order.get("quantity").getAsString();
                String priceperunit = order.get("pricePerUnit").getAsString();
                String totalprice = order.get("total").getAsString();
                text += "credits:" + credits + "\n";
                text += "order: \n";
                text += "     good: " + goods + "\n";
                text += "     quantity: " + quantity1 + "\n";
                text += "     pricePerUnit: " + priceperunit + "\n";
                text += "     total: " + totalprice + "\n";
                text += "Ship: \n";
                text += "     cargo: " + jsonObject.getAsJsonObject("ship").get("cargo") + "\n";
                text += "     class: " + jsonObject.getAsJsonObject("ship").get("class") + "\n";
                text += "     id: " + jsonObject.getAsJsonObject("ship").get("id") + "\n";
                text += "     location: " + jsonObject.getAsJsonObject("ship").get("location") + "\n";
                text += "     manufacturer: " + jsonObject.getAsJsonObject("ship").get("manufacturer") + "\n";
                text += "     maxCargo: " + jsonObject.getAsJsonObject("ship").get("maxCargo") + "\n";
                text += "     plating: " + jsonObject.getAsJsonObject("ship").get("plating") + "\n";
                text += "     specialAvailable: " + jsonObject.getAsJsonObject("ship").get("specialAvailable") + "\n";
                text += "     speed: " + jsonObject.getAsJsonObject("ship").get("speed") + "\n";
                text += "     type: " + jsonObject.getAsJsonObject("ship").get("type") + "\n";
                text += "     weapons: " + jsonObject.getAsJsonObject("ship").get("weapons") + "\n";
                text += "     x: " + jsonObject.getAsJsonObject("ship").get("x") + "\n";
                text += "     y: " + jsonObject.getAsJsonObject("ship").get("y") + "\n";
                return text;
            } catch (Exception e) {
                JsonObject error = jsonObject.get("error").getAsJsonObject();
                try {
                    JsonObject errordata = error.get("data").getAsJsonObject();
                    String ShipIdError = errordata.get("shipId").getAsString();
                    System.out.println("Your ship id is not valid");
                    return "Your ship id is not valid";
                } catch (Exception e1) {

                }

                try {
                    JsonObject errordata = error.get("data").getAsJsonObject();
                    String QUantityError = errordata.get("quantity").getAsString();
                    System.out.println("The quantity must be at least 1.");
                    return "The quantity must be at least 1.";
                } catch (Exception e1) {

                }


                return "You may not have enough credits or the good dose not exist";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            return "Something went wrong with our request!";
        }
        catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            System.out.println("Something went wrong with our request! URISyntaxException");
            return "Something went wrong with our request! URISyntaxException";
        }
    }











    public static void main(String[] args) {
        GetFromWeb g = new GetFromWeb();
        g.checkvalid();
//      g.Register("nymph10","online");
//        //token da18300d-ce6b-4d55-bbce-9ba204960f33
//        g.LoginWithToken("ffe03569-b686-4d8d-bb08-39606d5c5baa");
//        g.GetLoan("8e928884-a103-43e6-b44f-b07710917d11","online");
        g.TakeoutLoan("8e928884-a103-43e6-b44f-b07710917d11","online");
//        g.getShipPrice("ffe03569-b686-4d8d-bb08-39606d5c5baa", "GR-MK-I");
//        g.GetShips("8e928884-a103-43e6-b44f-b07710917d11", "online");
       //shipID:cl1hidbsc20729915s6rn3v4ul6

        //g.PurchaseShip("8e928884-a103-43e6-b44f-b07710917d11", "GR-MK-I", "OE-PM-TR", 1, "online");
//        g.PurchaseShipFuel("8e928884-a103-43e6-b44f-b07710917d11", "cl1hhi3kf16573215s63x2p23g6", 1, "online");
//        g.PurchaseShipFuel("8e928884-a103-43e6-b44f-b07710917d11", "cl1hidbsc20729915s6rn3v4ul6", "-1", "online");

//        g.ViewMarket("ffe03569-b686-4d8d-bb08-39606d5c5baa", "OE-PM-TR", "o");
        //g.CreateFlightPlan("8e928884-a103-43e6-b44f-b07710917d11", "cl1hidbsc20729915s6rn3v4ul6", "OE-PM-TR", "online");
        g.SellGoods("8e928884-a103-43e6-b44f-b07710917d11", "cl1hidbsc20729915s6rn3v4ul6", "1","METALS", "online");
    }
}

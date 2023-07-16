package milestone2.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import milestone2.model.*;
import com.google.gson.JsonArray;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

import static milestone2.view.LoginPage.mediaPlayer;

public class CompanyDetail {
    private HandleInput handleInput = new HandleInput();
    CompanyDetailHandler controller;
    PasteHandler pasteController;
    Pastebin pastebin = new Pastebin();
    PasatebinString pasatebinString ;
    private Database database = new Database();
    private String argument;
    private String companysymbol;
    private JsonArray jsonarray;
    private String argumrnt_pastebin;
    public ArrayList<String> date;
    public ArrayList<Long> capitalexpenditures;
    public ArrayList<Long> operatingcashflow;
    public ArrayList<Long> netincome;
    public ArrayList<Long> profitLoss;
    public ArrayList<Long> dividendpayout;
    private CreateChart createchart = new CreateChart();
    public Label companydetail;
    public ProgressIndicator progressIndicator = new ProgressIndicator();
    private String is_cahce = "false";
    private String saved_company_name;
    private CompanyPojo Saved_companyPojo;
    private CompanyPojo companyPojo;
    private Chart dividendpayout_chart;
    private Chart profitloss_chart;
    private Chart netincome_chart;
    private Chart operatingcashflow_chart;
    private Chart capitalexpenditures_chart;
    private String result;
    String type;
    private String flag;


    public CompanyDetail(String argument, String argumrnt_pastebin, String companysymbol,CompanyPojo Saved_companyPojo,String result,String flag,String type) {
        this.argument = argument;
        this.argumrnt_pastebin = argumrnt_pastebin;
        this.companysymbol = companysymbol.replace("\n", "");
        this.Saved_companyPojo = Saved_companyPojo;
        this.result = result;
        this.flag = flag;
        this.type = type;
        System.out.println("flag in constructor: "+flag);

//        try {
//            System.out.println(Saved_companyPojo.companyName);
//            System.out.println(Saved_companyPojo.getCapitalexpenditures().get(Saved_companyPojo.getCapitalexpenditures().size() - 1));
//            System.out.println(Saved_companyPojo.getOperatingcashflow().get(Saved_companyPojo.getOperatingcashflow().size() - 1));
//            System.out.println(Saved_companyPojo.getNetincome().get(Saved_companyPojo.getNetincome().size() - 1));
//            System.out.println(Saved_companyPojo.getProfitLoss().get(Saved_companyPojo.getProfitLoss().size() - 1));
//            System.out.println(Saved_companyPojo.getDividendpayout().get(Saved_companyPojo.getDividendpayout().size() - 1));
//        } catch (Exception e) {
//            System.out.println("No data");
//        }
    }

    public void Start(Stage primaryStage) {
        // Create a label
        primaryStage.setTitle("Search Page");

        Scene scene = MainScence(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

        Cache_hit();
    }

    /**
     * Create the main scene
     * @param primaryStage the stage
     * @return
     */
    public Scene MainScence(Stage primaryStage) {

        try{
            System.out.println(Saved_companyPojo.companyName);
        }catch (Exception e){
            System.out.println("No saved company");
        }

        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        Menu menu = new Menu("Menu");
        MenuItem menuItem1 = new MenuItem("About");
        menuItem1.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("About");
            alert.setContentText("Author Name: Frank\nApplication Name: Alpha Vantage\nVersion: 1.0\nReference:https://stackoverflow.com/questions/52045233/adding-menubar-to-gridpane\nhttp://www.javafxchina.net/blog/2015/04/doc03_linechart/");
            alert.showAndWait();
        });

        menu.getItems().add(menuItem1);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        Image img = new Image("pause.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        Button btn8 = new Button();
        btn8.setGraphic(view);
        btn8.setOnAction(e -> {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }else{
                mediaPlayer.play();
            }
        });
        HBox hBox = new HBox();
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        HBox.setHgrow(btn8, Priority.NEVER);
        hBox.getChildren().add(menuBar);
        hBox.getChildren().add(btn8);

        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);


        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        VBox vBox1 = new VBox();
        vBox1.setSpacing(10);
        vBox1.setAlignment(Pos.CENTER);

        Text scenetitle = new Text("Company Detail");

        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        vBox1.getChildren().add(scenetitle);

        companydetail = new Label();
//        if(controller.CompanyDetail() != null){
//            CompanyDetail.setText(controller.CompanyDetail());
//        }else{
//            CompanyDetail.setText("Error: This is a free version of AlphaVantage. \nYou can call it 5 times per minute. \nIf you want to use it more than 5 times per minute, \nyou need to upgrade to AlphaVantage Premium.");
//        }

        vBox1.getChildren().add(companydetail);
        VBox vBox2 = new VBox();
        vBox2.setSpacing(10);
        vBox2.setAlignment(Pos.TOP_CENTER);
        Button btn1 = new Button("Operating CashFlow");
        btn1.setMaxSize(150, 30);
        btn1.setMinSize(150, 30);
        System.out.println(operatingcashflow);
        System.out.println(date);;
        btn1.setOnAction(e -> {
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
            LineChart chart = createchart.getStringNumberLineChart(operatingcashflow,date,"Operating Cashflow");
            grid.add(chart, 0, 1);
            operatingcashflow_chart = chart;
        });
        vBox2.getChildren().add(btn1);

        Button btn2 = new Button("Capital Expenditures");
        btn2.setMaxSize(150, 30);
        btn2.setMinSize(150, 30);
        btn2.setOnAction(e -> {
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
            LineChart chart = createchart.getStringNumberLineChart(capitalexpenditures,date,"Capital Expenditures");
            grid.add(chart, 0, 1);
            capitalexpenditures_chart = chart;
        });
        vBox2.getChildren().add(btn2);

        Button btn3 = new Button("Profit Loss");
        btn3.setMaxSize(150, 30);
        btn3.setMinSize(150, 30);
        btn3.setOnAction(e -> {
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
            LineChart chart = createchart.getStringNumberLineChart(profitLoss,date,"Profit Loss");
            grid.add(chart, 0, 1);
            profitloss_chart = chart;
        });
        vBox2.getChildren().add(btn3);

        VBox vBox3 = new VBox();
        vBox3.setSpacing(10);
        vBox3.setAlignment(Pos.TOP_CENTER);
        Button btn4 = new Button("Divident Payout");
        btn4.setMaxSize(150, 30);
        btn4.setMinSize(150, 30);
        btn4.setOnAction(e -> {
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
            LineChart chart = createchart.getStringNumberLineChart(dividendpayout,date,"DividentPayout");
            grid.add(chart, 0, 1);
            dividendpayout_chart = chart;
            });
        vBox3.getChildren().add(btn4);

        Button btn5 = new Button("Net Income");
        btn5.setMaxSize(150, 30);
        btn5.setMinSize(150, 30);
        btn5.setOnAction(e -> {
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
            LineChart chart = createchart.getStringNumberLineChart(netincome,date,"Net Income");
            grid.add(chart, 0, 1);
            netincome_chart = chart;
        });
        vBox3.getChildren().add(btn5);

        Button btn6 = new Button("Continue Searching");
        btn6.setMaxSize(150, 30);
        btn6.setMinSize(150, 30);
        btn6.setOnAction(e -> {
            System.out.println("I am here and this is flag: " + flag);
            MainMenu mainMenu = new MainMenu(argument,argumrnt_pastebin,Saved_companyPojo,result,flag,type);
            mainMenu.start(primaryStage);
        });
        vBox3.getChildren().add(btn6);

        progressIndicator.setMaxSize(300, 60);
        progressIndicator.setMinSize(300, 60);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
        vBox3.getChildren().add(progressIndicator);
        hBox1.getChildren().add(vBox1);
        hBox1.getChildren().add(vBox2);
        hBox1.getChildren().add(vBox3);
        grid.add(hBox1, 0, 0);

        Label label = new Label("Paste to pastebin(Link will show below)");
        vBox1.getChildren().add(label);

        TextArea textArea = new TextArea();
        textArea.setMaxSize(300,20);

        Button btn7 = new Button("Paste");
        btn7.setOnAction(e -> {
            pasatebinString = new PasatebinString(companysymbol, argumrnt_pastebin,argument, jsonarray, dividendpayout,date,profitLoss,capitalexpenditures,netincome,operatingcashflow);
            String result = pasatebinString.FinalOutput();
            Mytask2 task2 = new Mytask2(result,argumrnt_pastebin,this,textArea);
            Thread thread = new Thread(task2);
            thread.start();
        });
        vBox1.getChildren().add(textArea);
        vBox1.getChildren().add(btn7);

        Button button8 = new Button("Save Current Company for later comparison");
        Label label1 = new Label(result);
        button8.setOnAction(e -> {
            SetSavedCompany();
            System.out.println(type);
            if(netincome.get(netincome.size() - 1) == -1){
                if(type.equals("Net Income")){
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: No Data" ;
                }else if(type.equals("Operating Cashflow")){
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: No Data" ;
                }else if(type.equals("Capital Expenditures")){
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + "No Data" ;
                }else if(type.equals("Profit Loss")){
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + "No Data" ;
                }else if(type.equals("Dividend Payout")){
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + "No Data" ;
                }
            }else {
                if (type.equals("Net Income")) {
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + netincome.get(netincome.size() - 1);
                } else if (type.equals("Operating Cashflow")) {
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + operatingcashflow.get(operatingcashflow.size() - 1);
                } else if (type.equals("Capital Expenditures")) {
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + capitalexpenditures.get(capitalexpenditures.size() - 1);
                } else if (type.equals("Profit Loss")) {
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + profitLoss.get(profitLoss.size() - 1);
                } else if (type.equals("Dividend Payout")) {
                    result = "Saved Company name: " + companysymbol + "\nSaved Type: " + type + "\nSaved value: " + dividendpayout.get(dividendpayout.size() - 1);
                }
            }
            label1.setText(result);
            flag = "True";
        });
        ;
        vBox1.getChildren().add(button8);
        vBox3.getChildren().add(label1);


        VBox.setVgrow(grid, Priority.ALWAYS);
        vBox.getChildren().add(grid);
        Scene scene = new Scene(vBox, 780, 820);
        System.out.println("This is flag: "+flag);
        if(flag == "True" && argument.equals("offline")){
            AlertCompareResult(companyPojo,Saved_companyPojo, argument);
        }
        return scene;
    }

    /**
     * This method is used to set the saved company to the saved company pojo
     */
    private void SetSavedCompany() {
        saved_company_name = companysymbol;
        //alert choose one graph to save
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose one graph to save");
        alert.setHeaderText("Choose one graph to save");
        alert.setContentText("Choose one graph to save");
        ButtonType buttonTypeOne = new ButtonType("Dividend Payout");
        ButtonType buttonTypeTwo = new ButtonType("Capital Expenditures");
        ButtonType buttonTypeThree = new ButtonType("Net Income");
        ButtonType buttonTypeFour = new ButtonType("Profit Loss");
        ButtonType buttonTypeFive = new ButtonType("Operating CashFlow");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour, buttonTypeFive);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            type = "Dividend Payout";
            Saved_companyPojo = new CompanyPojo(saved_company_name,type,dividendpayout,dividendpayout_chart);
        } else if (result.get() == buttonTypeTwo) {
            type = "Capital Expenditures";
            Saved_companyPojo = new CompanyPojo(saved_company_name,type,capitalexpenditures,capitalexpenditures_chart);
        } else if (result.get() == buttonTypeThree) {
            type = "Net Income";
            Saved_companyPojo = new CompanyPojo(saved_company_name,type,netincome,netincome_chart);
        } else if (result.get() == buttonTypeFour) {
            type = "Profit Loss";
            Saved_companyPojo = new CompanyPojo(saved_company_name,type,profitLoss,profitloss_chart);
        } else if (result.get() == buttonTypeFive) {
            type = "Operating Cashflow";
            Saved_companyPojo = new CompanyPojo(saved_company_name,type,operatingcashflow, operatingcashflow_chart);
        }
    }
    /**
     * This method is used to alert the compare result
     * @param companyPojo
     * @param Saved_companyPojo
     * @param argument
     */
    public void AlertCompareResult(CompanyPojo Saved_companyPojo, CompanyPojo companyPojo,String argument) {
        CompareCompany compareCompany = new CompareCompany(companyPojo,Saved_companyPojo,argument);
        String result = compareCompany.FinalResult();
        if(!result.equals("False")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Company Compare Result");
            alert.setHeaderText("Company Compare Result");
            alert.setContentText(result);
            alert.showAndWait();
        }

    }

    /**
     *This method is used to handle the input from the web
     */
    public void HandleInput() {
        this.netincome = handleInput.GetnetIncome(jsonarray, argument);
        this.date = handleInput.GetYear(jsonarray, argument);
        this.capitalexpenditures = handleInput.GetcapitalExpenditures(jsonarray, argument);
        this.operatingcashflow = handleInput.OperatingCashFlow(jsonarray, argument);
        this.profitLoss = handleInput.GetprofitLoss(jsonarray, argument);
        this.dividendpayout = handleInput.GetDividendPayout(jsonarray, argument);
    }

    /**
     *This method is used to set the jsonarray
     */
    public void set_jsonarray(JsonArray jsonArray){
        this.jsonarray = jsonArray;
    }

    /**
     *This method is used to handle the situation when cache is hit
     */
    public void Cache_hit(){
        this.controller = new CompanyDetailHandler(companysymbol, argument);
        if(argument.equals("online")) {
            if (database.SelectDate(companysymbol).size() == 0) {
                Mytask1 mytask1 = new Mytask1(jsonarray,controller,this,"add",argument,companysymbol,"online",Saved_companyPojo);
                Thread thread = new Thread(mytask1);
                thread.start();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cache Hit");
                alert.setContentText("Do you want to use the cache?");
                ButtonType okButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");

                alert.getButtonTypes().setAll(okButton, noButton);
                alert.showAndWait().ifPresent(type -> {
                    if (alert.getResult() == okButton) {
                        setIs_cahce("true");
                        Mytask1 mytask1 = new Mytask1(jsonarray,controller,this,"add",argument,companysymbol,"cache", Saved_companyPojo);
                        Thread thread = new Thread(mytask1);
                        thread.start();
                    } else if (alert.getResult() == noButton) {
                        Mytask1 mytask1 = new Mytask1(jsonarray,controller,this,"update",argument,companysymbol,"online", Saved_companyPojo);
                        Thread thread = new Thread(mytask1);
                        thread.start();
                    }
                });
            }
        }else{
            HandleInput();
        }
        String text = controller.CompanyDetail();
        companydetail.setText(text);

    }

    /**
     *This method is used to set the cache hit parameter
     */
    public void setIs_cahce(String is_cache){
        this.is_cahce = is_cache;
    }
}

class Mytask1 extends Task<Void> {

    private JsonArray jsonarray;
    private CompanyDetailHandler controller;
    private CompanyDetail companyDetail;
    private String update_or_add;
    private SaveCache savecache;
    private String argument;
    private String company_symbol;
    private String cache_or_online;
    private DatabaseHandler database_handler;
    private CompanyPojo saved_companyPojo;


    public Mytask1(JsonArray jsonArray, CompanyDetailHandler controller, CompanyDetail companyDetail, String update_or_add, String argument, String company_symbol, String cache_or_online, CompanyPojo saved_companyPojo) {
        this.jsonarray = jsonArray;
        this.controller = controller;
        this.companyDetail = companyDetail;
        this.update_or_add = update_or_add;
        this.argument = argument;
        this.company_symbol = company_symbol;
        this.cache_or_online = cache_or_online;
        this.database_handler = new DatabaseHandler(company_symbol);
        this.saved_companyPojo = saved_companyPojo;

    }

    /**
     *This method is used to create a new thread to get all kind of data from all kinds of sources(online and cache)
     */
    @Override
    protected Void call() throws Exception {
        companyDetail.progressIndicator.setVisible(true);
        if(cache_or_online.equals("online")){
            jsonarray = controller.CompanyFinacial();
        }else if(cache_or_online.equals("cache")){
            companyDetail.netincome =database_handler.GetNetIncome();
            companyDetail.date = database_handler.GetDate();
            companyDetail.capitalexpenditures = database_handler.GetCapitalExpenditure();
            companyDetail.operatingcashflow = database_handler.GetOperatingCashflow();
            companyDetail.profitLoss = database_handler.GetProfitLoss();
            companyDetail.dividendpayout = database_handler.GetDividendPayout();
        }
        return null;
    }

    /**
     *This method is used to handle when the thread is finished
     */
    @Override
    protected void succeeded() {
        Platform.runLater(() -> {
            if(cache_or_online.equals("online")) {
                companyDetail.set_jsonarray(jsonarray);
                savecache = new SaveCache(argument, company_symbol, jsonarray);
                boolean check = savecache.Change_Cache(update_or_add);
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("This Company has not data or you have hit 5 limit of request per minute. This will cause nothing will be shown in the chart.");
                    alert.showAndWait();
                }
                companyDetail.HandleInput();
            }
            companyDetail.progressIndicator.setVisible(false);

            CompanyPojo companyPojo;
            CreateChart createchart = new CreateChart();
            if(companyDetail.type == "Net Income"){
                LineChart lineChart = createchart.getStringNumberLineChart( companyDetail.netincome,companyDetail.date, "Net Income");
                companyPojo = new CompanyPojo(company_symbol, companyDetail.type, companyDetail.netincome,lineChart );
                companyDetail.AlertCompareResult(companyPojo,saved_companyPojo,argument);
            }else if(companyDetail.type == "Capital Expenditure"){
                LineChart lineChart = createchart.getStringNumberLineChart( companyDetail.capitalexpenditures,companyDetail.date, "Capital Expenditures");
                companyPojo = new CompanyPojo(company_symbol, companyDetail.type, companyDetail.capitalexpenditures, lineChart);
                companyDetail.AlertCompareResult(companyPojo,saved_companyPojo,argument);
            }else if(companyDetail.type == "Operating Cashflow"){
                LineChart lineChart = createchart.getStringNumberLineChart( companyDetail.operatingcashflow,companyDetail.date, "Operating Cashflow");
                companyPojo = new CompanyPojo(company_symbol, companyDetail.type, companyDetail.operatingcashflow, lineChart);
                companyDetail.AlertCompareResult(companyPojo,saved_companyPojo,argument);
            }else if(companyDetail.type == "Profit Loss"){
                LineChart lineChart = createchart.getStringNumberLineChart( companyDetail.profitLoss,companyDetail.date, "Profit Loss");
                companyPojo = new CompanyPojo(company_symbol, companyDetail.type, companyDetail.profitLoss, lineChart);
                companyDetail.AlertCompareResult(companyPojo,saved_companyPojo,argument);
            }else if(companyDetail.type == "Dividend Payout"){
                LineChart lineChart = createchart.getStringNumberLineChart( companyDetail.dividendpayout,companyDetail.date, "Dividend Payout");
                companyPojo = new CompanyPojo(company_symbol, companyDetail.type, companyDetail.dividendpayout, lineChart);
                companyDetail.AlertCompareResult(companyPojo,saved_companyPojo,argument);
            }
        });
    }
}

class Mytask2 extends Task<Void> {
    private String result;
    private String argument;
    private CompanyDetail companyDetail;
    private PasteHandler pasteHandler;
    private Pastebin paste = new Pastebin();
    private TextArea textArea;

    public Mytask2(String result,String argumrnt_pastebin, CompanyDetail companyDetail, TextArea textArea){
        this.result = result;
        this.argument = argumrnt_pastebin;
        this.companyDetail = companyDetail;
        this.textArea = textArea;
    }

    /**
     *This method is used to create a new thread to paste the data to pastebin
     */
    @Override
    protected Void call() throws Exception {
        companyDetail.progressIndicator.setVisible(true);
        pasteHandler = new PasteHandler(result, argument,paste);
        String url = pasteHandler.postResult();
        textArea.setText(url);
        return null;
    }

    /**
     *This method is used to handle when the thread is finished
     */
    @Override
    protected void succeeded() {
        Platform.runLater(() -> {
            companyDetail.progressIndicator.setVisible(false);
        });
    }
}

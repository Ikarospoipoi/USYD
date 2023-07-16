package milestone2.model;

import java.io.IOException;

public class PasteHandler {
    private String result;
    private String argument;
    private Pastebin pastebin;

    public PasteHandler(String result, String argument, Pastebin pastebin) {
        this.pastebin = pastebin;
        this.result = result;
        this.argument = argument;
    }

    /**
     * This method is used to post the result to the pastebin.
     * @return GetFromWeb
     */
    public String postResult() throws IOException, InterruptedException {
        return getPastebin().post(result, argument);
    }

    /**
     * This method is used to set the pastebin.
     * @return GetFromWeb
     */
    public void setPastebin(Pastebin pastebin) {
        this.pastebin = pastebin;
    }

    /**
     * This method is used to get the pastebin.
     * @return GetFromWeb
     */
    public Pastebin getPastebin() {
        return pastebin;
    }
}

package lander.expandable;

/**
 * Created by magdi on 01/08/2016.
 */
public class BodyContent {
    private String content;
    private String key;

    public BodyContent(){}

    public BodyContent(String content,String key) {
        this.content = content;
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

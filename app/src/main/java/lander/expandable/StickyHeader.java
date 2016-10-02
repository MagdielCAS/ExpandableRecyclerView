package lander.expandable;

/**
 * Created by magdi on 02/10/2016.
 */
public class StickyHeader {
    private String header;
    private int sectionFirstPosition;

    public StickyHeader(){

    }

    public StickyHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setSectionFirstPosition(int sectionFirstPosition) {
        this.sectionFirstPosition = sectionFirstPosition;
    }

    public int getSectionFirstPosition() {
        return sectionFirstPosition;
    }
}

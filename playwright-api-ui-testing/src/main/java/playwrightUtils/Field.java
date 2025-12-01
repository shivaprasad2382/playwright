package playwrightUtils;

public class Field {

    private String name;
    private String locatorType;
    private String locatorValue;
    private String type;
    private String value;
//    private String xpath;

    public Field() {
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getLocatorType() {
        return locatorType;
    }

    public String getLocatorValue() {
        return locatorValue;
    }

//    public String getxPath() {
//        return xpath;
//    }

}

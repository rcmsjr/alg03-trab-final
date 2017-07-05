package gtfs.entities;

/**
 * Created by robertomariano on 04/07/17.
 */
public class Route extends EntityBase {
    private String shortName;
    private String longName;

    public Route(String id, String shortName, String longName) {
        super(id);
        this.shortName = shortName;
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
}

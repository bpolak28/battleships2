package pl.bpol.model;

public class Field {

    private String type;

    private String location;

    private boolean hit = false;

    public Field(String type) {
        this.type = type;
    }

    public Field(){

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public String toString() {
        return "Field{" +
                "type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", hit=" + hit +
                '}';
    }
}

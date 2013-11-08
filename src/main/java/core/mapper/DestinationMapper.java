package core.mapper;

import javax.xml.bind.annotation.XmlRootElement;

import model.Destination;

@XmlRootElement(name = "destination")
public class DestinationMapper extends Mapper<Destination> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Destination map(Destination origin) {
        origin.setName(name);
        return origin;
    }

}

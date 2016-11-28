package tree;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.nio.file.Path;

/**
 * Created by master on 21.11.2016.
 */


public class AnyInfo {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
    @JacksonXmlProperty(isAttribute = true)
    private Path path;
    public AnyInfo(String name,Path path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return name;
    }
}

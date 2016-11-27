package tree;

import java.nio.file.Path;

/**
 * Created by master on 21.11.2016.
 */
public class AnyInfo {

    private String name;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

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

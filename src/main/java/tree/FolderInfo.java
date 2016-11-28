package tree;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.nio.file.Path;

/**
 * Created by master on 21.11.2016.
 */

@JacksonXmlRootElement(localName = "folder")
public class FolderInfo extends AnyInfo {

    public FolderInfo(String name, Path path) {
        super(name, path);
    }
}

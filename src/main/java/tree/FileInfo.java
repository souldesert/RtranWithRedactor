package tree;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.nio.file.Path;

/**
 * Created by master on 21.11.2016.
 */
@JacksonXmlRootElement(localName = "file")
public class FileInfo extends AnyInfo {

    public FileInfo(String name, Path path) {
        super(name,path);
    }
}

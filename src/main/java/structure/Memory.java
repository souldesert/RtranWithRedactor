package structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by Alex on 27.11.2016.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "memory")
public class Memory {

    @JacksonXmlProperty(isAttribute = true)
    String type;

    @JacksonXmlProperty(isAttribute = true)
    String name;

    @JacksonXmlProperty(isAttribute = true)
    String leftName;

    @JacksonXmlProperty(isAttribute = true)
    String rightName;

    @JacksonXmlProperty
    ColumnsName columnsName;

    public Memory() {
    }

    public Memory(String name) {
        this.name = name;
    }

    public Memory(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Memory(String type, String leftName, String rightName) {
        this.type = type;
        this.leftName = leftName;
        this.rightName = rightName;
    }

    public Memory(String type, String name, ColumnsName columnsName) {
        this.type = type;
        this.name = name;
        this.columnsName = columnsName;
    }
}

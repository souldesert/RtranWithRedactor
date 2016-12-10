package structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Created by Alex on 27.11.2016.
 */

//@Value.Immutable
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "arm")
public class Arm {

    @JacksonXmlProperty(isAttribute = true)
    String begin;

    @JsonDeserialize(as = ImmutableObservableList.class)
    @JacksonXmlProperty
    ObservableList<structure.Edge> edge = FXCollections.observableArrayList();         // массив

    public Arm(String begin) {
        this.begin = begin;

    }

    public void addEdge(structure.Edge edge) {
        this.edge.add(edge);
    }

    public void updateEdge(structure.Edge edge) {
        this.edge.add(this.edge.size()-1, edge);
    }

    public structure.Edge getEdge(int index) {
        return edge.get(index);
    }

}

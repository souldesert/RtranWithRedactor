package project; /**
 * Created by svkreml on 20.11.2016.
 */

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class ProjectStructure {
    @JacksonXmlProperty(isAttribute = true)
    String root;
    @JacksonXmlProperty
    ArrayList<ProjFile> folder= new ArrayList<>();
    @JacksonXmlProperty
    ArrayList<ProjFile> file= new ArrayList<>();

    public ProjectStructure() {
    }

    public ProjectStructure(String root) {
        this.root = root;
    }

    public ArrayList<ProjFile> getChildren() {
        ArrayList<ProjFile> output = new ArrayList<ProjFile>();
        output.addAll(folder);
        output.addAll(file);
        return output;
    }
}

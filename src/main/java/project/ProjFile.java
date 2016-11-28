package project;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

/**
 * Created by svkreml on 20.11.2016.
 */
public class ProjFile {
    @JacksonXmlProperty(isAttribute = true)
    String name;
    @JacksonXmlProperty(isAttribute = true)
    String type;
    @JacksonXmlProperty(localName = "file")
    ArrayList<ProjFile> file = new ArrayList<>();
    @JacksonXmlProperty(localName = "folder")
    ArrayList<ProjFile> folder = new ArrayList<>();

    public ProjFile() {
    }

    @Override
    public String toString() {
        if (file == null && folder == null)
            return "file: " + name + ", atr: " + type;
        else
            return "folder: " + name + ", atr: " + type;
    }

    public ArrayList<ProjFile> getChildren() {
        ArrayList<ProjFile> output = new ArrayList<ProjFile>();
        output.addAll(folder);
        output.addAll(file);
        return output;
    }

    public boolean isLeaf() {
        if (file == null && folder == null)
            return true;
        return false;
    }
}

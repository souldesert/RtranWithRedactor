package project;

import com.fasterxml.jackson.dataformat.xml.annotation.*;


/**
 * Created by svkreml on 20.11.2016.
 */
@JacksonXmlRootElement(localName = "project")
public class ProjectTreeRoot {
    @JacksonXmlProperty(isAttribute = true)
    String type;
    @JacksonXmlProperty(isAttribute = true)
    String version;
    @JacksonXmlProperty(isAttribute = true)
    String project_name;

    public ProjectTreeRoot() {
    }

    public ProjectTreeRoot(String type, String version, String project_name, ProjectStructure project_structure) {
        this.type = type;
        this.version = version;
        this.project_name = project_name;
        this.project_structure = project_structure;
    }

    public ProjectStructure getProject_structure() {
        return project_structure;
    }

    @JacksonXmlProperty
    ProjectStructure project_structure;
}

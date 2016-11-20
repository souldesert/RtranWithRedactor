package project;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by svkreml on 20.11.2016.
 */
public class Project {
    public ProjectTreeRoot getPr() {
        return pr;
    }
    ProjectTreeRoot pr;

    public void create(File directory) throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        pr= new ProjectTreeRoot("R-MACHINE", "-3.14", directory.getName(), new ProjectStructure(directory.toString()));
        xmlMapper.writeValue(new File(directory, "project.rtran"), pr);
    }
    public ProjectTreeRoot open(File directory) throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        return xmlMapper.readValue(new File("project.rtran"), ProjectTreeRoot.class);
    }
}
 /*   public void Project() throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        pr = xmlMapper.readValue(new File("project.xml"), ProjectTreeRoot.class);
        xmlMapper.writeValue(new File("project.xml"),pr);
    }
*/
package com.sap.hcp.utils;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class MavenApi {
    private String pomFileDirectory;

    private List<String> goals;

    public void setPomFileDirectory(String pomFileDirectory) {
        this.pomFileDirectory = pomFileDirectory;
    }

    public String getPomFileDirectory() {
        return pomFileDirectory;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public List<String> getGoals() {
        return goals;
    }

    public void buildApplication() {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(pomFileDirectory + File.separator + "pom.xml"));
        request.setGoals(goals);

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));

        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            throw new RuntimeException(e);
        }
    }

    public String generatedWarName() {
        File target = new File(pomFileDirectory + File.separator + "target");
        File[] warFiles = target.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".war");
            }
        });

        if (warFiles.length == 1)
            return warFiles[0].getAbsolutePath();
        else
            return null;
    }
}
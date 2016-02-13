package com.sap.hcp.utils;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class GithubApi {
    private Git git;

    private String repositoryUri;

    private String username;

    private String password;

    public void setRepositoryUri(String repositoryUri) {
        this.repositoryUri = repositoryUri;
    }

    public String getRepositoryUri() {
        return repositoryUri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void cloneRepository() {
        String repositoryName = repositoryUri.split("%2F")[repositoryUri.split("%2F").length - 1];
        File directory = new File(System.getProperty("java.io.tmpdir") + File.separator + repositoryName);

        if (!directory.exists()) {
            try {
                System.out.println(repositoryUri.replaceAll("%3A", ":").replaceAll("%2F", "/"));
                git = Git.cloneRepository().setURI(repositoryUri.replaceAll("%3A", ":").replaceAll("%2F", "/")).setDirectory(new File(System.getProperty("java.io.tmpdir") + File.separator + repositoryName)).setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
            } catch (GitAPIException e) {
                throw new RuntimeException(e);
            }
        }
        else
            throw new RuntimeException("directory exists. manually delete directory");
    }

    public String getDirectoryPath() {
        String repositoryName = repositoryUri.split("%2F")[repositoryUri.split("%2F").length - 1];
        return System.getProperty("java.io.tmpdir") + File.separator + repositoryName;
    }

    public void deleteRepository() {
        String repositoryName = repositoryUri.split("%2F")[repositoryUri.split("%2F").length - 1];
        File directory = new File(System.getProperty("java.io.tmpdir") + File.separator + repositoryName);

        try {
            git.getRepository().close();
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
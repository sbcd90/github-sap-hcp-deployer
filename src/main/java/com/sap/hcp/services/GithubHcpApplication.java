package com.sap.hcp.services;

import com.sap.hcp.utils.GithubApi;
import com.sap.hcp.utils.MavenApi;
import com.sap.hcp.utils.NeoCommandLine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GithubHcpApplication extends HttpServlet {
    private String repositoryUri;

    private String username;

    private String password;

    private List<String> goals;

    private String host;

    private String account;

    private String user;

    private String hcpPassword;

    private String appname;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        String[] records = body.split("&");

        for (String record : records) {
            String[] pair = record.split("=");

            if ("Github".equals(pair[0].split("-")[0])) {
                if ("repositoryUri".equals(pair[0].split("-")[1]))
                    repositoryUri = pair[1];
                if ("username".equals(pair[0].split("-")[1]))
                    username = pair[1];
                if ("password".equals(pair[0].split("-")[1]))
                    password = pair[1];
            }

            if ("Maven".equals(pair[0].split("-")[0])) {
                if ("goals".equals(pair[0].split("-")[1])) {
                    goals = Arrays.asList(pair[1].split("\\+"));
                }
            }

            if ("Neo".equals(pair[0].split("-")[0])) {
                if ("host".equals(pair[0].split("-")[1]))
                    host = pair[1];
                if ("account".equals(pair[0].split("-")[1]))
                    account = pair[1];
                if ("user".equals(pair[0].split("-")[1]))
                    user = pair[1];
                if ("hcpPassword".equals(pair[0].split("-")[1]))
                    hcpPassword = pair[1];
                if ("appname".equals(pair[0].split("-")[1]))
                    appname = pair[1];
            }
        }

        GithubApi githubApi = doGithubTask();

        doMavenTask(githubApi);
        doNeoTask(githubApi);
        doCleanup(githubApi);

        PrintWriter writer = resp.getWriter();
        writer.println("success");
    }

    private GithubApi doGithubTask() {
        GithubApi githubApi = new GithubApi();
        githubApi.setRepositoryUri(repositoryUri);
        githubApi.setUsername(username);
        githubApi.setPassword(password);

        githubApi.cloneRepository();

        return githubApi;
    }

    private void doMavenTask(GithubApi githubApi) {
        MavenApi mavenApi = new MavenApi();
        mavenApi.setGoals(goals);
        mavenApi.setPomFileDirectory(githubApi.getDirectoryPath());

        mavenApi.buildApplication();
    }

    private void doNeoTask(GithubApi githubApi) {
        NeoCommandLine neoCommandLine = new NeoCommandLine();
        neoCommandLine.setAccount(account);
        neoCommandLine.setAppName(appname);
        neoCommandLine.setHost(host);
        neoCommandLine.setPassword(hcpPassword);
        neoCommandLine.setSourceLocation(githubApi.getDirectoryPath());
        neoCommandLine.setUser(user);

        neoCommandLine.deployApplication();
    }

    private void doCleanup(GithubApi githubApi) {
        githubApi.deleteRepository();
    }
}
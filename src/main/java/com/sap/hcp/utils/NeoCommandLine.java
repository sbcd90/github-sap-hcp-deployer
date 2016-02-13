package com.sap.hcp.utils;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

import java.io.File;
import java.io.OutputStreamWriter;

public class NeoCommandLine {
    private String host;

    private String account;

    private String user;

    private String password;

    private String appName;

    private String sourceLocation;

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void deployApplication() {
        Commandline commandline = new Commandline();

        if (System.getProperty("os.name").startsWith("Windows"))
            commandline.setExecutable(System.getenv("NEO_SDK") + File.separator + "tools" + File.separator + "neo.bat");
        else
            commandline.setExecutable(System.getenv("NEO_SDK") + File.separator + "tools" + File.separator + "neo.sh");

        commandline.setWorkingDirectory(System.getenv("NEO_SDK") + File.separator + "tools");
        commandline.addArguments(new String[]{"deploy"});
        commandline.addArguments(new String[]{"--host", host});
        commandline.addArguments(new String[]{"--account", account});
        commandline.addArguments(new String[]{"--user", user});
        commandline.addArguments(new String[]{"--password", password});
        commandline.addArguments(new String[]{"--application", appName});
        commandline.addArguments(new String[]{"--source", sourceLocation});

        WriterStreamConsumer systemOut = new WriterStreamConsumer(new OutputStreamWriter(System.out));

        try {
            CommandLineUtils.executeCommandLine(commandline, systemOut, systemOut);
        } catch (CommandLineException e) {
            throw new RuntimeException(e);
        }

    }
}
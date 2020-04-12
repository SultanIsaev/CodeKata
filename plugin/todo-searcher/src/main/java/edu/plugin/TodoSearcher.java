package edu.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

@Mojo(name = "search")
public class TodoSearcher extends AbstractMojo {

    private final int MAX_DEPTH = 999;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "greeting", defaultValue = "Hello!")
    private String greeting;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(greeting);
        File basedir = project.getBasedir();
        getLog().info(basedir.getAbsolutePath() + " base dir");
        Stream<Path> foundFiles = Stream.of(Paths.get(basedir.getAbsolutePath()).normalize())
                .flatMap(p -> {
                    if (p.toFile().isFile()) {
                        return Stream.of(p);
                    } else if (p.toFile().isDirectory()) {
                        try {
                            return Files.find(p, MAX_DEPTH, (x, bfa) -> bfa.isRegularFile());
                        } catch (IOException e) {
                            throw new RuntimeException("Cannot find file", e);
                        }
                    }
                    return Stream.empty();
                })
                .filter(f -> f.toFile().getName().endsWith(".java"));

        getLog().info("files found");
        foundFiles.forEach(file -> {
            try {
                Scanner scanner = new Scanner(file);
                int currentLine = 1;
                while (scanner.hasNextLine()) {
                    String str = scanner.findInLine("[\\/]{2,}\\s*TODO\\s*");
                    if (str != null) {
                        getLog().info(file.getFileName() + " found TODO on line number: " + currentLine);
                    }
                    currentLine++;
                    scanner.nextLine();
                }
                scanner.close();
            } catch (IOException e) {
                throw new RuntimeException("Cannot not read file");
            }
        });
        getLog().info("done");
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }
}
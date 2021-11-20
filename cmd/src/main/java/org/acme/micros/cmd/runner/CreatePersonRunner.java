package org.acme.micros.cmd.runner;

import org.acme.micros.cmd.runner.command.CreatePersonCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * A command line operation that is run from the app startup with the passed arguments
 */
@Component
@RequiredArgsConstructor
public class CreatePersonRunner implements CommandLineRunner, ExitCodeGenerator {

    private final CreatePersonCommand command;
    private final IFactory picocliFactory;
    private int exitCode;

    @Override
    public void run(final String... args) {
        exitCode = new CommandLine(command, picocliFactory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}

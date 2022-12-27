package de.litexo.commands;

import de.litexo.model.external.OpenttdServer;
import de.litexo.repository.DefaultRepository;

import javax.enterprise.inject.spi.CDI;
import java.util.Optional;

public class PauseCommand extends Command {

    private final DefaultRepository repository;

    public PauseCommand(DefaultRepository repository) {
        super("pause");
        this.repository = repository;
    }

    @Override
    public boolean check(String logs) {
        if (logs.contains("*** Game paused (manual)") || logs.contains("Game is already paused")) {
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess(String openttdServeId) {

        Optional<OpenttdServer> openttdServer = this.repository.getOpenttdServer(openttdServeId);
        if (openttdServer.isPresent()) {
            openttdServer.get().setPaused(true);
            this.repository.updateServer(openttdServeId, openttdServer.get());
        }
        super.onSuccess(openttdServeId);
    }
}

package org.example.tools;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.example.mainMenu;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PromoUpdater {

    private static final String REMOTE_URL = "https://github.com/alxgarci/promodb_remote.git";


    public static void commitPushDB() {
        File f = mainMenu.createLocalFile("promo_database","csv");
        try {
            String commitMsg = "++UPDATE PROMODB: " + getLocalDate();
            Repository localRepo = new FileRepository("/tmp/.git");
            Git git = Git.open(localRepo.getIndexFile());
            git.add().addFilepattern(f.getPath()).call();
            git.commit().setAll(true).setMessage(commitMsg).call();

            PushCommand push = git.push();
            UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider("username", "pwd");
            push.setCredentialsProvider(user);
            push.setRemote(REMOTE_URL);
            push.call();

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }

    }

    private static String getLocalDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public static void updateDB() {
        try {
            Git git = Git.cloneRepository().setURI(REMOTE_URL).setDirectory(new File("tmp/")).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

    }
}

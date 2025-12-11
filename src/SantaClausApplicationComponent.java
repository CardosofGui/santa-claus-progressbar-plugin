import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SantaClausApplicationComponent implements LafManagerListener, ApplicationActivationListener {
    public SantaClausApplicationComponent() {
    }

    @Override
    public void lookAndFeelChanged(@NotNull LafManager lafManager) {
        updateProgressBarUi();
    }

    @Override
    public void applicationActivated(@NotNull IdeFrame ideFrame) {
        updateProgressBarUi();
    }
    private void updateProgressBarUi() {
        UIManager.put("ProgressBarUI", SantaClausProgressBarUi.class.getName());
        UIManager.getDefaults().put(SantaClausProgressBarUi.class.getName(), SantaClausProgressBarUi.class);
    }
}


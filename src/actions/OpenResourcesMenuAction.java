package actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class OpenResourcesMenuAction extends AbstractAction {

	private static final long serialVersionUID = 6521355599768018729L;

	public OpenResourcesMenuAction() {
		putValue(Action.NAME, "Open resources directory");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String directoryPath = "resources";
		Path path = Paths.get(directoryPath);
		if (!path.toFile().exists()) {
			path.toFile().mkdir();
		}
		if (!path.toFile().isDirectory()) {
			return;
		}
		try {
			if (FileSystems.getDefault().supportedFileAttributeViews().contains("file")) {
				Runtime.getRuntime().exec("explorer.exe \"" + path.toAbsolutePath() + "\"");
			} else {
				Desktop.getDesktop().open(path.toFile());
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}

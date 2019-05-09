package view.stages;

import controller.SettingsController;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import view.settingsroot.SettingsRootPane;

public class SettingsStage extends Stage {

	public SettingsStage(Model model, Stage owner) {
		this(model, owner, null);
	}
	
	public SettingsStage(Model model, Stage owner, String defaultSetting) {
		this.setTitle("Settings");
//		this.setMaxWidth(100);
//		this.setMaxHeight(100);
		if(owner != null) this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);

		SettingsRootPane root = new SettingsRootPane(defaultSetting);
		this.setScene(new Scene(root, 450, 450));

		new SettingsController(root, model);
	}
	
}

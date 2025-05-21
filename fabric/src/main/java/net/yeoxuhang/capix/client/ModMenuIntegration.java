package net.yeoxuhang.capix.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.yeoxuhang.capix.client.gui.CapeSettingsScreen;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return CapeSettingsScreen::new;
    }
}
package nray7882.believe;

import org.bukkit.plugin.java.JavaPlugin;

public final class Believe extends JavaPlugin {

    @Override
    public void onEnable() {

        // Register event listener
        getServer().getPluginManager().registerEvents(new LevitationListener(), this);
    }
}

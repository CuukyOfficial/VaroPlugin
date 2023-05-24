package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;
import java.util.List;

import de.cuuky.varo.Main;
import de.varoplugin.cfw.player.hud.AnimationData;
import de.varoplugin.cfw.player.hud.UnmodifiableAnimationData;

public class TablistConfig extends BoardConfig {

    private AnimationData<String> header;
    private AnimationData<String> footer;

    public TablistConfig() {
        super("plugins/Varo/config/tablist.yml");
    }

    @Override
    protected boolean shouldReset() {
        return !this.configuration.contains("header.updatedelay");
    }

    @Override
    protected void load() {
        this.configuration.options().header("Die Liste alle Placeholder findest du unter /varo placeholder!");

        ArrayList<String> firstHeaderFrame = new ArrayList<>();
        firstHeaderFrame.add(" ");
        firstHeaderFrame.add("&7Oo    %projectname%    &7oO");
        firstHeaderFrame.add(" ");

        ArrayList<String> secondHeaderFrame = new ArrayList<>();
        secondHeaderFrame.add(" ");
        secondHeaderFrame.add("&7oO    %projectname%    &7Oo");
        secondHeaderFrame.add(" ");

        ArrayList<String> firstFooterFrame = new ArrayList<>();
        firstFooterFrame.add("&7------------------------");
        firstFooterFrame.add(" ");
        firstFooterFrame.add("&7Varo Plugin");
        firstFooterFrame.add("&7v.%colorcode%%pluginVersion% &7by %colorcode%Cuuky");
        firstFooterFrame.add(" ");
        firstFooterFrame.add("&7Discord");
        firstFooterFrame.add("%colorcode%" + Main.DISCORD_INVITE);
        firstFooterFrame.add(" ");
        firstFooterFrame.add("%colorcode%%currDay%&7.%colorcode%%currMonth%&7.%colorcode%%currYear%");
        firstFooterFrame.add("%colorcode%%currHour%&7:%colorcode%%currMin%&7:%colorcode%%currSec%");
        firstFooterFrame.add(" ");
        firstFooterFrame.add("&7------------------------");

        ArrayList<ArrayList<String>> headerFrames = new ArrayList<>();
        headerFrames.add(firstHeaderFrame);
        headerFrames.add(secondHeaderFrame);

        ArrayList<ArrayList<String>> footerFrames = new ArrayList<>();
        footerFrames.add(firstFooterFrame);

        this.configuration.addDefault("header.updatedelay", 10);
        this.configuration.addDefault("header.content", headerFrames);
        this.configuration.addDefault("footer.updatedelay", 0);
        this.configuration.addDefault("footer.content", footerFrames);

        this.header = toAnimationData(this.configuration.getInt("header.updatedelay"), this.configuration.getList("header.content"));
        this.footer = toAnimationData(this.configuration.getInt("footer.updatedelay"), this.configuration.getList("footer.content"));
    }

    public AnimationData<String> getHeader() {
        return this.header;
    }

    public AnimationData<String> getFooter() {
        return this.footer;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static AnimationData<String> toAnimationData(int delay, List<?> list) {
        return new UnmodifiableAnimationData<>(delay, (String[]) list.stream().map(l -> String.join("\n", (Iterable) l)).toArray(String[]::new));
    }
}
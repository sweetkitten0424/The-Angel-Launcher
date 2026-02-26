/*
 * ATLauncher - https://github.com/ATLauncher/ATLauncher
 * Copyright (C) 2013-2022 ATLauncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.lusd1.the_angel_launcher.data;

import java.awt.Window;
import java.nio.file.Path;
import java.util.List;

import org.lusd1.the_angel_launcher.data.curseforge.CurseForgeFile;
import org.lusd1.the_angel_launcher.data.curseforge.CurseForgeProject;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.LoaderVersion;
import org.lusd1.the_angel_launcher.data.modrinth.ModrinthFile;
import org.lusd1.the_angel_launcher.data.modrinth.ModrinthProject;
import org.lusd1.the_angel_launcher.data.modrinth.ModrinthVersion;
import org.lusd1.the_angel_launcher.gui.dialogs.ProgressDialog;

/**
 * Interface for mod management. Used by Instances as well as Servers to manage the mods/plugins for them.
 */
public interface ModManagement {
    public abstract Path getRoot();

    public abstract String getName();

    public abstract String getMinecraftVersion();

    public abstract LoaderVersion getLoaderVersion();

    public abstract boolean supportsPlugins();

    public abstract boolean isForgeLikeAndHasInstalledSinytraConnector();

    public abstract List<DisableableMod> getMods();

    public abstract void addMod(DisableableMod mod);

    public abstract void addMods(List<DisableableMod> modsToAdd);

    public abstract void removeMod(DisableableMod mod);

    public abstract void addFileFromCurseForge(CurseForgeProject mod, CurseForgeFile file, ProgressDialog<Void> dialog);

    public abstract void addFileFromModrinth(ModrinthProject project, ModrinthVersion version, ModrinthFile file,
            ProgressDialog<Void> dialog);

    public abstract void scanMissingMods(Window parent);

    public abstract void save();
}

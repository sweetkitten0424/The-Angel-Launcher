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
package org.lusd1.the_angel_launcher;

import java.awt.Color;
import java.time.Instant;
import java.util.Date;

import org.lusd1.the_angel_launcher.annot.ExcludeFromGsonSerialization;
import org.lusd1.the_angel_launcher.data.AbstractAccount;
import org.lusd1.the_angel_launcher.data.AccountTypeAdapter;
import org.lusd1.the_angel_launcher.data.ColorTypeAdapter;
import org.lusd1.the_angel_launcher.data.DateTypeAdapter;
import org.lusd1.the_angel_launcher.data.InstantTypeAdapter;
import org.lusd1.the_angel_launcher.data.PackVersion;
import org.lusd1.the_angel_launcher.data.PackVersionTypeAdapter;
import org.lusd1.the_angel_launcher.data.microsoft.OauthTokenResponse;
import org.lusd1.the_angel_launcher.data.microsoft.OauthTokenResponseTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.Arguments;
import org.lusd1.the_angel_launcher.data.minecraft.ArgumentsTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.Library;
import org.lusd1.the_angel_launcher.data.minecraft.LibraryTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.fabric.FabricLibrary;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.fabric.FabricLibraryTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.fabric.FabricMetaLauncherMeta;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.fabric.FabricMetaLauncherMetaTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.forge.ForgeLibrary;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.forge.ForgeLibraryTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.legacyfabric.LegacyFabricLibrary;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.legacyfabric.LegacyFabricLibraryTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.legacyfabric.LegacyFabricMetaLauncherMeta;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.legacyfabric.LegacyFabricMetaLauncherMetaTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.quilt.QuiltLibrary;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.quilt.QuiltLibraryTypeAdapter;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.quilt.QuiltMetaLauncherMeta;
import org.lusd1.the_angel_launcher.data.minecraft.loaders.quilt.QuiltMetaLauncherMetaTypeAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Gsons {
    public static final ExclusionStrategy exclusionAnnotationStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(ExcludeFromGsonSerialization.class) != null;
        }
    };

    private static final Gson BASE = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(AbstractAccount.class, new AccountTypeAdapter())
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .registerTypeAdapter(Color.class, new ColorTypeAdapter())
            .registerTypeAdapter(OauthTokenResponse.class, new OauthTokenResponseTypeAdapter())
            .registerTypeAdapter(PackVersion.class, new PackVersionTypeAdapter())
            .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
            .registerTypeAdapter(Library.class, new LibraryTypeAdapter())
            .registerTypeAdapter(Arguments.class, new ArgumentsTypeAdapter())
            .registerTypeAdapter(FabricMetaLauncherMeta.class, new FabricMetaLauncherMetaTypeAdapter())
            .registerTypeAdapter(FabricLibrary.class, new FabricLibraryTypeAdapter())
            .registerTypeAdapter(LegacyFabricMetaLauncherMeta.class, new LegacyFabricMetaLauncherMetaTypeAdapter())
            .registerTypeAdapter(LegacyFabricLibrary.class, new LegacyFabricLibraryTypeAdapter())
            .registerTypeAdapter(ForgeLibrary.class, new ForgeLibraryTypeAdapter())
            .registerTypeAdapter(QuiltLibrary.class, new QuiltLibraryTypeAdapter())
            .registerTypeAdapter(QuiltMetaLauncherMeta.class, new QuiltMetaLauncherMetaTypeAdapter())
            .addSerializationExclusionStrategy(exclusionAnnotationStrategy).create();

    public static final Gson DEFAULT = BASE.newBuilder().setPrettyPrinting().create();

    public static final Gson DEFAULT_SLIM = BASE.newBuilder().create();
}

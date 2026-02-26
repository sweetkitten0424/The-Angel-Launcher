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
package org.lusd1.the_angel_launcher.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.lusd1.the_angel_launcher.constants.Constants;
import org.lusd1.the_angel_launcher.data.technic.TechnicModpack;
import org.lusd1.the_angel_launcher.data.technic.TechnicSearchResults;
import org.lusd1.the_angel_launcher.data.technic.TechnicSolderModpack;
import org.lusd1.the_angel_launcher.data.technic.TechnicSolderModpackManifest;
import org.lusd1.the_angel_launcher.network.DownloadException;
import org.lusd1.the_angel_launcher.network.NetworkClient;

import okhttp3.CacheControl;

/**
 * Various utility methods for interacting with the CurseForge API.
 */
public class TechnicApi {
    public static TechnicSearchResults getTrendingModpacks() {
        return NetworkClient.getCached(String.format("%s/trending?build=%s", Constants.TECHNIC_API_URL,
                Constants.LAUNCHER_NAME.toLowerCase(Locale.ENGLISH)), TechnicSearchResults.class,
                new CacheControl.Builder().maxStale(10, TimeUnit.MINUTES).build());
    }

    public static TechnicSearchResults searchModpacks(String query) {
        return NetworkClient.getCached(String.format("%s/search?q=%s&build=%s", Constants.TECHNIC_API_URL, query,
                Constants.LAUNCHER_NAME.toLowerCase(Locale.ENGLISH)), TechnicSearchResults.class,
                new CacheControl.Builder().maxStale(10, TimeUnit.MINUTES).build());
    }

    public static TechnicModpack getModpackBySlug(String slug) {
        return NetworkClient.getCached(String.format("%s/modpack/%s?build=%s", Constants.TECHNIC_API_URL, slug,
                Constants.LAUNCHER_NAME.toLowerCase(Locale.ENGLISH)), TechnicModpack.class,
                new CacheControl.Builder().maxStale(10, TimeUnit.MINUTES).build());
    }

    public static TechnicModpack getModpackBySlugWithThrow(String slug) throws DownloadException {
        return NetworkClient.getCachedWithThrow(String.format("%s/modpack/%s?build=%s", Constants.TECHNIC_API_URL, slug,
                Constants.LAUNCHER_NAME.toLowerCase(Locale.ENGLISH)), TechnicModpack.class,
                new CacheControl.Builder().maxStale(10, TimeUnit.MINUTES).build());
    }

    private static Object normalizeSolderUrl(String solderUrl) {
        if (solderUrl.endsWith("/")) {
            return solderUrl.substring(0, solderUrl.length() - 1);
        }

        return solderUrl;
    }

    public static TechnicSolderModpack getSolderModpackBySlug(String solderUrl, String slug) {
        return NetworkClient.getCached(String.format("%s/modpack/%s", normalizeSolderUrl(solderUrl), slug),
                TechnicSolderModpack.class,
                new CacheControl.Builder().maxStale(10, TimeUnit.MINUTES).build());
    }

    public static TechnicSolderModpackManifest getSolderModpackManifest(String solderUrl, String slug, String build) {
        return NetworkClient.getCached(String.format("%s/modpack/%s/%s", normalizeSolderUrl(solderUrl), slug, build),
                TechnicSolderModpackManifest.class,
                new CacheControl.Builder().maxStale(10, TimeUnit.MINUTES).build());
    }
}

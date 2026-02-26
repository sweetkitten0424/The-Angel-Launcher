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
package org.lusd1.the_angel_launcher.gui.panels.packbrowser;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import org.mini2Dx.gettext.GetText;

import org.lusd1.the_angel_launcher.App;
import org.lusd1.the_angel_launcher.builders.HTMLBuilder;
import org.lusd1.the_angel_launcher.constants.UIConstants;
import org.lusd1.the_angel_launcher.data.minecraft.VersionManifestVersion;
import org.lusd1.the_angel_launcher.data.minecraft.VersionManifestVersionType;
import org.lusd1.the_angel_launcher.data.modrinth.ModrinthProject;
import org.lusd1.the_angel_launcher.data.modrinth.ModrinthProjectType;
import org.lusd1.the_angel_launcher.data.modrinth.ModrinthSearchHit;
import org.lusd1.the_angel_launcher.data.modrinth.ModrinthSearchResult;
import org.lusd1.the_angel_launcher.gui.card.NilCard;
import org.lusd1.the_angel_launcher.gui.card.packbrowser.ModrinthPackCard;
import org.lusd1.the_angel_launcher.gui.dialogs.InstanceInstallerDialog;
import org.lusd1.the_angel_launcher.gui.dialogs.ProgressDialog;
import org.lusd1.the_angel_launcher.managers.AccountManager;
import org.lusd1.the_angel_launcher.managers.ConfigManager;
import org.lusd1.the_angel_launcher.managers.DialogManager;
import org.lusd1.the_angel_launcher.managers.LogManager;
import org.lusd1.the_angel_launcher.network.Analytics;
import org.lusd1.the_angel_launcher.network.analytics.AnalyticsEvent;
import org.lusd1.the_angel_launcher.utils.ModrinthApi;
import org.lusd1.the_angel_launcher.utils.Utils;

public class ModrinthPacksPanel extends PackBrowserPlatformPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    boolean hasMorePages = true;

    @Override
    protected void loadPacks(JPanel contentPanel, String minecraftVersion, String category, String sort,
                             boolean sortDescending, String search, int page) {
        ModrinthSearchResult searchResult = ModrinthApi.searchModPacks(minecraftVersion, search, page - 1, sort,
            category);

        hasMorePages = searchResult != null && searchResult.offset + searchResult.hits.size() < searchResult.totalHits;

        if (searchResult == null || searchResult.hits.isEmpty()) {
            contentPanel.removeAll();
            contentPanel.add(
                new NilCard(new HTMLBuilder().text(GetText
                        .tr("There are no packs to display.<br/><br/>Try removing your search query and try again."))
                    .build()),
                gbc);
            return;
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = UIConstants.FIELD_INSETS;
        gbc.fill = GridBagConstraints.BOTH;

        List<ModrinthPackCard> cards = searchResult.hits.stream().map(ModrinthPackCard::new)
            .collect(Collectors.toList());

        contentPanel.removeAll();

        for (ModrinthPackCard card : cards) {
            contentPanel.add(card, gbc);
            gbc.gridy++;
        }
    }

    @Override
    public void loadMorePacks(JPanel contentPanel, String minecraftVersion, String category, String sort,
                              boolean sortDescending, String search, int page) {
        ModrinthSearchResult searchResult = ModrinthApi.searchModPacks(minecraftVersion, search, page - 1, sort,
            category);

        hasMorePages = searchResult != null && searchResult.offset + searchResult.hits.size() < searchResult.totalHits;

        if (searchResult != null) {
            for (ModrinthSearchHit pack : searchResult.hits) {
                contentPanel.add(new ModrinthPackCard(pack), gbc);
                gbc.gridy++;
            }
        }
    }

    @Override
    public String getPlatformName() {
        return "Modrinth";
    }

    @Override
    public String getAnalyticsCategory() {
        return "ModrinthPack";
    }

    @Override
    public boolean supportsSearch() {
        return true;
    }

    @Override
    public boolean hasCategories() {
        return true;
    }

    @Override
    public Map<String, String> getCategoryFields() {
        Map<String, String> categoryFields = new LinkedHashMap<>();

        ModrinthApi.getCategoriesForModpacks().stream()
            .forEach(c -> categoryFields.put(c.name, Utils.capitalize(c.name)));

        return categoryFields;
    }

    @Override
    public boolean hasSort() {
        return true;
    }

    @Override
    public boolean supportsSortOrder() {
        return false;
    }

    @Override
    public Map<String, String> getSortFields() {
        Map<String, String> sortFields = new LinkedHashMap<>();

        sortFields.put("relevance", GetText.tr("Relevance"));
        sortFields.put("downloads", GetText.tr("Downloads"));
        sortFields.put("follows", GetText.tr("Follows"));
        sortFields.put("newest", GetText.tr("Newest"));
        sortFields.put("updated", GetText.tr("Updated"));

        return sortFields;
    }

    @Override
    public Map<String, Boolean> getSortFieldsDefaultOrder() {
        return new LinkedHashMap<>();
    }

    @Override
    public boolean supportsMinecraftVersionFiltering() {
        return true;
    }

    @Override
    public List<VersionManifestVersionType> getSupportedMinecraftVersionTypesForFiltering() {
        List<VersionManifestVersionType> supportedTypes = new ArrayList<>();

        supportedTypes.add(VersionManifestVersionType.RELEASE);

        return supportedTypes;
    }

    @Override
    public List<VersionManifestVersion> getSupportedMinecraftVersionsForFiltering() {
        List<VersionManifestVersion> supportedTypes = new ArrayList<>();

        return supportedTypes;
    }

    @Override
    public boolean hasPagination() {
        return true;
    }

    @Override
    public boolean hasMorePages() {
        return hasMorePages;
    }

    @Override
    public boolean supportsManualAdding() {
        return true;
    }

    @Override
    public void addById(String id) {
        String packLookup = id;

        if (id.startsWith("https://modrinth.com/modpack")) {
            Pattern pattern = Pattern
                .compile("modrinth\\.com\\/modpack\\/([\\w-]+)");
            Matcher matcher = pattern.matcher(id);

            if (!matcher.find() || matcher.groupCount() < 1) {
                LogManager.error("Cannot install as the url was not a valid Modrinth modpack url");
                return;
            }

            packLookup = matcher.group(1);
        }

        String packToLookup = packLookup;
        // #. {0} is the platform were getting info from (e.g. CurseForge/Modrinth)
        ProgressDialog<ModrinthProject> progressDialog = new ProgressDialog<>(
            GetText.tr("Looking Up Pack On {0}", "Modrinth"),
            0,
            // #. {0} is the platform were getting info from (e.g. CurseForge/Modrinth)
            GetText.tr("Looking Up Pack On {0}", "Modrinth"),
            // #. {0} is the platform were getting info from (e.g. CurseForge/Modrinth)
            GetText.tr("Cancelling Looking Up Pack On {0}", "Modrinth"));
        progressDialog.addThread(new Thread(() -> {
            progressDialog.setReturnValue(ModrinthApi.getProject(packToLookup));
            progressDialog.doneTask();
            progressDialog.close();
        }));
        progressDialog.start();

        ModrinthProject project = progressDialog.getReturnValue();

        if (project == null || project.projectType != ModrinthProjectType.MODPACK) {
            DialogManager.okDialog().setType(DialogManager.ERROR).setTitle(GetText.tr("Pack Not Found"))
                .setContent(
                    GetText.tr(
                        "A pack with that id/slug was not found. Please check the id/slug/url and try again."))
                .show();
            return;
        }

        if (AccountManager.getSelectedAccount() == null) {
            DialogManager.okDialog().setTitle(GetText.tr("No Account Selected"))
                .setContent(GetText.tr("Cannot create instance as you have no account selected."))
                .setType(DialogManager.ERROR).show();

            if (AccountManager.getAccounts().isEmpty()) {
                App.navigate(UIConstants.LAUNCHER_ACCOUNTS_TAB);
            }
        } else {
            Analytics.trackEvent(AnalyticsEvent.forPackInstall(project));
            InstanceInstallerDialog instanceInstallerDialog = new InstanceInstallerDialog(project);
            instanceInstallerDialog.setVisible(true);
        }
    }

    @Override
    public String getPlatformMessage() {
        return ConfigManager.getConfigItem("platforms.modrinth.message", null);
    }
}

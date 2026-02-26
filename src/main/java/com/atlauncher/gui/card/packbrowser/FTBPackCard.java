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
package org.lusd1.the_angel_launcher.gui.card.packbrowser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Comparator;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLDocument;

import org.mini2Dx.gettext.GetText;

import org.lusd1.the_angel_launcher.App;
import org.lusd1.the_angel_launcher.constants.UIConstants;
import org.lusd1.the_angel_launcher.data.ftb.FTBPackArt;
import org.lusd1.the_angel_launcher.data.ftb.FTBPackArtType;
import org.lusd1.the_angel_launcher.data.ftb.FTBPackManifest;
import org.lusd1.the_angel_launcher.evnt.listener.RelocalizationListener;
import org.lusd1.the_angel_launcher.evnt.manager.RelocalizationManager;
import org.lusd1.the_angel_launcher.gui.components.BackgroundImageLabel;
import org.lusd1.the_angel_launcher.gui.dialogs.InstanceInstallerDialog;
import org.lusd1.the_angel_launcher.managers.AccountManager;
import org.lusd1.the_angel_launcher.managers.DialogManager;
import org.lusd1.the_angel_launcher.network.Analytics;
import org.lusd1.the_angel_launcher.network.analytics.AnalyticsEvent;
import org.lusd1.the_angel_launcher.utils.Markdown;
import org.lusd1.the_angel_launcher.utils.OS;

public class FTBPackCard extends JPanel implements RelocalizationListener {
    private final JButton installButton = new JButton(GetText.tr("Install"));
    private final JButton websiteButton = new JButton(GetText.tr("Website"));

    public FTBPackCard(final FTBPackManifest pack) {
        super();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(null, pack.name, TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                App.THEME.getBoldFont().deriveFont(15f)));

        RelocalizationManager.addListener(this);

        String imageUrl = null;
        if (pack.art != null) {
            Optional<FTBPackArt> art = pack.art.stream()
                    .filter(a -> a.type == FTBPackArtType.LOGO || a.type == FTBPackArtType.SQUARE)
                    .sorted(Comparator.comparingInt((FTBPackArt a) -> a.updated).reversed()).findFirst();
            if (art.isPresent()) {
                imageUrl = art.get().url;
            }
        }

        JSplitPane splitter = new JSplitPane();

        BackgroundImageLabel imageLabel = new BackgroundImageLabel(imageUrl, 150, 150);
        imageLabel.setPreferredSize(new Dimension(300, 150));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        splitter.setLeftComponent(imageLabel);

        JPanel actionsPanel = new JPanel(new BorderLayout());
        splitter.setRightComponent(actionsPanel);
        splitter.setEnabled(false);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        installButton.addActionListener(e -> {
            if (AccountManager.getSelectedAccount() == null) {
                DialogManager.okDialog().setTitle(GetText.tr("No Account Selected"))
                        .setContent(GetText.tr("Cannot create instance as you have no account selected."))
                        .setType(DialogManager.ERROR).show();

                if (AccountManager.getAccounts().isEmpty()) {
                    App.navigate(UIConstants.LAUNCHER_ACCOUNTS_TAB);
                }
            } else {
                Analytics.trackEvent(AnalyticsEvent.forPackInstall(pack));
                InstanceInstallerDialog instanceInstallerDialog = new InstanceInstallerDialog(pack);
                instanceInstallerDialog.setVisible(true);
            }
        });
        buttonsPanel.add(installButton);

        websiteButton.addActionListener(e -> OS.openWebBrowser(pack.getWebsiteUrl()));
        buttonsPanel.add(websiteButton);

        // The Feed The Beast website only displays modpacks with the 'FTB'
        // tag present, so we should hide the button for packs without the tag.
        websiteButton.setVisible(pack.hasTag("FTB"));

        JEditorPane descArea = new JEditorPane("text/html",
                String.format("<html>%s</html>", Markdown.render(pack.description)));

        Font font = App.THEME.getNormalFont();
        String bodyRule = "p { font-family: " + font.getFamily() + "; " +
                "font-size: " + font.getSize() + "pt; }";
        ((HTMLDocument) descArea.getDocument()).getStyleSheet().addRule(bodyRule);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setHighlighter(null);
        descArea.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                OS.openWebBrowser(e.getURL());
            }
        });

        actionsPanel.add(new JScrollPane(descArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        actionsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        actionsPanel.setPreferredSize(new Dimension(0, 155));

        add(splitter, BorderLayout.CENTER);
    }

    @Override
    public void onRelocalization() {
        installButton.setText(GetText.tr("Install"));
        websiteButton.setText(GetText.tr("Website"));
    }
}

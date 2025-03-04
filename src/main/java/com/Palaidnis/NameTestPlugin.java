package com.Palaidnis;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.Client;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PluginDescriptor(
        name = "Name Test",
        description = "Generates random names for testing.",
        tags = {"name", "generator", "test"}
)
@Slf4j
public class NameTestPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private ClientToolbar clientToolbar;

    private NameTestPanel panel;
    private NavigationButton navButton;

    private final Random random = new Random();
    private List<String> commonWords;

    @Override
    protected void startUp() {
        loadCommonWords(); // Load and filter common words
        panel = new NameTestPanel();
        panel.updateName(generateName());

        BufferedImage icon = ImageUtil.loadImageResource(getClass(), "icon.png");
        navButton = NavigationButton.builder()
                .tooltip("Name Test")
                .icon(icon)
                .priority(5)
                .panel(panel)
                .build();

        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() {
        clientToolbar.removeNavigation(navButton);
    }

    private void loadCommonWords() {
        commonWords = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/words_alpha.txt")) {
            if (inputStream == null) {
                throw new RuntimeException("Failed to load words_alpha.txt");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String word = line.trim().toLowerCase();
                    if (isCommonWord(word)) {
                        commonWords.add(word);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to load common words", e);
        }
    }

    private boolean isCommonWord(String word) {
        // Basic heuristic to filter out uncommon words
        return word.length() >= 4 && word.length() <= 12 && word.matches("[a-z]+");
    }

    private String generateName() {
        if (commonWords.isEmpty()) {
            return "No words available";
        }
        return commonWords.get(random.nextInt(commonWords.size()));
    }
}
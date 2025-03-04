package com.Palaidnis;

import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import java.awt.*;

public class NameTestPanel extends PluginPanel {
    private final JLabel nameLabel = new JLabel("Generated Name: ");

    public NameTestPanel() {
        setLayout(new BorderLayout());
        add(nameLabel, BorderLayout.NORTH);
    }

    public void updateName(String name) {
        nameLabel.setText("Generated Name: " + name);
    }
}
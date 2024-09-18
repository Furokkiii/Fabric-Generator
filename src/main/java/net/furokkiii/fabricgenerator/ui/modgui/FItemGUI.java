package net.furokkiii.fabricgenerator.ui.modgui;

import net.furokkiii.fabricgenerator.Generator;
import net.furokkiii.fabricgenerator.element.custom.RoundedBorder;
import net.furokkiii.fabricgenerator.element.custom.RoundedJComboBox;
import net.furokkiii.fabricgenerator.element.custom.RoundedJSpinner;
import net.furokkiii.fabricgenerator.element.custom.RoundedVTextField;
import net.furokkiii.fabricgenerator.element.types.FItem;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.dialogs.TypedTextureSelectorDialog;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.TabListField;
import net.mcreator.ui.minecraft.TextureSelectionButton;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

import static net.mcreator.element.types.Item.encodeModelType;

public class FItemGUI extends ModElementGUI<FItem> {
    private TextureSelectionButton texture;

    private RoundedVTextField toolTip = new RoundedVTextField(28, 20);

    private final RoundedJSpinner stackSize = new RoundedJSpinner(new SpinnerNumberModel(64, 1, 64, 1), 20);
    private final RoundedVTextField name = new RoundedVTextField(28, 20);
    private final RoundedJComboBox<String> rarity = new RoundedJComboBox<>(new String[]{ "COMMON", "UNCOMMON", "RARE", "EPIC" }, 20);

    private RoundedJSpinner enchantability;
    private RoundedJSpinner useDuration;
    private RoundedJSpinner damageCount;
    private RoundedJSpinner destroySpeed;
    private final JCheckBox destroyAnyBlock = L10N.checkbox("elementgui.common.enable");

    private final TabListField creativeTabs = new TabListField(mcreator);

    private static final Model normal = new Model.BuiltInModel("Normal");
    private static final Model tool = new Model.BuiltInModel("Tool");
    public static final Model[] builtinitemmodels = new Model[] { normal, tool };
    private final SearchableComboBox<Model> renderType = new SearchableComboBox<>(builtinitemmodels);

    private final RoundedJSpinner damageVsEntity = new RoundedJSpinner(new SpinnerNumberModel(0, 0, 128000, 0.1), 20);
    private final JCheckBox enableMeleeDamage = new JCheckBox();

    // FOOD
    private final JCheckBox isFood = L10N.checkbox("elementgui.common.enable");
    private final RoundedJComboBox<String> animation = new RoundedJComboBox<>(new String[] { "NONE", "EAT" }, 20);
    private final RoundedJSpinner saturation = new RoundedJSpinner(new SpinnerNumberModel(0.3, -1000, 1000, 0.1), 20);
    private final RoundedJSpinner nutritionalValue = new RoundedJSpinner(new SpinnerNumberModel(4, -1000, 1000, 1), 20);
    private final JCheckBox isAlwaysEdible = L10N.checkbox("elementgui.common.enable");

    public FItemGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);

        this.enchantability = new RoundedJSpinner(new SpinnerNumberModel(0, -100, 128000, 1), 20);
        this.useDuration = new RoundedJSpinner(new SpinnerNumberModel(0, -100, 128000, 1), 20);
        this.damageCount = new RoundedJSpinner(new SpinnerNumberModel(0, 0, 128000, 1), 20);
        this.destroySpeed = new RoundedJSpinner(new SpinnerNumberModel(1.0, -100.0, 128000.0, 0.5), 20);

        isFood.setSelected(false);
        isAlwaysEdible.setSelected(false);

        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        // ==== Visual Settings Panel ====
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15); // Padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JPanel visualSettingsPanel = new JPanel(new GridBagLayout());
        visualSettingsPanel.setOpaque(false);
        visualSettingsPanel.setBorder(BorderFactory.createTitledBorder(
                new RoundedBorder(Color.GRAY, 2, 15), "Visual Settings", TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints visualGbc = new GridBagConstraints();
        visualGbc.insets = new Insets(5, 10, 4, 10);
        visualGbc.anchor = GridBagConstraints.WEST;
        visualGbc.gridx = 0;
        visualGbc.gridy = 0;
        visualGbc.weightx = 0;

        JLabel textureLabel = new JLabel("Item Texture:");
        textureLabel.setFont(textureLabel.getFont().deriveFont(14f));
        visualSettingsPanel.add(textureLabel, visualGbc);

        visualGbc.gridx = 1;
        Dimension squareSize = new Dimension(80, 80);
        this.texture = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        texture.setPreferredSize(squareSize);
        texture.setMinimumSize(squareSize);
        texture.setMaximumSize(squareSize);
        visualSettingsPanel.add(texture, visualGbc);

        visualGbc.gridx = 0;
        visualGbc.gridy = 1;
        JLabel renderTypeDescription = new JLabel("<html>Render Type:<br/><small>Select the type of rendering for the item.</small></html>");
        renderTypeDescription.setFont(renderTypeDescription.getFont().deriveFont(14f));
        visualSettingsPanel.add(renderTypeDescription, visualGbc);

        visualGbc.gridx = 1;
        renderType.setPreferredSize(new Dimension(320, 40));
        renderType.setToolTipText("Choose the rendering type for the item. This affects how the item is displayed in the game.");
        visualSettingsPanel.add(renderType, visualGbc);

        mainPanel.add(visualSettingsPanel, gbc);

        this.addPage(L10N.t("fb.item.visual"), mainPanel);

        // ==== Item Properties Panel ====
        JPanel itemPropertiesPanel = new JPanel(new GridBagLayout());
        itemPropertiesPanel.setOpaque(false);
        itemPropertiesPanel.setBorder(BorderFactory.createTitledBorder(
                new RoundedBorder(Color.GRAY, 2, 15), "Item Properties", TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints itemGbc = new GridBagConstraints();
        itemGbc.fill = GridBagConstraints.HORIZONTAL;
        itemGbc.insets = new Insets(5, 10, 4, 10);
        itemGbc.anchor = GridBagConstraints.WEST;
        itemGbc.gridx = 0;
        itemGbc.weightx = 0;

        Dimension componentSize = new Dimension(320, 40);

        JLabel itemNameLabel = new JLabel("In-game Name:");
        itemNameLabel.setFont(itemNameLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(itemNameLabel, itemGbc);
        itemGbc.gridx = 1;
        name.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("common/gui_name"), name), itemGbc);
        name.setText(getViewName());

        itemGbc.gridx = 0;
        itemGbc.gridy = 1;
        JLabel tooltipLabel = new JLabel("Tooltip:");
        tooltipLabel.setFont(tooltipLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(tooltipLabel, itemGbc);
        itemGbc.gridx = 1;
        toolTip.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/tooltip"), toolTip), itemGbc);

        itemGbc.gridx = 0;
        itemGbc.gridy = 2;
        JLabel rarityLabel = new JLabel("Rarity:");
        rarityLabel.setFont(rarityLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(rarityLabel, itemGbc);
        itemGbc.gridx = 1;
        rarity.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/rarity"), rarity), itemGbc);

        /*itemGbc.gridx = 0;
        itemGbc.gridy = 3;
        JLabel creativeTabsLabel = new JLabel("Creative Tabs:");
        creativeTabsLabel.setFont(creativeTabsLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(creativeTabsLabel, itemGbc);
        itemGbc.gridx = 1;
        creativeTabs.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/creative_tab"), creativeTabs), itemGbc);*/

        itemGbc.gridx = 0;
        itemGbc.gridy = 5;
        JLabel stackSizeLabel = new JLabel("Max Stack size:");
        stackSizeLabel.setFont(stackSizeLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(stackSizeLabel, itemGbc);
        itemGbc.gridx = 1;
        stackSize.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/stack_size"), stackSize), itemGbc);

        itemGbc.gridx = 0;
        itemGbc.gridy = 6;
        JLabel enchantabilityLabel = new JLabel("Enchantability:");
        enchantabilityLabel.setFont(enchantabilityLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(enchantabilityLabel, itemGbc);
        itemGbc.gridx = 1;
        enchantability.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/enchantability"), enchantability), itemGbc);

        itemGbc.gridx = 0;
        itemGbc.gridy = 7;
        JLabel useDurationLabel = new JLabel("Use Duration:");
        useDurationLabel.setFont(useDurationLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(useDurationLabel, itemGbc);
        itemGbc.gridx = 1;
        useDuration.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/use_duration"), useDuration), itemGbc);

        itemGbc.gridx = 0;
        itemGbc.gridy = 8;
        JLabel destroySpeedLabel = new JLabel("Item destroy speed:");
        destroySpeedLabel.setFont(destroySpeedLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(destroySpeedLabel, itemGbc);
        itemGbc.gridx = 1;
        destroySpeed.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/destroy_speed"), destroySpeed), itemGbc);

        itemGbc.gridx = 0;
        itemGbc.gridy = 9;
        JLabel useCountLabel = new JLabel("Item use count/durability:");
        useCountLabel.setFont(useCountLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(useCountLabel, itemGbc);
        itemGbc.gridx = 1;
        damageCount.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/number_of_uses"), damageCount), itemGbc);

        itemGbc.gridx = 0;
        itemGbc.gridy = 10;
        JLabel destoryAnyBlockLabel = new JLabel("Can destroy any block:");
        destoryAnyBlockLabel.setFont(destoryAnyBlockLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(destoryAnyBlockLabel, itemGbc);
        itemGbc.gridx = 1;
        destroyAnyBlock.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/can_destroy_any_block"), destroyAnyBlock), itemGbc);

        itemGbc.gridx = 0;
        itemGbc.gridy = 11;
        JLabel naimationLabel = new JLabel("Item animation:");
        naimationLabel.setFont(destoryAnyBlockLabel.getFont().deriveFont(14f));
        itemPropertiesPanel.add(naimationLabel, itemGbc);
        itemGbc.gridx = 1;
        animation.setPreferredSize(componentSize);
        itemPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/animation"), animation), itemGbc);

        JPanel mainPanel2 = new JPanel(new GridBagLayout());
        mainPanel2.setOpaque(false);
        GridBagConstraints propertiesGbc = new GridBagConstraints();
        propertiesGbc.fill = GridBagConstraints.HORIZONTAL;
        propertiesGbc.insets = new Insets(15, 15, 15, 15);
        propertiesGbc.gridx = 0;
        propertiesGbc.gridy = 0;
        propertiesGbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel2.add(itemPropertiesPanel, propertiesGbc);

        this.addPage(L10N.t("fb.item.properties"), mainPanel2);

        // ==== FOOD PROPERTIES ==== //
        JPanel foodPropertiesPanel = new JPanel(new GridBagLayout());
        foodPropertiesPanel.setOpaque(false);
        foodPropertiesPanel.setBorder(BorderFactory.createTitledBorder(
                new RoundedBorder(Color.GRAY, 2, 15), "Food Properties", TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints foodGbc = new GridBagConstraints();
        foodGbc.fill = GridBagConstraints.HORIZONTAL;
        foodGbc.insets = new Insets(5, 10, 4, 10);
        foodGbc.anchor = GridBagConstraints.WEST;
        foodGbc.gridx = 0;
        foodGbc.weightx = 0;

        foodGbc.gridx = 0;
        foodGbc.gridy = 1;
        JLabel foodLabel = new JLabel("Is this item Food?:");
        foodLabel.setFont(foodLabel.getFont().deriveFont(14f));
        foodPropertiesPanel.add(foodLabel, foodGbc);
        foodGbc.gridx = 1;
        isFood.setPreferredSize(componentSize);
        foodPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/is_food"), isFood), foodGbc);

        foodGbc.gridx = 0;
        foodGbc.gridy = 2;
        JLabel nutritionLabel = new JLabel("Nutritional value:");
        nutritionLabel.setFont(nutritionLabel.getFont().deriveFont(14f));
        foodPropertiesPanel.add(nutritionLabel, foodGbc);
        foodGbc.gridx = 1;
        nutritionalValue.setPreferredSize(componentSize);
        foodPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/nutritional_value"), nutritionalValue), foodGbc);

        foodGbc.gridx = 0;
        foodGbc.gridy = 3;
        JLabel saturationLabel = new JLabel("Saturation:");
        saturationLabel.setFont(saturationLabel.getFont().deriveFont(14f));
        foodPropertiesPanel.add(saturationLabel, foodGbc);
        foodGbc.gridx = 1;
        saturation.setPreferredSize(componentSize);
        foodPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/saturation"), saturation), foodGbc);

        foodGbc.gridx = 0;
        foodGbc.gridy = 4;
        JLabel edibleLabel = new JLabel("Is always edible:");
        edibleLabel.setFont(edibleLabel.getFont().deriveFont(14f));
        foodPropertiesPanel.add(edibleLabel, foodGbc);
        foodGbc.gridx = 1;
        isAlwaysEdible.setPreferredSize(componentSize);
        foodPropertiesPanel.add(HelpUtils.wrapWithHelpButton(
                this.withEntry("item/saturation"), isAlwaysEdible), foodGbc);

        JPanel mainPanel3 = new JPanel(new GridBagLayout());
        mainPanel3.setOpaque(false);
        mainPanel3.add(foodPropertiesPanel, foodGbc);

        this.addPage(L10N.t("fb.item.foodproperties"), mainPanel3);
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult.PASS();
    }

    @Override
    protected void openInEditingMode(FItem item) {
        name.setText(item.name);
        toolTip.setText(item.toolTip);
        rarity.setSelectedItem(item.rarity);
        texture.setTexture(item.texture);
        creativeTabs.setListElements(item.creativeTabs);
        stackSize.setValue(item.stackSize);
        enchantability.setValue(item.enchantability);
        destroySpeed.setValue(item.destroySpeed);
        useDuration.setValue(item.useDuration);
        damageCount.setValue(item.damageCount);
        destroyAnyBlock.setSelected(item.destroyAnyBlock);
        damageVsEntity.setValue(item.damageVSEntity);
        enableMeleeDamage.setSelected(item.enableMeleeDamage);
        renderType.setSelectedItem(item.renderType);

        //FOOD
        isFood.setSelected(item.isFood);
        animation.setSelectedItem(item.animation);
        saturation.setValue(item.saturation);
        nutritionalValue.setValue(item.nutritionalValue);
        isAlwaysEdible.setSelected(item.isAlwaysEdible);

        Model model = item.getItemModel();
        if (model != null)
            renderType.setSelectedItem(model);
    }

    @Override
    public FItem getElementFromGUI() {
        FItem item = new FItem(modElement);
        item.name = name.getText();
        item.toolTip = (String) toolTip.getText();
        item.rarity = (String) rarity.getSelectedItem();
        item.creativeTabs = creativeTabs.getListElements();
        item.stackSize = (int) stackSize.getValue();
        item.enchantability = (int) enchantability.getValue();
        item.destroySpeed = (double) destroySpeed.getValue();
        item.useDuration = (int) useDuration.getValue();
        item.damageCount = (int) damageCount.getValue();
        item.destroyAnyBlock = destroyAnyBlock.isSelected();
        item.damageVSEntity = (double) damageVsEntity.getValue();
        item.enableMeleeDamage = enableMeleeDamage.isSelected();

        item.texture = texture.getTextureHolder();
        item.renderType = encodeModelType(Objects.requireNonNull(renderType.getSelectedItem()).getType());
        item.customModelName = Objects.requireNonNull(renderType.getSelectedItem()).getReadableName();

        //FOOD
        item.isFood = (boolean) isFood.isSelected();
        item.animation = (String) animation.getSelectedItem();
        item.saturation = (double) saturation.getValue();
        item.nutritionalValue = (int) nutritionalValue.getValue();
        item.isAlwaysEdible = (boolean) isAlwaysEdible.isSelected();

        return item;
    }
}

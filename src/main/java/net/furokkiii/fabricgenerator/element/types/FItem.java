package net.furokkiii.fabricgenerator.element.types;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.parts.TextureHolder;
import net.mcreator.element.types.interfaces.IItem;
import net.mcreator.element.types.interfaces.IItemWithModel;
import net.mcreator.element.types.interfaces.IItemWithTexture;
import net.mcreator.minecraft.MCItem;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.TexturedModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static net.mcreator.element.types.Item.decodeModelType;

public class FItem extends GeneratableElement implements IItem, IItemWithModel, IItemWithTexture {

    public int renderType;
    public TextureHolder texture;
    public String customModelName;

    public String name;
    public String rarity;
    public List<TabEntry> creativeTabs;
    public int stackSize;
    public int enchantability;
    public int useDuration;
    public int damageCount;
    public double destroySpeed;
    public boolean destroyAnyBlock;

    public boolean enableMeleeDamage;
    public double damageVSEntity;

    public String toolTip;

    // FOOD SETTINGS
    public boolean isFood;
    public String animation;
    public int nutritionalValue;
    public double saturation;
    public boolean isAlwaysEdible;

    public FItem(ModElement element) {
        super(element);

        this.creativeTabs = new ArrayList<>();

        this.rarity = "COMMON";

        this.destroySpeed = 1;

        this.isFood = false;

        this.animation = "NONE";
        this.saturation = 0.3f;
        this.nutritionalValue = 1;
        this.isAlwaysEdible = false;

        this.toolTip = "";
    }

    public BufferedImage generateModElementPicture() {
        return ImageUtils.resizeAndCrop(texture.getImage(TextureType.ITEM), 32);
    }

    public Model getItemModel() {
        Model.Type modelType = Model.Type.BUILTIN;
        if (this.renderType == 1) {
            modelType = Model.Type.JSON;
        } else if (this.renderType == 2) {
            modelType = Model.Type.OBJ;
        }
        return Model.getModelByParams(this.getModElement().getWorkspace(), this.customModelName, modelType);
    }

    public Map<String, TextureHolder> getTextureMap() {
        Model model = this.getItemModel();
        return model instanceof TexturedModel && ((TexturedModel)model).getTextureMapping() != null ? ((TexturedModel)model).getTextureMapping().getTextureMap() : null;
    }

    public List<MCItem> providedMCItems() {
        return List.of(new MCItem.Custom(this.getModElement(), null, "item"));
    }

    public TextureHolder getTexture() {
        return this.texture;
    }

    public boolean hasNormalModel() {
        return decodeModelType(renderType) == Model.Type.BUILTIN && customModelName.equals("Normal");
    }

    public boolean hasToolModel() {
        return decodeModelType(renderType) == Model.Type.BUILTIN && customModelName.equals("Tool");
    }

    public boolean hasNonDefaultAnimation() {
        return isFood ? !animation.equals("eat") : !animation.equals("none");
    }

    public boolean hasTooltip() {
        return !toolTip.isEmpty();
    }
}

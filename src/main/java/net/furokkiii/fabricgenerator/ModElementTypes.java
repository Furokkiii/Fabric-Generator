package net.furokkiii.fabricgenerator;

import net.furokkiii.fabricgenerator.element.types.FItem;
import net.furokkiii.fabricgenerator.ui.modgui.FItemGUI;
import net.mcreator.element.ModElementType;

import static net.mcreator.element.ModElementTypeLoader.register;

public class ModElementTypes {
    public static ModElementType<?> FITEM;

    public static void load() {
        FITEM = register(
                new ModElementType<>("fitem", 'i', FItemGUI::new, FItem.class)
        );
    }
}

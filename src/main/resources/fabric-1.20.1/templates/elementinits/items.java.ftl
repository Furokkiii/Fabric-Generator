<#-- @formatter:off -->
package ${package}.item;
import ${package}.item.*;

public class ModItems {

    <#list items as item>
    <#if item.getModElement().getTypeString() == "fitem">
    public static Item ${item.getModElement().getRegistryNameUpper()} = registerItem("${item.getModElement().getRegistryName()}", new ${item.getModElement().getName()}Item(new Item.Settings()));
    </#if>
    </#list>

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(${JavaModName}.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {
            <#list items as item>
            entries.add(${item.getModElement().getRegistryNameUpper()});
            </#list>
        });
    }
}
<#-- @formatter:on -->
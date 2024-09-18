<#-- @formatter:off -->
package ${package};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${package}.item;

public class ${JavaModName} implements ModInitializer {

    public static final String MOD_ID = "${modid}";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Starting ${JavaModName}");

        <#if w.hasElementsOfBaseType("item")>// ==== ITEMS ==== //</#if>
		<#if w.hasElementsOfBaseType("item")>ModItems.registerModItems();</#if>
        <#if w.hasElementsOfBaseType("item")>LOGGER.info("${JavaModName} => Loading Items");</#if>

        <#if w.hasElementsOfType("gui")>// ==== SCREENS ==== //</#if>
        <#if w.hasElementsOfType("gui")>ModScreenHandlers.registerScreenHandlers();</#if>
        <#if w.hasElementsOfType("gui")>LOGGER.info("${JavaModName} => Loading Screens");</#if>

        <#if w.hasElementsOfType("procedure")>// ==== Procedures ==== //</#if>
        <#if w.hasElementsOfType("procedure")>ModProcedures.loadProcedures();</#if>
    }
}
<#-- @formatter:on -->
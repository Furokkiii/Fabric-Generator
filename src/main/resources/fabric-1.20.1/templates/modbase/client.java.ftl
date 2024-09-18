<#-- @formatter:off -->
package ${package};
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ${JavaModName}Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        <#if w.hasElementsOfType("gui")>// ==== SCREENS ==== //</#if>
        <#if w.hasElementsOfType("gui")>ModScreenHandlers.registerScreensClient();</#if>
    }
}
<#-- @formatter:on -->
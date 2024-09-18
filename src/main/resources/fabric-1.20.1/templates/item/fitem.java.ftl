<#-- @formatter:off -->
<#include "../mcitems.ftl">
package ${package}.item.custom;

<#compress>
public class ${name}Item extends Item {
	public ${name}Item(Settings settings) {
        super(settings
                .maxCount(${data.stackSize})
		.rarity(Rarity.${data.rarity})
		<#if data.damageCount != 0>
		.maxDamage(${data.damageCount})
		</#if>
		<#if data.isFood>
		.food(new FoodComponent.Builder().hunger(${data.nutritionalValue}).saturationModifier((float) ${data.saturation})<#if data.isAlwaysEdible>.alwaysEdible()</#if>.build())
		</#if>);
    }

	<#if data.enchantability != 0>
    @Override
    public int getEnchantability() {
        return ${data.enchantability};
    }
	</#if>

	<#if data.destroySpeed != 1>
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return ${data.destroySpeed}F;
    }
    </#if>

	<#if data.destroyAnyBlock>
    @Override
    public boolean isCorrectForDrops(ItemStack stack, BlockState state) {
        return true;
    }
	</#if>

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return ${data.useDuration};
    }

	<#if data.hasNonDefaultAnimation()>
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.${data.animation?upper_case};
    }
	</#if>

    <#if data.toolTip != "">
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.literal("${data.toolTip}"));
    }
    </#if>
}
</#compress>
<#-- @formatter:on -->
package net.trueHorse.enchantmentStones;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AddEnchantmentStoneRecipe extends SpecialCraftingRecipe {

    public AddEnchantmentStoneRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        boolean hasEquipment = false;
        boolean hasEnchantmentStone = false;

        for(int i=0;i<inventory.size();i++){
            ItemStack stack = inventory.getStack(i);
            if(!stack.isEmpty()){
                if(stack.isIn(EnchantmentStones.ENCHANTMENT_STONES)&&!hasEnchantmentStone){
                    hasEnchantmentStone=true;
                    continue;
                }
                if(((ItemAccess)stack.getItem()).isEquipment(stack)&&!hasEquipment){
                    hasEquipment = true;
                    continue;
                }
                return false;
            }
        }

        return hasEnchantmentStone&&hasEquipment;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack equipmentStack = ItemStack.EMPTY;
        ItemStack stoneStack = ItemStack.EMPTY;

        for(int i=0;i<inventory.size();i++){
            ItemStack stack = inventory.getStack(i);
            if(!stack.isEmpty()&&((ItemAccess)stack.getItem()).isEquipment(stack)){
                equipmentStack = stack.copy();
                break;
            }
        }
        for(int i=0;i<inventory.size();i++){
            ItemStack stack = inventory.getStack(i);
            if(!stack.isEmpty()&&stack.isIn(EnchantmentStones.ENCHANTMENT_STONES)){
                stoneStack = stack.copy();
                break;
            }
        }

        ((ItemStackAccess)(Object)equipmentStack).addEnchantmentStone(stoneStack);
        return equipmentStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<AddEnchantmentStoneRecipe> getSerializer() {
        return EnchantmentStonesRecipeSerializer.ADD_ENCHANTMENT_STONE;
    }
}

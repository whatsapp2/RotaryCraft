/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesDryingBed
{
	private static final RecipesDryingBed DryingBase = new RecipesDryingBed();

	private HashMap<Fluid, ItemStack> recipeList = new HashMap();
	private HashMap<Fluid, Integer> amounts = new HashMap();

	public static final RecipesDryingBed getRecipes()
	{
		return DryingBase;
	}

	private RecipesDryingBed()
	{
		this.addRecipe(FluidRegistry.WATER, 250, ItemStacks.salt);
		this.addRecipe(FluidRegistry.LAVA, 1000, new ItemStack(Items.gold_nugget));
		this.addRecipe("oil", 200, ItemStacks.tar);

		ArrayList<ItemStack> li = OreDictionary.getOres("rubber");
		if (li == null || li.isEmpty()) {
			li = OreDictionary.getOres("itemRubber");
		}
		if (li != null && !li.isEmpty())
			this.addRecipe("lubricant", 100, li.get(0));

		this.addRecipe("chroma", 2000, new ItemStack(Items.emerald));
	}

	private void addRecipe(Fluid f, int amount, ItemStack out)
	{
		recipeList.put(f, out);
		amounts.put(f, amount);
	}

	private void addRecipe(String s, int amount, ItemStack out)
	{
		Fluid f = FluidRegistry.getFluid(s);
		if (f != null)
			this.addRecipe(f, amount, out);
	}

	public ItemStack getDryingResult(FluidStack liquid)
	{
		Fluid f = liquid.getFluid();
		if (amounts.containsKey(f)) {
			int req = amounts.get(f);
			if (req > liquid.amount)
				return null;
			return recipeList.get(f);
		}
		else
			return null;
	}

	public Fluid getRecipe(ItemStack result) {
		for (Fluid f : recipeList.keySet()) {
			ItemStack is = recipeList.get(f);
			if (ReikaItemHelper.matchStacks(result, is))
				return f;
		}
		return null;
	}

	public int getRecipeConsumption(ItemStack result) {
		for (Fluid f : recipeList.keySet()) {
			ItemStack is = recipeList.get(f);
			if (ReikaItemHelper.matchStacks(result, is))
				return amounts.get(f);
		}
		return 0;
	}

	public boolean isRecipeFluid(Fluid f) {
		return recipeList.containsKey(f);
	}
}

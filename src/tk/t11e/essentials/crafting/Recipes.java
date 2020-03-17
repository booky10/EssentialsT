package tk.t11e.essentials.crafting;
// Created by booky10 in EssentialsT (18:58 05.03.20)

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.t11e.essentials.main.Main;
import tk.t11e.api.util.BlockGroup;

import java.util.Collection;

public class Recipes {

    public static void register() {
        /*for (Material material : BlockGroup.GLASS_PANES.getGroup())
            Bukkit.addRecipe(getGlassItemFrameRecipe(material));*/
    }

    public static Recipe getGlassItemFrameRecipe(Material ingredient) {
        ItemStack result = new ItemStack(Material.ITEM_FRAME);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName("§fGlass Item Frame");
        meta.setLocalizedName("§fGlass Item Frame");
        meta.setCustomModelData(1);
        result.setItemMeta(meta);
        Material ingredient2 = Material.ITEM_FRAME;
        NamespacedKey key = new NamespacedKey(Main.main,
                "glass_item_frame_" + ingredient.getKey().getKey());
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("aaa", "aba", "aaa");
        recipe.setIngredient('a', ingredient);
        recipe.setIngredient('b', ingredient2);
        return recipe;
    }
}
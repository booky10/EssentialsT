package tk.t11e.essentials.util;
// Created by booky10 in EssentialsT (17:45 14.02.20)

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class ItemIDs {

    public static int getID(Material material) {
        List<Material> materials= Arrays.asList(Material.values());
        for (int i = 0; i < materials.size(); i++)
            if(i==materials.indexOf(material))
                return i;
        return 0;
    }
}
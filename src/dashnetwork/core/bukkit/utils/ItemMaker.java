package dashnetwork.core.bukkit.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

public class ItemMaker {

    private final Material material;
    private int amount;
    private String name;
    private Color color;
    private List<String> lore;
    private List<ItemFlag> itemFlags;
    private Map<Enchantment, Integer> enchantments;
    private boolean unbreakable;

    public ItemMaker(Material material) {
        this.material = material;
        this.amount = 1;
        this.unbreakable = false;
    }

    public ItemMaker name(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        return this;
    }

    public ItemMaker lore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemMaker amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemMaker unbreakable() {
        this.unbreakable = true;
        return this;
    }

    public ItemMaker color(Color color) {
        this.color = color;
        return this;
    }

    public ItemMaker flags(ItemFlag... flags) {
        this.itemFlags = Arrays.asList(flags);
        return this;
    }

    public ItemMaker enchant(Enchantment enchantment, int level) {
        if (enchantments == null)
            enchantments = new HashMap<>();

        enchantments.put(enchantment, level);
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null)
                meta.setDisplayName(name);

            if (lore != null)
                meta.setLore(lore);

            if (itemFlags != null)
                for (ItemFlag flag : itemFlags)
                    meta.addItemFlags(flag);

            if (enchantments != null)
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet())
                    meta.addEnchant(entry.getKey(), entry.getValue(), true);

            meta.spigot().setUnbreakable(unbreakable);
            item.setItemMeta(meta);

            if (meta instanceof LeatherArmorMeta) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;

                if (color != null)
                    leatherArmorMeta.setColor(color);

                item.setItemMeta(leatherArmorMeta);
            }
        }

        return item;
    }
}
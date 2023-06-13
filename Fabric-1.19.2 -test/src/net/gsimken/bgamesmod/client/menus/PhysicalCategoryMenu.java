 package net.gsimken.bgamesmod.client.menus;


import net.gsimken.bgamesmod.client.ModMenus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import java.util.HashMap;

 public class PhysicalCategoryMenu extends ScreenHandler {
     public final static HashMap<String, Object> guistate = new HashMap<>();
     public final PlayerEntity player;
     public PhysicalCategoryMenu(int id, PlayerInventory inv, PacketByteBuf extraData) {
         super(ModMenus.PHYSICAL_CATEGORY.get(), id);
         this.player = inv.player;

     }


     @Override
     public boolean canUse(PlayerEntity player) {
         return true;
     }

     @Override
     public ItemStack transferSlot(PlayerEntity playerIn, int index) {
         return ItemStack.EMPTY;
     }


 }

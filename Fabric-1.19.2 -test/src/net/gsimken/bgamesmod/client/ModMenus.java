package net.gsimken.bgamesmod.client;

import net.gsimken.bgamesmod.BgamesMod;
import net.gsimken.bgamesmod.client.menus.ChooseCategoriesMenu;
import net.gsimken.bgamesmod.client.menus.CognitiveCategoryMenu;
import net.gsimken.bgamesmod.client.menus.PhysicalCategoryMenu;
import net.gsimken.bgamesmod.client.menus.SocialCategoryMenu;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<ScreenHandlerType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BgamesMod.MOD_ID);

    public static final RegistryObject<ScreenHandlerType<ChooseCategoriesMenu>> CHOOSE_CATEGORY = registerMenuType(ChooseCategoriesMenu::new,"bgamesmod_choose_category");
    public static final RegistryObject<ScreenHandlerType<PhysicalCategoryMenu>> PHYSICAL_CATEGORY = registerMenuType(PhysicalCategoryMenu::new,"bgamesmod_physical_category");
    public static final RegistryObject<ScreenHandlerType<CognitiveCategoryMenu>> COGNITIVE_CATEGORY = registerMenuType(CognitiveCategoryMenu::new,"bgamesmod_cognitive_category");
    public static final RegistryObject<ScreenHandlerType<SocialCategoryMenu>> SOCIAL_CATEGORY = registerMenuType(SocialCategoryMenu::new,"bgamesmod_social_category");


    private static <T extends ScreenHandler> RegistryObject<ScreenHandlerType<T>> registerMenuType(IContainerFactory<T> factory,String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}

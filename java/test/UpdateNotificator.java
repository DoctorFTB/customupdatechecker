package test;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UpdateNotificator {

    private static boolean sended;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent e) {
        if (!sended && e.getEntity() == Minecraft.getMinecraft().player && e.getEntity().world.isRemote) {
            if (!TestMain.VERSION.trim().equals(UpdateThread.version.trim())) {
                sended = true;
                TextComponentString str1 = new TextComponentString(TestMain.MODNAME + " is outdated. Click");
                TextComponentString str2 = new TextComponentString("to update.");
                TextComponentString here = new TextComponentString(" here ");
				here.getStyle()
						.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
								TestMain.DOWNLOADLINK))
						.setColor(TextFormatting.GREEN).setBold(true);
                Minecraft.getMinecraft().player.sendMessage(str1.appendSibling(here).appendSibling(str2));
            }
        } else if (sended)
            MinecraftForge.EVENT_BUS.unregister(this);
    }
}
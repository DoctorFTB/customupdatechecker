package test;

import java.lang.reflect.Constructor;
import java.util.Map;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.common.ForgeVersion.Status;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = TestMain.MODID, name = TestMain.MODNAME, version = TestMain.VERSION)
public class TestMain {

	public static final String MODID = "test", MODNAME = "Test", VERSION = "@VERSION@", DOWNLOADLINK = "google.com";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		new UpdateThread();
		MinecraftForge.EVENT_BUS.register(new UpdateNotificator());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		if (!VERSION.trim().equals(UpdateThread.version.trim())) {
			try {
				Map<ModContainer, CheckResult> results = ReflectionHelper.getPrivateValue(ForgeVersion.class, new ForgeVersion(), "results");
				Constructor<CheckResult> constructor = (Constructor<CheckResult>) CheckResult.class.getDeclaredConstructors()[0];
				constructor.setAccessible(true);
				CheckResult res = constructor.newInstance(Status.OUTDATED, null, null, "google.com");
				results.put(Loader.instance().activeModContainer(), res);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

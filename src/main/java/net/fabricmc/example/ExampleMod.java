package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("modid");

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register(((player, world, hand, hitResult) -> {
			if (player.getStackInHand(hand).getItem() != Items.WITHER_SKELETON_SKULL) return ActionResult.PASS;
			var block = world.getBlockState(hitResult.getBlockPos());
			if (block.getBlock() != Blocks.SPAWNER) return  ActionResult.PASS;
			var blockEntity = (MobSpawnerBlockEntity) world.getBlockEntity(hitResult.getBlockPos());
			blockEntity.getLogic().setEntityId(EntityType.WITHER_SKELETON);
			player.getStackInHand(hand).decrement(1);
			return ActionResult.SUCCESS;
		}));

		LOGGER.info("Hello Fabric world!");
	}
}

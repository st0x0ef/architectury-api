/*
 * This file is part of architectury.
 * Copyright (C) 2020, 2021, 2022 architectury
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package dev.architectury.event.events.common;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface InteractionEvent {
    /**
     * @see LeftClickBlock#click(Player, InteractionHand, BlockPos, Direction)
     */
    Event<LeftClickBlock> LEFT_CLICK_BLOCK = EventFactory.createInteractionResult();
    /**
     * @see RightClickBlock#click(Player, InteractionHand, BlockPos, Direction)
     */
    Event<RightClickBlock> RIGHT_CLICK_BLOCK = EventFactory.createInteractionResult();
    /**
     * @see RightClickItem#click(Player, InteractionHand)
     */
    Event<RightClickItem> RIGHT_CLICK_ITEM = EventFactory.createInteractionResult();
    /**
     * @see ClientLeftClickAir#click(Player, InteractionHand)
     */
    Event<ClientLeftClickAir> CLIENT_LEFT_CLICK_AIR = EventFactory.createLoop();
    /**
     * @see ClientRightClickAir#click(Player, InteractionHand)
     */
    Event<ClientRightClickAir> CLIENT_RIGHT_CLICK_AIR = EventFactory.createLoop();
    /**
     * @see InteractEntity#interact(Player, Entity, InteractionHand)
     */
    Event<InteractEntity> INTERACT_ENTITY = EventFactory.createEventResult();
    /**
     * @see FarmlandTrample#trample(Level, BlockPos, BlockState, double, Entity)
     */
    Event<FarmlandTrample> FARMLAND_TRAMPLE = EventFactory.createInteractionResult();
    /**
     * @see UseItemOnBlock#useItemOn(Level, Player, InteractionHand, ItemStack, BlockState, BlockHitResult)
     */
    Event<UseItemOnBlock> USE_ITEM_ON_BLOCK = EventFactory.createInteractionResult();
    /**
     * @see UseBlockWithoutItem#useWithoutItem(Level, Player, BlockState, BlockHitResult)
     */
    Event<UseBlockWithoutItem> USE_BLOCK_WITHOUT_ITEM = EventFactory.createInteractionResult();
    /**
     * @see UseItemOn#useOn(UseOnContext)
     */
    Event<UseItemOn> USE_ITEM_ON = EventFactory.createInteractionResult();
    /**
     * @see UseItem#use(Level, Player, InteractionHand)
     */
    Event<UseItem> USE_ITEM = EventFactory.createInteractionResult();
    
    interface RightClickBlock {
        /**
         * Invoked whenever a player right clicks a block.
         * Equivalent to Forge's {@code PlayerInteractEvent.RightClickBlock} event and Fabric's {@code UseBlockCallback}.
         *
         * @param player The player right clicking the block.
         * @param hand   The hand that is used.
         * @param pos    The position of the block in the level.
         * @param face   The face of the block clicked.
         * @return A {@link InteractionResult} determining the outcome of the event,
         * the action may be cancelled by the result.
         */
        InteractionResult click(Player player, InteractionHand hand, BlockPos pos, Direction face);
    }
    
    interface LeftClickBlock {
        /**
         * Invoked whenever a player left clicks a block.
         * Equivalent to Forge's {@code PlayerInteractEvent.LeftClickBlock} event and Fabric's {@code AttackBlockCallback}.
         *
         * @param player The player left clicking the block.
         * @param hand   The hand that is used.
         * @param pos    The position of the block in the level. Use {@link Player#getCommandSenderWorld()} to get the level.
         * @param face   The face of the block clicked.
         * @return A {@link InteractionResult} determining the outcome of the event,
         * the action may be cancelled by the result.
         */
        InteractionResult click(Player player, InteractionHand hand, BlockPos pos, Direction face);
    }
    
    interface RightClickItem {
        /**
         * Invoked whenever a player uses an item on a block.
         * Equivalent to Forge's {@code PlayerInteractEvent.RightClickItem} event and Fabric's {@code UseItemCallback}.
         *
         * @param player The player right clicking the block.
         * @param hand   The hand that is used.
         * @return A {@link InteractionResult} determining the outcome of the event,
         * the action may be cancelled by the result.
         */
        InteractionResult click(Player player, InteractionHand hand);
    }
    
    interface ClientRightClickAir {
        /**
         * Invoked whenever a player right clicks the air.
         * This only occurs on the client.
         * Equivalent to Forge's {@code PlayerInteractEvent.RightClickEmpty} event.
         *
         * @param player The player. Always {@link net.minecraft.client.player.LocalPlayer}
         * @param hand   The hand used.
         */
        void click(Player player, InteractionHand hand);
    }
    
    interface ClientLeftClickAir {
        /**
         * Invoked whenever a player left clicks the air.
         * This only occurs on the client.
         * Equivalent to Forge's {@code PlayerInteractEvent.LeftClickEmpty} event.
         *
         * @param player The player. Always {@link net.minecraft.client.player.LocalPlayer}
         * @param hand   The hand used.
         */
        void click(Player player, InteractionHand hand);
    }
    
    interface InteractEntity {
        /**
         * Invoked whenever a player right clicks an entity.
         * Equivalent to Forge's {@code PlayerInteractEvent.EntityInteract} event.
         *
         * @param player The player clicking the entity.
         * @param entity Then entity the player clicks.
         * @param hand   The used hand.
         * @return A {@link EventResult} determining the outcome of the event,
         * the action may be cancelled by the result.
         */
        EventResult interact(Player player, Entity entity, InteractionHand hand);
    }
    
    interface FarmlandTrample {
        /**
         * Invoked when an entity attempts to trample farmland.
         * Equivalent to Forge's {@code BlockEvent.FarmlandTrampleEvent} event.
         *
         * @param world    The level where the block and the player are located in.
         * @param pos      The position of the block.
         * @param state    The state of the block.
         * @param distance The distance of the player to the block.
         * @param entity   The entity trampling.
         * @return A {@link InteractionResult} determining the outcome of the event,
         * the action may be cancelled by the result.
         */
        InteractionResult trample(Level world, BlockPos pos, BlockState state, double distance, Entity entity);
    }
    
    interface UseItemOnBlock {
        /**
         * Invoked when a block's own use behaviour runs for a player holding an item, from
         * {@link BlockState#useItemOn(ItemStack, Level, Player, InteractionHand, BlockHitResult)}.
         * Fires on both the logical client and the logical server.
         *
         * <p>This is the middle of the three phases vanilla walks through on a right click, and is finer grained than
         * {@link #RIGHT_CLICK_BLOCK}: that event fires once per right click, before the game decides which behaviour to
         * run, while this one fires only when the block behaviour itself is about to run.
         *
         * <p>Equivalent to NeoForge's {@code UseItemOnBlockEvent} in the {@code BLOCK} phase and Fabric's
         * {@code BlockEvents.USE_ITEM_ON}.
         *
         * @param level  The level the block is in.
         * @param player The player interacting with the block.
         * @param hand   The hand that is used.
         * @param stack  The stack held in {@code hand}, may be empty.
         * @param state  The state of the block being interacted with.
         * @param hit    The hit result pointing at the block.
         * @return A {@link InteractionResult} determining the outcome of the event. Anything other than
         * {@link InteractionResult#PASS} replaces the block's behaviour and is returned to vanilla in its place.
         */
        InteractionResult useItemOn(Level level, Player player, InteractionHand hand, ItemStack stack, BlockState state, BlockHitResult hit);
    }
    
    interface UseBlockWithoutItem {
        /**
         * Invoked when a block's empty-handed use behaviour runs, from
         * {@link BlockState#useWithoutItem(Level, Player, BlockHitResult)}. Fires on both the logical client and the
         * logical server.
         *
         * <p>Vanilla reaches this after {@link #USE_ITEM_ON_BLOCK} declines to consume the interaction, so a block such
         * as a door can still be opened while the player is holding an item that does nothing here.
         *
         * <p>Fabric exposes this natively as {@code BlockEvents.USE_WITHOUT_ITEM}; NeoForge has no equivalent event, so
         * Architectury supplies it with a mixin there.
         *
         * @param level  The level the block is in.
         * @param player The player interacting with the block.
         * @param state  The state of the block being interacted with.
         * @param hit    The hit result pointing at the block.
         * @return A {@link InteractionResult} determining the outcome of the event. Anything other than
         * {@link InteractionResult#PASS} replaces the block's behaviour and is returned to vanilla in its place.
         */
        InteractionResult useWithoutItem(Level level, Player player, BlockState state, BlockHitResult hit);
    }
    
    interface UseItemOn {
        /**
         * Invoked when an item's own use-on-block behaviour runs, from {@link net.minecraft.world.item.Item#useOn(UseOnContext)}.
         * Fires on both the logical client and the logical server.
         *
         * <p>This is the last of the three phases vanilla walks through on a right click, reached once the block
         * declined to consume the interaction. Note the context's {@link UseOnContext#getPlayer() player} may be
         * {@code null}, as items are also placed by non-player sources such as dispensers.
         *
         * <p>Equivalent to NeoForge's {@code UseItemOnBlockEvent} in the {@code ITEM_AFTER_BLOCK} phase and Fabric's
         * {@code ItemEvents.USE_ON}. The two loaders cancel at slightly different depths: replacing the result here
         * still awards {@link net.minecraft.stats.Stats#ITEM_USED} on Fabric, but not on NeoForge.
         *
         * @param context The context of the interaction.
         * @return A {@link InteractionResult} determining the outcome of the event. Anything other than
         * {@link InteractionResult#PASS} replaces the item's behaviour and is returned to vanilla in its place.
         */
        InteractionResult useOn(UseOnContext context);
    }
    
    interface UseItem {
        /**
         * Invoked when an item's own use behaviour runs, from
         * {@link net.minecraft.world.item.Item#use(Level, Player, InteractionHand)}. Fires on both the logical client
         * and the logical server.
         *
         * <p>This is finer grained than {@link #RIGHT_CLICK_ITEM}: that event fires once when the player right clicks
         * with nothing else to interact with, while this one fires whenever the item's use behaviour itself is about to
         * run, including when a block interaction falls through to it.
         *
         * <p>Fabric exposes this natively as {@code ItemEvents.USE}; NeoForge has no equivalent event, so Architectury
         * supplies it with a mixin there.
         *
         * @param level  The level the player is in.
         * @param player The player using the item.
         * @param hand   The hand that is used.
         * @return A {@link InteractionResult} determining the outcome of the event. Anything other than
         * {@link InteractionResult#PASS} replaces the item's behaviour and is returned to vanilla in its place.
         */
        InteractionResult use(Level level, Player player, InteractionHand hand);
    }
}

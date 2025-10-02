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

package dev.architectury.hooks.client.fluid.fabric;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class ClientFluidStackHooksImpl {
    @Nullable
    public static TextureAtlasSprite getStillTexture(@Nullable BlockAndTintGetter level, @Nullable BlockPos pos, FluidState state) {
        if (state.getType() == Fluids.EMPTY) return null;
        var handler = FluidRenderHandlerRegistry.INSTANCE.get(state.getType());
        if (handler == null) return null;
        var sprites = handler.getFluidSprites(level, pos, state);
        if (sprites == null) return null;
        return sprites[0];
    }
    
    @Nullable
    public static TextureAtlasSprite getStillTexture(FluidStack stack) {
        var sprites = FluidVariantRendering.getSprites(FluidStackHooksFabric.toFabric(stack));
        return sprites == null ? null : sprites[0];
    }
    
    @Nullable
    public static TextureAtlasSprite getStillTexture(Fluid fluid) {
        var sprites = FluidVariantRendering.getSprites(FluidVariant.of(fluid));
        return sprites == null ? null : sprites[0];
    }
    
    @Nullable
    public static TextureAtlasSprite getFlowingTexture(@Nullable BlockAndTintGetter level, @Nullable BlockPos pos, FluidState state) {
        if (state.getType() == Fluids.EMPTY) return null;
        var handler = FluidRenderHandlerRegistry.INSTANCE.get(state.getType());
        if (handler == null) return null;
        var sprites = handler.getFluidSprites(level, pos, state);
        if (sprites == null) return null;
        return sprites[1];
    }
    
    @Nullable
    public static TextureAtlasSprite getFlowingTexture(FluidStack stack) {
        var sprites = FluidVariantRendering.getSprites(FluidStackHooksFabric.toFabric(stack));
        return sprites == null ? null : sprites[1];
    }
    
    @Nullable
    public static TextureAtlasSprite getFlowingTexture(Fluid fluid) {
        var sprites = FluidVariantRendering.getSprites(FluidVariant.of(fluid));
        return sprites == null ? null : sprites[1];
    }
    
    public static int getColor(@Nullable BlockAndTintGetter level, @Nullable BlockPos pos, FluidState state) {
        if (state.getType() == Fluids.EMPTY) return -1;
        var handler = FluidRenderHandlerRegistry.INSTANCE.get(state.getType());
        if (handler == null) return -1;
        return handler.getFluidColor(level, pos, state);
    }
    
    public static int getColor(FluidStack stack) {
        return FluidVariantRendering.getColor(FluidStackHooksFabric.toFabric(stack));
    }
    
    public static int getColor(Fluid fluid) {
        if (fluid == Fluids.EMPTY) return -1;
        var handler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);
        if (handler == null) return -1;
        return handler.getFluidColor(null, null, fluid.defaultFluidState());
    }
}

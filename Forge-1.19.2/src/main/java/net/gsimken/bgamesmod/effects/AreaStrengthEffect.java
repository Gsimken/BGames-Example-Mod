package net.gsimken.bgamesmod.effects;

import net.gsimken.bgamesmod.networking.ModMessages;
import net.gsimken.bgamesmod.networking.packet.CreateSquareRingParticleS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.stream.Collectors;

public class AreaStrengthEffect extends MobEffect {
    int ticks=5;
    public AreaStrengthEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier){

        int radius = Math.min(amplifier*2,20);
        if(entity instanceof Player){

            if(!entity.level.isClientSide()) {

                ;//max radius of 40 blocks
                AABB boundingBox = entity.getBoundingBox().inflate(radius);
                List<LivingEntity> entities = entity.level.getEntitiesOfClass(LivingEntity.class, boundingBox);

                entities = entities.stream()
                        .filter(entityCollected -> entityCollected instanceof TamableAnimal && ((TamableAnimal) entityCollected).isTame() ||  entityCollected instanceof Horse && ((Horse) entityCollected).isTamed() || entityCollected instanceof Player)
                        .collect(Collectors.toList());
                for (int i = 0; i < entities.size(); i++) {
                    LivingEntity objective = entities.get(i);

                    if (!objective.hasEffect(MobEffects.DAMAGE_BOOST) && objective!=entity) {
                        objective.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 2));
                    }
                }
                if(this.ticks>=15){
                    ServerLevel level = (ServerLevel)entity.getLevel();
                    List<ServerPlayer> players = level.getPlayers(LivingEntity::isAlive);
                    BlockPos blockpos = entity.blockPosition();
                    for (ServerPlayer player: players) {
                        if (blockpos.closerToCenterThan(new Vec3(player.getX(), player.getY(), player.getZ()), 32.0D)){
                            ModMessages.sendToPlayer(
                                    new CreateSquareRingParticleS2CPacket(1,
                                            entity.getX(),
                                            entity.getY(),
                                            entity.getZ(),
                                            radius*2,
                                            30),
                                    player);
                        }
                    }
                    this.ticks=0;

                }
                this.ticks++;

            }

        }

        super.applyEffectTick(entity,amplifier);
    }
    @Override
    public boolean isDurationEffectTick(int Duration,int amplifier){
        return true;
    }
}

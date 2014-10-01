/*
 * Copyright (c) CovertJaguar, 2011 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at railcraft.wikispaces.com.
 */
package mods.railcraft.api.electricity;

import java.util.Random;
import mods.railcraft.api.carts.CartTools;
import mods.railcraft.api.carts.ILinkageManager;
import mods.railcraft.api.tracks.RailTools;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This interface provides a simple means of using or producing Electricity
 * within a train.
 * <p>
 * The original Ic2 Battery Carts implement IEnergyTransfer. IEnergyTransfer was
 * a naive implementation of a Energy storage system for carts. I'll leave it in
 * place because of its Ic2 specific functions, but for all intents and purposes
 * this is the recommended and easier to implement interface for Electricity
 * related minecarts. In fact, the Railcraft Ic2 Energy Carts will be
 * redirecting to this interface. The Energy Loaders will continue to work
 * exclusively with IEnergyTransfer for the moment due to the high Ic2 coupling
 * of their design. An alternative loader block utilizing the IElectricMinecart
 * interface may be provided in the future, but no guarantee.
 * <p>
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public interface IElectricMinecart {

    public ChargeHandler getChargeHandler();

    public static final class ChargeHandler {

        public static final int DRAW_INTERVAL = 8;
        private static final Random rand = new Random();

        public enum Type {

            /**
             * Users draw power from tracks, sources, and storage.
             */
            USER,
            /**
             * Sources provide power to users, but not storage. This interface
             * specifies no explicit way to charge Sources, that's up to the
             * implementer. Railcraft provides no Sources currently, and may
             * never do so.
             */
            SOURCE,
            /**
             * Storage provide power to users, but can't draw from tracks or
             * sources. This interface specifies no explicit way to charge
             * Storage, that's up to the implementer. Railcraft may provide a
             * trackside block in the future for charging Storage, but does not
             * currently.
             */
            STORAGE;
        }

        private final EntityMinecart minecart;
        private final Type type;
        private double capacity, charge = 0;
        private final double lossPerTick;
        private int clock = rand.nextInt();
        private int drewFromTrack;

        public ChargeHandler(EntityMinecart minecart, Type type, double capactiy) {
            this(minecart, type, capactiy, 0.0);
        }

        public ChargeHandler(EntityMinecart minecart, Type type, double capacity, double lossPerTick) {
            this.minecart = minecart;
            this.type = type;
            this.capacity = capacity;
            this.lossPerTick = lossPerTick;
        }

        public double getCharge() {
            return charge;
        }

        public double getCapacity() {
            return capacity;
        }

        public double getLosses() {
            return lossPerTick;
        }

        public Type getType() {
            return type;
        }

        /**
         * Averages the charge between two ChargeHandlers.
         * <p>
         * @param other
         */
        public void balance(ChargeHandler other) {
            double total = charge + other.charge;
            double half = total / 2.0;
            charge = half;
            other.charge = half;
        }

        public void setCharge(double charge) {
            this.charge = charge;
        }

        public void addCharge(double charge) {
            this.charge += charge;
        }

        /**
         * Remove up to the requested amount of charge and returns the amount
         * removed.
         * <p>
         * @param request
         * @return charge removed
         */
        public double removeCharge(double request) {
            if (charge >= request) {
                charge -= request;
                return request;
            }
            double ret = charge;
            charge = 0.0;
            return ret;
        }

        /**
         * ********************************************************************
         * The following functions must be called from your EntityMinecart
         * subclass
         * ********************************************************************
         */
        /**
         * Must be called once per tick while on tracks by the owning object.
         * Server side only.
         * <p>
         * <blockquote><pre>
         * {@code
         * public void onEntityUpdate()
         *  {
         *     super.onEntityUpdate();
         *     if (!world.isRemote)
         *        chargeHandler.tick();
         *  }
         * }
         * </pre></blockquote>
         */
        public void tick() {
            clock++;

            if (lossPerTick > 0.0)
                removeCharge(lossPerTick);

            if (drewFromTrack > 0)
                drewFromTrack--;
            else if (type == Type.USER && charge < capacity && clock % DRAW_INTERVAL == 0) {
                ILinkageManager lm = CartTools.getLinkageManager(minecart.worldObj);
                for (EntityMinecart cart : lm.getCartsInTrain(minecart)) {
                    if (cart instanceof IElectricMinecart) {
                        ChargeHandler ch = ((IElectricMinecart) cart).getChargeHandler();
                        if (ch.getType() != Type.USER && ch.getCharge() > 0) {
                            charge += ch.removeCharge(capacity - charge);
                            break;
                        }
                    }
                }
            }
        }

        /**
         * If you want to be able to draw power from the track, this function
         * needs to be called once per tick. Server side only. Generally this
         * means overriding the EnityMinecart.func_145821_a() function. You
         * don't have to call this function if you don't care about drawing from
         * tracks.
         * <p>
         * <blockquote><pre>
         * {@code
         * protected void func_145821_a(int trackX, int trackY, int trackZ, double maxSpeed, double slopeAdjustement, Block trackBlock, int trackMeta)
         *  {
         *     super.func_145821_a(trackX, trackY, trackZ, maxSpeed, slopeAdjustement, trackBlock, trackMeta);
         *     chargeHandler.tickOnTrack(trackX, trackY, trackZ);
         *  }
         * }
         * </pre></blockquote>
         */
        public void tickOnTrack(int trackX, int trackY, int trackZ) {
            if (type == Type.USER && charge < capacity && clock % DRAW_INTERVAL == 0) {
                IElectricGrid track = RailTools.getTrackObjectAt(minecart.worldObj, trackX, trackY, trackZ, IElectricGrid.class);
                if (track != null) {
                    double draw = track.getChargeHandler().removeCharge(capacity - charge);
                    if (draw > 0.0)
                        drewFromTrack = DRAW_INTERVAL * 4;
                    charge += draw;
                }
            }
        }

        /**
         * Must be called by the owning object's save function.
         * <p>
         * <blockquote><pre>
         * {@code
         * public void writeEntityToNBT(NBTTagCompound data)
         *  {
         *     super.writeEntityToNBT(data);
         *     chargeHandler.writeToNBT(data);
         *  }
         * }
         * </pre></blockquote>
         * <p>
         * @param nbt
         */
        public void writeToNBT(NBTTagCompound nbt) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setDouble("charge", charge);
            nbt.setTag("chargeHandler", tag);
        }

        /**
         * Must be called by the owning object's load function.
         * <p>
         * <blockquote><pre>
         * {@code
         * public void readFromNBT(NBTTagCompound data)
         *  {
         *     super.readFromNBT(data);
         *     chargeHandler.readFromNBT(data);
         *  }
         * }
         * </pre></blockquote>
         * <p>
         * @param nbt
         */
        public void readFromNBT(NBTTagCompound nbt) {
            NBTTagCompound tag = nbt.getCompoundTag("chargeHandler");
            charge = tag.getDouble("charge");
        }

    }
}

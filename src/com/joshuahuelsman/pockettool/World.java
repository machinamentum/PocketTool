/*******************************************************************************
 * Copyright (c) 2012 Joshua Huelsman.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Joshua Huelsman - Initial implementation.
 *******************************************************************************/
package com.joshuahuelsman.pockettool;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import org.spout.nbt.ByteTag;
import org.spout.nbt.CompoundMap;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.FloatTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.ListTag;
import org.spout.nbt.LongTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;
import org.spout.nbt.stream.NBTOutputStream;

public class World {
	
	public static final String GAMETYPE = "GameType";
	public static final String LASTPLAYED = "LastPlayed";
	public static final String LEVELNAME = "LevelName";
	public static final String PLATFORM = "Platform";
	public static final String PLAYER = "Player";
	public static final String RANDOMSEED = "RandomSeed";
	public static final String SIZEONDISK = "SizeOnDisk";
	public static final String SPAWNX = "SpawnX";
	public static final String SPAWNY = "SpawnY";
	public static final String SPAWNZ = "SpawnZ";
	public static final String STORAGEVERSION = "StorageVersion";
	public static final String TIME = "Time";
	public World(CompoundTag level){
		CompoundMap data = level.getValue();
		gameType = ((IntTag)data.get(GAMETYPE)).getValue();
		lastPlayed = ((LongTag)data.get(LASTPLAYED)).getValue();
		levelName = ((StringTag)data.get(LEVELNAME)).getValue();
		platform = ((IntTag)data.get(PLATFORM)).getValue();
		player = new Player((CompoundTag) data.get(PLAYER));
		randomSeed = ((LongTag)data.get(RANDOMSEED)).getValue();
		sizeOnDisk = ((LongTag)data.get(SIZEONDISK)).getValue();
		spawnX = ((IntTag)data.get(SPAWNX)).getValue();
		spawnY = ((IntTag)data.get(SPAWNY)).getValue();
		spawnZ = ((IntTag)data.get(SPAWNZ)).getValue();
		storageVersion = ((IntTag)data.get(STORAGEVERSION)).getValue();
		time = ((LongTag)data.get(TIME)).getValue();
	}
	public int gameType;
	public long lastPlayed;
	public String levelName;
	public int platform;
	public Player player;
	public long randomSeed;
	public long sizeOnDisk;
	public int spawnX;
	public int spawnY;
	public int spawnZ;
	public int storageVersion;
	public long time;
	//*******Used for organizational purposes  ************** //
	public String path;
	public String name;
	public World(String name, String path, int i)
	{
		this.name = name;
		this.path = path;
	}
	
	
	public void save()
	{
		String mPath = path + "/level.dat";
		
		try {
			File outFile = new File(mPath);
			if(outFile.exists())
			{
				outFile.delete();
			}
			FileOutputStream fo = new FileOutputStream(outFile);
			BufferedOutputStream bo = new BufferedOutputStream(fo);
			bo.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(3).array());
			ByteArrayOutputStream temp = new ByteArrayOutputStream();
			NBTOutputStream stream = new NBTOutputStream(temp,false,true);
			stream.writeTag(getTag());
			stream.close();
			bo.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(temp.size()).array());
			bo.write(temp.toByteArray());
			temp.close();
			bo.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CompoundTag getTag()
	{
		CompoundMap out = new CompoundMap();
		out.put(new IntTag(GAMETYPE,gameType));
		out.put(new LongTag(LASTPLAYED,lastPlayed));
		out.put(new StringTag(LEVELNAME,levelName));
		out.put(new IntTag(PLATFORM,platform));
		out.put(player.getTag());
		out.put(new LongTag(RANDOMSEED,randomSeed));
		out.put(new LongTag(SIZEONDISK,sizeOnDisk));
		out.put(new IntTag(SPAWNX,spawnX));
		out.put(new IntTag(SPAWNY,spawnY));
		out.put(new IntTag(SPAWNZ,spawnZ));
		out.put(new IntTag(STORAGEVERSION,storageVersion));
		out.put(new LongTag(TIME,time));
		return new CompoundTag("",out);
	}
	
	public class Player {
		public static final String DIMENSION = "Dimension";
		public static final String INVENTORY = "Inventory";
		public static final String POS = "Pos";
		public static final String MOTION = "Motion";
		public static final String SCORE = "Score";
		public static final String ROTATION = "Rotation";
		public static final String FALLDISTANCE = "FallDistance";
		public static final String FIRE = "Fire";
		public static final String AIR = "Air";
		public static final String ONGROUND = "OnGround";
		public static final String ATTACKTIME = "AttackTime";
		public static final String DEATHTIME = "DeathTime";
		public static final String HEALTH = "Health";
		public static final String HURTTIME = "HurtTime";
		public int dimension = 0;
		public Inventory inventory;
		public int score;
		
		public Position pos;
		public Motion motion;
		public Rotation rotation;
		public float fallDistance;
		public short fire;
		public short air;
		public byte onGround;
		public short attackTime;
		public short deathTime;
		public short health;
		public short hurtTime;
		
		@SuppressWarnings("unchecked")
		public Player(CompoundTag player)
		{
			CompoundMap data = player.getValue();
			dimension = ((IntTag)data.get(DIMENSION)).getValue();
			inventory = new Inventory((ListTag<Tag>)data.get(INVENTORY));
			score = ((IntTag)data.get(SCORE)).getValue();
			pos = new Position((ListTag<FloatTag>)data.get(POS));
			motion = new Motion((ListTag<FloatTag>)data.get(MOTION));
			rotation = new Rotation((ListTag<FloatTag>)data.get(ROTATION));
			fallDistance = ((FloatTag)data.get(FALLDISTANCE)).getValue();
			fire = ((ShortTag)data.get(FIRE)).getValue();
			air = ((ShortTag)data.get(AIR)).getValue();
			onGround = ((ByteTag)data.get(ONGROUND)).getValue();
			attackTime = ((ShortTag)data.get(ATTACKTIME)).getValue();
			deathTime = ((ShortTag)data.get(DEATHTIME)).getValue();
			health = ((ShortTag)data.get(HEALTH)).getValue();
			hurtTime = ((ShortTag)data.get(HURTTIME)).getValue();
		}
		
		public CompoundTag getTag()
		{
			CompoundMap data = new CompoundMap();
			data.put(new IntTag(DIMENSION,dimension));
			data.put(INVENTORY,inventory.getTag());
			data.put(new IntTag(SCORE,score));
			data.put(pos.getTag());
			data.put(motion.getTag());
			data.put(rotation.getTag());
			data.put(new FloatTag(FALLDISTANCE,fallDistance));
			data.put(new ShortTag(FIRE,fire));
			data.put(new ShortTag(AIR,air));
			data.put(new ByteTag(ONGROUND,onGround));
			data.put(new ShortTag(ATTACKTIME,attackTime));
			data.put(new ShortTag(DEATHTIME,deathTime));
			data.put(new ShortTag(HEALTH,health));
			data.put(new ShortTag(HURTTIME,hurtTime));
			return new CompoundTag(PLAYER,data);
		}
		
		public class Rotation {
			public float yaw;
			public float pitch;
			
			public Rotation(ListTag<FloatTag> pos)
			{
				yaw = pos.getValue().get(0).getValue();
				pitch = pos.getValue().get(1).getValue();
				
			}
			
			public ListTag<FloatTag> getTag()
			{
				ArrayList<FloatTag> tags = new ArrayList<FloatTag>();
				tags.add(new FloatTag("",yaw));
				tags.add(new FloatTag("",pitch));
				return new ListTag<FloatTag>(ROTATION,FloatTag.class,tags);
			}

		}
		
		public class Motion {
			public float dx;
			public float dy;
			public float dz;
			public Motion(ListTag<FloatTag> pos)
			{
				dx = pos.getValue().get(0).getValue();
				dy = pos.getValue().get(1).getValue();
				dz = pos.getValue().get(2).getValue();
			}
			
			public ListTag<FloatTag> getTag()
			{
				ArrayList<FloatTag> tags = new ArrayList<FloatTag>();
				tags.add(new FloatTag("",dx));
				tags.add(new FloatTag("",dy));
				tags.add(new FloatTag("",dz));
				return new ListTag<FloatTag>(MOTION,FloatTag.class,tags);
			}

		}
		
		public class Position {
			public float x;
			public float y;
			public float z;
			public Position(ListTag<FloatTag> pos)
			{
				x = pos.getValue().get(0).getValue();
				y = pos.getValue().get(1).getValue();
				z = pos.getValue().get(2).getValue();
			}
			
			public ListTag<FloatTag> getTag()
			{
				ArrayList<FloatTag> tags = new ArrayList<FloatTag>();
				tags.add(new FloatTag("",x));
				tags.add(new FloatTag("",y));
				tags.add(new FloatTag("",z));
				return new ListTag<FloatTag>(POS,FloatTag.class,tags);
			}
		}
		
		public class Inventory {
			ArrayList<Slot> slots;
			
			public Inventory(ListTag<Tag> inventory)
			{
				slots = new ArrayList<Slot>();
				
				for(Tag t : inventory.getValue())
				{
					if(t instanceof CompoundTag)
					{
						slots.add(new Slot((CompoundTag)t));
					}
				}
			}
			
			
			public ListTag<CompoundTag> getTag()
			{
				ArrayList<CompoundTag> tags = new ArrayList<CompoundTag>();
				for(Slot slot : slots)
				{
					tags.add(slot.getTag());
				}
				 
				return new ListTag<CompoundTag>(INVENTORY, CompoundTag.class, tags);
			}
			public class Slot {
				public static final String SLOT = "Slot";
				public static final String ID = "id";
				public static final String COUNT = "Count";
				public static final String DAMAGE = "Damage";
				public Slot(CompoundTag slot)
				{
					CompoundMap data = slot.getValue();
					slotid = ((ByteTag)data.get(SLOT)).getValue();
					itemid = ((ShortTag)data.get(ID)).getValue();
					count = ((ByteTag)data.get(COUNT)).getValue();
					damage = ((ShortTag)data.get(DAMAGE)).getValue();
				}
				
				public CompoundTag getTag()
				{
					CompoundMap out = new CompoundMap();
					out.put(new ByteTag(SLOT,slotid));
					out.put(new ShortTag(ID,itemid));
					out.put(new ByteTag(COUNT,count));
					out.put(new ShortTag(DAMAGE,damage));
					
					return new CompoundTag("",out);
					
				}
				
				public byte slotid;
				public short itemid;
				public byte count;
				public short damage;
			}
		}
	}
}

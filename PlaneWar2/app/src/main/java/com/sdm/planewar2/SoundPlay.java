package com.sdm.planewar2;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

@SuppressLint("UseSparseArrays")
public class SoundPlay {
	private SoundPool sound;
	private HashMap<Integer, Integer>map;
	private MainActivity mainActivity;
	
	public SoundPlay(MainActivity mainActivity){
		this.mainActivity=mainActivity;
		map=new HashMap<Integer, Integer>();
		sound=new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
	}
	
	public void initSound(){
		map.put(1, sound.load(mainActivity, R.raw.button, 1));
		map.put(2, sound.load(mainActivity, R.raw.get_goods, 1));
		map.put(3, sound.load(mainActivity, R.raw.shoot, 1));
		map.put(4, sound.load(mainActivity, R.raw.smallexplosion, 1));
		map.put(5, sound.load(mainActivity, R.raw.middleexplosion2, 1));
		map.put(6, sound.load(mainActivity, R.raw.bigexplosion, 1));
		map.put(7, sound.load(mainActivity, R.raw.myplaneexplosion3, 1));
	}
	
	public void play(int id,int loop){
		sound.play(id, 1, 1, 1, loop, 1.0f);
	}

}

package com.krm.common.utils;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

public class MediaTransferUtil {

	//[ac3, adpcm_adx, adpcm_ima_wav, adpcm_ms, adpcm_swf, adpcm_yamaha, flac, g726, libamr_nb, libamr_wb, 
	//libfaac, libgsm, libgsm_ms, libmp3lame, libvorbis, mp2, pcm_alaw, pcm_mulaw, pcm_s16be, pcm_s16le, 
	//pcm_s24be, pcm_s24daud, pcm_s24le, pcm_s32be, pcm_s32le, pcm_s8, pcm_u16be, pcm_u16le, pcm_u24be, 
	//pcm_u24le, pcm_u32be, pcm_u32le, pcm_u8, pcm_zork, roq_dpcm, sonic, sonicls, vorbis, wmav1, wmav2]
	
	/** 
     * mp3转化为wav
     *  
     * @param source 
     *            源文件 
     * @param desFileName 
     *            目标文件名 
     * @return 转化后的文件 
     */  
	public static File mp3ToWav(File source, String desFileName) throws Exception {
		File target = new File(desFileName);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("wmav2");
		audio.setBitRate(new Integer(1280000)); // 音频比率
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("wav");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs);
		return target;
	}

	/**
	 * wav转化为mp3
	 * 
	 * @param source
	 *            源文件
	 * @param desFileName
	 *            目标文件名
	 * @return 转化后的文件
	 */
	public static File wavToMp3(File source, String desFileName) throws Exception {
		File target = new File(desFileName);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(1280000)); // 音频比率
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs);
		return target;
	}
}

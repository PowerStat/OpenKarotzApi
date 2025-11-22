/*
 * Copyright (C) 2021-2025 Dipl.-Inform. Kai Hofmann. All rights reserved!
 */
package de.powerstat.openkarotz;


import java.util.List;

import com.google.gson.annotations.SerializedName;


/**
 * Rabbit json results.
 */
public final class RabbitResults2
 {
  /**
   * Return.
   */
  @SerializedName("return")
  public boolean return_;

  /**
   * Silent.
   */
  public int silent;

  /**
   * Karotz percent used space.
   */
  public int karotzPercentUsedSpace;

  /**
   * USB stick percent used space.
   */
  public int usbPercentUsedSpace;

  /**
   * Color.
   */
  public String color;

  /**
   * Secondary color.
   */
  public String secondaryColor;

  /**
   * Pulse.
   */
  public int pulse;

  /**
   * No memory.
   */
  public int noMemory;

  /**
   * Speed.
   */
  public String speed;

  /**
   * Disabled.
   */
  public int disabled;

  /**
   * Left ear.
   */
  public int left;

  /**
   * Right ear.
   */
  public int right;

  /**
   * Message.
   */
  public String msg;

  /**
   * List of tags.
   */
  public List<?> tags;

  /**
   * Count.
   */
  public int count;

  /**
   * Played.
   */
  public boolean played;

  /**
   * Cache.
   */
  public boolean cache;

  /**
   * Voice language.
   */
  public String voicelanguage;

  /**
   * Voice gender.
   */
  public String voicegender;

  /**
   * id.
   */
  public String id;

  /**
   * List of snapshots.
   */
  public List<Snapshots> snapshots;

  /**
   * URL.
   */
  public String url;

  /**
   * Moods.
   */
  public int moods;

  /**
   * Hour.
   */
  public int hour;

  /**
   * List of sounds.
   */
  public List<Sounds> sounds;

  /**
   * Version.
   */
  public int version;

  /**
   * Ears disabled.
   */
  public int earsDisabled;

  /**
   * Sleep.
   */
  public int sleep;

  /**
   * Sleep time.
   */
  public int sleepTime;

  /**
   * LED color.
   */
  public String ledColor;

  /**
   * LED pulse.
   */
  public int ledPulse;

  /**
   * TTS cache size.
   */
  public long ttsCacheSize;

  /**
   * USB free space.
   */
  public String usbFreeSpace;

  /**
   * Karotz free space.
   */
  public String karotzFreeSp;

  /**
   * WLAN mac address.
   */
  public String wlanMac;

  /**
   * NB tags.
   */
  public int nbTags;

  /**
   * NB moods.
   */
  public int nbMoods;

  /**
   * NB sounds.
   */
  public int nbSounds;

  /**
   * NB stories.
   */
  public int nbStories;

  /**
   * NB data dir.
   */
  public String dataDir;


  /**
   * Returns the string representation of this RabbitResults.
   *
   * The exact details of this representation are unspecified and subject to change, but the following may be regarded as typical:
   *
   * "RabbitResults[return=, ...]"
   *
   * @return String representation of this RabbitResults.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
   {
    final StringBuilder result = new StringBuilder();
    result.append("RabbitResults[return = ").append(return_);
    result.append("; silent = ").append(silent);
    result.append("; karotzPercentUsedSpace = ").append(karotzPercentUsedSpace);
    result.append("; usbPercentUsedSpace = ").append(usbPercentUsedSpace);
    result.append("; color = ").append(color);
    result.append("; secondaryColor = ").append(secondaryColor);
    result.append("; pulse = ").append(pulse);
    result.append("; no_Memory = ").append(noMemory);
    result.append("; speed = ").append(speed);
    result.append("; disabled = ").append(disabled);
    result.append("; left = ").append(left);
    result.append("; right = ").append(right);
    result.append("; msg = ").append(msg);
    // tags
    result.append("; count = ").append(count);
    result.append("; played = ").append(played);
    result.append("; cache = ").append(cache);
    result.append("; voicelanguage = ").append(voicelanguage);
    result.append("; voicegender = ").append(voicegender);
    result.append("; id = ").append(id);
    // snapshots
    result.append("; url = ").append(url);
    result.append("; moods = ").append(moods);
    result.append("; hour = ").append(hour);
    // sounds
    result.append("; version = ").append(version);
    result.append("; earsDisabled = ").append(earsDisabled);
    result.append("; sleep = ").append(sleep);
    result.append("; sleepTime = ").append(sleepTime);
    result.append("; ledColor = ").append(ledColor);
    result.append("; ledPulse = ").append(ledPulse);
    result.append("; ttsCacheSize = ").append(ttsCacheSize);
    result.append("; usbFreeSpace = ").append(usbFreeSpace);
    result.append("; karotzFreeSp = ").append(karotzFreeSp);
    result.append("; wlanMac = ").append(wlanMac);
    result.append("; nbTags = ").append(nbTags);
    result.append("; nbMoods = ").append(nbMoods);
    result.append("; nbSounds = ").append(nbSounds);
    result.append("; nbStories = ").append(nbStories);
    result.append("; dataDir = ").append(dataDir);
    result.append(']');
    return result.toString();
   }

 }

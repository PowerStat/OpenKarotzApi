/*
 * Copyright (C) 2021 Dipl.-Inform. Kai Hofmann. All rights reserved!
 */
package de.powerstat.openkarotz;


import java.util.List;

import com.google.gson.annotations.SerializedName;


/**
 * Rabbit json results.
 */
public final class RabbitResults
 {
  @SerializedName("return")
  public int return_;
  public int silent;

  public int karotz_percent_used_space;
  public int usb_percent_used_space;

  public String color;
  public String secondary_color;
  public int pulse;
  public int no_memory;
  public String speed;

  public int disabled;
  public int left;
  public int right;
  public String msg;

  public List<?> tags;

  public int count;

  public int played;
  public int cache;
  public String voicelanguage;
  public String voicegender;
  public String id;

  public List<Snapshots> snapshots;

  public String url;

  public int moods;

  public int hour;

  public List<Sounds> sounds;

  public int version;

  public int ears_disabled;
  public int sleep;
  public int sleep_time;
  public String led_color;
  public int led_pulse;
  public long tts_cache_size;
  public String usb_free_space;
  public String karotz_free_sp;
  public String wlan_mac;
  public int nb_tags;
  public int nb_moods;
  public int nb_sounds;
  public int nb_stories;
  public String data_dir;


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
    result.append("RabbitResults[return = ").append(this.return_);
    result.append("; silent = ").append(this.silent);
    result.append("; karotz_percent_used_space = ").append(this.karotz_percent_used_space);
    result.append("; usb_percent_used_space = ").append(this.usb_percent_used_space);
    result.append("; color = ").append(this.color);
    result.append("; secondary_color = ").append(this.secondary_color);
    result.append("; pulse = ").append(this.pulse);
    result.append("; no_memory = ").append(this.no_memory);
    result.append("; speed = ").append(this.speed);
    result.append("; disabled = ").append(this.disabled);
    result.append("; left = ").append(this.left);
    result.append("; right = ").append(this.right);
    result.append("; msg = ").append(this.msg);
    // tags
    result.append("; count = ").append(this.count);
    result.append("; played = ").append(this.played);
    result.append("; cache = ").append(this.cache);
    result.append("; voicelanguage = ").append(this.voicelanguage);
    result.append("; voicegender = ").append(this.voicegender);
    result.append("; id = ").append(this.id);
    // snapshots
    result.append("; url = ").append(this.url);
    result.append("; moods = ").append(this.moods);
    result.append("; hour = ").append(this.hour);
    // sounds
    result.append("; version = ").append(this.version);
    result.append("; ears_disabled = ").append(this.ears_disabled);
    result.append("; sleep = ").append(this.sleep);
    result.append("; sleep_time = ").append(this.sleep_time);
    result.append("; led_color = ").append(this.led_color);
    result.append("; led_pulse = ").append(this.led_pulse);
    result.append("; tts_cache_size = ").append(this.tts_cache_size);
    result.append("; usb_free_space = ").append(this.usb_free_space);
    result.append("; karotz_free_sp = ").append(this.karotz_free_sp);
    result.append("; wlan_mac = ").append(this.wlan_mac);
    result.append("; nb_tags = ").append(this.nb_tags);
    result.append("; nb_moods = ").append(this.nb_moods);
    result.append("; nb_sounds = ").append(this.nb_sounds);
    result.append("; nb_stories = ").append(this.nb_stories);
    result.append("; data_dir = ").append(this.data_dir);
    result.append(']');
    return result.toString();
   }

 }

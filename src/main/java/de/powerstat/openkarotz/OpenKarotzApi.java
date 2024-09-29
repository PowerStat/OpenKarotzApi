/*
 * Copyright (C) 2021 Dipl.-Inform. Kai Hofmann. All rights reserved!
 */
package de.powerstat.openkarotz;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import de.powerstat.validation.ValidationUtils;
import de.powerstat.validation.values.Hostname;


/**
 * Open Karotz api.
 */
public final class OpenKarotzApi
 {
  /**
   * Logger.
   */
  private static final Logger LOGGER = LogManager.getLogger(OpenKarotzApi.class);

  /**
   * HTTP client.
   */
  private final CloseableHttpClient httpclient;

  /**
   * Karotz hostname.
   */
  private final Hostname hostname;


  /**
   * Constructor.
   *
   * @param httpclient CloseableHttpClient
   * @param hostname Karotz hostname
   */
  public OpenKarotzApi(final CloseableHttpClient httpclient, final Hostname hostname)
   {
    super();
    this.hostname = Objects.requireNonNull(hostname, "hostname"); //$NON-NLS-1$
    this.httpclient = Objects.requireNonNull(httpclient, "httpclient"); //$NON-NLS-1$
   }


  /**
   * Get new instance for a OpenKarotzApi by hostname.
   *
   * @param httpclient CloseableHttpClient
   * @param hostname FB hostname
   * @return A new OpenKarotzApi instance.
   * @throws NullPointerException If hostname is null
   */
  public static OpenKarotzApi newInstance(final CloseableHttpClient httpclient, final Hostname hostname)
   {
    return new OpenKarotzApi(httpclient, hostname);
   }


  /**
   * Get new instance for an OpenKarotzApi by hostname.
   *
   * @param hostname Karotz hostname
   * @return A new OpenKarotzApi instance.
   * @throws ParserConfigurationException Parser configuration exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws KeyStoreException Key store exception
   * @throws KeyManagementException Key management exception
   * @throws NullPointerException If hostname is null
   */
  public static OpenKarotzApi newInstance(final String hostname) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParserConfigurationException
   {
    final CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build())).build();
    return newInstance(httpclient, Hostname.of(hostname));
   }


  /**
   * Get string.
   *
   * @param urlPath URL path
   * @return String
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws UnsupportedOperationException When a bad request appears, could happen when using commands from a newer api version
   *
   * TODO urlPath value object
   */
  private String getString(final String urlPath) throws IOException
   {
    assert urlPath != null;
    System.out.println("params: " + ValidationUtils.sanitizeUrlPath(urlPath));
    try (CloseableHttpResponse response = this.httpclient.execute(new HttpGet("http://" + this.hostname.stringValue() + "/cgi-bin" + ValidationUtils.sanitizeUrlPath(urlPath))))//$NON-NLS-1$ //$NON-NLS-2$
     {
      final int responseCode = response.getStatusLine().getStatusCode();
      if (responseCode != HttpURLConnection.HTTP_OK)
       {
        if (LOGGER.isDebugEnabled())
         {
          LOGGER.debug(response.getStatusLine());
         }
        if (LOGGER.isInfoEnabled())
         {
          LOGGER.info("HttpStatus: " + response.getStatusLine().getStatusCode() + ":");  //$NON-NLS-1$//$NON-NLS-2$
         }
        if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST)
         {
          throw new UnsupportedOperationException("Possibly you used a command from a newer api version?"); //$NON-NLS-1$
         }
       }
      final HttpEntity entity = response.getEntity();
      if (LOGGER.isDebugEnabled())
       {
        LOGGER.debug("ContentType: " + entity.getContentType()); //$NON-NLS-1$
       }
      final String result = EntityUtils.toString(entity);
      if (LOGGER.isDebugEnabled())
       {
        LOGGER.debug("getString: " + result); //$NON-NLS-1$
       }
      return result;
     }
   }


  /**
   * Get free space on karotz in percent.
   *
   * @return Free space on karotz in percent
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public int getFreeKarotzSpace() throws IOException
   {
    final String result = getString("get_free_space"); //$NON-NLS-1$
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.karotz_percent_used_space;
   }


  /**
   * Get free space on karotz usb in percent.
   *
   * @return Free space on karotz usb in percent or -1 if no usb memory is found
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public int getFreeUsbSpace() throws IOException
   {
    final String result = getString("get_free_space"); //$NON-NLS-1$
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.usb_percent_used_space;
   }


  /**
   * Get sound list.
   *
   * @return List with sound names
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public List<String> getSoundList() throws IOException
   {
    final String result = getString("sound_list"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    final List<String> soundNames = new ArrayList<>();
    final List<Sounds> sounds = jsonResult.sounds;
    for (final Sounds sound : sounds)
     {
      soundNames.add(sound.id);
     }
    return soundNames;
   }


  /**
   * Wakeup.
   *
   * @param silent true: silent, false otherwise
   * @return true: silent, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean wakeup(final boolean silent) throws IOException
   {
    final String result = getString("wakeup?silent=" + (silent ? "1" : "0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.silent == 1;
   }


  /**
   * Sleep.
   *
   * @return false: sleeping; true: already sleeping
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean sleep() throws IOException
   {
    final String result = getString("sleep"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 1;
   }


  /**
   * Ears reset.
   *
   * @return false: sleeping; true: reset
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean earsReset() throws IOException
   {
    final String result = getString("ears_reset"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Ears random position.
   *
   * @return true: random ears positions
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean earsRandom() throws IOException
   {
    final String result = getString("ears_random"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    if (jsonResult.return_ == 0)
     {
      final int leftEar = jsonResult.left;
      final int rightEar = jsonResult.right;
      System.out.println("left: " + leftEar + "; right: " + rightEar); //$NON-NLS-1$ //$NON-NLS-2$
     }
    return jsonResult.return_ == 0;
   }


  /**
   * Ears mode.
   *
   * @param disabled true: ears disabled, false otherwise
   * @return true: ears disabled, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean earsMode(final boolean disabled) throws IOException
   {
    final String result = getString("ears_mode?disable=" + (disabled ? '1' : '0')); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    if (jsonResult.return_ == 0)
     {
      final int disbaled = jsonResult.disabled;
      return disbaled == 1;
     }
    return jsonResult.return_ == 0;
   }


  /**
   * Ears position.
   *
   * @param left Left ears position (0-17)
   * @param right Right ears position (0-17)
   * @param reset true: do reset first, false otherwise
   * @return true: ears position set, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean earsPosition(final int left, final int right, final boolean reset) throws IOException
   {
    if ((left < 0) || (left > 17) || (right < 0) || (right > 17))
     {
      throw new IllegalArgumentException("Invalid ear position, must be 0-17"); //$NON-NLS-1$
     }
    final String result = getString("ears?left=" + left + "&right=" + right + "&noreset=" + (reset ? '0' : '1')); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    if (jsonResult.return_ == 0)
     {
      final int leftEar = jsonResult.left;
      final int rightEar = jsonResult.right;
      System.out.println("left: " + leftEar + "; right: " + rightEar); //$NON-NLS-1$ //$NON-NLS-2$
      // TODO verify correct positions
     }
    return jsonResult.return_ == 0;
   }


  // TODO Special ear positions: sleep, alert, engaged, interested, inspired, attentive, enthusastic, excited, active, strong, determinded, proud, afraid, jittery, nervous. distressed, scared, guilty, ashamed, hostile, annoyed, irritable

  // TODO Get actual ear positions


  /**
   * Led color.
   *
   * @param color Color (rrggbb)
   * @param pulse true: pulse, false no pulse
   * @param speed Speed > 0
   * @param color2 Color 2 (rrggbb)
   * @return true: led color set, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean ledColor(final String color, final boolean pulse, final int speed, final String color2) throws IOException
   {
    if (color.length() != 6)
     {
      throw new IllegalArgumentException("Color must be rrggbb (0-9,a-f)"); //$NON-NLS-1$
     }
    if (!color.matches("^[0-9a-fA-F]{6}$")) //$NON-NLS-1$
     {
      throw new IllegalArgumentException("Color must be rrggbb (0-9,a-f)"); //$NON-NLS-1$
     }
    if (pulse)
     {
      if (speed < 0)
       {
        throw new IllegalArgumentException("Speed must be >= 0"); //$NON-NLS-1$
       }
      if (color2.length() != 6)
       {
        throw new IllegalArgumentException("Color2 must be rrggbb (0-9,a-f)"); //$NON-NLS-1$
       }
      if (!color2.matches("^[0-9a-fA-F]{6}$")) //$NON-NLS-1$
       {
        throw new IllegalArgumentException("Color2 must be rrggbb (0-9,a-f)"); //$NON-NLS-1$
       }
    }
    final StringBuilder params = new StringBuilder();
    params.append("leds?color="); //$NON-NLS-1$
    params.append(color);
    if (pulse)
     {
      params.append("&pulse=1"); //$NON-NLS-1$
      params.append("&speed="); //$NON-NLS-1$
      params.append(speed);
      params.append("&color2="); //$NON-NLS-1$
      params.append(color2);
     }
    final String result = getString(params.toString());
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    if (jsonResult.return_ == 0)
     {
      /*
      jsonResult.color;
      jsonResult.led_color;
      jsonResult.colorResult;
      jsonResult.secondary_color;
      jsonResult.pulse;
      jsonResult.speed;
      jsonResult.no_memory;
      */
      // TODO verify color
     }
    return jsonResult.return_ == 0;
   }


  /**
   * Display cache.
   *
   * @return Counter >= 0 or -1 on error
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public int displayCache() throws IOException
   {
    final String result = getString("display_cache"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    if (jsonResult.return_ == 0)
     {
      final int count = jsonResult.count;
      return count;
     }
    return -1;
   }


  /**
   * Clear cache.
   *
   * @return true: cleared, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   */
  public boolean clearCache() throws IOException
   {
    final String result = getString("clear_cache"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    System.out.println(jsonResult.msg);
    return jsonResult.return_ == 0;
   }


  /**
   * Text to speach.
   *
   * @param female true: female; false: male
   * @param language fr, fr-CA, en-US, en-GB, de, it, es, nl, af, sq
   * @param text Text to speach
   * @return true: success, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If language or text is null
   */
  public boolean tts(final boolean female, final String language, final String text) throws IOException
   {
    Objects.requireNonNull(language);
    Objects.requireNonNull(text);
    final String[] languages = {"fr", "fr-CA", "en-US", "en-GB", "de", "it", "es", "nl", "af", "sq", "ar", "hy", "bs", "pt-BR", "hr", "cs", "da", "en-AU", "eo", "fi", "el", "ht", "hi", "hu", "is", "id", "ja", "ko", "la", "no", "pl", "pt-PT", "ro", "ru", "sr", "sh", "sk", "sw", "sv", "ta", "th", "tr", "vi", "cy"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ //$NON-NLS-16$ //$NON-NLS-17$ //$NON-NLS-18$ //$NON-NLS-19$ //$NON-NLS-20$ //$NON-NLS-21$ //$NON-NLS-22$ //$NON-NLS-23$ //$NON-NLS-24$ //$NON-NLS-25$ //$NON-NLS-26$ //$NON-NLS-27$ //$NON-NLS-28$ //$NON-NLS-29$ //$NON-NLS-30$ //$NON-NLS-31$ //$NON-NLS-32$ //$NON-NLS-33$ //$NON-NLS-34$ //$NON-NLS-35$ //$NON-NLS-36$ //$NON-NLS-37$ //$NON-NLS-38$ //$NON-NLS-39$ //$NON-NLS-40$ //$NON-NLS-41$ //$NON-NLS-42$ //$NON-NLS-43$ //$NON-NLS-44$
    if (!Arrays.asList(languages).contains(language))
     {
      throw new IllegalArgumentException("Language not supported: " + language); //$NON-NLS-1$
     }
    int voiceNr = 0;
    for (int i = 0; i < languages.length; ++i)
     {
      if (language.equals(languages[i]))
       {
        voiceNr = (i * 2) + (female ? 2 : 1);
        break;
       }
     }
    final String result = getString("tts?voice=" + voiceNr + "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString())); //$NON-NLS-1$ //$NON-NLS-2$
    System.out.println(result);
    final RabbitResults2 jsonResult = new Gson().fromJson(result, RabbitResults2.class);
    // {"return": true, "played": true, "cache": false, "voicelanguage": "fr", "voicegender": "male", "id": "e9847b2545c521ff03cd5358bfce3781"}
    return jsonResult.return_;
   }


  /**
   * Play sound by id.
   *
   * @param soundId Sound id
   * @return true: played, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean playSoundById(final String soundId) throws IOException
   {
    Objects.requireNonNull(soundId);
    // TODO Part of sound list?
    final String result = getString("sound?id=" + soundId); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Play sound by url.
   *
   * @param soundUrl Sound url
   * @return true: played, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean playSoundByUrl(final String soundUrl) throws IOException
   {
    Objects.requireNonNull(soundUrl);
    // TODO verify url
    final String result = getString("sound?url=" + URLEncoder.encode(soundUrl, StandardCharsets.UTF_8.toString())); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Quit playing sound.
   *
   * @return true: successful, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean quitSound() throws IOException
   {
    final String result = getString("sound_control?cmd=quit"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Pause playing sound.
   *
   * @return true: successful, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean pauseSound() throws IOException
   {
    final String result = getString("sound_control?cmd=pause"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Start squeezebox.
   *
   * @return true: successful, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean startSqueezebox() throws IOException
   {
    final String result = getString("squeezebox?cmd=start"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Stop squeezebox.
   *
   * @return true: successful, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean stopSqueezebox() throws IOException
   {
    final String result = getString("squeezebox?cmd=stop"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Snapshot list.
   *
   * @return List of snapshot file names
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public List<String> snapshotList() throws IOException
   {
    final String result = getString("snapshot_list"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    final List<String> list = new ArrayList<>();
    if (jsonResult.return_ == 0)
     {
      for (final Snapshots snapshot : jsonResult.snapshots)
       {
        list.add(snapshot.id);
       }
     }
    return list;
   }


  /**
   * Clear snapshots.
   *
   * @return true: successful, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean clearSnapshots() throws IOException
   {
    final String result = getString("clear_snapshots"); //$NON-NLS-1$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }


  /**
   * Take snapshot.
   *
   * @param silent true: take snapshot silent, false otherwise
   * @return true: successful, false otherwise
   * @throws IOException IO exception
   * @throws ClientProtocolException Client protocol exception
   * @throws NullPointerException If soundId is null
   */
  public boolean takeSnapshot(final boolean silent) throws IOException
   {
    final String result = getString("snapshot?silent=" + (silent ? "1" : "0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    System.out.println(result);
    final RabbitResults jsonResult = new Gson().fromJson(result, RabbitResults.class);
    return jsonResult.return_ == 0;
   }



/*
   hostname

   /cgi-bin/voice_list

   Picture
   /cgi-bin/snapshot_get?filename=
   /cgi-bin/snapshot_view?silent=
   /cgi-bin/snapshot_ftp?server=&user=&password=&remote_dir=&silent=1    {"return":"1","msg":"Missing mandatory parameter : (server)" }
   /cgi-bin/save_snapshot
   /cgi-bin/webcam

   RFID
   /cgi-bin/rfid_delete?tag=undefined         {"return":"1","msg":"Tag ID not found."}
   /cgi-bin/rfid_unassign?tag=undefined       {"return":"1","msg":"Tag ID not found."}
   /cgi-bin/rfid_rename?tag=undefined.....    {"return":"1","msg":"Tag ID not found."}
   /cgi-bin/rfid_start_record                 {"return":"0"}
   /cgi-bin/rfid_stop_record                  {"return":"0"}
   /cgi-bin/rfid_list_ext                     { "tags":[],"return":"0" }
   /cgi-bin/rfid_list                         { "tags":[],"return":"0" }

   Apps
   /cgi-bin/apps/moods?id=1    {"moods":"1","return":"0"}
   /cgi-bin/apps/moods         {"moods":"231","return":"0"}
   /cgi-bin/apps/clock?hour=0  {"return":"0","hour":"0"}
   /cgi-bin/apps/clock         {"return":"0","hour":"21"}

   Lists
   /cgi-bin/status             {"version":"200","ears_disabled":"0","sleep":"0","sleep_time":"0","led_color":"00FF00","led_pulse":"0","tts_cache_size":"0","usb_free_space":"-1","karotz_free_space":"148.5M","eth_mac":"00:00:00:00:00:00","wlan_mac":"00:0E:8E:2C:AF:F9","nb_tags":"0","nb_moods":"305","nb_sounds":"14","nb_stories":"0","karotz_percent_used_space":"37","usb_percent_used_space":"","data_dir":"/usr/openkarotz"}

   Button
   ?


apps.clock.install
apps.moods.install
apps.story.install
apps_check_install
install_21
install_moods
install_story

info.sh
package-enablessh.sh
package-openkarotz.sh
package-reset.sh
package-run.sh
package-showlog.sh
package-timebutton.sh
package-update-installpage.sh

test.py

cmdlist

ears.inc
leds.inc
setup.inc
setup.inc.200
setup.inc.karotz
setup.inc.usb
tts.inc
url.inc
url_ext.inc
utils.inc
webcam.inc

ajax_proxy
api_test
apps_list
check_health
clear_rfid
clear_snapshots
cmd
correct_permissions
create_ip_sound
dbus_events
download
eedomus_info
flash_update
get_all_moods
get_apps_list
get_file
get_firmware_list
get_list
get_moods
get_patch_list
get_update_list
get_version
hc2_info
health_check_config
moods
moods_build_list
moods_list
radios_list
radios_update
read_patch
read_update
reboot
reset_install_flag
rfid_assign_eedomus_macro
rfid_assign_karotz
rfid_assign_url
rfid_assign_vera_scene
rfid_assign_zibase_cmd
rfid_backup
rfid_delete
rfid_info
rfid_list
rfid_list_ext
rfid_rename
rfid_restore
rfid_start_record
rfid_stop_record
rfid_test_eedomus_macro
rfid_test_url
rfid_test_vera_scene
rfid_test_zibase_cmd
rfid_unassign
save_snapshot
sendmail
setclock
start_ok
status
stories
stories_list
tag_list
tools_clearlog
tools_controllog
tools_log
tools_ls
tools_net
tools_ps
update
update_scripts
url.eedomus
url.vera
vera_info
voice_list
webcam
zibase_info

apps/
  clock
  mikeyhorloge
  moods
*/

 }

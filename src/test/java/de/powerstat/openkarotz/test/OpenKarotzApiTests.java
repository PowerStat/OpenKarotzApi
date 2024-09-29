/*
 * Copyright (C) 2019-2021 Dipl.-Inform. Kai Hofmann. All rights reserved!
 */
package de.powerstat.openkarotz.test;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import de.powerstat.openkarotz.OpenKarotzApi;
import de.powerstat.validation.values.Hostname;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


/**
 * OpenKarotz Api tests.
 */
@SuppressFBWarnings({"EC_NULL_ARG", "NAB_NEEDLESS_BOOLEAN_CONSTANT_CONVERSION", "RV_NEGATING_RESULT_OF_COMPARETO", "SPP_USE_ZERO_WITH_COMPARATOR"})
public class OpenKarotzApiTests
 {
  /**
   * Logger.
   */
  private static final Logger LOGGER = LogManager.getLogger(OpenKarotzApiTests.class);

  /**
   * Karotz host name.
   */
  private static final String KAROTZ = "192.168.1.0"; //$NON-NLS-1$


  /**
   * Default constructor.
   */
  public OpenKarotzApiTests()
   {
    super();
   }


  /**
   * Test newInstance.
   *
   * @throws KeyManagementException KeyManagementException
   * @throws NoSuchAlgorithmException NoSuchAlgorithmException
   * @throws KeyStoreException KeyStoreException
   * @throws ParserConfigurationException ParserConfigurationException
   */
  @Test
  public void newInstance1() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParserConfigurationException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    assertNotNull(api, "newInstance failed!"); //$NON-NLS-1$
   }


  /**
   * Test newInstance.
   *
   * @throws KeyManagementException KeyManagementException
   * @throws NoSuchAlgorithmException NoSuchAlgorithmException
   * @throws KeyStoreException KeyStoreException
   * @throws ParserConfigurationException ParserConfigurationException
   */
  @Test
  public void newInstance2() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParserConfigurationException
   {
    final CloseableHttpClient mockHttpclient = mock(CloseableHttpClient.class);
    final OpenKarotzApi api = OpenKarotzApi.newInstance(mockHttpclient, Hostname.of(KAROTZ));
    assertNotNull(api, "newInstance failed!"); //$NON-NLS-1$
   }


  /**
   * Get free space on karotz test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void getFreeKarotzSpace() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final int freespace = api.getFreeKarotzSpace();
    assertAll(
      () -> assertTrue(freespace > 0, "Karotz is out of memory") //$NON-NLS-1$
    );
   }


  /**
   * Get free space on karotz test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void getFreeUsbSpace() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final int freespace = api.getFreeUsbSpace();
    assertAll(
      () -> assertEquals(-1, freespace, "Karotz has usb space") //$NON-NLS-1$
    );
   }


  /**
   * Get sounds test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void getSounds() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final List<String> sounds = api.getSoundList();
    final List<String> expectedSounds = new ArrayList<>();
    expectedSounds.add("bip1");
    expectedSounds.add("bling");
    expectedSounds.add("flush");
    expectedSounds.add("install_ok");
    expectedSounds.add("jet1");
    expectedSounds.add("laser_15");
    expectedSounds.add("merde");
    expectedSounds.add("ready");
    expectedSounds.add("rfid_error");
    expectedSounds.add("rfid_ok");
    expectedSounds.add("saut1");
    expectedSounds.add("start");
    expectedSounds.add("twang_01");
    expectedSounds.add("twang_04");
    assertAll(
      () -> assertEquals(expectedSounds, sounds, "More or less than the basic sounds found") //$NON-NLS-1$
    );
   }


  /**
   * Wakeup test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void wakeup() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean silent1 = api.wakeup(true);
    final boolean silent2 = api.wakeup(false);
    assertAll(
      () -> assertTrue(silent1, "Was not silent"), //$NON-NLS-1$
      () -> assertFalse(silent2, "Was silent") //$NON-NLS-1$
    );
   }


  /**
   * Sleep test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void sleep() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean sleep1 = api.sleep();
    final boolean sleep2 = api.sleep();
    assertAll(
      () -> assertFalse(sleep1, "Already sleeping"), //$NON-NLS-1$
      () -> assertTrue(sleep2, "Not already sleeping") //$NON-NLS-1$
    );
    api.wakeup(true);
   }


  /**
   * Ears reset test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void earsReset() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean success = api.earsReset();
    assertAll(
      () -> assertTrue(success, "Can not reset ears") //$NON-NLS-1$
    );
   }


  /**
   * Ears random test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void earsRandom() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean success = api.earsRandom();
    assertAll(
      () -> assertTrue(success, "Can not set randoms ears position") //$NON-NLS-1$
    );
   }


  /**
   * Ears mode test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void earsMode() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean success1 = api.earsMode(true);
    final boolean success2 = api.earsMode(false);
    assertAll(
      () -> assertTrue(success1, "Can't disable ears"), //$NON-NLS-1$
      () -> assertFalse(success2, "Can't enable ears") //$NON-NLS-1$
    );
   }


  /**
   * Ears position test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void earsPosition() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean success1 = api.earsPosition(0, 0, false);
    assertAll(
      () -> assertTrue(success1, "Can't postion ears") //$NON-NLS-1$
    );
   }


  /**
   * Led color test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void ledColor() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean success = api.ledColor("000000", false, 1000, "000000"); //$NON-NLS-1$ //$NON-NLS-2$
    assertAll(
      () -> assertTrue(success, "Can't set led color") //$NON-NLS-1$
    );
   }


  /**
   * Display cache test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void displayCache() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final int count = api.displayCache();
    assertAll(
      () -> assertEquals(0, count, "Display cache not empty") //$NON-NLS-1$
    );
   }


  /**
   * Clear cache test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void clearCache() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.clearCache();
    assertAll(
      () -> assertTrue(result, "Cache not cleared") //$NON-NLS-1$
    );
   }


  /**
   * TTS test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void tts() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.tts(false, "de", "Hallo Welt"); //$NON-NLS-2$
    assertAll(
      () -> assertTrue(result, "Text spoken") //$NON-NLS-1$
    );
   }


  /**
   * Play sound by id test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void playSoundById() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.playSoundById("twang_04"); // bip1, bling, flush, install_ok, jet1, laser_15, merde, ready, rfid_error, rfid_ok, saut1, start, twang_01, twang_04
    assertAll(
      () -> assertTrue(result, "Sound not played") //$NON-NLS-1$
    );
   }


  /**
   * Play sound by url test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void playSoundByUrl() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.playSoundByUrl("http://streaming.radionomy.com/Bob-Marley"); //$NON-NLS-1$
    assertAll(
      () -> assertTrue(result, "Sound not played") //$NON-NLS-1$
    );
    try
     {
      java.util.concurrent.TimeUnit.SECONDS.sleep(10);
     }
    catch (final InterruptedException e)
     {
      // ignore
     }
    final boolean result2 = api.quitSound();
   }


  /**
   * Quit sound test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void quitSound() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.playSoundByUrl("http://streaming.radionomy.com/Bob-Marley"); //$NON-NLS-1$
    try
     {
      java.util.concurrent.TimeUnit.SECONDS.sleep(10);
     }
    catch (final InterruptedException e)
     {
      // ignore
     }
    final boolean result2 = api.quitSound();
    assertAll(
      () -> assertTrue(result2, "Cound not quit sound playing") //$NON-NLS-1$
    );
   }


  /**
   * Pause sound test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void pauseSound() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.playSoundByUrl("http://streaming.radionomy.com/Bob-Marley"); //$NON-NLS-1$
    try
     {
      java.util.concurrent.TimeUnit.SECONDS.sleep(10);
     }
    catch (final InterruptedException e)
     {
      // ignore
     }
    final boolean result2 = api.pauseSound();
    assertAll(
      () -> assertTrue(result2, "Cound not pause sound playing") //$NON-NLS-1$
    );
   }


  /**
   * Start squeezebox test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void startStopSqueezebox() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.startSqueezebox();
    try
     {
      java.util.concurrent.TimeUnit.SECONDS.sleep(10);
     }
    catch (final InterruptedException e)
     {
      // ignore
     }
    final boolean result2 = api.stopSqueezebox();
    assertAll(
      () -> assertTrue(result, "Cound not start squeezebox"), //$NON-NLS-1$
      () -> assertTrue(result2, "Cound not stop squeezebox") //$NON-NLS-1$
    );
   }


  /**
   * Snapshot list test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void snapshotList() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final List<String> result = api.snapshotList();
    assertAll(
      () -> assertTrue(result.size() > 0, "Cound not get snapshot list") //$NON-NLS-1$
    );
    for (final String filename : result)
     {
      System.out.println(filename);
     }
   }


  /**
   * Clear snapshots test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void clearSnapshots() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.clearSnapshots();
    assertAll(
      () -> assertTrue(result, "Cound not clear snapshots") //$NON-NLS-1$
    );
   }


  /**
   * Take snapshot test.
   *
   * @throws IOException IO exception
   * @throws NoSuchAlgorithmException No such algorithm exception
   * @throws ParserConfigurationException Parser cofiguration exception
   * @throws KeyStoreException  Key store exception
   * @throws KeyManagementException  Key management exception
   * @throws InvalidKeyException Invalid key exception
   */
  @Test
  public void takeSnapshot() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, ParserConfigurationException, InvalidKeyException
   {
    final OpenKarotzApi api = OpenKarotzApi.newInstance(KAROTZ);
    final boolean result = api.takeSnapshot(false);
    assertAll(
      () -> assertTrue(result, "Cound not take snapshot") //$NON-NLS-1$
    );
   }

 }

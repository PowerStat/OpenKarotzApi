/*
 * Copyright (C) 2021-2024 Dipl.-Inform. Kai Hofmann. All rights reserved!
 */


/**
 * Open karotz module.
 */
open module de.powerstat.openkarotz
 {
  exports de.powerstat.openkarotz;

  requires java.xml;

  requires org.apache.logging.log4j;
  requires transitive de.powerstat.validation;
  requires com.google.gson;

  requires transitive org.apache.httpcomponents.httpclient;
  requires org.apache.httpcomponents.httpcore;
  requires org.apache.commons.codec;

  requires com.github.spotbugs.annotations;
  requires org.junit.jupiter.api;
  requires org.junit.platform.launcher;
  requires org.junit.platform.suite.api;
  requires org.junit.jupiter.params;
  // requires io.cucumber.java;
  // requires io.cucumber.junit.platform.engine;
  // requires nl.jqno.equalsverifier;

 }

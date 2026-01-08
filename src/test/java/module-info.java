/*
 * Copyright (C) 2021-2026 Dipl.-Inform. Kai Hofmann. All rights reserved!
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements; and to You under the Apache License, Version 2.0.
 */


/**
 * Open karotz module.
 */
open module de.powerstat.openkarotz
 {
  exports de.powerstat.openkarotz;

  requires java.xml;

  requires org.apache.logging.log4j;
  requires transitive de.powerstat.ddd;
  requires com.google.gson;

  requires transitive org.apache.httpcomponents.httpclient;
  requires org.apache.httpcomponents.httpcore;
  requires org.apache.commons.codec;

  requires org.checkerframework.checker.qual;
  requires org.jmolecules.ddd;

  requires com.github.spotbugs.annotations;
  requires org.junit.jupiter.api;
  requires org.junit.platform.launcher;
  requires org.junit.platform.suite.api;
  requires org.junit.jupiter.params;
  requires org.mockito;
  // requires io.cucumber.java;
  // requires io.cucumber.junit.platform.engine;
  // requires nl.jqno.equalsverifier;

 }

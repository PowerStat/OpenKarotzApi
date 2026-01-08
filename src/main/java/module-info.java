/*
 * Copyright (C) 2021-2026 Dipl.-Inform. Kai Hofmann. All rights reserved!
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements; and to You under the Apache License, Version 2.0.
 */


/**
 * OpenKarotz module.
 */
module de.powerstat.openkarotz
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

 }

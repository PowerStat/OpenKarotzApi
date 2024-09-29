/*
 * Copyright (C) 2021 Dipl.-Inform. Kai Hofmann. All rights reserved!
 */


/**
 * OpenKarotz module.
 */
module de.powerstat.openkarotz
 {
  exports de.powerstat.openkarotz;

  requires java.xml;

  requires org.apache.logging.log4j;
  requires transitive de.powerstat.validation;
  requires com.google.gson;

  requires transitive org.apache.httpcomponents.httpclient;
  requires org.apache.httpcomponents.httpcore;
  requires org.apache.commons.codec;

 }

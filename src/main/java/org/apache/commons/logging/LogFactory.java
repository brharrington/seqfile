package org.apache.commons.logging;

/**
 * Place holder so we can remove the dependency on commons-logging.
 */
public final class LogFactory {

  private static final Log LOG = new NoopLog();

  public static Log getLog(Class<?> cls) {
    return LOG;
  }

  public static Log getLog(String str) {
    return LOG;
  }
}

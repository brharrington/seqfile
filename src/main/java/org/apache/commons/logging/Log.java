package org.apache.commons.logging;

/**
 * Place holder so we can remove the dependency on commons-logging.
 */
public interface Log {
  boolean isTraceEnabled();
  boolean isDebugEnabled();
  boolean isInfoEnabled();
  boolean isWarnEnabled();
  boolean isErrorEnabled();
  boolean isFatalEnabled();

  void trace(Object o);
  void debug(Object o);
  void info(Object o);
  void warn(Object o);
  void error(Object o);
  void fatal(Object o);

  void trace(Object o, Throwable t);
  void debug(Object o, Throwable t);
  void info(Object o, Throwable t);
  void warn(Object o, Throwable t);
  void error(Object o, Throwable t);
  void fatal(Object o, Throwable t);
}

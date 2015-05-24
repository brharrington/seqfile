package org.apache.commons.logging;

/**
 * Place holder so we can remove the dependency on commons-logging.
 */
public class NoopLog implements Log {
  @Override public boolean isTraceEnabled() {
    return false;
  }

  @Override public boolean isDebugEnabled() {
    return false;
  }

  @Override public boolean isInfoEnabled() {
    return false;
  }

  @Override public boolean isWarnEnabled() {
    return false;
  }

  @Override public boolean isErrorEnabled() {
    return false;
  }

  @Override public boolean isFatalEnabled() {
    return false;
  }

  @Override public void trace(Object o) {
  }

  @Override public void debug(Object o) {
  }

  @Override public void info(Object o) {
  }

  @Override public void warn(Object o) {
  }

  @Override public void error(Object o) {
  }

  @Override public void fatal(Object o) {
  }

  @Override public void trace(Object o, Throwable t) {
  }

  @Override public void debug(Object o, Throwable t) {
  }

  @Override public void info(Object o, Throwable t) {
  }

  @Override public void warn(Object o, Throwable t) {
  }

  @Override public void error(Object o, Throwable t) {
  }

  @Override public void fatal(Object o, Throwable t) {
  }
}

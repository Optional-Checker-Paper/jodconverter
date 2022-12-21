/*
 * Copyright 2004 - 2012 Mirko Nasato and contributors
 *           2016 - 2020 Simon Braconnier and contributors
 *
 * This file is part of JODConverter - Java OpenDocument Converter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jodconverter.remote.ssl;

import org.checkerframework.checker.nullness.qual.Nullable;

/** Contains the SSL configuration to secure communication with LibreOffice Online. */
@SuppressWarnings({
  "PMD.ArrayIsStoredDirectly",
  "PMD.MethodReturnsInternalArray",
  "PMD.TooManyFields",
  "PMD.UseVarargs"
})
public class SslConfig {

  private boolean enabled = true;
  private String[] ciphers;
  private String[] enabledProtocols;
  private String keyAlias;
  private String keyPassword;
  private String keyStore;
  private String keyStorePassword;
  private String keyStoreType;
  private String keyStoreProvider;
  private String trustStore;
  private String trustStorePassword;
  private String trustStoreType;
  private String trustStoreProvider;
  private String protocol = "TLS";
  private boolean trustAll;
  private boolean verifyHostname = true;

  /**
   * Gets whether SSL support is enabled.
   *
   * @return {@code true} if SSL is enabled; {@code false otherwise}.
   */
  public boolean isEnabled() {
    return this.enabled;
  }

  /**
   * Sets whether SSL support is enabled.
   *
   * @param enabled {@code true} if SSL is enabled; {@code false otherwise}.
   */
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Gets the supported SSL ciphers.
   *
   * @return The supported SSL ciphers.
   */
  public @Nullable String[] getCiphers() {
    return this.ciphers;
  }

  /**
   * Sets the supported SSL ciphers.
   *
   * @param ciphers The supported SSL ciphers.
   */
  public void setCiphers(final @Nullable String[] ciphers) {
    this.ciphers = ciphers;
  }

  /**
   * Gets the alias that identifies the key in the key store.
   *
   * @return The alias that identifies the key in the key store.
   */
  public @Nullable String getKeyAlias() {
    return this.keyAlias;
  }

  /**
   * Sets the alias that identifies the key in the key store.
   *
   * @param keyAlias The alias that identifies the key in the key store.
   */
  public void setKeyAlias(final @Nullable String keyAlias) {
    this.keyAlias = keyAlias;
  }

  /**
   * Gets the password used to access the key in the key store.
   *
   * @return The key password.
   */
  public @Nullable String getKeyPassword() {
    return this.keyPassword;
  }

  /**
   * Sets the password used to access the key in the key store.
   *
   * @param keyPassword The key password.
   */
  public void setKeyPassword(final @Nullable String keyPassword) {
    this.keyPassword = keyPassword;
  }

  /**
   * Gets the path to the key store that holds the SSL certificate (typically a jks file).
   *
   * @return The path to the key store.
   */
  public @Nullable String getKeyStore() {
    return this.keyStore;
  }

  /**
   * Sets the path to the key store that holds the SSL certificate (typically a jks file).
   *
   * @param keyStore The path to the key store.
   */
  public void setKeyStore(final @Nullable String keyStore) {
    this.keyStore = keyStore;
  }

  /**
   * Gets the password used to load the key store.
   *
   * @return The key store password.
   */
  public @Nullable String getKeyStorePassword() {
    return this.keyStorePassword;
  }

  /**
   * Sets the password used to load the key store.
   *
   * @param keyStorePassword The key store password.
   */
  public void setKeyStorePassword(final @Nullable String keyStorePassword) {
    this.keyStorePassword = keyStorePassword;
  }

  /**
   * Gets the type of the key store.
   *
   * @return The key store type.
   */
  public @Nullable String getKeyStoreType() {
    return this.keyStoreType;
  }

  /**
   * Sets the type of the key store.
   *
   * @param keyStoreType The key store type.
   */
  public void setKeyStoreType(final @Nullable String keyStoreType) {
    this.keyStoreType = keyStoreType;
  }

  /**
   * Gets the provider for the key store.
   *
   * @return The key store provider.
   */
  public @Nullable String getKeyStoreProvider() {
    return this.keyStoreProvider;
  }

  /**
   * Sets the provider for the key store.
   *
   * @param keyStoreProvider The key store provider.
   */
  public void setKeyStoreProvider(final @Nullable String keyStoreProvider) {
    this.keyStoreProvider = keyStoreProvider;
  }

  /**
   * Gets the enabled SSL protocols.
   *
   * @return The enabled SSL protocols.
   */
  public @Nullable String[] getEnabledProtocols() {
    return this.enabledProtocols;
  }

  /**
   * Sets the enabled SSL protocols.
   *
   * @param enabledProtocols The enabled SSL protocols to set.
   */
  public void setEnabledProtocols(final @Nullable String[] enabledProtocols) {
    this.enabledProtocols = enabledProtocols;
  }

  /**
   * Gets the trust store that holds SSL certificates.
   *
   * @return The path to the trust store.
   */
  public @Nullable String getTrustStore() {
    return this.trustStore;
  }

  /**
   * Sets the trust store that holds SSL certificates.
   *
   * @param trustStore The path to the trust store.
   */
  public void setTrustStore(final @Nullable String trustStore) {
    this.trustStore = trustStore;
  }

  /**
   * Gets the password used to load the trust store.
   *
   * @return The trust store password.
   */
  public @Nullable String getTrustStorePassword() {
    return this.trustStorePassword;
  }

  /**
   * Sets the password used to load the trust store.
   *
   * @param trustStorePassword The trust store password.
   */
  public void setTrustStorePassword(final @Nullable String trustStorePassword) {
    this.trustStorePassword = trustStorePassword;
  }

  /**
   * Gets the type of the trust store.
   *
   * @return The trust store type.
   */
  public @Nullable String getTrustStoreType() {
    return this.trustStoreType;
  }

  /**
   * Sets the type of the trust store.
   *
   * @param trustStoreType The trust store type.
   */
  public void setTrustStoreType(final @Nullable String trustStoreType) {
    this.trustStoreType = trustStoreType;
  }

  /**
   * Gets the provider for the trust store.
   *
   * @return The trust store provider.
   */
  public @Nullable String getTrustStoreProvider() {
    return this.trustStoreProvider;
  }

  /**
   * Sets the provider for the trust store.
   *
   * @param trustStoreProvider The trust store provider.
   */
  public void setTrustStoreProvider(final @Nullable String trustStoreProvider) {
    this.trustStoreProvider = trustStoreProvider;
  }

  /**
   * Gets the SSL protocol to use. If not set, it defaults to TLS.
   *
   * @return The SSL protocol.
   */
  public @Nullable String getProtocol() {
    return this.protocol;
  }

  /**
   * Sets the SSL protocol to use.
   *
   * @param protocol The SSL protocol to use.
   */
  public void setProtocol(final @Nullable String protocol) {
    this.protocol = protocol;
  }

  /**
   * Gets whether all certificates are trusted (certificate validation becomes disabled). If not
   * set, it defaults to false.
   *
   * @return {@code true} if all certificates are trusted, {@code false} otherwise.
   */
  public boolean isTrustAll() {
    return this.trustAll;
  }

  /**
   * Sets whether all certificates are trusted (certificate validation becomes disabled).
   *
   * @param trustAll {@code true} to trust all certificates, {@code false} otherwise.
   */
  public void setTrustAll(final boolean trustAll) {
    this.trustAll = trustAll;
  }

  /**
   * Gets whether hostname should be verified during SSL handshake. If not set, it defaults to true.
   *
   * @return {@code true} if the hostname should be verified, {@code false} otherwise.
   */
  public boolean isVerifyHostname() {
    return this.verifyHostname;
  }

  /**
   * Sets whether hostname should be verified during SSL handshake.
   *
   * @param verifyHostname {@code true} if the hostname should be verified, {@code false} otherwise.
   */
  public void setVerifyHostname(final boolean verifyHostname) {
    this.verifyHostname = verifyHostname;
  }
}

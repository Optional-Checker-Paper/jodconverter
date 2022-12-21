/*
 * Copyright (c) 2004 - 2012; Mirko Nasato and contributors
 *               2016 - 2022; Simon Braconnier and contributors
 *               2022 - present; JODConverter
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

package org.jodconverter.local.office.utils;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.XComponentContext;
import org.junit.jupiter.api.Test;

import org.jodconverter.core.test.util.AssertUtil;

/** Contains tests for the {@link Lo} class. */
class LoTest {

  @Test
  void classWellDefined() {
    AssertUtil.assertUtilityClassWellDefined(Lo.class);
  }

  @Test
  void createInstFromSvcFactory_WithUnoException_ThrowWrappedUnoException() throws Exception {

    final XMultiServiceFactory sfactory = mock(XMultiServiceFactory.class);

    given(sfactory.createInstance("Whatever")).willThrow(Exception.class);
    assertThatExceptionOfType(WrappedUnoException.class)
        .isThrownBy(() -> Lo.createInstance(sfactory, Object.class, "Whatever"));
  }

  @Test
  void createInstFromSvcFactoryDep_WithUnoException_ThrowWrappedUnoException() throws Exception {

    final XMultiServiceFactory sfactory = mock(XMultiServiceFactory.class);

    given(sfactory.createInstance("Whatever")).willThrow(Exception.class);
    assertThatExceptionOfType(WrappedUnoException.class)
        .isThrownBy(() -> Lo.createInstanceMSF(sfactory, Object.class, "Whatever"));
  }

  @Test
  void createInstFromCompFact_WithUnoException_ThrowWrappedUnoException() throws Exception {

    final XComponentContext context = mock(XComponentContext.class);
    final XMultiComponentFactory cfactory = mock(XMultiComponentFactory.class);
    given(context.getServiceManager()).willReturn(cfactory);
    given(cfactory.createInstanceWithContext("Whatever", context)).willThrow(Exception.class);

    assertThatExceptionOfType(WrappedUnoException.class)
        .isThrownBy(() -> Lo.createInstance(context, Object.class, "Whatever"));
  }

  @Test
  void createInstFromCompFactDep_WithUnoException_ThrowWrappedUnoException() throws Exception {

    final XComponentContext context = mock(XComponentContext.class);
    final XMultiComponentFactory cfactory = mock(XMultiComponentFactory.class);
    given(context.getServiceManager()).willReturn(cfactory);
    given(cfactory.createInstanceWithContext("Whatever", context)).willThrow(Exception.class);

    assertThatExceptionOfType(WrappedUnoException.class)
        .isThrownBy(() -> Lo.createInstanceMCF(context, Object.class, "Whatever"));
  }
}

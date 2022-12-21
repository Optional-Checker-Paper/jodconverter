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

package org.jodconverter.local.filter.text;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XServiceInfo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.jodconverter.core.office.OfficeContext;
import org.jodconverter.local.MockUnoRuntimeExtension;
import org.jodconverter.local.filter.DefaultFilterChain;
import org.jodconverter.local.filter.Filter;
import org.jodconverter.local.office.utils.UnoRuntime;

/** Contains tests for the {@link TextInserterFilter} class. */
@ExtendWith(MockUnoRuntimeExtension.class)
class TextInserterFilterTest {

  @Nested
  class New {

    @Test
    @SuppressWarnings("ConstantConditions")
    void withNullText_ShouldThrowNullPointerException() {
      assertThatNullPointerException().isThrownBy(() -> new TextInserterFilter(null, 1, 1, 1, 1));
      assertThatNullPointerException()
          .isThrownBy(() -> new TextInserterFilter(null, 1, 1, new HashMap<>()));
    }

    @Test
    void withBlankText_ShouldThrowIllegalArgumentException() {
      assertThatIllegalArgumentException().isThrownBy(() -> new TextInserterFilter("", 1, 1, 1, 1));
      assertThatIllegalArgumentException()
          .isThrownBy(() -> new TextInserterFilter("", 1, 1, new HashMap<>()));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void withNullShapeProperties_ShouldThrowNullPointerException() {
      assertThatNullPointerException().isThrownBy(() -> new TextInserterFilter("Text", 1, 1, null));
    }

    @Test
    void withValid_ShouldNotThrowAnyException() {
      assertThatCode(() -> new TextInserterFilter("Text", 1, 1, 1, 1)).doesNotThrowAnyException();
      assertThatCode(() -> new TextInserterFilter("Text", 1, 1, new HashMap<>()))
          .doesNotThrowAnyException();
    }
  }

  @Nested
  class DoFilter {

    @Test
    void withUnsupportedDocument_ShouldCallNextFilter(final UnoRuntime unoRuntime)
        throws Exception {

      final OfficeContext context = mock(OfficeContext.class);
      final XComponent document = mock(XComponent.class);
      final XServiceInfo serviceInfo = mock(XServiceInfo.class);
      given(unoRuntime.queryInterface(XServiceInfo.class, document)).willReturn(serviceInfo);
      given(serviceInfo.supportsService(anyString())).willReturn(false);

      final TextInserterFilter filter = new TextInserterFilter("TEXT", 1, 1, 1, 1);
      final Filter next = mock(Filter.class);

      final DefaultFilterChain chain = new DefaultFilterChain(false, filter, next);
      chain.doFilter(context, document);

      verify(next, times(1)).doFilter(context, document, chain);
    }
  }
}

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

package org.jodconverter.local.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** Contains tests for the {@link UnmodifiableFilterChain} class. */
class UnmodifiableFilterChainTest {

  @Nested
  class New {

    @Test
    void shouldBeReadOnly() {

      final UnmodifiableFilterChain chain = new UnmodifiableFilterChain(NoopFilter.NOOP);
      assertThatExceptionOfType(UnsupportedOperationException.class)
          .isThrownBy(() -> chain.addFilter(RefreshFilter.REFRESH));
    }
  }

  @Nested
  class Copy {

    @Test
    void with2Filters_ShouldCopy2Filters() {

      final List<Filter> filters = new ArrayList<>();
      filters.add(NoopFilter.NOOP);
      filters.add(RefreshFilter.REFRESH);
      final UnmodifiableFilterChain chain =
          new UnmodifiableFilterChain(NoopFilter.NOOP, RefreshFilter.REFRESH);
      assertThat(chain.copy()).extracting("filters").isEqualTo(filters);
    }
  }
}

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

package org.jodconverter.local;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.util.FileUtils;

/** Helper class use to convert files while testing. */
public final class ConvertUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConvertUtil.class);

  // Input format to be skipped when testing al possible conversion.
  // They may fail on some OS or LO/OO version.
  private static final List<String> SKIPPED_INPUT_FORMAT =
      Arrays.asList(
          "odg", "svg", "fodg", "fodp", "fods", "fodt", "docx", "dotx", "xlsx", "xltx", "pptx",
          "potx");

  // Output formats to be skipped when testing al possible conversion.
  // They may fail on some OS or LO/OO version.
  private static final List<String> SKIPPED_OUTPUT_FORMAT =
      Arrays.asList(
          "svg", "png", "jpg", "jpeg", "tif", "tiff", "gif", "swf", "sxc", "sxi", "sxw", "fodg",
          "fodp", "fods", "fodt", "docx", "dotx", "xlsx", "xltx", "pptx", "potx", "xhtml");

  /**
   * Runnable used to convert a document. This kind of runner is useful when a conversion must be
   * done in his own thread.
   */
  public static class ConvertRunner implements Runnable {

    private final File source;
    private final File target;
    private final DocumentConverter converter;

    /**
     * Constructs a new runner with the specified arguments.
     *
     * @param source The source file.
     * @param target The target file.
     * @param converter The converter.
     */
    public ConvertRunner(final File source, final File target, final DocumentConverter converter) {
      super();

      this.source = source;
      this.target = target;
      this.converter = converter;
    }

    @Override
    public void run() {

      final DocumentFormat sourceFmt =
          converter
              .getFormatRegistry()
              .getFormatByExtension(
                  Objects.requireNonNull(FileUtils.getExtension(source.getName())));
      assert sourceFmt != null;
      final String sourceExt = sourceFmt.getExtension();

      final DocumentFormat targetFmt =
          converter
              .getFormatRegistry()
              .getFormatByExtension(
                  Objects.requireNonNull(FileUtils.getExtension(target.getName())));
      assert targetFmt != null;
      final String targetExt = targetFmt.getExtension();

      try {

        LOGGER.info("Converting [{} -> {}]...", sourceExt, targetExt);
        converter.convert(source).to(target).execute();
        LOGGER.debug("Conversion done [{} -> {}]...", sourceExt, targetExt);

        // Check that the created file is not empty. The programmer still have to
        // manually if the content of the output file looks good.
        assertThat(source).isFile();
        assertThat(source.length()).isGreaterThan(0L);

      } catch (Exception ex) {

        // Log the error.
        final String message = "Could not convert from " + sourceExt + " to " + targetExt + ".";
        if (ex.getCause() instanceof com.sun.star.task.ErrorCodeIOException) {
          final com.sun.star.task.ErrorCodeIOException ioEx =
              (com.sun.star.task.ErrorCodeIOException) ex.getCause();
          if (LOGGER.isErrorEnabled()) {
            LOGGER.error(message + " " + ioEx.getMessage(), ioEx);
          }
        } else {
          if (LOGGER.isErrorEnabled()) {
            LOGGER.error(message + " " + ex.getMessage(), ex);
          }
        }

        throw new RuntimeException(ex);
      }
    }
  }

  /**
   * Converts a source file into all supported formats.
   *
   * @param sourceFile The source file.
   * @param outputDir The output directory.
   * @param converter The converter.
   */
  public static void convertFileToSupportedFormats(
      final File sourceFile, final File outputDir, final DocumentConverter converter) {

    // Detect input format
    final String inputExt = FileUtils.getExtension(sourceFile.getName());
    final DocumentFormat inputFormat =
        DefaultDocumentFormatRegistry.getFormatByExtension(Objects.requireNonNull(inputExt));
    if (inputFormat == null) {
      LOGGER.info("Skipping unsupported input format {}", inputExt);
      return;
    }
    assertThat(inputFormat).as("check %s's input format", inputExt).isNotNull();

    // Get all supported output formats
    final Set<DocumentFormat> outputFormats =
        inputFormat.getInputFamily() == null
            ? new HashSet<>()
            : DefaultDocumentFormatRegistry.getOutputFormats(inputFormat.getInputFamily());

    // Convert the input file into all the supported output formats.
    // This will create 1 output file per output format.
    for (final DocumentFormat outputFormat : outputFormats) {

      // Skip test that doesn't work on all os or with all office installation.
      if (checkSkipConversion(inputFormat, outputFormat)) {
        continue;
      }

      // Create an output file
      final File targetFile =
          new File(outputDir, sourceFile.getName() + "." + outputFormat.getExtension());
      targetFile.deleteOnExit();

      // Delete existing file
      FileUtils.deleteQuietly(targetFile);

      // Convert the file to the desired format
      new ConvertRunner(sourceFile, targetFile, converter).run();
    }
  }

  private static boolean checkSkipConversion(
      final DocumentFormat inputFormat, final DocumentFormat outputFormat) {
    if (SKIPPED_INPUT_FORMAT.contains(inputFormat.getExtension())
        || SKIPPED_OUTPUT_FORMAT.contains(outputFormat.getExtension())) {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info(
            "Skipping {} to {} test", inputFormat.getExtension(), outputFormat.getExtension());
      }
      return true;
    }
    return false;
  }

  // Suppresses default constructor, ensuring non-instantiability.
  private ConvertUtil() {
    throw new AssertionError("Utility class must not be instantiated");
  }
}

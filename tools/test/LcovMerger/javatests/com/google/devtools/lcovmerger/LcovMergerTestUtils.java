// Copyright 2018 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.lcovmerger;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Helper class for creating and parsing lcov tracefiles and the necessary data structured used by
 * {@code LcovMerger}.
 *
 * The tests are floating around 2 main tracefiles (numbered with 1 and 2 throughout the tests code
 * base). The tracefiles refer the same source file with different coverage data, making them good
 * candidates for being merged.
 *
 * There are multiple static variables defined for information extracted from each of
 * tracefile{1,2} (e.g. the number of lines found, the number of functions hit, lines execution).
 *
 * Additionally, the 2 tracefiles may be used in tests multiple times with different source
 * filenames so they could be considered as different entries in the merged tracefile.
 */
public class LcovMergerTestUtils {

  // The content of tracefile1.
  static final ImmutableList<String> TRACEFILE1 = ImmutableList.of(
      "SF:SOURCE_FILENAME",
      "FN:10,file1-func1",
      "FN:20,file1-func2",
      "FN:25,file1-func3",
      "FNDA:3,file1-func1",
      "FNDA:5,file1-func2",
      "FNDA:0,file1-func3",
      "FNF:3",
      "FNH:2",
      "DA:10,3",
      "DA:11,3",
      "DA:12,30",
      "DA:13,30",
      "DA:15,3",
      "DA:16,0",
      "DA:17,0",
      "DA:19,3",
      "DA:20,5",
      "DA:21,5",
      "DA:22,5",
      "DA:23,5",
      "DA:25,0",
      "DA:26,0",
      "LH:10",
      "LF:14",
      "end_of_record"
  );

  static final ImmutableList<String> TRACEFILE1_DIFFERENT_NAME = ImmutableList.of(
      "SF:DIFFERENT_SOURCE_FILENAME",
      "FN:10,file1-func1",
      "FN:20,file1-func2",
      "FN:25,file1-func3",
      "FNDA:3,file1-func1",
      "FNDA:5,file1-func2",
      "FNDA:0,file1-func3",
      "FNF:3",
      "FNH:2",
      "DA:10,3",
      "DA:11,3",
      "DA:12,30",
      "DA:13,30",
      "DA:15,3",
      "DA:16,0",
      "DA:17,0",
      "DA:19,3",
      "DA:20,5",
      "DA:21,5",
      "DA:22,5",
      "DA:23,5",
      "DA:25,0",
      "DA:26,0",
      "LH:10",
      "LF:14",
      "end_of_record"
  );

  // The content of tracefile2.
  static final ImmutableList<String> TRACEFILE2 = ImmutableList.of(
      "SF:SOURCE_FILENAME",
      "FN:10,file1-func1",
      "FN:20,file1-func2",
      "FN:25,file1-func3",
      "FNDA:2,file1-func1",
      "FNDA:3,file1-func2",
      "FNDA:2,file1-func3",
      "FNF:3",
      "FNH:3",
      "DA:10,2",
      "DA:11,2",
      "DA:12,20",
      "DA:13,20",
      "DA:15,2",
      "DA:16,2",
      "DA:17,2",
      "DA:19,0",
      "DA:20,3",
      "DA:21,3",
      "DA:22,3",
      "DA:23,3",
      "DA:25,2",
      "DA:26,2",
      "LH:13",
      "LF:14",
      "end_of_record"
  );

  // The content of a tracefile after tracefile1 and tracefile2 were merged together.
  static final ImmutableList<String> MERGED_TRACEFILE = ImmutableList.of(
      "SF:SOURCE_FILENAME",
      "FN:10,file1-func1",
      "FN:20,file1-func2",
      "FN:25,file1-func3",
      "FNDA:5,file1-func1",
      "FNDA:8,file1-func2",
      "FNDA:2,file1-func3",
      "FNF:3",
      "FNH:3",
      "DA:10,5",
      "DA:11,5",
      "DA:12,50",
      "DA:13,50",
      "DA:15,5",
      "DA:16,2",
      "DA:17,2",
      "DA:19,3",
      "DA:20,8",
      "DA:21,8",
      "DA:22,8",
      "DA:23,8",
      "DA:25,2",
      "DA:26,2",
      "LH:14",
      "LF:14",
      "end_of_record"
  );

  static final String SOURCE_FILENAME = "SOURCE_FILENAME";
  static final int NR_FUNCTIONS_FOUND = 3;
  static final int NR_FUNCTIONS_HIT_TRACEFILE1 = 2;
  static final int NR_FUNCTIONS_HIT_TRACEFILE2 = 3;

  static final String FUNC_1 = "file1-func1";
  static final int FUNC_1_LINE_NR = 10;
  static final int FUNC_1_NR_EXECUTED_LINES_TRACEFILE1 = 3;
  static final int FUNC_1_NR_EXECUTED_LINES_TRACEFILE2 = 2;

  static final String FUNC_2 = "file1-func2";
  static final int FUNC_2_LINE_NR = 20;
  static final int FUNC_2_NR_EXECUTED_LINES_TRACEFILE1 = 5;
  static final int FUNC_2_NR_EXECECUTED_LINES_TRACEFILE2 = 3;

  static final String FUNC_3 = "file1-func3";
  static final int FUNC_3_LINE_NR = 25;
  static final int FUNC_3_NR_EXECUTED_LINES_TRACEFILE1 = 0;
  static final int FUNC_3_NR_EXECUTED_LINES_TRACEFILE2 = 2;

  static final int NR_LINES_FOUND = 14;
  static final int NR_LINES_HIT_TRACEFILE1 = 10;
  static final int NR_LINES_HIT_TRACEFILE2 = 13;

  static final int MAX_LINES_IN_FILE = 27;

  static int[] createLinesExecution1() {
    int[] lineExecutionCountForTracefile = new int[MAX_LINES_IN_FILE];
    for (int i = 0; i < MAX_LINES_IN_FILE; ++i) {
      lineExecutionCountForTracefile[i] = -1;       // no corresponding DA line for line i
    }

    lineExecutionCountForTracefile[10] = 3;         // DA:10,3
    lineExecutionCountForTracefile[11] = 3;         // DA:11,3
    lineExecutionCountForTracefile[12] = 30;        // DA:12,30
    lineExecutionCountForTracefile[13] = 30;        // DA:13,30
    lineExecutionCountForTracefile[15] = 3;         // DA:15,3
    lineExecutionCountForTracefile[16] = 0;         // DA:16,0
    lineExecutionCountForTracefile[17] = 0;         // DA:17,0
    lineExecutionCountForTracefile[19] = 3;         // DA:19,3
    lineExecutionCountForTracefile[20] = 5;         // DA:20,5
    lineExecutionCountForTracefile[21] = 5;         // DA:21,5
    lineExecutionCountForTracefile[22] = 5;         // DA:22,5
    lineExecutionCountForTracefile[23] = 5;         // DA:23,5
    lineExecutionCountForTracefile[25] = 0;         // DA:25,0
    lineExecutionCountForTracefile[26] = 0;         // DA:26,0
    return lineExecutionCountForTracefile;
  }

  static int[] createLinesExecution2() {
    int[] lineExecutionCountForTracefile = new int[MAX_LINES_IN_FILE];
    for (int i = 0; i < MAX_LINES_IN_FILE; ++i) {
      lineExecutionCountForTracefile[i] = -1;       // no corresponding DA line for line i
    }

    lineExecutionCountForTracefile[10] = 2;         // DA:10,2
    lineExecutionCountForTracefile[11] = 2;         // DA:11,2
    lineExecutionCountForTracefile[12] = 20;        // DA:12,20
    lineExecutionCountForTracefile[13] = 20;        // DA:13,20
    lineExecutionCountForTracefile[15] = 2;         // DA:15,2
    lineExecutionCountForTracefile[16] = 2;         // DA:16,2
    lineExecutionCountForTracefile[17] = 2;         // DA:17,2
    lineExecutionCountForTracefile[19] = 0;         // DA:19,0
    lineExecutionCountForTracefile[20] = 3;         // DA:20,3
    lineExecutionCountForTracefile[21] = 3;         // DA:21,3
    lineExecutionCountForTracefile[22] = 3;         // DA:22,3
    lineExecutionCountForTracefile[23] = 3;         // DA:23,3
    lineExecutionCountForTracefile[25] = 2;         // DA:25,2
    lineExecutionCountForTracefile[26] = 2;         // DA:26,2
    return lineExecutionCountForTracefile;
  }

  static SourceFileCoverage createSourceFile1(int[] lineExecutionCountForTracefile) {
    return createSourceFile1(SOURCE_FILENAME, lineExecutionCountForTracefile);
  }

  // Create source file coverage data, excluding branch coverage
  static SourceFileCoverage createSourceFile1(String sourceFilename, int[] lineExecutionCount) {
    SourceFileCoverage sourceFile = new SourceFileCoverage(sourceFilename);

    sourceFile.addLineNumber(FUNC_1, FUNC_1_LINE_NR);
    sourceFile.addFunctionExecution(FUNC_1, FUNC_1_NR_EXECUTED_LINES_TRACEFILE1);

    sourceFile.addLineNumber(FUNC_2, FUNC_2_LINE_NR);
    sourceFile.addFunctionExecution(FUNC_2, FUNC_2_NR_EXECUTED_LINES_TRACEFILE1);

    sourceFile.addLineNumber(FUNC_3, FUNC_3_LINE_NR);
    sourceFile.addFunctionExecution(FUNC_3, FUNC_3_NR_EXECUTED_LINES_TRACEFILE1);

    sourceFile.nrFunctionsFound(NR_FUNCTIONS_FOUND);
    sourceFile.nrFunctionsHit(NR_FUNCTIONS_HIT_TRACEFILE1);


    for (int line = FUNC_1_LINE_NR; line < MAX_LINES_IN_FILE; line++) {
      if (lineExecutionCount[line] >= 0) {
        sourceFile.addLine(
            line, LineCoverage.create(
                line, lineExecutionCount[line], null));
      }
    }

    sourceFile.nrOfLinesWithNonZeroExecution(NR_LINES_HIT_TRACEFILE1);
    sourceFile.nrOfInstrumentedLines(NR_LINES_FOUND);

    return sourceFile;
  }

  // Create source file coverage data, excluding branch coverage
  static SourceFileCoverage createSourceFile2(int[] lineExecutionCount) {
    SourceFileCoverage sourceFileCoverage = new SourceFileCoverage(SOURCE_FILENAME);

    sourceFileCoverage.addLineNumber(FUNC_1, FUNC_1_LINE_NR);
    sourceFileCoverage.addFunctionExecution(FUNC_1, FUNC_1_NR_EXECUTED_LINES_TRACEFILE2);

    sourceFileCoverage.addLineNumber(FUNC_2, FUNC_2_LINE_NR);
    sourceFileCoverage.addFunctionExecution(FUNC_2, FUNC_2_NR_EXECECUTED_LINES_TRACEFILE2);

    sourceFileCoverage.addLineNumber(FUNC_3, FUNC_3_LINE_NR);
    sourceFileCoverage.addFunctionExecution(FUNC_3, FUNC_3_NR_EXECUTED_LINES_TRACEFILE2);

    sourceFileCoverage.nrFunctionsFound(NR_FUNCTIONS_FOUND);
    sourceFileCoverage.nrFunctionsHit(NR_FUNCTIONS_HIT_TRACEFILE2);


    for (int line = FUNC_1_LINE_NR; line < MAX_LINES_IN_FILE; line++) {
      if (lineExecutionCount[line] >= 0) {
        sourceFileCoverage.addLine(
            line, LineCoverage.create(
                line, lineExecutionCount[line], null));
      }
    }

    sourceFileCoverage.nrOfLinesWithNonZeroExecution(NR_LINES_HIT_TRACEFILE2);
    sourceFileCoverage.nrOfInstrumentedLines(NR_LINES_FOUND);

    return sourceFileCoverage;
  }

  private static void assertLinesExecution_tracefile1(Map<Integer, LineCoverage> lines) {
    int[] lineExecution = createLinesExecution1();

    assertThat(lines.size()).isEqualTo(NR_LINES_FOUND);

    for (int line = 10; line < lineExecution.length; line++) {
      if (lineExecution[line] >= 0) {
        LineCoverage lineCoverage = lines.get(line);
        assertThat(lineCoverage.executionCount()).isEqualTo(lineExecution[line]);
        assertThat(lineCoverage.lineNumber()).isEqualTo(line);
        assertThat(lineCoverage.checksum()).isNull();
      }
    }
  }

  private static void assertLines_tracefile2(Map<Integer, LineCoverage> lines) {
    int[] lineExecution = createLinesExecution2();

    assertThat(lines.size()).isEqualTo(NR_LINES_FOUND);

    for (int lineIndex = 10; lineIndex < lineExecution.length; lineIndex++) {
      if (lineExecution[lineIndex] >= 0) {
        LineCoverage line = lines.get(lineIndex);
        assertThat(line.executionCount()).isEqualTo(lineExecution[lineIndex]);
        assertThat(line.lineNumber()).isEqualTo(lineIndex);
        assertThat(line.checksum()).isNull();
      }
    }
  }

  static void assertTracefile1(SourceFileCoverage sourceFile) {
    Map<String, Integer> lineNumbers = sourceFile.getLineNumbers();
    assertThat(lineNumbers.size()).isEqualTo(3);
    assertThat(lineNumbers.keySet()).containsAllOf(FUNC_1, FUNC_2, FUNC_3);
    assertThat(lineNumbers.get(FUNC_1)).isEqualTo(FUNC_1_LINE_NR);
    assertThat(lineNumbers.get(FUNC_2)).isEqualTo(FUNC_2_LINE_NR);
    assertThat(lineNumbers.get(FUNC_3)).isEqualTo(FUNC_3_LINE_NR);

    Map<String, Integer> functionsExecution = sourceFile.getFunctionsExecution();
    assertThat(functionsExecution.size()).isEqualTo(3);
    assertThat(functionsExecution.keySet()).containsAllOf(FUNC_1, FUNC_2, FUNC_3);
    assertThat(functionsExecution.get(FUNC_1)).isEqualTo(FUNC_1_NR_EXECUTED_LINES_TRACEFILE1);
    assertThat(functionsExecution.get(FUNC_2)).isEqualTo(FUNC_2_NR_EXECUTED_LINES_TRACEFILE1);
    assertThat(functionsExecution.get(FUNC_3)).isEqualTo(FUNC_3_NR_EXECUTED_LINES_TRACEFILE1);

    assertLinesExecution_tracefile1(sourceFile.getLines());

    assertThat(sourceFile.nrOfInstrumentedLines()).isEqualTo(14);
    assertThat(sourceFile.nrOfLinesWithNonZeroExecution()).isEqualTo(10);
  }

  static void assertTracefile2(SourceFileCoverage sourceFile) {
    Map<String, Integer> lineNumbers = sourceFile.getLineNumbers();
    assertThat(lineNumbers.size()).isEqualTo(3);
    assertThat(lineNumbers.keySet()).containsAllOf(FUNC_1, FUNC_2, FUNC_3);
    assertThat(lineNumbers.get(FUNC_1)).isEqualTo(FUNC_1_LINE_NR);
    assertThat(lineNumbers.get(FUNC_2)).isEqualTo(FUNC_2_LINE_NR);
    assertThat(lineNumbers.get(FUNC_3)).isEqualTo(FUNC_3_LINE_NR);

    Map<String, Integer> functionsExecution = sourceFile.getFunctionsExecution();
    assertThat(functionsExecution.size()).isEqualTo(3);
    assertThat(functionsExecution.keySet()).containsAllOf(FUNC_1, FUNC_2, FUNC_3);
    assertThat(functionsExecution.get(FUNC_1)).isEqualTo(FUNC_1_NR_EXECUTED_LINES_TRACEFILE2);
    assertThat(functionsExecution.get(FUNC_2)).isEqualTo(FUNC_2_NR_EXECECUTED_LINES_TRACEFILE2);
    assertThat(functionsExecution.get(FUNC_3)).isEqualTo(FUNC_3_NR_EXECUTED_LINES_TRACEFILE2);

    assertLines_tracefile2(sourceFile.getLines());

    assertThat(sourceFile.nrOfInstrumentedLines()).isEqualTo(14);
    assertThat(sourceFile.nrOfLinesWithNonZeroExecution()).isEqualTo(13);
  }

  static void assertMergedLineNumbers(TreeMap<String, Integer> lineNumbers) {
    assertThat(lineNumbers.size()).isEqualTo(3);
    assertThat(lineNumbers.keySet()).containsAllOf(FUNC_1, FUNC_2, FUNC_3);
    assertThat(lineNumbers.get(FUNC_1)).isEqualTo(FUNC_1_LINE_NR);
    assertThat(lineNumbers.get(FUNC_2)).isEqualTo(FUNC_2_LINE_NR);
    assertThat(lineNumbers.get(FUNC_3)).isEqualTo(FUNC_3_LINE_NR);
  }

  static void assertMergedFunctionsExecution(TreeMap<String, Integer> functionsExecution) {
    assertThat(functionsExecution.size()).isEqualTo(3);
    assertThat(functionsExecution.keySet()).containsAllOf(FUNC_1, FUNC_2, FUNC_3);
    assertThat(functionsExecution.get(FUNC_1)).isEqualTo(
        FUNC_1_NR_EXECUTED_LINES_TRACEFILE1 + FUNC_1_NR_EXECUTED_LINES_TRACEFILE2);
    assertThat(functionsExecution.get(FUNC_2)).isEqualTo(
        FUNC_2_NR_EXECUTED_LINES_TRACEFILE1 + FUNC_2_NR_EXECECUTED_LINES_TRACEFILE2);
    assertThat(functionsExecution.get(FUNC_3)).isEqualTo(
        FUNC_3_NR_EXECUTED_LINES_TRACEFILE1 + FUNC_3_NR_EXECUTED_LINES_TRACEFILE2);
  }

  static void assertMergedLines(
      Map<Integer, LineCoverage> lines,
      int[] linesExecution1,
      int[] linesExecution2) {
    assertThat(lines.size()).isEqualTo(14);

    for (int lineIndex = 10; lineIndex < MAX_LINES_IN_FILE; ++lineIndex) {
      int totalExecutionCount = 0;
      boolean wasInstrumented = false;
      if (linesExecution1[lineIndex] >= 0) {
        totalExecutionCount += linesExecution1[lineIndex];
        wasInstrumented = true;
      }
      if (linesExecution2[lineIndex] >= 0) {
        totalExecutionCount += linesExecution2[lineIndex];
        wasInstrumented = true;
      }
      if (wasInstrumented) {
        LineCoverage line = lines.get(lineIndex);
        assertThat(line.executionCount()).isEqualTo(totalExecutionCount);
        assertThat(line.lineNumber()).isEqualTo(lineIndex);
        assertThat(line.checksum()).isNull();
      }
    }
  }

  static void assertMergedSourceFile(
      SourceFileCoverage merged, int[] linesExecution1, int[] linesExecution2) {
    assertMergedLineNumbers(merged.getLineNumbers());
    assertMergedFunctionsExecution(merged.getFunctionsExecution());
    assertMergedLines(merged.getLines(), linesExecution1, linesExecution2);

    assertThat(merged.nrFunctionsFound()).isEqualTo(NR_FUNCTIONS_FOUND);
    assertThat(merged.nrFunctionsHit()).isEqualTo(NR_FUNCTIONS_FOUND);
    assertThat(merged.nrOfLinesWithNonZeroExecution()).isEqualTo(14);
    assertThat(merged.nrOfInstrumentedLines()).isEqualTo(14);
  }
}

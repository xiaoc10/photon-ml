/*
 * Copyright 2017 LinkedIn Corp. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.linkedin.photon.ml.diagnostics.fitting

import org.testng.annotations.Test

import com.linkedin.photon.ml.Evaluation
import com.linkedin.photon.ml.diagnostics.reporting.SectionPhysicalReport

/**
 * Sanity checks for FittingToPhysicalReportTransformer
 */
class FittingToPhysicalReportTransformerTest {
  import org.testng.Assert._

  import FittingToPhysicalReportTransformerTest._

  @Test
  def checkHappyPath(): Unit = {
    val xData = (1 until NUM_SAMPLES).map( x => x.toDouble / NUM_SAMPLES ).toArray
    val report = FittingReport(
      Map(FIRST_METRIC -> (xData, xData, xData),
          SECOND_METRIC -> (xData, xData, xData)),
      "Message"
    )
    val transformer = new FittingToPhysicalReportTransformer()
    val transformed = transformer.transform(report)

    assertTrue(transformed.isInstanceOf[SectionPhysicalReport])
    assertEquals(transformed.items.length, EXPECTED_SECTIONS)
    transformed.items.foreach(x => {
      assertTrue(x.isInstanceOf[SectionPhysicalReport])
    })
  }
}

object FittingToPhysicalReportTransformerTest {

  private val FIRST_METRIC = Evaluation.MEAN_ABSOLUTE_ERROR
  private val SECOND_METRIC = Evaluation.MEAN_SQUARE_ERROR
  private val NUM_SAMPLES = 10
  private val EXPECTED_SECTIONS = 2

}

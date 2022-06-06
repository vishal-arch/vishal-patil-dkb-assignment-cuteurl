package com.dkb.miniurl.metrics

import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UrlShorteningMetricsTest {

    private val registry = SimpleMeterRegistry()
    private val urlShorteningMetrics = UrlShorteningMetrics(registry)

    @Test
    fun shouldCreateCounterAndIncrement() {
        urlShorteningMetrics.createCounterMetricsForLinkClicked("DUMMY_TAG")
        assertThat(
            registry.find(urlShorteningMetrics.LINK_CLICKED_COUNTER).tag("alias", "DUMMY_TAG")
        ).isNotNull

        urlShorteningMetrics.incrementLinkClickedCounter("DUMMY_TAG")
        assertThat(
            registry.find(urlShorteningMetrics.LINK_CLICKED_COUNTER).tag("alias", "DUMMY_TAG")
                .counter()?.count()
        ).isEqualTo(1.0)
    }
}
package com.dkb.miniurl.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class UrlShorteningMetrics(val meterRegistry: MeterRegistry) {

    val LINK_CLICKED_COUNTER = "miniurl.link.clicked"

    fun createCounterMetricsForLinkClicked(tag: String) {

        Counter.builder(LINK_CLICKED_COUNTER)
            .description("The counter keeps the count of ${tag} link clicked .")
            .tag("alias",tag)
            .register(meterRegistry)
    }

    fun incrementLinkClickedCounter(tag: String) {
        meterRegistry.counter(LINK_CLICKED_COUNTER,"alias",tag ).increment()
    }

}
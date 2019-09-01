package com.example.rx.test

import com.example.rx.scheduler.SchedulerConfiguration
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

object TestSchedulerConfigurationImpl {
    val schedulerConfiguration = object : SchedulerConfiguration {
        override val io: Scheduler = Schedulers.trampoline()

        override val computation: Scheduler = Schedulers.trampoline()

        override val ui: Scheduler = Schedulers.trampoline()

        override val time: Scheduler = Schedulers.trampoline()

        override val test: Scheduler = TestScheduler()
    }
}
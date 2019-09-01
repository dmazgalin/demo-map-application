package com.example.rx.scheduler

import io.reactivex.Scheduler

interface SchedulerConfiguration {
    val io: Scheduler
    val computation: Scheduler
    val ui: Scheduler
    val time: Scheduler
    val test: Scheduler
}
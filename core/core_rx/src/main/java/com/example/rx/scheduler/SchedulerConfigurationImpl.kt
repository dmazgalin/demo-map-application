package com.example.rx.scheduler

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class SchedulerConfigurationImpl: SchedulerConfiguration {
    override val io: Scheduler = Schedulers.io()
    override val computation: Scheduler = Schedulers.computation()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
    override val time: Scheduler = Schedulers.computation()
    override val test: Scheduler = Schedulers.trampoline()
}
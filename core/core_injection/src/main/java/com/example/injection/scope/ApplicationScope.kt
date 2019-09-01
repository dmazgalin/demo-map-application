package com.example.injection.scope

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import javax.inject.Scope

import java.lang.annotation.RetentionPolicy

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class ApplicationScope {
}
package com.dkb.miniurl.controller.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(allowedTargets = [AnnotationTarget.FIELD,AnnotationTarget.CLASS])
@Constraint(validatedBy = [ValidUrlValidator::class])
annotation class ValidUrl(

    val message: String = "must contain valid Url",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []

)
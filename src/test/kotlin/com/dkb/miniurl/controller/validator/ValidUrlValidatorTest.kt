package com.dkb.miniurl.controller.validator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import javax.validation.ConstraintValidatorContext
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext

@ExtendWith(MockitoExtension::class)
class ValidUrlValidatorTest {

    val context = mock(ConstraintValidatorContext::class.java)
    val builder = mock(ConstraintViolationBuilder::class.java)

    @ParameterizedTest
    @ValueSource(strings = ["www.abc.com", "http://www.abc.com", "https://www.abc.com", "abc.com"])
    fun shouldReturnTrueOnValidUrlCheck(url: String) {
        val validator = ValidUrlValidator()
        val isValid = validator.isValid(url, context)
        assertThat(isValid).isTrue()
    }


    @ParameterizedTest
    @ValueSource(strings = ["wwwabc", ".com", "http", "www"])
    fun shouldReturnFalseOnValidUrlCheck(url: String) {

        `when`(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder)
        `when`(builder.addPropertyNode(any())).thenReturn(mock(NodeBuilderCustomizableContext::class.java))
        val validator = ValidUrlValidator()
        val isValid = validator.isValid(url, context)
        assertThat(isValid).isFalse()
    }

}
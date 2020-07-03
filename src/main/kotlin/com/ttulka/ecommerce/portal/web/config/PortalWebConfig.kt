package com.ttulka.ecommerce.portal.web.config

import com.ttulka.ecommerce.portal.web.CatalogController
import com.ttulka.ecommerce.sales.catalog.FindCategories
import org.springframework.context.annotation.Configuration
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

/**
 * Configuration for Portal Web.
 */
@Configuration
internal class PortalWebConfig {
    /**
     * Web Layout Advice for Portal.
     *
     *
     * Adds a list of Category items into the layout model.
     */
    @ControllerAdvice(basePackageClasses = [CatalogController::class])
    internal inner class WebLayoutAdvice(
            private val findCategories: FindCategories) {

        @ModelAttribute
        fun decorateWithCategories(model: Model) {
            model.addAttribute("categories", findCategories.all().map {
                mapOf(
                    "uri" to it.uri().value(),
                    "title" to it.title().value())
            })
            print("")
        }
    }
}
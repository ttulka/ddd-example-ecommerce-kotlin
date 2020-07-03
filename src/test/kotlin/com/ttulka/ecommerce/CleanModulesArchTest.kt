package com.ttulka.ecommerce

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.ttulka.ecommerce.common.events.DomainEvent
import org.junit.jupiter.api.Test

class CleanModulesArchTest {

    @Test
    fun sales_catalog_service_has_no_dependency_on_others() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.sales.catalog")
        val rule: ArchRule = ArchRuleDefinition.classes().should().onlyDependOnClassesThat(
                JavaClass.Predicates.resideOutsideOfPackages(
                        "com.ttulka.ecommerce.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.sales.catalog.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.common.."))))
        rule.check(importedClasses)
    }

    @Test
    fun sales_catalog_domain_has_no_dependency_to_its_implementation() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.sales.catalog")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.sales.catalog.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.sales.catalog.jdbc..")
        rule.check(importedClasses)
    }

    @Test
    fun sales_order_service_has_no_dependencies_on_others_except_events() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.sales.order")
        val rule: ArchRule = ArchRuleDefinition.classes().should().onlyDependOnClassesThat(
                JavaClass.Predicates.resideOutsideOfPackages(
                        "com.ttulka.ecommerce.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.sales.order.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.common..")
                ).or(JavaClass.Predicates.assignableTo(DomainEvent::class.java).or(JavaClass.Predicates.NESTED_CLASSES))))
        rule.check(importedClasses)
    }

    @Test
    fun sales_order_domain_has_no_dependency_to_its_implementation() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.sales.order")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.sales.order.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.sales.order.jdbc..")
        rule.check(importedClasses)
    }

    @Test
    fun sales_cart_service_has_no_dependency_on_others() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.sales.cart")
        val rule: ArchRule = ArchRuleDefinition.classes().should().onlyDependOnClassesThat(
                JavaClass.Predicates.resideOutsideOfPackages(
                        "com.ttulka.ecommerce.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.sales.cart.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.common.."))))
        rule.check(importedClasses)
    }

    @Test
    fun billing_payment_service_has_no_dependencies_on_others_except_events() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.billing.payment")
        val rule: ArchRule = ArchRuleDefinition.classes().should().onlyDependOnClassesThat(
                JavaClass.Predicates.resideOutsideOfPackages(
                        "com.ttulka.ecommerce.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.billing.payment.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.common..")
                ).or(JavaClass.Predicates.assignableTo(DomainEvent::class.java).or(JavaClass.Predicates.NESTED_CLASSES))))
        rule.check(importedClasses)
    }

    @Test
    fun billing_payment_domain_has_no_dependency_to_its_implementation() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.billing.payment")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.billing.payment.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.billing.payment.jdbc..")
        rule.check(importedClasses)
    }

    @Test
    fun shipping_delivery_service_has_no_dependencies_on_others_except_events() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.shipping.delivery")
        val rule: ArchRule = ArchRuleDefinition.classes().should().onlyDependOnClassesThat(
                JavaClass.Predicates.resideOutsideOfPackages(
                        "com.ttulka.ecommerce.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.shipping.delivery.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.common..")
                ).or(JavaClass.Predicates.assignableTo(DomainEvent::class.java).or(JavaClass.Predicates.NESTED_CLASSES))))
        rule.check(importedClasses)
    }

    @Test
    fun shipping_delivery_domain_has_no_dependency_to_its_implementation() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.shipping.delivery")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.shipping.delivery.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.shipping.delivery.jdbc..")
        rule.check(importedClasses)
    }

    @Test
    fun shipping_dispatching_service_has_no_dependencies_on_others_except_delivery_and_events() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.shipping.dispatching")
        val rule: ArchRule = ArchRuleDefinition.classes().should().onlyDependOnClassesThat(
                JavaClass.Predicates.resideOutsideOfPackages(
                        "com.ttulka.ecommerce.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.shipping.dispatching.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.shipping.delivery..")
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.common..")
                ).or(JavaClass.Predicates.assignableTo(DomainEvent::class.java).or(JavaClass.Predicates.NESTED_CLASSES))))
        rule.check(importedClasses)
    }

    @Test
    fun shipping_dispatching_domain_has_no_dependency_to_its_implementation() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.shipping.dispatching")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.shipping.dispatching.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.shipping.dispatching.jdbc..")
        rule.check(importedClasses)
    }

    @Test
    fun warehouse_service_has_no_dependencies_on_others_except_events() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.warehouse")
        val rule: ArchRule = ArchRuleDefinition.classes().should().onlyDependOnClassesThat(
                JavaClass.Predicates.resideOutsideOfPackages(
                        "com.ttulka.ecommerce.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.warehouse.."
                ).or(JavaClass.Predicates.resideInAPackage("com.ttulka.ecommerce.common..")
                ).or(JavaClass.Predicates.assignableTo(DomainEvent::class.java).or(JavaClass.Predicates.NESTED_CLASSES))))
        rule.check(importedClasses)
    }

    @Test
    fun warehouse_domain_has_no_dependency_to_its_implementation() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.warehouse")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.warehouse.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.warehouse.jdbc..")
        rule.check(importedClasses)
    }

    @Test
    fun catalog_service_has_no_dependencies_on_billing() {
        val importedClasses = ClassFileImporter().importPackages("com.ttulka.ecommerce.portal")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.ecommerce.billing..")
        rule.check(importedClasses)
    }

    @Test
    fun portal_web_uses_only_its_own_use_cases_and_no_direct_dependencies_on_other_services() {
        val importedClasses = ClassFileImporter().importPackages(
                "com.ttulka.ecommerce.sales",
                "com.ttulka.ecommerce.warehouse",
                "com.ttulka.ecommerce.shipping",
                "com.ttulka.ecommerce.billing")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().onlyHaveDependentClassesThat().resideOutsideOfPackage(
                        "com.ttulka.ecommerce.portal.web..")
        rule.check(importedClasses)
    }
}
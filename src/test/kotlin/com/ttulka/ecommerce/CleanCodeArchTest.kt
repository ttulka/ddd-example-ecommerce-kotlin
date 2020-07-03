package com.ttulka.ecommerce

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.Test

class CleanCodeArchTest {

    @Test
    fun no_classes_ending_with_impl() {
        val importedClasses = ClassFileImporter().importPackages("com.ttulka.ecommerce")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().haveSimpleNameNotEndingWith("Impl")
        rule.check(importedClasses)
    }

    @Test
    fun no_classes_ending_with_service() {
        val importedClasses = ClassFileImporter().importPackages("com.ttulka.ecommerce")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().haveSimpleNameNotEndingWith("Service")
        rule.check(importedClasses)
    }

    @Test
    fun no_classes_ending_with_dto() {
        val importedClasses = ClassFileImporter().importPackages("com.ttulka.ecommerce")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().haveSimpleNameNotEndingWith("DTO")
                .andShould().haveSimpleNameNotEndingWith("Dto")
        rule.check(importedClasses)
    }

    @Test
    fun no_classes_ending_with_repository() {
        val importedClasses = ClassFileImporter().importPackages("com.ttulka.ecommerce")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().haveSimpleNameNotEndingWith("Repository")
                .andShould().haveSimpleNameNotEndingWith("Repo")
        rule.check(importedClasses)
    }

    @Test
    fun no_classes_ending_with_entity() {
        val importedClasses = ClassFileImporter().importPackages("com.ttulka.ecommerce")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().haveSimpleNameNotEndingWith("Entity")
        rule.check(importedClasses)
    }

    @Test
    fun no_classes_ending_with_aggregate() {
        val importedClasses = ClassFileImporter().importPackages("com.ttulka.ecommerce")
        val rule: ArchRule = ArchRuleDefinition.classes()
                .should().haveSimpleNameNotEndingWith("Aggregate")
        rule.check(importedClasses)
    }
}
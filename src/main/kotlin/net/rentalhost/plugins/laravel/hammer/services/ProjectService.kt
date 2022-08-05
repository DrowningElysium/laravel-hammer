package net.rentalhost.plugins.laravel.hammer.services

import net.rentalhost.plugins.hammer.services.UrlService
import net.rentalhost.plugins.hammer.services.ProjectService as BaseProjectService

class ProjectService: BaseProjectService() {
    companion object {
        var instance: ProjectService = ProjectService()
    }

    override val name: String = "Laravel Hammer"

    override val sentryDsn: String = "https://8c46d0ca554447f8827b58c46e36a923@o55698.ingest.sentry.io/6627285"

    override val urls: UrlService = object: UrlService() {
        override val homeUrl: String = "https://github.com/hammer-tools/laravel-hammer"

        override val changelogUrl: String = "https://github.com/hammer-tools/laravel-hammer/blob/master/CHANGELOG.md"

        override val freemiumUrl: String = "https://github.com/hammer-tools/laravel-hammer/wiki/Freemium"

        override val inspectionsUrl: String = "https://github.com/hammer-tools/laravel-hammer/wiki/Inspections"

        override val reviewsUrl: String = "https://plugins.jetbrains.com/plugin/00000--laravel-hammer/reviews/new"
    }

    override val settings: SettingsService = SettingsService()
}
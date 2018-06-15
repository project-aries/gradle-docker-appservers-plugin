/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aries.gradle.docker.appserver.plugin

import com.aries.gradle.docker.application.plugin.GradleDockerApplicationPlugin
import com.aries.gradle.docker.application.plugin.domain.AbstractApplication

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *  Plugin providing common tasks for starting (*Up), stopping (*Stop), and deleting (*Down) a dockerized application server.
 */
class GradleDockerAppServerPlugin implements Plugin<Project> {

    @Override
    void apply(final Project project) {

        // 1.) apply required plugins
        project.plugins.apply('gradle-docker-application-plugin')

        // 2.) get docker-application container
        final NamedDomainObjectContainer<AbstractApplication> appContainers = project.extensions.getByName(GradleDockerApplicationPlugin.EXTENSION_NAME)

        // 3.) create our various dockerized databases
        createTomcatApplication(appContainers)
    }

    // create the default dockerized tomcat application server
    private void createTomcatApplication(final NamedDomainObjectContainer<AbstractApplication> appContainers) {
        appContainers.create('tomcat', {
            main {
                repository = 'tomcat'
                tag = 'alpine'
                create {
                    env = ["CREATED_BY_PLUGIN=${GradleDockerAppServerPlugin.class.simpleName}"]
                }
                liveness {
                    probe(300000, 10000, 'org.apache.catalina.startup.Catalina.start Server startup in')
                }
            }
        })
    }
}

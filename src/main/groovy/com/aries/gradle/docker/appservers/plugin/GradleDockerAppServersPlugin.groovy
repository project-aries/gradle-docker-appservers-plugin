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

package com.aries.gradle.docker.appservers.plugin

import com.aries.gradle.docker.applications.plugin.GradleDockerApplicationsPlugin
import com.aries.gradle.docker.applications.plugin.domain.AbstractApplication
import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *  Plugin providing common tasks for starting (*Up), stopping (*Stop), and deleting (*Down) a dockerized application server.
 */
class GradleDockerAppServersPlugin implements Plugin<Project> {

    public static final String EXTENSION_NAME = 'appservers'

    @Override
    void apply(final Project project) {

        // 1.) apply required plugins
        project.plugins.apply('gradle-docker-applications-plugin')

        // 2.) create plugin extension point
        final GradleDockerAppServersExtension extensionPoint = project.extensions.create(EXTENSION_NAME, GradleDockerAppServersExtension)

        // 3.) get docker-application container
        final NamedDomainObjectContainer<AbstractApplication> appContainers = project.extensions.getByName(GradleDockerApplicationsPlugin.EXTENSION_NAME)

        // 4.) create our various dockerized appservers
        createTomcatApplication(appContainers, extensionPoint)
        createWebsphereApplication(appContainers, extensionPoint, project)
    }

    // create the default dockerized tomcat application server
    private void createTomcatApplication(final NamedDomainObjectContainer<AbstractApplication> appContainers,
                                         final GradleDockerAppServersExtension extensionPoint) {

        appContainers.create('tomcat', {
            main {
                repository = 'tomcat'
                tag = '8.5-alpine'
                create {
                    env = ["CREATED_BY_PLUGIN=${GradleDockerAppServersPlugin.class.simpleName}"]

                    // if requested use randomPorts otherwise default to main port at 8080
                    def hostPort = extensionPoint.randomPorts ? '' : '8080'
                    portBindings = ["${hostPort}:8080"]
                }
                stop {
                    cmd = ['catalina.sh', 'stop', '60', 'force']
                    successOnExitCodes = [0, 137] // cover stopping the container the hard way as well as bringing it down gracefully
                    timeout = 60000
                    probe(70000, 10000)
                }
                liveness {
                    probe(300000, 10000, 'org.apache.catalina.startup.Catalina.start Server startup in')
                }
            }
            data {
                create {
                    volumes = ['/usr/local/tomcat']
                }
            }
        })
    }

    // create the default dockerized websphere application server
    private void createWebsphereApplication(final NamedDomainObjectContainer<AbstractApplication> appContainers,
                                            final GradleDockerAppServersExtension extensionPoint,
                                            final Project project) {

        appContainers.create('websphere', {
            main {
                repository = 'ibmcom/websphere-traditional'
                tag = '9.0.0.7-profile'
                create {
                    env = ["CREATED_BY_PLUGIN=${GradleDockerAppServersPlugin.class.simpleName}",
                            'UPDATE_HOSTNAME=true']

                    def hostPort = extensionPoint.randomPorts ? '' : '9043'
                    def httpsPort = extensionPoint.randomPorts ? '' : '9443'
                    portBindings = ["${hostPort}:9043", "${httpsPort}:9443"]
                    exposePorts('tcp', [9043, 9443]) // they don't expose their own ports so we'll do it for them
                }
                files {

                    // setting default password so that user does not have to
                    // jump into container and find it.
                    def passwordFileClosure = {
                        if (!project.getBuildDir().exists()) {
                            if (!project.getBuildDir().mkdirs()) {
                                throw new GradleException("Unable to create directory @ ${project.getBuildDir().path}")
                            }
                        }
                        def passwordFile = project.file("${project.getBuildDir().path}/PASSWORD")
                        passwordFile.withWriter { wrt ->
                            wrt.println('wsadmin')
                        }
                        passwordFile
                    }

                    withFile(passwordFileClosure, '/tmp')
                }
                stop {
                    timeout = 120000
                }
                liveness {
                    probe(300000, 10000, 'open for e-business')
                }
            }
            data {
                create {
                    volumes = ['/opt/IBM/WebSphere']
                }
            }
        })
    }
}

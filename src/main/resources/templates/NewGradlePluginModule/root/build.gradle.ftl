apply plugin: 'groovy'

ext {
    GROUP_ID = ''
    ARTIFACT_ID = ''
    VERSION = ''
}

repositories {
    mavenLocal()
}

dependencies {
    ${getConfigurationName("compile")} fileTree(dir: 'libs', include: ['*.jar'])
    ${getConfigurationName("compile")} gradleApi()
    ${getConfigurationName("compile")} localGroovy()
}

project.publishing {
    publications {
        pluginPublication(MavenPublication) {
            from components.java
            groupId GROUP_ID
            artifactId ARTIFACT_ID
            version VERSION
        }
    }
}

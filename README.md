# JSword - Free Bible Study Software

![](http://www.cloudbees.com/sites/default/files/Button-Powered-by-CB.png)

## This fork exists to have a GitHub Maven repository for JSWORD

## Install
Add this to pom.xml
````xml
<dependency>
  <groupId>org.crosswire</groupId>
  <artifactId>jsword</artifactId>
  <version>2.2</version>
</dependency>
````

Create a settings.xml with the following content
````xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <profiles>
        <profile>
            <id>github</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>https://repo1.maven.org/maven2</url>
                </repository>
                <repository>
                    <id>github</id>
                    <url>https://maven.pkg.github.com/clausnz/*</url>
                </repository>
            </repositories>
        </profile>
    </profiles>
</settings>
````
To use Dependency-Shot in your maven project, just declare the following repository in your pom

```
      <repositories>
                <repository>
                        <id>dependencyshot</id>
                        <name>Dependency-Shot Repository</name>
                        <url>http://dependency-shot.googlecode.com/svn/maven-repository/</url>
                        <releases>
                                <enabled>true</enabled>
                        </releases>
                        <snapshots>
                                <enabled>true</enabled>
                        </snapshots>
                </repository>
        </repositories>
```

and use the following dependencies

```
       <dependency>
            <groupId>cx.ath.mancel01</groupId>
            <artifactId>dependency-shot-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>cx.ath.mancel01</groupId>
            <artifactId>dependency-shot-impl</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```
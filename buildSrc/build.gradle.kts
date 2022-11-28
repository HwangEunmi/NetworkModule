plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

// gradle의 check task 실행시 detekt 수행 X
tasks.named("check").configure {
    this.setDependsOn(this.dependsOn.filterNot {
        it is TaskProvider <*> && it.name == "detekt"
    })
}
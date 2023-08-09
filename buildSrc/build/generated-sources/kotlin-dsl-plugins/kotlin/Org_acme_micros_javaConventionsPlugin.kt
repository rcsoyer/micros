/**
 * Precompiled [org.acme.micros.java-conventions.gradle.kts][Org_acme_micros_java_conventions_gradle] script plugin.
 *
 * @see Org_acme_micros_java_conventions_gradle
 */
public
class Org_acme_micros_javaConventionsPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Org_acme_micros_java_conventions_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}

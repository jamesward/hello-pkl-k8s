import io.fabric8.kubernetes.client.Config
import io.fabric8.kubernetes.client.KubernetesClientBuilder
import org.testcontainers.k3s.K3sContainer
import org.testcontainers.utility.DockerImageName
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class PklK8sTest {

    val k3s = K3sContainer(DockerImageName.parse("rancher/k3s:v1.21.3-k3s1"))

    @BeforeTest
    fun before() {
        k3s.start()
    }

    @Test
    fun test() {
        val kubeConfigYaml = k3s.kubeConfigYaml
        val config = Config.fromKubeconfig(kubeConfigYaml)
        KubernetesClientBuilder().withConfig(config).build().use { client ->
            javaClass.classLoader.getResourceAsStream("myresources.yaml").use {
                client.load(it).inNamespace("default").create()
            }

            val gameConfig = client.configMaps().inNamespace("default").withName("game-config-env-file").get()

            assert(gameConfig.data["lives"] == "3")
        }
    }

    @AfterTest
    fun after() {
        k3s.stop()
    }

}

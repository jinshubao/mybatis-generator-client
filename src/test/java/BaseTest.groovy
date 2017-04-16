import org.junit.Test
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.config.Configuration
import org.mybatis.generator.config.GeneratedKey
import org.mybatis.generator.config.TableConfiguration
import org.mybatis.generator.config.xml.ConfigurationParser
import org.mybatis.generator.internal.DefaultShellCallback

/**
 * Created by jinshubao on 2017/4/12.
 */
class BaseTest {

    StringBuffer sb = new StringBuffer()

    @Test
    void test() {
        def xml = new XmlParser().parse("/generatorConfig.xml")
        generProperty(xml, xml.name() as String, "")
        println sb
    }


    void generProperty(Node node, String nodeName, String table) {
        sb << table << "<${node.name()}"
        node.attributes().forEach({ key, value ->
            def fullName
            if (nodeName) {
                fullName = nodeName + "." + node.name() + "." + key
            } else {
                fullName = node.name() + "." + key
            }
            sb << " " << key
            sb << '="${'
            sb << fullName
            sb << '}"'
            println fullName + "=" + value
        })
        def nl = node.value() as NodeList
        if (nl && !nl.isEmpty()) {
            sb << ">" << "\r\n"
            for (int i = 0; i < nl.size(); i++) {
                def no = nl[i] as Node
                generProperty(no, nodeName + "." + no.name(), "\t" + table)
            }
            sb << table << "</${node.name()}>" << "\r\n"
        } else {
            sb << " />" << "\r\n"
        }
    }

    @Test
    void test2() {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File(getClass().getResource("/generatorConfig.xml").toURI());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        def context = config.getContext("context1")
        def tableConfiguration = new TableConfiguration(context)
        tableConfiguration.setTableName("biz_district")
        tableConfiguration.generatedKey = new GeneratedKey("ID", "MySql", true, null)
        context.getTableConfigurations().add(tableConfiguration)
        println config.toDocument().formattedContent
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    @Test
    void test3() {
        List<String> warnings = new ArrayList<String>()
        boolean overwrite = true;
        Configuration config = new Configuration()

        //   ... fill out the config object as appropriate...

        DefaultShellCallback callback = new DefaultShellCallback(overwrite)
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings)
        myBatisGenerator.generate(null)
    }
}

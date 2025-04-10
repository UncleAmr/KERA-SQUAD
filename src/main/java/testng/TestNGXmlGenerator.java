package testng;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestNGXmlGenerator {

    public static void generateXmlByPackage() {
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE suite SYSTEM \"https://testng.org/testng-1.0.dtd\" >\n" +
                "<suite name=\"GeneratedSuite\">\n" +
                "    <test name=\"Package Tests\">\n" +
                "        <packages>\n" +
                "            <package name=\"testng.generated\"/>\n" +
                "        </packages>\n" +
                "    </test>\n" +
                "</suite>";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("testng.xml"))) {
            writer.write(xmlContent);
            System.out.println("testng.xml generated for all classes in package: testng.generated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

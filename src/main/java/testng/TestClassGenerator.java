package testng;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestClassGenerator {

	public static void generateTestClass(String className, List<String> classNames, List<String> inputs) {
	    String directoryPath = "src/test/java/testng/generated";
	    File directory = new File(directoryPath);
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }

	    String fileName = directoryPath + "/" + className + ".java";

	    StringBuilder content = new StringBuilder();
	    content.append("package testng.generated;\n\n");
	    content.append("import org.testng.annotations.Test;\n");
	    content.append("import executor.CodeExecutor;\n"); 
	    content.append("import java.util.List;\n\n");

	    content.append("public class ").append(className).append(" {\n");
	    content.append("    @Test\n");
	    content.append("    public void run() {\n");
	    content.append("        CodeExecutor.executeSteps(\n");
	    content.append("            List.of(");

	    for (int i = 0; i < classNames.size(); i++) {
	        content.append("\"").append(classNames.get(i)).append("\"");
	        if (i != classNames.size() - 1) content.append(", ");
	    }

	    content.append("),\n            List.of(");

	    for (int i = 0; i < inputs.size(); i++) {
	        content.append("\"").append(inputs.get(i).replace("\"", "\\\"")).append("\"");
	        if (i != inputs.size() - 1) content.append(", ");
	    }

	    content.append(")\n        );\n");
	    content.append("    }\n");
	    content.append("}\n");

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        writer.write(content.toString());
	        System.out.println("Test class generated with real steps: " + fileName);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.format("Example of writing and reading text files in Java version %s%n.", getJavaVersion());

        readTextFile();
    }

    private static void readTextFile() {

        File existingTextFile = null;
        try {
            existingTextFile = chooseExistingTextFileDialog();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (existingTextFile == null) {
            System.out.println("No file chosen");
            return;
        }

        try (FileReader textReader = new FileReader(existingTextFile)) {
            try (BufferedReader bufferedReader = new BufferedReader(textReader)) {

                String lineOfText;
                while ((lineOfText = bufferedReader.readLine()) != null) {
                    System.out.println(lineOfText);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Finished reading the file");

    }


    /**
     * Opens the operating system specific file chooser dialog so the user can
     * choose which file they want to perform an operation upon, such as opening
     * the file.
     * <p>
     * This specific example is hard-coded to look only at files with a .txt extension
     * but can be modified to accept an incoming argument that contains the specific
     * filtering the user wants to use.
     *
     * @return instance of java.io.File associated with the chosen file.
     * @throws IOException
     */
    private static File chooseExistingTextFileDialog() throws IOException {
        // check where in the directory structure we are when we start running:
        String currentDir = new File(".").getCanonicalPath();
        System.out.format("Current directory: %s%n", currentDir);

        File f = null;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setCurrentDirectory(new File("."));
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("text files", "txt");
        fc.addChoosableFileFilter(fileFilter);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Please choose a file");
        int returnVal = fc.showOpenDialog(null);
        // if Yes/Ok is clicked...
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
        }
        return f;
    }


    /**
     * get the java version that is running the current program
     *
     * @return string containing the java version running the current program
     */
    private static String getJavaVersion() {
        Runtime.Version rtv = Runtime.version();
        return String.format("%s.%s.%s.%s", rtv.feature(), rtv.interim(), rtv.update(), rtv.patch());
    }
}
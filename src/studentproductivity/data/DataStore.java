package studentproductivity.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class DataStore {

    private static final String DATA_FILE = "data/student_productivity.dat";

    public static void save(Object data) {

        File file = new File(DATA_FILE);

        // Make sure the data directory exists
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(file))) {

            output.writeObject(data);

            System.out.println("Data saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error saving data: " + e.getMessage()
            );
        }
    }

    public static Object load() {

        File file = new File(DATA_FILE);

        if (!file.exists()) {

            System.out.println("No saved data found.");

            return null;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            return input.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    "Error loading data: " + e.getMessage()
            );

            backupIncompatibleData(file);

            return null;

        } catch (RuntimeException e) {

            /*
             * Handles incompatible old serialized objects.
             *
             * Example:
             * Old Project.deadline = String
             * New Project.deadline = LocalDate
             */

            System.out.println(
                    "Saved data is incompatible with the current project model."
            );

            System.out.println(
                    "Old data will be backed up and a fresh data store will be created."
            );

            backupIncompatibleData(file);

            return null;
        }
    }

    private static void backupIncompatibleData(File file) {

        try {

            Path source = file.toPath();

            Path backup = Path.of(
                    file.getParent(),
                    "student_productivity_old.dat"
            );

            Files.move(
                    source,
                    backup,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "Old data backed up to: " + backup
            );

        } catch (IOException e) {

            System.out.println(
                    "Could not back up old data: "
                    + e.getMessage()
            );

            /*
             * If backup fails, leave the original file untouched.
             * The application will still start with fresh data.
             */
        }
    }
}
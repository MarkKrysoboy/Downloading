import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static String DEFAULT_FOLDER = "D://User//Games//";

    public static void unziping(String zipPath, String zipName) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath + zipName))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(zipPath + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String fileName) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    public static void main(String[] args) {
        unziping(DEFAULT_FOLDER + "savegames//", "save_archive.zip");
        String filePath = "";

        File dir = new File(DEFAULT_FOLDER + "savegames//");
        if (dir.isDirectory()) {
            filePath = dir.listFiles()[0].getPath();
        }
        System.out.println(openProgress(filePath));
    }
}

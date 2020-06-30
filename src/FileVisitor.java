import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileVisitor {

    private static StringBuilder sb = new StringBuilder();
    static List<Language> listOfLanguages = new ArrayList<>();
    static Set<String> listNamesOfLanguages = new HashSet();

    public static void open(String dirName){

        listOfLanguages = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get("./"+dirName), new SimpleFileVisitor<Path>(){

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    FileChannel channel = FileChannel.open(file);
                    Pattern pattern = Pattern.compile(".\\\\"+dirName+"\\\\(.*)\\\\(.*)");
                    Matcher matcher = pattern.matcher(file.toString());
                    String languageName = "";

                    if(matcher.matches()) {
                        languageName = matcher.group(1);
                        listNamesOfLanguages.add(languageName);
                    }

                    MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
                    byteBuffer.load();

                    for (int i = 0; i < byteBuffer.limit(); i++) {
                        sb.append((char)byteBuffer.get());
                    }

                    Language language = new Language(languageName, sb);
                    listOfLanguages.add(language);

                    sb = new StringBuilder();

                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    static String getText(){
        return sb.toString();
    }

    static Set<String> getNamesOfLanguages(){
        return listNamesOfLanguages;
    }

    static List<Language> getListOfLanguages(){
        return listOfLanguages;
    }
}

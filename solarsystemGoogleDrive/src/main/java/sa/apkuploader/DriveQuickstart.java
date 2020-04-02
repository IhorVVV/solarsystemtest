package sa.apkuploader;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class DriveQuickstart {
    private static final String APPLICATION_NAME = "Apk uploader";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
//    quickstart-1585577564657-7c6c497fb9d7.json

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String credentialJson) throws IOException {
        InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(credentialJson));
        return GoogleCredential.fromStream(in)
                .createScoped(SCOPES);
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        String fileName = args[0];
        String folderId = args[1];
        String apkPath = args[2];
        String credJson = args[3];
        System.out.println("Folder id =  " + folderId);
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, credJson))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File previousApk = findPreviousFile(fileName, folderId, service);

        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        java.io.File filePath = new java.io.File(apkPath);
        FileContent mediaContent = new FileContent(null, filePath);
        if (previousApk == null) {
            insertFile(folderId, service, fileMetadata, mediaContent);
        } else {
            updateFile(folderId, service, previousApk, fileMetadata, mediaContent);
        }

    }

    private static void updateFile(String folderId, Drive service, File previousApk, File fileMetadata, FileContent mediaContent) throws IOException {
        StringBuilder previousParents = new StringBuilder();
        for (String parent : previousApk.getParents()) {
            previousParents.append(parent);
            previousParents.append(',');
        }
        System.out.printf("Previous file: %s (%s)\n", previousApk.getName(), previousApk.getId());
        service.files().update(previousApk.getId(), fileMetadata, mediaContent)
                .setAddParents(folderId)
                .setRemoveParents(previousParents.toString())
                .setFields("id, parents")
                .execute();
    }

    private static void insertFile(String folderId, Drive service, File fileMetadata, FileContent mediaContent) throws IOException {
        System.out.println("Previous file not found");
        fileMetadata.setParents(Collections.singletonList(folderId));
        service.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
    }

    private static File findPreviousFile(String fileName, String folderId, Drive service) throws IOException {
        String pageToken = null;
        File previousApk = null;
        do {
            FileList result = service.files().list()
                    .setQ(String.format("mimeType != 'application/vnd.google-apps.folder' and '%s' in parents", folderId))
                    .setFields("nextPageToken, files(id, name, parents)")
                    .setPageToken(pageToken)
                    .execute();
            for (File file : result.getFiles()) {
                System.out.printf("Found file: %s (%s)\n",
                        file.getName(), file.getId());
                    if (file.getName().equals(fileName)) {
                        previousApk = file;
                        break;
                    }
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        return previousApk;
    }
}
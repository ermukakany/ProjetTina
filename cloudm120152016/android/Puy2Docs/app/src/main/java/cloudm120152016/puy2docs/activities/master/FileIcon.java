package cloudm120152016.puy2docs.activities.master;


import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.Objects;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class FileIcon {

    private IIcon ext;

    public FileIcon(String fileName) {
        this.ext = getFileIcon(fileName);
    }

    public IIcon getExt() {
        return ext;
    }

    private boolean isPdf(String ext) {
        return Objects.equals(ext, "pdf");
    }

    private boolean isText(String ext) {
        return Objects.equals(ext, "txt") || Objects.equals(ext, "lex");
    }

    private boolean isZip(String ext) {
        return Objects.equals(ext, "zip") || Objects.equals(ext, "rar");
    }

    private boolean isImage(String ext) {
        return Objects.equals(ext, "jpeg") || Objects.equals(ext, "png");
    }

    private boolean isAudio(String ext) {
        return Objects.equals(ext, "zip") || Objects.equals(ext, "rar");
    }

    private boolean isVideo(String ext) {
        return Objects.equals(ext, "zip") || Objects.equals(ext, "rar");
    }

    private boolean isApplication(String ext) {
        return Objects.equals(ext, "zip") || Objects.equals(ext, "rar");
    }

    private boolean isCode(String ext) {
        return Objects.equals(ext, "c") || Objects.equals(ext, "java");
    }

    private IIcon getFileIcon(String fileName) {

        String ext = MimeTypeMap.getFileExtensionFromUrl((Uri.encode(fileName)));

        if (isPdf(ext)) {
            return FontAwesome.Icon.faw_file_pdf_o;
        }

        else if (isText(ext)) {
            return FontAwesome.Icon.faw_file_text_o;
        }

        else if (isZip(ext)) {
            return FontAwesome.Icon.faw_file_archive_o;
        }

        else if (isImage(ext)) {
            return FontAwesome.Icon.faw_file_image_o;
        }

        else if (isAudio(ext)) {
            return FontAwesome.Icon.faw_file_audio_o;
        }

        else if (isVideo(ext)) {
            return FontAwesome.Icon.faw_file_video_o;
        }

        else if (isApplication(ext)) {
            return FontAwesome.Icon.faw_file_video_o;
        }

        else if (isCode(ext)) {
            return FontAwesome.Icon.faw_file_code_o;
        }

        else {
            return FontAwesome.Icon.faw_file;
        }
    }
}

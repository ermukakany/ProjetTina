package cloudm120152016.puy2docs.activities.master;


import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;

import com.google.gson.internal.bind.ObjectTypeAdapter;
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
        return Objects.equals(ext, "txt") || Objects.equals(ext, "lex")|| Objects.equals(ext,"doc")|| Objects.equals(ext,"docx") || Objects.equals(ext,"odt");
    }

    private boolean isZip(String ext) {
        return Objects.equals(ext, "zip") || Objects.equals(ext, "rar") || Objects.equals(ext,"ace") || Objects.equals(ext,"bz") || Objects.equals(ext,"gz") || Objects.equals(ext,"tar") || Objects.equals(ext,"zdg");
    }

    private boolean isImage(String ext) {
        return Objects.equals(ext, "jpeg") || Objects.equals(ext, "png") || Objects.equals(ext, "jpg") || Objects.equals(ext,"gif");
    }

    private boolean isAudio(String ext) {
        return Objects.equals(ext, "ac3") || Objects.equals(ext, "mp3") || Objects.equals(ext, "flac") || Objects.equals(ext,"aac") || Objects.equals(ext,"cda") || Objects.equals(ext,"ac3") || Objects.equals(ext,"wav") || Objects.equals(ext,"mp4");
    }

    private boolean isVideo(String ext) {
        return Objects.equals(ext, "mkv") || Objects.equals(ext, "avi") || Objects.equals(ext, "divx") || Objects.equals(ext,"flv") || Objects.equals(ext,"mov") || Objects.equals(ext,"mpeg");
    }

    private boolean isApplication(String ext) {
        return Objects.equals(ext, "exe") || Objects.equals(ext, "apk") || Objects.equals(ext,"app");
    }

    private boolean isCode(String ext) {
        return Objects.equals(ext, "c") || Objects.equals(ext, "java") || Objects.equals(ext,"r") || Objects.equals(ext, "py") || Objects.equals(ext,"asm") || Objects.equals(ext,"cpp")|| Objects.equals(ext,"cmd")|| Objects.equals(ext,"cp")|| Objects.equals(ext,"css") || Objects.equals(ext,"html") || Objects.equals(ext,"php") || Objects.equals(ext,"js") || Objects.equals(ext,"rb") || Objects.equals(ext,"sql") || Objects.equals(ext,"xml");
    }

    private boolean isPowerPoint(String ext) {
        return Objects.equals(ext,"pps") || Objects.equals(ext,"ppt") || Objects.equals(ext,"odp") || Objects.equals(ext,"sdd") || Objects.equals(ext,"sdp");
    }

    private boolean isExcel(String ext) {
        return Objects.equals(ext,"xls") || Objects.equals(ext, "xlsx") || Objects.equals(ext,"odf") || Objects.equals(ext,"sdc") || Objects.equals(ext,"sdf") || Objects.equals(ext,"xlsx");
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
            return FontAwesome.Icon.faw_android;
        }

        else if (isCode(ext)) {
            return FontAwesome.Icon.faw_file_code_o;
        }

        else if (isPowerPoint(ext)) {
            return FontAwesome.Icon.faw_file_powerpoint_o;
        }

        else if (isExcel(ext)) {
            return FontAwesome.Icon.faw_file_excel_o;
        }

        else {
            return FontAwesome.Icon.faw_file;
        }
    }
}

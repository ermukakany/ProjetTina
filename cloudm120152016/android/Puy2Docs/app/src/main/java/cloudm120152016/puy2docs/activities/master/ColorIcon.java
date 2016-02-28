package cloudm120152016.puy2docs.activities.master;


import android.graphics.Color;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.typeface.IIcon;

public class ColorIcon {
    private int color;

    public ColorIcon(IIcon icon) {
        this.color = getColorIcon(icon);
    }

    public int getColor() {
        return color;
    }

    private int getColorIcon(IIcon icon) {
        if (icon == FontAwesome.Icon.faw_file_pdf_o) {
            return Color.parseColor("#E91E63");
        }

        else if (icon == FontAwesome.Icon.faw_file_text_o) {
            return Color.parseColor("#9E9E9E");
        }

        else if (icon == FontAwesome.Icon.faw_file_archive_o) {
            return Color.parseColor("#795548");
        }

        else if (icon == FontAwesome.Icon.faw_file_code_o) {
            return Color.parseColor("#795548");
        }

        else {
            return Color.parseColor("#F57C00");
        }
    }
}

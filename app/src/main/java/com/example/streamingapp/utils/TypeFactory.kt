package com.example.streamingapp.utils

import android.content.Context
import android.graphics.Typeface

/**
 * Description: This type factory class is used for the purpose of providing custom typefaces.
 *
 * @author Ankit Mishra
 * @since 14/02/22
 *
 * @param context Provide context.
 */
class TypeFactory(context: Context) {

    /**
     * Description: This enum class is used for getting different fonts.
     *
     * @author Ankit Mishra
     * @since 11/02/22
     */
    enum class FontType(val id: Int, val fontPath: String) {
        REGULAR(1, "fonts/Quicksand-Regular.ttf"),
        LIGHT(2, "fonts/Quicksand-Light.ttf"),
        MEDIUM(3, "fonts/Quicksand-Medium.ttf"),
        SEMI_BOLD(4, "fonts/Quicksand-SemiBold.ttf"),
        BOLD(5, "fonts/Quicksand-Bold.ttf");


        companion object {
            /**
             * Description: This method is used for getting font type from id.
             *
             * @author Ankit Mishra
             * @since 11/02/22
             *
             * @param id Provide id for font type.
             */
            fun getFontType(id: Int) = values().find { it.id == id } ?: REGULAR
        }
    }

    var regular: Typeface = Typeface.createFromAsset(context.assets, FontType.REGULAR.fontPath)
    var light: Typeface = Typeface.createFromAsset(context.assets, FontType.LIGHT.fontPath)
    var medium: Typeface = Typeface.createFromAsset(context.assets, FontType.MEDIUM.fontPath)
    var semibold: Typeface = Typeface.createFromAsset(context.assets, FontType.SEMI_BOLD.fontPath)
    var bold: Typeface = Typeface.createFromAsset(context.assets, FontType.BOLD.fontPath)

}

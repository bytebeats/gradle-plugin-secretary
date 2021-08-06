package me.bytebeats.agp.secretary

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/6 19:56
 * @Version 1.0
 * @Description TO-DO
 */

object Resources {
    fun getIcon(icon: String): Icon = IconLoader.getIcon("/icons/$icon", Resources::class.java)
}


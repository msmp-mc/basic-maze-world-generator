package world.anhgelus.msmp.basicmazeworldgenerator.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream

object FileHelper {
    fun readFile(iS: InputStream): String {
        val builder = StringBuilder()
        val br = iS.bufferedReader()
        while (br.readLine() != null)
            builder.append(br.readLine()).append("\n")
        return builder.toString()
    }
}
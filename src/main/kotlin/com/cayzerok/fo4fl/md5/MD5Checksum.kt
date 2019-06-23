package com.cayzerok.fo4fl.md5

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

data class MasterElem(val path: String, val checksum: String)

var masterList = arrayListOf<MasterElem>()

class MD5Checksum {

    @Throws(Exception::class)
    fun createChecksum(filename: String): ByteArray {
        val fis = FileInputStream(filename)

        val buffer = ByteArray(1024)
        val complete = MessageDigest.getInstance("md5")
        var numRead: Int

        do {
            numRead = fis.read(buffer)
            if (numRead > 0) {
                complete.update(buffer, 0, numRead)
            }
        } while (numRead != -1)

        fis.close()
        return complete.digest()
    }

    @Throws(Exception::class)
    fun getMD5Checksum(filename: String): String {
        val b = createChecksum(filename)
        var result = ""

        for (i in b.indices) {
            result += Integer.toString((b[i] + 0xff) + 0x100, 16).substring(1)
        }
        return result
    }


    fun checkMD5Directory(path: String) {
        val directories = arrayListOf<String>()
        val file = File(path)
        val index = file.listFiles()
        index.forEach {
            if (it.isDirectory) directories.add(it.path)
            else {
                println(it.path)
                masterList.add(MasterElem(it.path, getMD5Checksum(it.path)))
            }
        }
        if (directories.size > 0) {
            directories.forEach {
                checkMD5Directory(it)
            }
        }
    }
}
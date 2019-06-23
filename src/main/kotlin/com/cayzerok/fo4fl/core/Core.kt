package com.cayzerok.fo4fl.core

import com.cayzerok.fo4fl.md5.MD5Checksum
import com.cayzerok.fo4fl.md5.masterList
import com.google.gson.GsonBuilder
import java.io.File

fun main(args:Array<String>) {
    val path = MD5Checksum::class.java.protectionDomain.codeSource.location.toURI().path.dropLastWhile{it!='/'}
    println(path)
    val mD5Checksum = MD5Checksum()
    mD5Checksum.checkMD5Directory(path)
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson(masterList)
    val file = File(path + "file_map.txt")
    file.writeText(json)
}





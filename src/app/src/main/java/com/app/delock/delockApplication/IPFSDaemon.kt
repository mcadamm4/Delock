package com.app.delock.delockApplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import okio.Okio
import java.io.File
import java.io.FileNotFoundException

/* 
DISCLAIMER:
    This code was written by Github user ligi for the IPFSDroid project
    and was open-sourced to help promote mobile development with 
    IPFS on the Android platform 

    IPFSDroid Github - https://github.com/ligi/IPFSDroid
    ligi Github - https://github.com/ligi/IPFSDroid/commits?author=ligi
 */


class IPFSDaemon(val androidContext: Context) {

    private fun getBinaryFile() = File(androidContext.filesDir, "ipfsbin")
    private fun getRepoPath() = File(androidContext.filesDir, ".ipfs_repo")

    fun isReady() = File(getRepoPath(), "version").exists()

    private fun getBinaryFileByABI(abi: String) = when {
        abi.toLowerCase().startsWith("x86") -> "x86"
        abi.toLowerCase().startsWith("arm") -> "arm"
        else -> "unknown"
    }

    @SuppressLint("SetTextI18n")
    fun download(activity: Activity): String {
        var readTxt = ""
        try {
            downloadFile(activity)
            getBinaryFile().setExecutable(true)

            val exec = run("init")
            exec.waitFor()
            readTxt = exec.inputStream.bufferedReader().readText() + exec.errorStream.bufferedReader().readText()
        } catch (e: FileNotFoundException) {
            System.err.print(e)
        }
        return readTxt
    }

    fun run(cmd: String): Process {
        val env = arrayOf("IPFS_PATH=" + getRepoPath().absoluteFile)
        val command = getBinaryFile().absolutePath + " " + cmd

        try {
            return Runtime.getRuntime().exec(command, env)
        }
        catch (e: Exception) {
            throw e
        }
    }

    private fun downloadFile(activity: Activity) {
        val source = Okio.buffer(Okio.source(activity.assets.open(getBinaryFileByABI(Build.CPU_ABI))))
        val sink = Okio.buffer(Okio.sink(getBinaryFile()))
        while (!source.exhausted()) {
            source.read(sink.buffer(), 1024)
        }
        source.close()
        sink.close()

    }

}
@file:JvmName("Main")

package net.pearx.scraper.bashim

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.tongfei.progressbar.ProgressBar
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.io.File
import java.io.PrintWriter
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val BASH_ENDPOINT = "https://bash.im/"
const val BASH_INDEX_ENDPOINT = BASH_ENDPOINT + "index/{PAGE}"
val JSON = Json {}

fun main(args: Array<String>) {
    val output = File(args[0])
    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy в H:mm").withZone(
        ZoneId.ofOffset(
            "UTC",
            ZoneOffset.ofHours(4)
        )
    ) // UTC offset of the timestamps is +4, you can find it there: https://bash.im/rss/

    println("Getting pages count...")
    val docMain = Jsoup.connect(BASH_ENDPOINT).get()
    val pages = docMain.getElementsByClass("pager__input").first().attr("value").toInt()
    println("Starting scraping $pages pages into ${output.absolutePath}.")
    PrintWriter(output).use { pw ->
        for (i in ProgressBar.wrap((1..pages).toList(), "Scraping")) {
            val doc = Jsoup.connect(BASH_INDEX_ENDPOINT.replace("{PAGE}", i.toString())).get()
            val quotes = doc.getElementsByClass("quote__frame")
            quotes.reverse()
            for (quote in quotes) {
                val id = quote.getElementsByClass("quote__header_permalink").text().substring(1).toInt()
                val date = Instant.from(dateFormat.parse(quote.getElementsByClass("quote__header_date").text()))
                val ratingRaw = quote.getElementsByClass("quote__total").text()
                val rating = if (ratingRaw == "...") null else ratingRaw.toInt()
                val text = quote.getElementsByClass("quote__body").first().toText()

                pw.append(JSON.encodeToString(BashQuote(id, date, rating, text)))
                pw.appendln()
            }
        }
    }
    println("Done scraping.")
}

fun Node.toText(): String = buildString { toText(this) }.trim()

fun Node.toText(appendable: Appendable) {
    if (this is Element && "quote__strips" in classNames())
        return
    if (this is TextNode)
        appendable.append(this.wholeText)
    else if (this is Element && this.tagName() == "br")
        appendable.append(System.lineSeparator())
    if (childNodeSize() > 0) {
        for (ch in childNodes()) ch.toText(appendable)
    }
}
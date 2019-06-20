package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import java.net.URL

/**
 * @since 0.8.0
 * @author sergey
 */
fun HttpContext.url(url: URL) {
    scheme = url.protocol
    if (scheme != "http" && scheme != "https")
        throw IllegalArgumentException("unexpected scheme: $scheme")

    host = url.host ?: throw IllegalArgumentException("unexpected host: $host")

    if (url.port != -1) {
        port = url.port
    }
    path = url.path
}

/**
 *
 * @throws `MalformedURLException` if no protocol is specified, or an
 * unknown protocol is found.
 *
 * @since 0.8.0
 * @author sergey
 */
fun HttpContext.url(url: String) {
    url(java.net.URL(url))
}

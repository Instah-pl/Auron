package io.instah.auron.gradlePlugin.web

import io.instah.auron.gradlePlugin.config.AuronConfigScope

//TODO: Add support for tags like og:title and og:description
fun generateIndexHtml(
    config: AuronConfigScope
): String = """
    <!DOCTYPE html>
    <html>
        <head>
            <script src="${config.configUUID}-main.js"></script>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </head>
        
        <body>
            <canvas id="ComposeTarget"></canvas>
        </body>
    </html>
""".trimIndent()
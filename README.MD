# Bash.im Scraper
A utility written in Kotlin that scraps all the quotes from Russian bash.im website and writes them into a single JSONL file.
# Building
1. Run `./gradlew build` for *nix and `gradlew.bat build` for Windows.
2. Newly built .jar files should be inside the `build/libs` directory.
# Running
`java -jar bashim-scraper-1.0.0-all.jar <output path>`

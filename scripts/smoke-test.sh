#!/bin/bash
# Starts a Paper/Folia server with the built plugin and checks that the plugin loads and enables without errors.
# usage: scripts/smoke-test.sh <paper|folia> <minecraft version> [java binary] [plugin jar]
set -euo pipefail

project="$1"
version="$2"
java="${3:-java}"
jar="${4:-$(ls "$(pwd)"/build/libs/VaroPlugin-*.jar | grep -vE "sources|javadoc" | head -1)}"

dir="$(mktemp -d)"
trap 'rm -rf "$dir"' EXIT
cd "$dir"

url="$(curl -fsS "https://fill.papermc.io/v3/projects/$project/versions/$version/builds" | python3 -c "import sys,json; print(json.load(sys.stdin)[0]['downloads']['server:default']['url'])")"
curl -fsS -o server.jar "$url"
mkdir plugins && cp "$jar" plugins/
echo "eula=true" > eula.txt
printf 'online-mode=false\nlevel-type=flat\ngenerate-structures=false\nview-distance=4\n' > server.properties

mkfifo input
"$java" -Xmx1G -jar server.jar nogui < input > log.txt 2>&1 &
server=$!
exec 3> input

for i in $(seq 1 180); do
    grep -q 'Done (' log.txt && break
    kill -0 "$server" 2>/dev/null || break
    sleep 1
done

# Give scheduled tasks of the plugin a moment to run
sleep 10
echo "stop" >&3
wait "$server" || true

cat log.txt | tail -n 40
grep -q 'Done (' log.txt || { echo "Server did not start"; exit 1; }
grep -q 'Enabling VaroPlugin' log.txt || { echo "Plugin was not enabled"; exit 1; }
if grep -E 'Could not load|Error occurred while enabling|Exception' log.txt | grep -vE 'authenticate'; then
    echo "Errors in server log"
    exit 1
fi
echo "OK: $project $version"

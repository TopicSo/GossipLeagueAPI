HOST="localhost:9000"

echo "=== Delete all games ==="
$ curl -X POST http://$HOST/games/deleteAll

echo ""
echo "=== Delete all players ==="
$ curl -X POST http://$HOST/players/deleteAll

echo ""
echo "=== Add Players ==="
sh addPlayers-RAW27.sh

echo ""
echo "=== Add Games ==="
sh addGames-RAW27.sh
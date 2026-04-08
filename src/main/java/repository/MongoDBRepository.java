package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import domain.GameStatus;
import domain.Position;
import domain.Side;
import domain.Turn;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class MongoDBRepository implements JdbcRepository {

    private static final Map<String, PieceType> NAME_TO_TYPE = Map.ofEntries(
            Map.entry("卒", PieceType.SOLDIER),
            Map.entry("兵", PieceType.SOLDIER),
            Map.entry("象", PieceType.ELEPHANT),
            Map.entry("车", PieceType.CHARIOT),
            Map.entry("車", PieceType.CHARIOT),
            Map.entry("士", PieceType.GUARD),
            Map.entry("楚", PieceType.KING),
            Map.entry("漢", PieceType.KING),
            Map.entry("包", PieceType.CANNON),
            Map.entry("马", PieceType.HORSE),
            Map.entry("馬", PieceType.HORSE),
            Map.entry("．", PieceType.EMPTY)
    );

    private final String URI = "mongodb://localhost:27017";
    private final String DB_NAME = "janggi_db";

    @Override
    public Long getNextId() {
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> counters = database.getCollection("counters");

            Document result = counters.findOneAndUpdate(
                    Filters.eq("_id", "gameId"),
                    Updates.inc("seq", 1L),
                    new FindOneAndUpdateOptions()
                            .upsert(true)
                            .returnDocument(ReturnDocument.AFTER)
            );
            return result.getLong("seq");
        }
    }

    @Override
    public void saveBoard(Long gameId, Turn turn, Map<Position, Piece> board) {
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection("boards");

            Document boardDoc = new Document();
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                String key = position.createStoreKey();

                Document pieceDoc = new Document("type", piece.getName())
                        .append("side", piece.getSide().name());

                boardDoc.append(key, pieceDoc);
            }

            Document finalDoc = new Document("_id", gameId)
                    .append("turn", turn.current().name())
                    .append("layout", boardDoc);

            collection.replaceOne(new Document("_id", gameId), finalDoc, new ReplaceOptions().upsert(true));
        }
    }

    @Override
    public GameStatus findBoard(String gameId) {
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection("boards");

            Long id = Long.parseLong(gameId);
            Document document = collection.find(Filters.eq("_id", id)).first();
            if (document == null) {
                return null;
            }
            Document layout = (Document) document.get("layout");
            Map<Position, Piece> board = new HashMap<>();

            for (int x = 1; x <= 9; x++) {
                for (int y = 1; y <= 10; y++) {
                    Document pieceDoc = (Document) layout.get(x + " " + y);
                    String typeName = pieceDoc.getString("type");
                    String sideName = pieceDoc.getString("side");

                    PieceType pieceType = NAME_TO_TYPE.get(typeName);
                    Side side = Side.valueOf(sideName);

                    board.put(Position.of(x, y), pieceType.create(side));
                }
            }

            String turnSide = document.getString("turn");
            Turn turn = new Turn(Side.valueOf(turnSide));

            return GameStatus.of(id, board, turn);
        }
    }
}

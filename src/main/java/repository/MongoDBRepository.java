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

public class MongoDBRepository implements GameRepository {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "janggi_db";

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

    @Override
    public Long getNextId() {
        try (MongoClient client = MongoClients.create(URI)) {
            MongoCollection<Document> counters = getCollection(client, "counters");
            Document result = counters.findOneAndUpdate(
                    Filters.eq("_id", "gameId"),
                    Updates.inc("seq", 1L),
                    new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
            );
            return result.getLong("seq");
        }
    }

    @Override
    public void deleteBoard(Long gameId) {
        try (MongoClient client = MongoClients.create(URI)) {
            MongoCollection<Document> collection = getCollection(client, "boards");
            collection.deleteOne(Filters.eq("_id", gameId));
        }
    }

    @Override
    public void saveBoard(Long gameId, Turn turn, Map<Position, Piece> board) {
        try (MongoClient client = MongoClients.create(URI)) {
            MongoCollection<Document> collection = getCollection(client, "boards");
            Document doc = createGameDocument(gameId, turn, board);
            collection.replaceOne(Filters.eq("_id", gameId), doc, new ReplaceOptions().upsert(true));
        }
    }

    @Override
    public GameStatus findBoard(String gameId) {
        try (MongoClient client = MongoClients.create(URI)) {
            MongoCollection<Document> collection = getCollection(client, "boards");
            Long id = Long.parseLong(gameId);
            Document document = collection.find(Filters.eq("_id", id)).first();
            return parseGameStatus(id, document);
        }
    }

    private MongoCollection<Document> getCollection(MongoClient client, String name) {
        MongoDatabase database = client.getDatabase(DB_NAME);
        return database.getCollection(name);
    }

    private Document createGameDocument(Long gameId, Turn turn, Map<Position, Piece> board) {
        Document boardDoc = new Document();
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            String key = entry.getKey().createStoreKey();
            Document pieceDoc = createPieceDocument(entry.getValue());
            boardDoc.append(key, pieceDoc);
        }
        return new Document("_id", gameId)
                .append("turn", turn.current().name())
                .append("layout", boardDoc);
    }

    private Document createPieceDocument(Piece piece) {
        return new Document("type", piece.getName()).append("side", piece.getSide().name());
    }

    private GameStatus parseGameStatus(Long id, Document document) {
        if (document == null) {
            return null;
        }
        Document layout = (Document) document.get("layout");
        Map<Position, Piece> board = parseBoard(layout);
        Turn turn = new Turn(Side.valueOf(document.getString("turn")));
        return GameStatus.of(id, board, turn);
    }

    private Map<Position, Piece> parseBoard(Document layout) {
        Map<Position, Piece> board = new HashMap<>();
        for (int x = 1; x <= 9; x++) {
            parseBoardColumn(layout, board, x);
        }
        return board;
    }

    private void parseBoardColumn(Document layout, Map<Position, Piece> board, int x) {
        for (int y = 1; y <= 10; y++) {
            Document pieceDoc = (Document) layout.get(x + " " + y);
            Piece piece = parsePiece(pieceDoc);
            board.put(Position.of(x, y), piece);
        }
    }

    private Piece parsePiece(Document pieceDoc) {
        PieceType type = NAME_TO_TYPE.get(pieceDoc.getString("type"));
        Side side = Side.valueOf(pieceDoc.getString("side"));
        return type.create(side);
    }
}

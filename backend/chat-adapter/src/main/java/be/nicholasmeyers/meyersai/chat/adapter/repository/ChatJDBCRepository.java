package be.nicholasmeyers.meyersai.chat.adapter.repository;

import be.nicholasmeyers.meyersai.chat.repository.ChatQueryRepository;
import be.nicholasmeyers.meyersai.chat.view.ChatMessageView;
import be.nicholasmeyers.meyersai.chat.view.ChatTypeView;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Repository
public class ChatJDBCRepository implements ChatQueryRepository {

    private final NamedParameterJdbcTemplate template;

    public ChatJDBCRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<ChatMessageView> findMessagesByConversationId(UUID conversationId) {
        String query = """
                SELECT conversation_id, content, type, timestamp
                FROM spring_ai_chat_memory
                WHERE conversation_id = :conversation_id
                ORDER BY timestamp ASC
                """;
        return template.query(
                query,
                Map.of("conversation_id", conversationId.toString()),
                this::mapRowChatMessageView
        );
    }

    @Override
    public List<UUID> findAllChatIds() {
        String query = "SELECT DISTINCT conversation_id FROM spring_ai_chat_memory";
        return template.getJdbcOperations().queryForList(
                query,
                String.class
        ).stream().filter(Objects::nonNull).map(UUID::fromString).toList();
    }

    private ChatMessageView mapRowChatMessageView(ResultSet rs, int rowNum) throws SQLException {
        UUID conversationId = UUID.fromString(rs.getString("conversation_id"));
        String content = rs.getString("content");
        String type = rs.getString("type");
        LocalDateTime localDateTime = rs.getTimestamp("timestamp").toLocalDateTime();
        return new ChatMessageView(conversationId, content, ChatTypeView.valueOf(type), localDateTime);
    }
}

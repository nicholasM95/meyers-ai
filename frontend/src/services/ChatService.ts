export interface ChatStreamOptions {
    message: string;
    onChunk: (chunk: string) => void;
    onComplete: () => void;
    onError: (error: Error) => void;
}

export const sendChatMessage = async (options: ChatStreamOptions): Promise<void> => {
    const { message, onChunk, onComplete, onError } = options;

    try {
        const response = await fetch('http://localhost:8080/chat/message', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ message }),
        });

        if (!response.ok) {
            onError(new Error(`HTTP error! status: ${response.status}`));
            return;
        }

        const reader = response.body?.getReader();
        const decoder = new TextDecoder();

        if (!reader) {
            onError(new Error('Response body is not readable'));
            return;
        }

        let buffer = ''; // Buffer for incomplete SSE events

        while (true) {
            const { done, value } = await reader.read();

            if (done) {
                onComplete();
                break;
            }

            // Decode the chunk and add to buffer
            buffer += decoder.decode(value, { stream: true });

            // SSE format: "data: content\n\n"
            // Split by double newline to get complete events
            const parts = buffer.split('\n\n');

            // Keep the last incomplete part in the buffer
            buffer = parts.pop() || '';

            // Process complete events
            for (const part of parts) {
                if (!part.trim()) continue;

                // Extract data from SSE format
                const lines = part.split('\n');
                for (const line of lines) {
                    if (line.startsWith('data: ')) {
                        const data = line.substring(6); // Remove "data: " prefix
                        onChunk(data);
                    } else if (line.startsWith('data:')) {
                        const data = line.substring(5); // Remove "data:" prefix (no space)
                        onChunk(data);
                    }
                }
            }
        }
    } catch (error) {
        onError(error instanceof Error ? error : new Error('Unknown error occurred'));
    }
};
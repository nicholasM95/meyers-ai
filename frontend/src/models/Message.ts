export type Role = 'user' | 'assistant';

export interface Message {
    id: number;
    role: Role;
    content: string;
    timestamp: Date;
}
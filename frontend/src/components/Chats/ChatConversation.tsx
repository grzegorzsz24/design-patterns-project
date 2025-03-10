import { useEffect, useState } from "react";
import { useStompClient, useSubscription } from "react-stomp-hooks";

import ChatModel from "../../models/ChatModel";
import { FC } from "react";
import LoadingSpinner from "../../ui/LoadingSpinner";
import MessageModel from "../../models/MessageModel";
import Messages from "./Messages";
import NewMessage from "./NewMessage";
import OutlineButton from "../../ui/OutlineButton";
import UserModel from "../../models/UserModel";
import UserProfile from "../../ui/UserProfile";
import { getChatMessages } from "../../services/chatService";
import { getUserById } from "../../services/userService";
import { useAppSelector } from "../../store/store";
import { useNotification } from "../../hooks/useNotification";

interface ChatConversationProps {
  currentChat: ChatModel | null;
}

const ChatConversation: FC<ChatConversationProps> = ({ currentChat }) => {
  const stompClient = useStompClient();
  const { userId: loggedInUserId } = useAppSelector((state) => state.user);
  const [user, setUser] = useState<UserModel | null>(null);
  const [messages, setMessages] = useState<MessageModel[]>([]);
  const { showErrorNotification } = useNotification();
  useSubscription(`/user/${loggedInUserId}/queue/messages`, (message) =>
    setMessages((prev) => [...prev, JSON.parse(message.body)]),
  );
  const [isLoading, setIsLoading] = useState(true);

  const fetchMessages = async () => {
    if (!currentChat) return;
    try {
      setIsLoading(true);
      const data = await getChatMessages(currentChat.id);
      if (data.status !== "ok") {
        throw new Error(data.message);
      }
      setMessages(data.data);
    } catch (error) {
      showErrorNotification(error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchUser = async () => {
    const userId = currentChat?.users.find(
      (user) => user !== Number(loggedInUserId),
    );
    if (!currentChat) return;
    try {
      const data = await getUserById(userId as number);
      if (data.status !== "ok") {
        throw new Error(data.message);
      }
      setUser(data.user);
    } catch (error) {
      showErrorNotification(error);
    }
  };

  const sendMessage = (text: string) => {
    const userId = currentChat?.users.find(
      (user) => user !== Number(loggedInUserId),
    );
    if (stompClient) {
      stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({
          senderId: Number(loggedInUserId),
          receiverId: userId as number,
          message: text,
        }),
      });
      setMessages((prev) => [
        ...prev,
        {
          channelId: currentChat?.id as number,
          senderId: Number(loggedInUserId),
          receiverId: userId as number,
          message: text,
          createdAt: new Date().toISOString(),
        },
      ]);
    }
  };

  useEffect(() => {
    fetchUser();
  }, [currentChat]);

  useEffect(() => {
    fetchMessages();
  }, [currentChat]);

  return (
    <div className="flex h-96 flex-col p-2 md:h-full md:p-4 ">
      {!currentChat && (
        <div className=" flex h-full items-center justify-center md:text-lg">
          Kliknij czat aby rozpocząć
        </div>
      )}
      {currentChat && (
        <>
          <div className="border-b-2 px-2 pb-4 dark:border-blue-600 md:px-4">
            {user && (
              <UserProfile
                imageUrl={user.imageUrl}
                firstName={user.firstName}
                lastName={user.lastName}
                nickname={user.nickname}
                size="medium"
              />
            )}
          </div>
          <div className="grow overflow-y-auto py-4">
            {isLoading && <LoadingSpinner small />}
            {!isLoading && messages.length === 0 && (
              <div className="text-md flex h-full flex-col items-center justify-end gap-4">
                <p>Brak wiadomości.</p>
                <OutlineButton
                  onClick={() => {
                    sendMessage("👋");
                  }}
                  size="sm"
                >
                  Pomachaj użytkowinkowi {user?.firstName} 👋
                </OutlineButton>
              </div>
            )}
            {!isLoading && messages.length > 0 && (
              <Messages messages={messages} />
            )}
          </div>
          <NewMessage sendMessage={sendMessage} />
        </>
      )}
    </div>
  );
};

export default ChatConversation;

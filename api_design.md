## API Design



## What should we take care about?

- Authentication
- Sorting and Filtering
- Shortcut endpoint
- Limiting output data, pagination
- Status codes
- Versioning
- Documentation


## Endpoint design

### Websocket

- Get new messages
- Send messages



### RESTful
- Users
  - Get current user
  - Get user by id
  - Get all users
  - Update user by id
  - Delete user by id
- Message
  - Get message by id
- Conversation
  - Get all conversations
  - Get conversation by id
  - Get all message by conversation id (paging by number of last message)
  - Get all members
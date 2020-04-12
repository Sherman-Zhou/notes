# Installation 
1. install erlang
2. install rabbitMq
3. enable management plugin

     rabbitmq-plugins enable rabbitmq_management

4. access console from http://localhost:15672/ using guest/ guest

# AMQP
## advanced message queue protocol
- Security
- Reliability
- Interoperability
- Standard
- Open standard

## elements
- Message Flow: It explains the message life cycle
- Exchanges: It accepts messages from publisher, and then routes to the Message Queues
   -  Name: Usually, server gives its name automatically
   -  Durable: Message Queue remains present or not, depending on whether durable is set or transient is set
   -  Auto-delete: When all queues finish, exchanges are deleted automatically
- Message Queues: It stores messages in memory or disk and delivers messages to the consumers, Weak FIFO
    - Name: Defines the name of Message Queue
    - Durable: If set, the Message Queue can't lose any message
    - Exclusive: If set, the Message Queue will be deleted after connection is closed
    - Auto-delete: If set, the Message Queue is deleted after last consumer has unsubscribed
- Bindings: It specifies the relationship between an exchange and a message queue that tells how to route messages to the right Message Queues

## Functional specifications
1. AMQP messages
    - Content that is a binary data
    - Header
    - Properties
2. Virtual hosts: have own groups of users, exchanges, message queues
3. Exchange types:
    - direct exchange type – amq.direct:  message.routingKey == queue.routingKey
    - fan-out exchange type – amq.fanout: message queue binds to the exchange with no arguments
    - topic exchange type – amq.topic:  queue.routingPattern matches message.routingKey
       news.* :  "*" matches single word 
       news.# matches zero or more words
    - headers exchange type – amq.match

# functionalities

- Ensures that messages are sent and received
- Routes the messages to the correct destinations
- Saves the state of the messages
- Supports multiple transportation protocols (AMQP, MQTT, STOMP, HTTP)
- Supports clustering
- Highly scalable, reliable and available
- Extendible with plugins
- Supports clients for almost any of language
- Large community support also provides commercial support

# routing algorithms
1. Pub-Sub
2. Direct
3. Topic based

# Clustering and High Availability
 20k~50k messages per second one machine
  
 1 million/sec
 https://tanzu.vmware.com/content/blog/rabbitmq-hits-one-million-messages-per-second-on-google-compute-engine 

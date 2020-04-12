# Channel

- FileChannel : File
- DatagramChannel: UDP
- SocketChannel: TCP
- ServerSocketChannel : TCP Server

  Channel vs Stream
  2 way(w and r) : 1 way(w/r)

  ## Scattering Reads

  ## Gathering Writes

  ## Channel to Channel Transfers

# Buffer

- ByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer

1. Write data into the Buffer
2. Call buffer.flip() (write mode to read mode)
3. Read data out of the Buffer
4. Call buffer.clear() or buffer.compact()

# Selector

1. Creating a Selector
   Selector selector = Selector.open();

2. Registering Channels with the Selector(except for FileChannel)
   hannel.configureBlocking(false);
   SelectionKey key = channel.register(selector, SelectionKey.OP_READ);

events:

- SelectionKey.OP_CONNECT
- SelectionKey.OP_ACCEPT
- SelectionKey.OP_READ
- SelectionKey.OP_WRITE

  int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

3.

Thread -> Selector -> N Channels

Channel <-> Buffer
